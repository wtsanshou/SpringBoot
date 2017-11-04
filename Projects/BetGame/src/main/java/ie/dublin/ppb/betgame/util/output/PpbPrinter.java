package ie.dublin.ppb.betgame.util.output;

import java.util.Optional;
import ie.dublin.ppb.betgame.model.Report;

/**
 * @desc It is used to output a report.
 */

public interface PpbPrinter {
	public int print(Optional<Report> report);
	public void printWelcom();
}
