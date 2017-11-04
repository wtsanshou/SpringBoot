package ie.dublin.ppb.betgame.util.input;

import java.io.IOException;
import java.util.Optional;

import ie.dublin.ppb.betgame.model.Game;

/**
 * @desc read data from a input data source source.
 */

public interface PpbReader {
	/**
	 * @desc read data from the input data source address
	 * @param String
	 *            path - the input data source address
	 * @return Optional<Game> - A game object created from the data source
	 */
	public Optional<Game> getData(String path) throws IOException;
}
