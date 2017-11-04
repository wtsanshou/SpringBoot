package ie.dublin.ppb.betgame.statistic;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import ie.dublin.ppb.betgame.model.Bet;
import ie.dublin.ppb.betgame.model.Game;
import ie.dublin.ppb.betgame.model.Group;
import ie.dublin.ppb.betgame.model.Report;

/**
 * @desc This interface is used to analyze input data and create a report from the
 *       analysis result.
 */

public interface PpbAnalyst {

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
	public List<Group> analyze(Optional<Game> game, Function<Bet, List<Object>> groupByer,
			Comparator<? super Group> customerComparator);

	/**
	 * @desc Create a writable report from the grouped and sorted data
	 * @param List<Group>
	 *            groups - the grouped and sorted data of the input data
	 * @param List<String>
	 *            titles - the titles of the report data
	 * @return Optional<Report> - A writable report
	 */
	public Optional<Report> getReport(List<Group> groups, List<String> titles);
}
