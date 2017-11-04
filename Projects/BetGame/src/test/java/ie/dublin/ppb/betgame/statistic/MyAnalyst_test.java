package ie.dublin.ppb.betgame.statistic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.junit.BeforeClass;
import org.junit.Test;

import ie.dublin.ppb.betgame.TestConfigers;
import ie.dublin.ppb.betgame.model.Bet;
import ie.dublin.ppb.betgame.model.Game;
import ie.dublin.ppb.betgame.model.Group;
import ie.dublin.ppb.betgame.model.PpbCurrency;
import ie.dublin.ppb.betgame.model.Report;
import ie.dublin.ppb.betgame.model.StringGroup;
import ie.dublin.ppb.betgame.util.input.PpbReader;
import ie.dublin.ppb.betgame.util.input.TextFileReader;

public class MyAnalyst_test {
	private static PpbReader reader;
	private static PpbAnalyst analyst;
	private static Optional<Game> game;

	private static final String path = TestConfigers.TEST1;

	private Function<Bet, List<Object>> groupBySelectionName = TestConfigers.groupBySelectionName;
	private Function<Bet, List<Object>> groupByCurrency = TestConfigers.groupByCurrency;
	private Function<Bet, List<Object>> groupBySelectionNameAndCurrency = TestConfigers.groupBySelectionNameAndCurrency;
	private Function<Bet, List<Object>> multipleGroupByers = TestConfigers.multipleGroupByers;

	private Comparator<? super Group> decendTotalStacksComparator = TestConfigers.decendTotalStacksComparator;
	private Comparator<? super Group> decendTotalPayoutComparator = TestConfigers.decendTotalPayoutComparator;
	private Comparator<? super Group> ascendTotalStacksComparator = TestConfigers.ascendTotalStacksComparator;
	private Comparator<? super Group> ascendTotalPayoutComparator = TestConfigers.ascendTotalPayoutComparator;

	private List<String> titlesWithSelectionName = TestConfigers.titlesWithSelectionName;
	private List<String> titlesWithCurrency = TestConfigers.titlesWithCurrency;
	private List<String> titlesWithSelectionNameAndCurrency = TestConfigers.titlesWithSelectionNameAndCurrency;
	private List<String> titlesWithMultipleGroupByers = TestConfigers.titlesWithMultipleGroupByers;

	private static PpbCurrency currencyGBP = TestConfigers.currencyGBP;
	private static PpbCurrency currencyEUR = TestConfigers.currencyEUR;
	private static Map<String, PpbCurrency> currencies = TestConfigers.currencies;

	@BeforeClass
	public static void setup() throws Exception {
		currencies.put("GBP", currencyGBP);
		currencies.put("EUR", currencyEUR);
		reader = new TextFileReader(currencies);
		game = reader.getData(path);
		analyst = new MyAnalyst(currencies);
	}

	@Test
	public void returnEmptyBets_whenAnalyseAnEmptyGame() {
		Optional<Game> emptyGame = Optional.empty();
		assertGroupedAndTotalPayoutSortedBets(emptyGame, groupBySelectionName, decendTotalPayoutComparator, 0, 1, 1);
		assertGroupedAndTotalStacksSortedBets(emptyGame, groupByCurrency, decendTotalStacksComparator, 0, 1, 1);

		assertGroupedAndTotalPayoutSortedBets(emptyGame, groupBySelectionNameAndCurrency, decendTotalPayoutComparator,
				0, 2, 1);
		assertGroupedAndTotalStacksSortedBets(emptyGame, groupBySelectionNameAndCurrency, decendTotalStacksComparator,
				0, 2, 1);

		assertGroupedAndTotalPayoutSortedBets(null, groupBySelectionName, decendTotalPayoutComparator, 0, 1, 1);
		assertGroupedAndTotalStacksSortedBets(null, groupByCurrency, decendTotalStacksComparator, 0, 1, 1);

		assertGroupedAndTotalPayoutSortedBets(null, groupBySelectionNameAndCurrency, decendTotalPayoutComparator, 0, 2,
				1);
		assertGroupedAndTotalStacksSortedBets(null, groupBySelectionNameAndCurrency, decendTotalStacksComparator, 0, 2,
				1);
	}

