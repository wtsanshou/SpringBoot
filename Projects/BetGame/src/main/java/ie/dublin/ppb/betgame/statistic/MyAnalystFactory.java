package ie.dublin.ppb.betgame.statistic;

import java.util.Map;

import ie.dublin.ppb.betgame.model.PpbCurrency;

public class MyAnalystFactory implements PpbAnalystFactory{

	@Override
	public PpbAnalyst CreateAnalyst(Map<String, PpbCurrency> currencies) {
		return new MyAnalyst(currencies);
	}

}
