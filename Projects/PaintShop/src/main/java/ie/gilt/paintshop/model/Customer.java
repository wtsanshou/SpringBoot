package ie.gilt.paintshop.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @desc this class will hold a list favorite paints of the customer
 * @author Tao Wei
 */
public class Customer {

	private List<Paint> paints;

	public Customer() {
		paints = new LinkedList<Paint>();
	}

	public List<Paint> getPaints() {
		return paints;
	}

	public void setPaints(List<Paint> paints) {
		this.paints = paints;
	}

	public void putPaint(Paint paint) {
		paints.add(paint);
	}

	public Paint getPaint(int id) {
		return paints.get(id);
	}
	
	public void removePaint(int paintID) {
		paints.remove(paintID);
	}

	/**
	 * @desc puts the paint to the end of the customer's paint list
	 * @param int
	 *            paintID - the location of the matte paint in the customer's paint
	 *            list
	 */
	public void swapToEnd(int paintID) {
		Paint temp = paints.get(paintID);
		paints.set(paintID, paints.get(paints.size() - 1));
		paints.set(paints.size() - 1, temp);
	}

	public int size() {
		return paints == null ? 0 : paints.size();
	}

}
