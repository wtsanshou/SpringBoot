package ie.dublin.ppb.betgame.statistic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import ie.dublin.ppb.betgame.model.Bet;
import ie.dublin.ppb.betgame.model.Game;
import ie.dublin.ppb.betgame.model.Group;
import ie.dublin.ppb.betgame.model.PpbCurrency;
import ie.dublin.ppb.betgame.model.Report;
import ie.dublin.ppb.betgame.model.StringGroup;
import lombok.extern.slf4j.XSlf4j;

/**
 * @desc A implement of PpbAnalyst which is used to analyze input data and
 *       create a report from the analysis result.
 */
@XSlf4j
public class MyAnalyst implements PpbAnalyst {
	private Map<String, PpbCurrency> currencies;
	private final String ERURO = "EUR";
	private final int PRECISION = 2;

	public MyAnalyst(Map<String, PpbCurrency> currencies) {
		this.currencies = currencies;
	}

	/**
	 * @desc group and sort the input data
	 * @param Optional<Game>
	 *            game - the bets of the input data
	 * @param Function<Bet,
	 *            List<Object>> groupByers - the data is grouped by the groupByers
	 * @param Comparator<?
	 *            super Group> comparator - the groups are sorted by the comparator
	 * @return Optional<Game> - A game object created from the data source
	 */
	public List<Group> analyze(Optional<Game> game, Function<Bet, List<Object>> groupByers,
			Comparator<? super Group> customerComparator) {
		if (game == null || !game.isPresent()) {
			log.warn("The game data is empty");
			return new ArrayList<Group>();
		}
		if (groupByers == null) {
			log.warn("The groupByers is null");
			return new ArrayList<Group>();
		}
		if (customerComparator == null) {
			log.warn("The customerComparator is null");
			return new ArrayList<Group>();
		}
		List<Group> unsortedGroups = groupBy(game, groupByers);

		List<Group> sortedGroups = unsortedGroups.stream().sorted(customerComparator).collect(Collectors.toList());
		return sortedGroups;
	}

	/**
	 * @desc Create a writable report from the grouped and sorted data
	 * @param List<Group>
	 *            groups - the grouped and sorted data of the input data
	 * @param List<String>
	 *            titles - the titles of the report data
	 * @return Optional<Report> - A writable report
	 */
	public Optional<Report> getReport(List<Group> groups, List<String> titles) {
		if (groups == null || groups.isEmpty()) {
			log.warn("The grouped data is empty");
			return Optional.empty();
		}
		if (titles == null || titles.isEmpty()) {
			log.warn("The titles are empty");
			return Optional.empty();
		}
		if (titles.size() != groups.get(0).getGroupByers().size() + 3) {
			log.warn("The titles size are not matched to data");
			return Optional.empty();
		}

		List<StringGroup> strGroups = converToStringGroup(groups);
		Report report = Report.builder().title(titles).groups(strGroups).build();
		return Optional.of(report);
	}

	private List<Group> groupBy(Optional<Game> game, Function<Bet, List<Object>> groupByers) {
		Map<List<Object>, List<Bet>> groups = game.get().getBets().stream()
				.collect(Collectors.groupingBy(groupByers, Collectors.toList()));

		List<Group> unsortedGroups = new ArrayList<>();

		for (List<Object> groupbyers : groups.keySet()) {
			List<String> groupBys = new ArrayList<>();
			for (Object groupbyer : groupbyers) {
				groupBys.add(groupbyer.toString());
			}
			List<Bet> aGroup = groups.get(groupbyers);
			Group group = createGroup(groupBys, aGroup);
			unsortedGroups.add(group);
		}
		return unsortedGroups;
	}

	private Group createGroup(List<String> groupBys, List<Bet> aGroup) {
		int count = aGroup.size();
		String currency = getCurrencyFrom(aGroup);
		double totalStack = aGroup.stream().collect(Collectors.summingDouble(bet -> getStacks(bet)));
		double totalPayout = aGroup.stream().collect(Collectors.summingDouble(bet -> getPayout(bet)));
		return Group.builder().groupByers(groupBys).noOfBets(count).totalStacks(totalStack).totalPayout(totalPayout)
				.currency(currency).build();
	}

	private Double getStacks(Bet bet) {
		double payment = bet.getStake();
		return (bet.getCurrency().equals(ERURO)) ? payment
				: payment * currencies.get(bet.getCurrency()).getCurrencyRate();
	}

	private String getCurrencyFrom(List<Bet> aGroup) {
		if (aGroup.isEmpty())
			return "";
		for (int i = 1; i < aGroup.size(); ++i) {
			if (!aGroup.get(i).getCurrency().equals(aGroup.get(i - 1).getCurrency()))
				return ERURO;
		}
		return aGroup.get(0).getCurrency();
	}

	private Double getPayout(Bet bet) {
		double payment = bet.getPrice() * bet.getStake();
		return (bet.getCurrency().equals(ERURO)) ? payment
				: payment * currencies.get(bet.getCurrency()).getCurrencyRate();
	}

	private List<StringGroup> converToStringGroup(List<Group> groups) {
		List<StringGroup> strGroups = new ArrayList<>();
		for (Group group : groups) {
			String totalStacksWithCurrencySign = attachCurrencySign(group, group.getTotalStacks());
			String totalPayoutWithCurrencySign = attachCurrencySign(group, group.getTotalPayout());
			StringGroup strGroup = StringGroup.builder().groupByers(group.getGroupByers())
					.noOfBets(String.valueOf(group.getNoOfBets())).totalStacks(totalStacksWithCurrencySign)
					.totalPayout(totalPayoutWithCurrencySign).build();
			strGroups.add(strGroup);
		}
		return strGroups;
	}

	private String attachCurrencySign(Group group, double value) {
		String currencyName = group.getCurrency();
		return (currencyName == null || currencyName.isEmpty() || (currencyName.equals(ERURO)))
				? currencies.get(ERURO).getSymbol() + roundToString(value, PRECISION)
				: currencies.get(currencyName).getSymbol()
						+ roundToString((value / currencies.get(currencyName).getCurrencyRate()), PRECISION);
	}

	/**
	 * @desc set the precision
	 * @param double
	 *            value - the setted double value
	 * @param int
	 *            precision - the precise digital number after dot
	 * @return String - A round String format precise value
	 */
	private String roundToString(double value, int precision) {
		int prec = 1;
		for (int i = 0; i < precision; ++i)
			prec *= 10;
		return String.valueOf(Math.floor((value * prec) + 0.5) / prec);
	}

}
