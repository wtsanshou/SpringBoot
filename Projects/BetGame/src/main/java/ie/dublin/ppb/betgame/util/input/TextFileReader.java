package ie.dublin.ppb.betgame.util.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import ie.dublin.ppb.betgame.model.Bet;
import ie.dublin.ppb.betgame.model.PpbCurrency;
import ie.dublin.ppb.betgame.model.Game;
import lombok.extern.slf4j.XSlf4j;

/**
 * @desc A implement of PpbReader which is used to read data from a text format
 *       input data source source.
 */

@XSlf4j
public class TextFileReader implements PpbReader {

	private Map<String, PpbCurrency> currencies;

	public TextFileReader(Map<String, PpbCurrency> currencies) {
		this.currencies = currencies;
	}

	/**
	 * @desc read data from the input data source address
	 * @param String
	 *            path - the input data source address
	 * @return Optional<Game> - A game object created from the data source
	 */
	public Optional<Game> getData(String path) throws IOException {

		if (path == null || path.isEmpty() || !path.endsWith(".txt")) {
			log.warn("The file '{}' is not valid", path);
			return Optional.empty();
		}

		File file = new File(path);
		if (!file.exists()) {
			log.warn("The file '{}' is not existed", path);
			return Optional.empty();
		}

		Optional<Game> betGame = getBetsFrom(file);

		return betGame;
	}

	private Optional<Game> getBetsFrom(File file) throws IOException {

		Optional<Game> game = Optional.empty();
		Scanner fileSc = null;
		try {
			fileSc = new Scanner(file);
			String firstLine = fileSc.nextLine();

			String[] titles = firstLine.split(",");
			int columnNum = titles.length;
			log.info("Column number is {}", columnNum);

			game = getBetGameInfo(fileSc, columnNum);
		} catch (FileNotFoundException e) {
			log.error("File not Found Exception: ", e);
			return Optional.empty();
		} catch (Exception e) {
			log.error("File reading Exception: ", e);
			return Optional.empty();
		} finally {
			if (null != fileSc) {
				fileSc.close();
			}
		}

		return game;
	}

	private Optional<Game> getBetGameInfo(Scanner fileSc, int columnNum) throws Exception {

		List<Bet> bets = new ArrayList<>();
		while (fileSc.hasNextLine()) {
			String line = fileSc.nextLine();
			String[] data = line.split(",");
			if(data==null || !trimData(data)) {
				log.warn("A line has empty element");
				return Optional.empty();
			}

			if (columnNum != data.length) {
				log.warn("The file content is illegal");
				return Optional.empty();
			}

			try {
				Bet bet = Bet.builder().betId(data[0]).betTimeStamp(Long.valueOf(data[1]))
						.selectionId(Long.valueOf(data[2])).selectionName(data[3]).stake(Double.valueOf(data[4]))
						.price(Double.valueOf(data[5])).currency(data[6]).build();
				if (!currencies.containsKey(bet.getCurrency())) {
					log.error("The Currency '{}' is invalid", bet.getCurrency());
					return Optional.empty();
				}
				bets.add(bet);
			} catch (NumberFormatException e) {
				log.error("The content of the file is invalid", e);
				return Optional.empty();
			}
		}
		return Optional.of(new Game(bets));
	}

	private boolean trimData(String[] data) {
		for (int i = 0; i < data.length; i++) {
			if(data[i].isEmpty()) {
				log.warn("the {}th element is empty", i+1);
				return false;
			}
			data[i] = data[i].trim();
		}
		return true;
	}

}
