package ie.gilt.paintshop.util;

import java.util.List;

import ie.gilt.paintshop.model.Customer;
import ie.gilt.paintshop.model.Paint;
import ie.gilt.paintshop.model.Shop;

/**
 * @desc this class will hold functions for printing information
 * @author Tao Wei
 */
public class ResultPrinter {

	/**
	 * @desc print the best combination of the paint shop
	 * @param String[]
	 *            res - the matching algorithm result
	 */
	public void printResult(List<String> res) {
		for (int i = 0; i < res.size(); ++i) {
			if (res.get(i) == null)
				System.out.print("G ");
			else
				System.out.print(res.get(i) + " ");
		}
	}

	/**
	 * @desc remind user to input a file path
	 */
	public void startPoint() {
		System.out.println("\nPlease enter a valid file path or enter 'exit' to exit");
	}

	/**
	 * @desc prints the customers infomation of the shop
	 * @param Shop
	 *            shop - the object of the shop includes color number and all its
	 *            customers' information
	 */
	public void showContentOf(Shop shop) {
		System.out.println("Contents of file:");
		for (Customer customer : shop.getCustomers()) {
			for (Paint paint : customer.getPaints())
				System.out.print(paint.getColor() + " " + (paint.isMatte() ? "M " : "G "));

			System.out.println();
		}
	}

	/**
	 * @desc prints that there is no solution exists and remind user to input a file
	 *       path again
	 */
	public void noSolution() {
		System.out.println("No solution exists");
		startPoint();
	}

}
