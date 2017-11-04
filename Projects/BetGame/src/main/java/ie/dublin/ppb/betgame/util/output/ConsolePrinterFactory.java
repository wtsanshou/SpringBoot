package ie.dublin.ppb.betgame.util.output;

public class ConsolePrinterFactory implements PpbPrinterFactory{

	@Override
	public PpbPrinter createPrinter() {
		return new ConsolePrinter();
	}

}