	@Test
	public void returnEmptyBets_whenAnalyseAnEmptyGrroupByers() {
		assertGroupedAndTotalPayoutSortedBets(game, null, decendTotalPayoutComparator, 0, 1, 1);
		assertGroupedAndTotalStacksSortedBets(game, null, decendTotalStacksComparator, 0, 1, 1);
	}

	@Test
	public void returnEmptyBets_whenAnalyseAnEmptyComparator() {
		assertGroupedAndTotalPayoutSortedBets(game, groupBySelectionName, null, 0, 1, 1);
		assertGroupedAndTotalStacksSortedBets(game, groupBySelectionNameAndCurrency, null, 0, 2, 1);
	}

	@Test
	public void returnGroupedAndSortedBets_whenGroupByOneGroupbyerSortedByTotalPayoutOrTotalStack() {
		assertGroupedAndTotalPayoutSortedBets(game, groupBySelectionName, decendTotalPayoutComparator, 4, 1, 1);
		assertGroupedAndTotalPayoutSortedBets(game, groupBySelectionName, ascendTotalPayoutComparator, 4, 1, -1);

		assertGroupedAndTotalStacksSortedBets(game, groupBySelectionName, decendTotalStacksComparator, 4, 1, 1);
		assertGroupedAndTotalStacksSortedBets(game, groupBySelectionName, ascendTotalStacksComparator, 4, 1, -1);

		assertGroupedAndTotalPayoutSortedBets(game, groupByCurrency, decendTotalPayoutComparator, 2, 1, 1);
		assertGroupedAndTotalPayoutSortedBets(game, groupByCurrency, ascendTotalPayoutComparator, 2, 1, -1);

		assertGroupedAndTotalStacksSortedBets(game, groupByCurrency, decendTotalStacksComparator, 2, 1, 1);
		assertGroupedAndTotalStacksSortedBets(game, groupByCurrency, ascendTotalStacksComparator, 2, 1, -1);

	}

	@Test
	public void returnGroupedAndSortedBets_whenGroupByTwoGroupbyersSortedByTotalPayout() {
		assertGroupedAndTotalPayoutSortedBets(game, groupBySelectionNameAndCurrency, decendTotalPayoutComparator, 8, 2,
				1);
		assertGroupedAndTotalPayoutSortedBets(game, groupBySelectionNameAndCurrency, ascendTotalPayoutComparator, 8, 2,
				-1);

		assertGroupedAndTotalStacksSortedBets(game, groupBySelectionNameAndCurrency, decendTotalStacksComparator, 8, 2,
				1);
		assertGroupedAndTotalStacksSortedBets(game, groupBySelectionNameAndCurrency, ascendTotalStacksComparator, 8, 2,
				-1);
	}

	@Test
	public void returnGroupedAndSortedBets_whenGroupByMultipleGroupbyersSortedByTotalPayout() {
		assertGroupedAndTotalPayoutSortedBets(game, multipleGroupByers, decendTotalPayoutComparator, 19, 3, 1);
		assertGroupedAndTotalPayoutSortedBets(game, multipleGroupByers, ascendTotalPayoutComparator, 19, 3, -1);

		assertGroupedAndTotalStacksSortedBets(game, multipleGroupByers, decendTotalStacksComparator, 19, 3, 1);
		assertGroupedAndTotalStacksSortedBets(game, multipleGroupByers, ascendTotalStacksComparator, 19, 3, -1);
	}

	@Test
	public void returnEmptyReport_whenCreateAReportWithEmptyTitles() {
		List<String> emptyTitle = new ArrayList<String>();
		assertReportTitles(game, groupBySelectionName, decendTotalPayoutComparator, emptyTitle, false, 0);
		assertReportTitles(game, groupByCurrency, decendTotalPayoutComparator, emptyTitle, false, 0);
		assertReportTitles(game, groupBySelectionNameAndCurrency, decendTotalPayoutComparator, emptyTitle, false, 0);
		assertReportTitles(game, multipleGroupByers, decendTotalPayoutComparator, emptyTitle, false, 0);

		assertReportTitles(game, groupBySelectionName, decendTotalPayoutComparator, null, false, 0);
		assertReportTitles(game, groupByCurrency, decendTotalPayoutComparator, null, false, 0);
		assertReportTitles(game, groupBySelectionNameAndCurrency, decendTotalPayoutComparator, null, false, 0);
		assertReportTitles(game, multipleGroupByers, decendTotalPayoutComparator, null, false, 0);
	}

