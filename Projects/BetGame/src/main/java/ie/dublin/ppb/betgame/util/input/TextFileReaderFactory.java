package ie.dublin.ppb.betgame.util.input;

import java.util.Map;
import ie.dublin.ppb.betgame.model.PpbCurrency;

public class TextFileReaderFactory implements PpbReaderFactory{

	@Override
	public PpbReader createReader(Map<String, PpbCurrency> currencies) {
		return new TextFileReader(currencies);
	}

}
