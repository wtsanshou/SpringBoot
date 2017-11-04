package ie.dublin.ppb.betgame;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import ie.dublin.ppb.betgame.model.Bet;
import ie.dublin.ppb.betgame.model.Group;
import ie.dublin.ppb.betgame.model.PpbCurrency;

public class TestConfigers {
	public static final String TEST1 = "app/testFiles/test1.txt";
	
	public static final Function<Bet, List<Object>> groupBySelectionName = wr -> Arrays.<Object>asList(wr.getSelectionName());
	public static final Function<Bet, List<Object>> groupByCurrency = wr -> Arrays.<Object>asList(wr.getCurrency());
	public static final Function<Bet, List<Object>> groupBySelectionNameAndCurrency = wr -> Arrays
			.<Object>asList(wr.getSelectionName(), wr.getCurrency());
	public static final Function<Bet, List<Object>> multipleGroupByers = wr -> Arrays.<Object>asList(wr.getSelectionName(),
			wr.getCurrency(), wr.getPrice());

	public static final Comparator<? super Group> decendTotalStacksComparator = (Group g1, Group g2) -> {
		return Double.compare(g2.getTotalStacks(), g1.getTotalStacks());
	};
	public static final Comparator<? super Group> ascendTotalStacksComparator = (Group g1, Group g2) -> {
		return Double.compare(g1.getTotalStacks(), g2.getTotalStacks());
	};
	
	public static final Comparator<? super Group> decendTotalPayoutComparator = (Group g1, Group g2) -> {
		return Double.compare(g2.getTotalPayout(), g1.getTotalPayout());
	};
	public static final Comparator<? super Group> ascendTotalPayoutComparator = (Group g1, Group g2) -> {
		return Double.compare(g1.getTotalPayout(), g2.getTotalPayout());
	};

	public static final List<String> titlesWithSelectionName = Arrays.asList("SelectionName", "No of bets", "Total Stack",
			"Total Payout");
	public static final List<String> titlesWithCurrency = Arrays.asList("Currency", "No of bets", "Total Stack", "Total Payout");
	public static final List<String> titlesWithSelectionNameAndCurrency = Arrays.asList("SelectionName", "Currency", "No of bets",
			"Total Stack", "Total Payout");
	public static final List<String> titlesWithMultipleGroupByers = Arrays.asList("SelectionName", "Currency", "Price",
			"No of bets", "Total Stack", "Total Payout");
	
	public static final PpbCurrency currencyGBP = PpbCurrency.builder().name("GBP").currencyRate(1.14).symbol("£").build();
	public static final PpbCurrency currencyEUR = PpbCurrency.builder().name("EUR").currencyRate(1.0).symbol("€").build();
	public static final Map<String, PpbCurrency> currencies = new HashMap<>();
}
