package ie.dublin.ppb.betgame.model;

import lombok.Builder;
import lombok.Data;

/**
 * @desc this class list all variables of the input data. Getters and Steers are
 *       created by Lombok
 */

@Data
@Builder
public class Bet {
	private String betId;
	private Long betTimeStamp;
	private Long selectionId;
	private String selectionName;
	private Double stake;
	private Double price;
	private String currency;
}
