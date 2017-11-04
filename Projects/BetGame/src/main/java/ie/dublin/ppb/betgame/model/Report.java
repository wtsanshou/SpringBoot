package ie.dublin.ppb.betgame.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * @desc this class is for writable report including string titles and string grouped data. Getters and Steers are
 *       created by Lombok
 */

@Builder
@Data
public class Report {
	private List<String> title;
	private List<StringGroup> groups;
}
