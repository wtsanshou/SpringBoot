package ie.dublin.ppb.betgame;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ie.dublin.ppb.betgame.model.Bet;
import ie.dublin.ppb.betgame.model.Game;
import ie.dublin.ppb.betgame.model.Group;
import ie.dublin.ppb.betgame.model.PpbCurrency;
import ie.dublin.ppb.betgame.model.Report;
import ie.dublin.ppb.betgame.statistic.PpbAnalystFactory;
import ie.dublin.ppb.betgame.statistic.MyAnalystFactory;
import ie.dublin.ppb.betgame.statistic.PpbAnalyst;
import ie.dublin.ppb.betgame.util.input.PpbReader;
import ie.dublin.ppb.betgame.util.input.PpbReaderFactory;
import ie.dublin.ppb.betgame.util.input.TextFileReaderFactory;
import ie.dublin.ppb.betgame.util.output.ConsolePrinterFactory;
import ie.dublin.ppb.betgame.util.output.PpbPrinter;
import ie.dublin.ppb.betgame.util.output.PpbPrinterFactory;
import lombok.extern.slf4j.XSlf4j;

/**
 * @desc this class will start the application. The application tries to read
 *       bets data, group and sort the data, and output a result report
 * @input a legal file path for file reading or url for http request reading
 * @output sorted groupByers, Number of Bets, Total Stakes and Total Payout as a
 *         report.
 * @exit enter "exit" to exit
 * @author Tao Wei
 */

@XSlf4j
@SpringBootApplication
public class BetGameApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BetGameApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("Starting...");

		// XXX different grouping and sorting strategies could be found in the Class
		// "TestConfigers"
		// Customer configurable groupByers, here group by SelectionName and Currency
		Function<Bet, List<Object>> groupBySelectionNameAndCurrency = wr -> Arrays.<Object>asList(wr.getSelectionName(),
				wr.getCurrency());

		// Customer configurable comparator, here sorted by TotalPayout
		Comparator<? super Group> totalPayoutComparator = (Group g1, Group g2) -> {
			return Double.compare(g2.getTotalPayout(), g1.getTotalPayout());
		};

		List<String> titlesWithSelectionNameAndCurrency = Arrays.asList("SelectionName", "Currency", "Number of Bets",
				"Total Stacks", "Total Payout");

		// Customer configurable currencies
		Map<String, PpbCurrency> currencies = configCurrencies();

		// Extensible Reader
		PpbReaderFactory textFileReaderFactory = new TextFileReaderFactory();
		PpbReader textReader = textFileReaderFactory.createReader(currencies);

		// Extensible Analyst
		PpbAnalystFactory myAnalystFactory = new MyAnalystFactory();
		PpbAnalyst analyst = myAnalystFactory.CreateAnalyst(currencies);

		// Extensible Printer. Creating file is different with console print. Creating
		// file needs to know file name and stored address
		PpbPrinterFactory consolePrinterFactory = new ConsolePrinterFactory();
		PpbPrinter consolePrinter = consolePrinterFactory.createPrinter();

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String path = "";
		consolePrinter.printWelcom();

		// here is the main logic
		while (!(path = sc.nextLine()).equalsIgnoreCase("exit")) {
			log.info("The file '{}' is reading", path);

			Optional<Game> game = readData(textReader, path);

			Optional<Report> report = analyzeData(analyst, game, groupBySelectionNameAndCurrency, totalPayoutComparator,
					titlesWithSelectionNameAndCurrency);

			consolePrinter.print(report);

			log.info("The file '{}' is finished", path);
			consolePrinter.printWelcom();
		}
	}

	/**
	 * @desc read data from input path
	 * @param PpbReader
	 *            reader - the reader used to read the data
	 * @param String
	 *            path - the data source address
	 * @return Optional<Game> - A game object created from the data source
	 */
	private Optional<Game> readData(PpbReader reader, String path) {
		Optional<Game> game = Optional.empty();
		try {
			game = reader.getData(path);
		} catch (IOException e) {
			log.error("IOException during the data reading fiel '{}'", path);
		}
		return game;
	}

	/**
	 * @desc analyze the data and create a report from the analysis result
	 * @param Function<Bet,
	 *            List<Object>> groupByers - the data is grouped by the groupByers
	 * @param Comparator<?
	 *            super Group> comparator - the groups are sorted by the comparator
	 * @return Optional<Report> - A writable report
	 */
	private Optional<Report> analyzeData(PpbAnalyst analyst, Optional<Game> game,
			Function<Bet, List<Object>> groupByers, Comparator<? super Group> comparator, List<String> titles) {
		List<Group> groups = analyst.analyze(game, groupByers, comparator);
		Optional<Report> report = analyst.getReport(groups, titles);
		return report;
	}

	/**
	 * @desc maps currency name to a Currency ojbect which includes currency name,
	 *       currency rate, and currency symbol
	 * @return Map<String, PpbCurrency> - A map of currency name and Currency
	 *         ojbect.
	 */
	private static Map<String, PpbCurrency> configCurrencies() {
		Map<String, PpbCurrency> currencies = new HashMap<>();
		// XXX: Euro is treated as base currency. Could change the Exchange rate or add
		// more currency here. The tests are using the following currencies configuration
		PpbCurrency currencyGBP = PpbCurrency.builder().name("GBP").currencyRate(1.14).symbol("£").build();
		PpbCurrency currencyEUR = PpbCurrency.builder().name("EUR").currencyRate(1.0).symbol("€").build();
		currencies.put("GBP", currencyGBP);
		log.info("Add currency '{}'", currencyGBP);
		currencies.put("EUR", currencyEUR);
		log.info("Add currency '{}'", currencyEUR);
		return currencies;
	}

}
