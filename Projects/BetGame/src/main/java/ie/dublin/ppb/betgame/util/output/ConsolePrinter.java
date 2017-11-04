package ie.dublin.ppb.betgame.util.output;

import java.util.Optional;
import java.util.stream.Collectors;
import ie.dublin.ppb.betgame.model.Report;
import ie.dublin.ppb.betgame.model.StringGroup;
import lombok.extern.slf4j.XSlf4j;

/**
 * @desc A implement of PpbPrinter which is used to print a report to console.
 */

@XSlf4j
public class ConsolePrinter implements PpbPrinter{

	/**
	 * @desc print a report to console
	 * @param Optional<Report> report - A writable report
	 * @return int - The row number of the printed report
	 */
	public int print(Optional<Report> report) {
		int rows = 0;
		if (!printTitle(report))
			return 0;
		for (StringGroup strGroup : report.get().getGroups()) {
			for (String groupbyer : strGroup.getGroupByers()) {
				System.out.print(groupbyer + ",\t");
			}
			System.out.print(
					strGroup.getNoOfBets() + ",\t\t" + strGroup.getTotalStacks() + ",\t" + strGroup.getTotalPayout() + "\n");
			rows++;
		}
		return rows;
	}

	public void printWelcom() {
		System.out.println("Please type the data input path or type \"exit\" to exit the programme");
	}
	
	private boolean printTitle(Optional<Report> report) {
		if (report == null || !report.isPresent()) {
			log.warn("The print result is empty");
			System.out.println("The print result is empty");
			return false;
		}
		
		System.out.println(report.get().getTitle().stream().map(i -> i.toString())
	     .collect(Collectors.joining(", ")));
		
		return true;
	}

}
