package ie.gilt.paintshop.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @desc this class will hold the color number of the shop and all customers of
 *       the shop
 * @author Tao Wei
 */
public class Shop {
	private final int MIN_COLOR_NUM = 1;
	private final int MAX_COLOR_NUM = 1000;
	private int colorNum;
	private List<Customer> customers;

	public Shop(int colorNum) throws IllegalArgumentException {
		setColorNum(colorNum);
		customers = new LinkedList<>();
	}

	public int getColorNum() {
		return colorNum;
	}

	/**
	 * @desc sets the color number of the shop
	 * @exception the
	 *                color number should be in the legal range of paint shop. Legal
	 *                range is [1, 1000]
	 * @param int
	 *            colorNum - the color number of the shop
	 */
	private void setColorNum(int colorNum) throws IllegalArgumentException {
		if (colorNum < 1 || colorNum > MAX_COLOR_NUM)
			throw new IllegalArgumentException();
		this.colorNum = colorNum;
	}

	public int getMinColorNum() {
		return MIN_COLOR_NUM;
	}

	public int getMaxColorNum() {
		return MAX_COLOR_NUM;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void putCustomer(Customer customer) {
		customers.add(customer);
	}
	
	public void removeCustomer(int customerID) {
		customers.remove(customerID);
	}

	public Customer getCustomer(int id) {
		return customers.get(id);
	}

	public int size() {
		return customers.size();
	}

	/**
	 * @desc sorts the list of the customers
	 * @param Comparator<Customer>
	 *            comparator - sorts the list of the customers by the comparator
	 */
	public void sortCustomers(Comparator<Customer> comparator) {
		Collections.sort(customers, comparator);
	}
}
