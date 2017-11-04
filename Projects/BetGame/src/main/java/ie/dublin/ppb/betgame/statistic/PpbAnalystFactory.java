package ie.dublin.ppb.betgame.statistic;

import java.util.Map;

import ie.dublin.ppb.betgame.model.PpbCurrency;

public interface PpbAnalystFactory {
	public PpbAnalyst CreateAnalyst(Map<String, PpbCurrency> currencies) ;
}
