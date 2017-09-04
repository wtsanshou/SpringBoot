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
 *       >>>>Step 1, If it is the begining of the algorithm or the solution is
 *       updated, search all customers; else, return the searching result
 * 
 *       >>>a. if the customer just has only one Paint left.
 * 
 *       >>a1. if the Paint color is in the final result
 * 
 *       >a11. if the Paint color surface is different from the final result,
 *       there is no solution and return null
 * 
 *       >a12. if the Paint color surface is the same as the final result,
 *       remove the customer and search next customer
 * 
 *       >>a2. if the Paint color is not in the final result, put the Paint into
 *       the final result. set the solution is updated and search next customer.
 * 
 *       >>>b. if the customer has more than one Paint left. Search each paint
 * 
 *       >>b1. if the Paint color is in the final result
 * 
 *       >b11. if the Paint color surface is different from the final result,
 *       remove the paint from the customer and set that the solution is
 *       updated. Check if the customer's paint list is empty.
 * 
 *       b111. if the customer's paint list is empty, there is no solution and
 *       return null
 * 
 *       b112. if the customer's paint list is not empty, check next paint.
 * 
 *       >b12. if the Paint color surface is the same as the final result,
 *       remove the customer and search next customer
 * 
 *       >>>c. after search all customers of the shop, go to step 1.
 * 
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
	 * @desc finds the solution by remove customer from the shop or remove paint from
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
				if (customer.paintSize() == 1) {
					Paint singlePaint = customer.getPaint(0);
					if (map.containsKey(singlePaint.getColor())) {
						if (doesConflictWithSomeone(map, singlePaint)) {
							return null;
						} else {
							shop.removeCustomer(i);
							i -= 1;
						}
					} else {
						map.put(singlePaint.getColor(), singlePaint.isMatte() ? "M" : "G");
						update = true;
						shop.removeCustomer(i);
						i -= 1;
					}
				} else {
					for (int p = 0; p < customer.paintSize(); ++p) {
						Paint paint = customer.getPaints().get(p);
						if (map.containsKey(paint.getColor())) {
							if (doesConflictWithSomeone(map, paint)) {
								update = true;
								customer.removePaint(p);
								p -= 1;
								if (customer.paintSize() == 0)
									return null;
							} else {
								shop.removeCustomer(i);
								i -= 1;
								break;
							}
						}
					}
				}
			}
		}
		return map;

	}

	private boolean doesConflictWithSomeone(Map<Integer, String> map, Paint paint) {
		return map.get(paint.getColor()).equals("M") ^ paint.isMatte();
	}

}
