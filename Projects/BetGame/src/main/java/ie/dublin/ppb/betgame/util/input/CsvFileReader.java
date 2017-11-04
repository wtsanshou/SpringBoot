package ie.dublin.ppb.betgame.util.input;

import java.io.IOException;
import java.util.Optional;

import ie.dublin.ppb.betgame.model.Game;

/**
 * @desc A implement of PpbReader which is used to read data from a csv input
 *       data source source.
 */

public class CsvFileReader implements PpbReader {

	@Override
	public Optional<Game> getData(String path) throws IOException {
		return null;
	}

}