	@Test
	public void returnEmptyReport_whenUseWrongTitleSize() {

		assertReportTitles(game, groupBySelectionName, decendTotalPayoutComparator, titlesWithSelectionNameAndCurrency,
				false, titlesWithSelectionName.size());
		assertReportTitles(game, groupBySelectionName, decendTotalPayoutComparator, titlesWithMultipleGroupByers, false,
				titlesWithSelectionName.size());

		assertReportTitles(game, groupBySelectionNameAndCurrency, decendTotalPayoutComparator, titlesWithSelectionName,
				false, titlesWithSelectionNameAndCurrency.size());
		assertReportTitles(game, groupBySelectionNameAndCurrency, decendTotalPayoutComparator,
				titlesWithMultipleGroupByers, false, titlesWithSelectionNameAndCurrency.size());

		assertReportTitles(game, multipleGroupByers, decendTotalPayoutComparator, titlesWithCurrency, false,
				titlesWithMultipleGroupByers.size());
		assertReportTitles(game, multipleGroupByers, decendTotalPayoutComparator, titlesWithSelectionNameAndCurrency,
				false, titlesWithMultipleGroupByers.size());
	}

	@Test
	public void returnReportWithTitle_whenCreateAReportFromGroups() {
		assertReportTitles(game, groupBySelectionName, decendTotalPayoutComparator, titlesWithSelectionName, true,
				titlesWithSelectionName.size());
		assertReportTitles(game, groupByCurrency, decendTotalPayoutComparator, titlesWithCurrency, true,
				titlesWithCurrency.size());
		assertReportTitles(game, groupBySelectionNameAndCurrency, decendTotalPayoutComparator,
				titlesWithSelectionNameAndCurrency, true, titlesWithSelectionNameAndCurrency.size());
		assertReportTitles(game, multipleGroupByers, decendTotalPayoutComparator, titlesWithMultipleGroupByers, true,
				titlesWithMultipleGroupByers.size());
	}

	@Test
	public void returnEmptyReport_whenCreateAReportWithEmptyGroups() {
		List<Group> groups = new ArrayList<>();
		assertReportExist(groups, titlesWithSelectionName, false);
		assertReportExist(groups, titlesWithCurrency, false);
		assertReportExist(groups, titlesWithSelectionNameAndCurrency, false);
		assertReportExist(groups, titlesWithMultipleGroupByers, false);

		assertReportExist(null, titlesWithSelectionName, false);
		assertReportExist(null, titlesWithCurrency, false);
		assertReportExist(null, titlesWithSelectionNameAndCurrency, false);
		assertReportExist(null, titlesWithMultipleGroupByers, false);
	}

	@Test
	public void theMaxAndMinValuseShouldBeCorrect_AfterGroupBySelectionNameAndSortByTotalPayout() {
		assertReportGroupsSortedByTotalPayout(game, groupBySelectionName, decendTotalPayoutComparator, titlesWithSelectionName, 4,
				"€183.86", "€139.12");
		assertReportGroupsSortedByTotalPayout(game, groupBySelectionName, ascendTotalPayoutComparator, titlesWithSelectionName, 4,
				"€139.12", "€183.86");
	}

	@Test
	public void theMaxAndMinValuseShouldBeCorrect_AfterGroupBySelectionNameAndSortByTotalStacks() {
		assertReportGroupsSortedByTotalStacks(game, groupBySelectionName, decendTotalStacksComparator, titlesWithSelectionName, 4,
				"€28.98", "€24.83");
		assertReportGroupsSortedByTotalStacks(game, groupBySelectionName, ascendTotalStacksComparator, titlesWithSelectionName, 4,
				"€24.83", "€28.98");
	}

	@Test
	public void theMaxAndMinValuseShouldBeCorrect_AfterGroupByCurrencyAndSortByTotalPayout() {
		assertReportGroupsSortedByTotalPayout(game, groupByCurrency, decendTotalPayoutComparator, titlesWithCurrency, 2, "£364.1",
				"€248.9");
		assertReportGroupsSortedByTotalPayout(game, groupByCurrency, ascendTotalPayoutComparator, titlesWithCurrency, 2, "€248.9",
				"£364.1");
	}

