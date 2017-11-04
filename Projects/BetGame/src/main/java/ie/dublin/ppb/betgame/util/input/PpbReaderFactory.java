package ie.dublin.ppb.betgame.util.input;

import java.util.Map;
import ie.dublin.ppb.betgame.model.PpbCurrency;

public interface PpbReaderFactory {
	public PpbReader createReader(Map<String, PpbCurrency> currencies);
}
