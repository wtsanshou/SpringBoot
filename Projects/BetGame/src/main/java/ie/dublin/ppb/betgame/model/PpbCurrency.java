package ie.dublin.ppb.betgame.model;
import lombok.Builder;
import lombok.Data;

/**
 * @desc this class is to store currencies information. Getters and Steers are
 *       created by Lombok
 */

@Data
@Builder
public class PpbCurrency {
	private String name;
	private String symbol;
	private double currencyRate;
}
