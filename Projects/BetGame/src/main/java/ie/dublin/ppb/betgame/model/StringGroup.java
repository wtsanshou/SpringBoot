package ie.dublin.ppb.betgame.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * @desc this class is for writable grouped data. Getters and Steers are created
 *       by Lombok
 */

@Builder
@Data
public class StringGroup {
	private List<String> groupByers;
	private String noOfBets;
	private String totalStacks;
	private String totalPayout;
}
