package ie.dublin.ppb.betgame.util.input;

import java.io.IOException;
import java.util.Optional;

import ie.dublin.ppb.betgame.model.Game;

/**
 * @desc A implement of PpbReader which is used to read data from a HTTP input
 *       data source source.
 */

public class HttpReader implements PpbReader {
	public Optional<Game> getData(String url) throws IOException {
		return null;
	}
}
