package ie.dublin.ppb.betgame.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * @desc this class list includes all the grouped data of the input data.
 *       Getters and Steers are created by Lombok
 */
@Builder
@Data
public class Group {
	List<String> groupByers;
	private int noOfBets;
	private double totalStacks;
	private double totalPayout;
	private String currency;
}
