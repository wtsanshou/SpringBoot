package ie.gilt.paintshop;

import java.util.Arrays;
import java.util.List;
import ie.gilt.paintshop.model.Customer;
import ie.gilt.paintshop.model.Paint;
import ie.gilt.paintshop.model.Shop;

/**
 * @desc this class will hold functions for color matching.
 * 
 *       >>>>Step 1, the customers of the shop will be sorted by the customers'
 *       paints size.
 * 
 *       >>>>Step 2, back tracking a solution.
 * 
 *       >>>A. check the next customer from the customers list of the shop.
 * 
 *       >>a. If found a color surface which is also favorited by previous
 *       customer, jump to A.
 * 
 *       >>b. If cannot find a color surface which is also favorited by previous
 *       customer, search if there is a null color surface.
 * 
 *       >b1. If found one, set the surface to the color result. back tracking a
 *       solution.
 * 
 *       >b2. If found a solution, return the solution.
 * 
 *       >b3. If it's not a solution, set the color back to null, search the next null color.
 * 
 *       >>c. If there is no solution even to the end of a customer, return no
 *       solution
 * 
 *       >>>>Step 3, return the searching result.
 * @return the matching result
 * @author Tao Wei
 */
public class Solution1 implements ShopManager{

	/**
	 * @desc finds the best combination of the paint shop
	 * @param Shop
	 *            shop - the object of the shop includes color number and all its
	 *            customers' information
	 * @return List<String> mem - the best combination of the paint shop.
	 */
	public List<String> matchCustomersColor(Shop shop) {

		String[] res = new String[shop.getColorNum()];

		shop.sortCustomers((Customer a, Customer b) -> a.paintSize() - b.paintSize());

		List<String> mem = Arrays.asList(res);
		if (doesSolutionFound(shop, mem, 0))
			return mem;
		else
			return null;
	}

	/**
	 * @desc finds a solution using back tracking algorithm
	 * @param Shop
	 *            shop - the object of the shop includes color number and all its
	 *            customers' information
	 * @param List<String>
	 *            mem - uses to save the solution for previous customers
	 * @param int
	 *            n - the nth customer
	 * @return boolean - if found a solution, return true, else return false.
	 */
	private boolean doesSolutionFound(Shop shop, List<String> mem, int n) {

		for (int j = n; j < shop.size(); ++j) {
			int i = 0;
			for (; i < shop.getCustomer(j).paintSize(); ++i) {
				Paint occupyPaint = shop.getCustomer(j).getPaint(i);
				if (isFavoritedBySomeone(mem, occupyPaint)) {
					break;
				}
			}
			if (i == shop.getCustomer(j).paintSize()) {
				for (i = 0; i < shop.getCustomer(j).paintSize(); ++i) {
					Paint emptyPaint = shop.getCustomer(j).getPaint(i);
					if (mem.get(emptyPaint.getColor() - 1) == null) {
						mem.set(emptyPaint.getColor() - 1, emptyPaint.isMatte() ? "M" : "G");
						if (doesSolutionFound(shop, mem, j + 1))
							return true;
						mem.set(emptyPaint.getColor() - 1, null);
					}
				}
			}
			if (i == shop.getCustomer(j).paintSize()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @desc checks if the color surface is also favorited by previous customer
	 * @param String[]
	 *            res - the previous matching result.
	 * @param Paint
	 *            occupyPaint - a Paint object of a customer
	 * @return boolean - if the color surface is also favorited by previous customer
	 *         return true, else return false
	 */
	private boolean isFavoritedBySomeone(List<String> res, Paint occupyPaint) {
		return res.get(occupyPaint.getColor() - 1) != null
				&& !(res.get(occupyPaint.getColor() - 1).equals("M") ^ occupyPaint.isMatte());
	}

}