	@Test
	public void theMaxAndMinValuseShouldBeCorrect_AfterGroupByCurrencyAndSortByTotalStacks() {
		assertReportGroupsSortedByTotalStacks(game, groupByCurrency, decendTotalStacksComparator, titlesWithCurrency, 2, "£61.1",
				"€38.25");
		assertReportGroupsSortedByTotalStacks(game, groupByCurrency, ascendTotalStacksComparator, titlesWithCurrency, 2, "€38.25",
				"£61.1");
	}

	@Test
	public void theMaxAndMinValuseShouldBeCorrect_AfterGroupBySelectionNameAndCurrencyAndSortByTotalPayout() {
		assertReportGroupsSortedByTotalPayout(game, groupBySelectionNameAndCurrency, decendTotalPayoutComparator, titlesWithSelectionNameAndCurrency,
				8, "£122.55", "€14.4");
		assertReportGroupsSortedByTotalPayout(game, groupBySelectionNameAndCurrency, ascendTotalPayoutComparator, titlesWithSelectionNameAndCurrency,
				8, "€14.4", "£122.55");
	}

	@Test
	public void theMaxAndMinValuseShouldBeCorrect_AfterGroupBySelectionNameAndCurrencyAndSortByTotalStacks() {
		assertReportGroupsSortedByTotalStacks(game, groupBySelectionNameAndCurrency, decendTotalStacksComparator, titlesWithSelectionNameAndCurrency,
				8, "£19.6", "€3.2");
		assertReportGroupsSortedByTotalStacks(game, groupBySelectionNameAndCurrency, ascendTotalStacksComparator, titlesWithSelectionNameAndCurrency,
				8, "€3.2", "£19.6");
	}

	@Test
	public void theMaxAndMinValuseShouldBeCorrect_AfterGroupByMultipleGroupByersAndSortByTotalPayout() {
		assertReportGroupsSortedByTotalPayout(game, multipleGroupByers, decendTotalPayoutComparator, titlesWithMultipleGroupByers, 19,
				"£76.65", "£3.0");
		assertReportGroupsSortedByTotalPayout(game, multipleGroupByers, ascendTotalPayoutComparator, titlesWithMultipleGroupByers, 19,
				"£3.0", "£76.65");
	}

	@Test
	public void theMaxAndMinValuseShouldBeCorrect_AfterGroupByMultipleGroupByersAndSortByTotalStacks() {
		assertReportGroupsSortedByTotalStacks(game, multipleGroupByers, decendTotalStacksComparator, titlesWithMultipleGroupByers, 19,
				"£10.7", "£0.5");
		assertReportGroupsSortedByTotalStacks(game, multipleGroupByers, ascendTotalStacksComparator, titlesWithMultipleGroupByers, 19,
				"£0.5", "£10.7");
	}

	private void assertGroupedAndTotalPayoutSortedBets(Optional<Game> game, Function<Bet, List<Object>> groupByers,
			Comparator<? super Group> comparator, int expectedGroupSize, int expectedGroupByerSize,
			int unexpectedOrder) {
		List<Group> groups = assertGroupedBets(game, groupByers, comparator, expectedGroupSize, expectedGroupByerSize);
		assertSortedTotalPayoutGroups(groups, unexpectedOrder);
	}

	private void assertGroupedAndTotalStacksSortedBets(Optional<Game> game, Function<Bet, List<Object>> groupByers,
			Comparator<? super Group> comparator, int expectedGroupSize, int expectedGroupByerSize, int expectedOrder) {
		List<Group> groups = assertGroupedBets(game, groupByers, comparator, expectedGroupSize, expectedGroupByerSize);
		assertSortedTotalStacksGroups(groups, expectedOrder);
	}

	private void assertReportTitles(Optional<Game> game, Function<Bet, List<Object>> groupByers,
			Comparator<? super Group> comparator, List<String> titles, boolean expectedExistReport,
			int expectedTitleSize) {
		List<Group> groups = analyst.analyze(game, groupByers, comparator);
		Optional<Report> report = analyst.getReport(groups, titles);
		assertEquals(expectedExistReport, report.isPresent());
		if (report.isPresent()) {
			assertEquals(expectedTitleSize, report.get().getTitle().size());
		}
	}

