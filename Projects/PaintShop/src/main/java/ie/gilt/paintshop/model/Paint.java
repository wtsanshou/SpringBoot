package ie.gilt.paintshop.model;

/**
 * @desc this class will hold color id and surface values of a Paint.
 * @author Tao Wei
 */
public class Paint {
	private int colorID;
	private boolean matte;

	public Paint(int colorID, boolean matte) {
		this.colorID = colorID;
		this.matte = matte;
	}

	public int getColor() {
		return colorID;
	}

	public boolean isMatte() {
		return matte;
	}

}
