package ie.gilt.paintshop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ie.gilt.paintshop.model.Customer;
import ie.gilt.paintshop.model.Paint;
import ie.gilt.paintshop.model.Shop;

/**
 * @desc this class will hold functions for color matching.
 * 
 *       >>>>Step 1, If the solution is updated, search all customers; else, go
 *       to step 2
 * 
 *       >>>a. if the customer just has one Paint left.
 * 
 *       >>a1. if the Paint is different from the final result, there is no
 *       solution and return "No solution"
 * 
 *       >>a2. else put the Paint to the final result and remove the customer
 *       and go to step 1.
 * 
 *       >>>b. if the customer has more than one Paint left.
 * 
 *       >>b1. if the first Paint is already in the final result, remove the
 *       customer and go to step 1.
 * 
 *       >>b2. else remove the paint from the customer's paint list, and search
 *       next customer.
 * 
 *       >>>c. after search all customers of the shop, go to step 1.
 * 
 *       >>>>Step 2, return the searching result.
 * @return the matching result
 * @author Tao Wei
 */
public class Solution2 implements ShopManager {

	/**
	 * @desc finds the best combination of the paint shop
	 * @param Shop
	 *            shop - the object of the shop includes color number and all its
	 *            customers' information
	 * @return String[] res - the best combination of the paint shop.
	 */
	public List<String> matchCustomersColor(Shop shop) {

		String[] res = new String[shop.getColorNum()];
		Map<Integer, String> map = searchASolution(shop);

		if (map == null)
			return null;

		List<String> solution = Arrays.asList(res);
		for (Entry<Integer, String> paint : map.entrySet()) {
			solution.set(paint.getKey() - 1, paint.getValue());
		}

		return solution;
	}
	

	/**
	 * @desc finds a solution by remove customer from the shop or remove paint from
	 *       costomer
	 * @param Shop
	 *            shop - the object of the shop includes color number and all its
	 *            customers' information
	 * @return Map<Integer, String> map - the best combination of the paint shop.
	 */
	private Map<Integer, String> searchASolution(Shop shop) {
		Map<Integer, String> map = new HashMap<>();
		boolean update = true;
		List<Customer> customers = shop.getCustomers();
		while (update) {
			update = false;
			for (int i = 0; i < customers.size(); ++i) {
				Customer customer = customers.get(i);
				if (customer.size() == 1) {
					Paint singlePaint = customer.getPaint(0);
					if (noSolution(map, singlePaint)) {
						return null;
					} else {
						map.put(singlePaint.getColor(), singlePaint.isMatte() ? "M" : "G");
						update = true;
						shop.removeCustomer(i);
						break;
					}
				} else {
					Paint firstPaint = customer.getPaints().get(0);
					if (isFavoritedBySomeone(map, firstPaint)) {
						update = true;
						shop.removeCustomer(i);
						break;
					} else {
						update = true;
						customer.removePaint(0);
					}
				}
			}
		}
		return map;

	}

	private boolean noSolution(Map<Integer, String> map, Paint singlePaint) {
		return map.containsKey(singlePaint.getColor()) && doesConflictWithSomeone(map, singlePaint);
	}

	private boolean isFavoritedBySomeone(Map<Integer, String> map, Paint firstPaint) {
		return map.containsKey(firstPaint.getColor()) && !doesConflictWithSomeone(map, firstPaint);
	}

	private boolean doesConflictWithSomeone(Map<Integer, String> map, Paint paint) {
		return map.get(paint.getColor()).equals("M") ^ paint.isMatte();
	}

}