	private void assertReportExist(List<Group> groups, List<String> titles, boolean expectedExistReport) {
		Optional<Report> report = analyst.getReport(groups, titles);
		assertEquals(expectedExistReport, report.isPresent());
	}

	private void assertReportGroupsSortedByTotalPayout(Optional<Game> game, Function<Bet, List<Object>> groupByers,
			Comparator<? super Group> comparator, List<String> titless, int expectedGroupSize,
			String expectedFirstTotalPayout, String expectedEndTotalPayout) {
		List<StringGroup> strGroup1 = assertReportGroupSize(game, groupByers, comparator, expectedGroupSize, titless);
		assertMaxMinTotalPayoutValuesInStringGroups(strGroup1, expectedFirstTotalPayout, expectedEndTotalPayout);
	}

	private void assertReportGroupsSortedByTotalStacks(Optional<Game> game, Function<Bet, List<Object>> groupByers,
			Comparator<? super Group> comparator, List<String> titles, int expectedGroupSize,
			String expectedFirstTotalPayout, String expectedEndTotalPayout) {
		List<StringGroup> strGroup1 = assertReportGroupSize(game, groupByers, comparator, expectedGroupSize, titles);
		assertMaxMinTotalStackValuesInStringGroups(strGroup1, expectedFirstTotalPayout, expectedEndTotalPayout);
	}

	private List<StringGroup> assertReportGroupSize(Optional<Game> game, Function<Bet, List<Object>> groupByers,
			Comparator<? super Group> comparator, int expectedGroupSize, List<String> titless) {
		List<Group> groups = analyst.analyze(game, groupByers, comparator);
		Optional<Report> report = analyst.getReport(groups, titless);
		assertEquals(true, report.isPresent());
		List<StringGroup> strGroup1 = report.get().getGroups();
		assertEquals(expectedGroupSize, strGroup1.size());
		return strGroup1;
	}

	private void assertMaxMinTotalPayoutValuesInStringGroups(List<StringGroup> strGroup,
			String expectedFirstTotalPayout, String expectedEndTotalPayout) {
		if (!strGroup.isEmpty()) {
			assertEquals(expectedFirstTotalPayout, strGroup.get(0).getTotalPayout());
			assertEquals(expectedEndTotalPayout, strGroup.get(strGroup.size() - 1).getTotalPayout());
		}
	}

	private void assertMaxMinTotalStackValuesInStringGroups(List<StringGroup> strGroup, String expectedFirstTotalPayout,
			String expectedEndTotalPayout) {
		if (!strGroup.isEmpty()) {
			assertEquals(expectedFirstTotalPayout, strGroup.get(0).getTotalStacks());
			assertEquals(expectedEndTotalPayout, strGroup.get(strGroup.size() - 1).getTotalStacks());
		}
	}

	private List<Group> assertGroupedBets(Optional<Game> game, Function<Bet, List<Object>> groupByers,
			Comparator<? super Group> comparator, int expectedGroupSize, int expectedGroupByerSize) {
		List<Group> groups = analyst.analyze(game, groupByers, comparator);
		assertEquals(expectedGroupSize, groups.size());
		assertGroupByerSize(expectedGroupByerSize, groups);
		return groups;
	}

	private void assertGroupByerSize(int expectedGroupByerSize, List<Group> groups) {
		for (Group group : groups) {
			assertEquals(expectedGroupByerSize, group.getGroupByers().size());
		}
	}

	private void assertSortedTotalPayoutGroups(List<Group> groups, int unexpectedOrder) {
		for (int i = 1; i < groups.size(); ++i) {
			assertNotEquals(unexpectedOrder,
					Double.compare(groups.get(i).getTotalPayout(), groups.get(i - 1).getTotalPayout()));
		}
	}

	private void assertSortedTotalStacksGroups(List<Group> groups, int unexpectedOrder) {
		for (int i = 1; i < groups.size(); ++i) {
			assertNotEquals(unexpectedOrder,
					Double.compare(groups.get(i).getTotalStacks(), groups.get(i - 1).getTotalStacks()));
		}
	}

}
