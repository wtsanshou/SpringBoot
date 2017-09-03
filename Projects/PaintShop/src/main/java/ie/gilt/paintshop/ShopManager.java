package ie.gilt.paintshop;

import java.util.List;

import ie.gilt.paintshop.model.Shop;

/**
 * @desc this class will hold functions for color matching.
 *
 * @author Tao Wei
 */
public interface ShopManager {

	/**
	 * @desc finds the best combination of a paint shop to fulfill the requirements
	 *       of all its customers
	 * @param Shop
	 *            shop - the object of the shop includes color number and all its
	 *            customers' information
	 * @return List<String> res - the best combination of the paint shop.
	 */
	List<String> matchCustomersColor(Shop shop);
}
