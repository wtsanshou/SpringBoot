package ie.dublin.ppb.betgame.util.output;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.junit.BeforeClass;
import org.junit.Test;
import ie.dublin.ppb.betgame.TestConfigers;
import ie.dublin.ppb.betgame.model.Bet;
import ie.dublin.ppb.betgame.model.Game;
import ie.dublin.ppb.betgame.model.Group;
import ie.dublin.ppb.betgame.model.PpbCurrency;
import ie.dublin.ppb.betgame.model.Report;
import ie.dublin.ppb.betgame.statistic.MyAnalyst;
import ie.dublin.ppb.betgame.statistic.PpbAnalyst;
import ie.dublin.ppb.betgame.util.input.PpbReader;
import ie.dublin.ppb.betgame.util.input.TextFileReader;

public class ConsolePrinter_test {
	private static PpbReader reader;
	private static PpbAnalyst analyst;
	private static Optional<Game> game;
	private static PpbPrinter printer;

	private static final String path = TestConfigers.TEST1;

	private Function<Bet, List<Object>> groupBySelectionName = TestConfigers.groupBySelectionName;
	private Function<Bet, List<Object>> groupByCurrency = TestConfigers.groupByCurrency;
	private Function<Bet, List<Object>> groupBySelectionNameAndCurrency = TestConfigers.groupBySelectionNameAndCurrency;
	private Function<Bet, List<Object>> multipleGroupByers = TestConfigers.multipleGroupByers;

	private Comparator<? super Group> totalStacksComparator = TestConfigers.decendTotalStacksComparator;
	private Comparator<? super Group> totalPayoutComparator = TestConfigers.decendTotalPayoutComparator;

	private static PpbCurrency currencyGBP = TestConfigers.currencyGBP;
	private static PpbCurrency currencyEUR = TestConfigers.currencyEUR;

	private List<String> titlesWithSelectionName = TestConfigers.titlesWithSelectionName;
	private List<String> titlesWithCurrency = TestConfigers.titlesWithCurrency;
	private List<String> titlesWithSelectionNameAndCurrency = TestConfigers.titlesWithSelectionNameAndCurrency;
	private List<String> titlesWithMultipleGroupByers = TestConfigers.titlesWithMultipleGroupByers;

	private static Map<String, PpbCurrency> currencies = TestConfigers.currencies;
	
	private List<Group> groups1 = analyst.analyze(game, groupBySelectionName, totalPayoutComparator);
	private List<Group> groups2 = analyst.analyze(game, groupByCurrency, totalStacksComparator);
	private List<Group> groups3 = analyst.analyze(game, groupBySelectionNameAndCurrency, totalPayoutComparator);
	private List<Group> groups4 = analyst.analyze(game, multipleGroupByers, totalStacksComparator);

	@BeforeClass
	public static void setup() throws Exception {
		currencies.put("GBP", currencyGBP);
		currencies.put("EUR", currencyEUR);
		reader = new TextFileReader(currencies);
		analyst = new MyAnalyst(currencies);
		game = reader.getData(path);
		printer = new ConsolePrinter();
	}

	@Test
	public void returnZero_whenConsolePrintAnEmptyGroups() {
		Optional<Report> nullReport1 = analyst.getReport(null, titlesWithSelectionName);
		assertEquals(0, printer.print(nullReport1));

		Optional<Report> nullReport2 = analyst.getReport(null, new ArrayList<String>());
		assertEquals(0, printer.print(nullReport2));

		Optional<Report> nullReport3 = analyst.getReport(null, null);
		assertEquals(0, printer.print(nullReport3));

		List<Group> emptyGroup = new ArrayList<>();

		Optional<Report> emptyReport1 = analyst.getReport(emptyGroup, titlesWithSelectionName);
		assertEquals(0, printer.print(emptyReport1));

		Optional<Report> emptyReport2 = analyst.getReport(emptyGroup, new ArrayList<String>());
		assertEquals(0, printer.print(emptyReport2));

		Optional<Report> emptyReport3 = analyst.getReport(emptyGroup, null);
		assertEquals(0, printer.print(emptyReport3));
	}

	@Test
	public void shouldPrintCorrectRowsOfData_whenConsolePrintTheReport() {
		Optional<Report> report1 = analyst.getReport(groups1, titlesWithSelectionName);
		assertEquals(4, printer.print(report1));

		Optional<Report> report2 = analyst.getReport(groups2, titlesWithCurrency);
		assertEquals(2, printer.print(report2));

		Optional<Report> report3 = analyst.getReport(groups3, titlesWithSelectionNameAndCurrency);
		assertEquals(8, printer.print(report3));

		Optional<Report> report4 = analyst.getReport(groups4, titlesWithMultipleGroupByers);
		assertEquals(19, printer.print(report4));
	}

}
