package ie.gilt.paintshop.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import ie.gilt.paintshop.model.Customer;
import ie.gilt.paintshop.model.Paint;
import ie.gilt.paintshop.model.Shop;
import lombok.extern.slf4j.XSlf4j;

/**
 * @desc this class will hold functions for file reading. Assume the legal
 *       format is: 1. the first line is the number of colors of the shop. The
 *       number is from 1 to 1000. 2. the rest each line as a customer with the
 *       format "colorID" space "surface" space. 3. the "colorID" is in the
 *       range of the shop's color number. 4. the "surface" only has two
 *       options: either "G" (gloss) or "M" (matte). 5. at most one matte for
 *       each customer.
 * @author Tao Wei
 */
@XSlf4j
public class FileManager {

	/**
	 * @desc reads the file content
	 * @param string
	 *            filePath - the path of the file to be read
	 * @return Shop - an object of Shop
	 */
	public Shop readFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			log.warn("The file '{}' is not existed", filePath);
			return null;
		}

		Shop shop = null;

		try {
			Scanner fileSc = new Scanner(file);
			String firstLine = fileSc.nextLine();
			int colorNum = Integer.parseInt(firstLine);
			shop = getCustomersInfo(fileSc, colorNum);
			fileSc.close();
		} catch (NumberFormatException e) {
			log.error("The color number reading Exception: ", e);
			return null;
		} catch (FileNotFoundException e) {
			log.error("File reading Exception: ", e);
			return null;
		}

		return shop;
	}

	/**
	 * @desc collects color number and paints of each customer if a customer like a
	 *       matte, put the matte to the end of the customer's paint list
	 * @param Scanner
	 *            fileSc - file Scanner
	 * @param int
	 *            colorNum - the number of color in this shop
	 * @return Shop - an object of Shop
	 */
	private Shop getCustomersInfo(Scanner fileSc, int colorNum) {
		Shop shop = null;
		try {
			shop = new Shop(colorNum);
		} catch (IllegalArgumentException e) {
			log.error("The color number '{}' is out of legal range", colorNum, e);
			return null;
		}

		while (fileSc.hasNextLine()) {
			String line = fileSc.nextLine();
			Customer customer = new Customer();
			String[] colorSurfaces = line.split(" ");
			int matte = -1;
			for (int i = 1; i < colorSurfaces.length; i += 2) {
				int colorID = isLegalFormat(shop, colorSurfaces, i);

				if (colorID == 0)
					return null;

				Paint paint = new Paint(colorID, colorSurfaces[i].equals("M"));

				if (matte != -1 && paint.isMatte()) {
					log.error("There are more than one matte for a customer at '{}'", line);
					return null;
				}

				if (paint.isMatte())
					matte = i / 2;
				customer.putPaint(paint);
			}

			// XXX: if the customer like a matte, put it to the end of the customer's paint
			// list
			if (matte != -1)
				customer.swapToEnd(matte);

			shop.putCustomer(customer);
		}

		return shop;
	}

	/**
	 * @desc checks the String format of each customer
	 * @param Shop
	 *            shop - the current shop object. It is used to get the color number
	 *            legal range
	 * @param String[]
	 *            colorSurfaces - the content of a customer
	 * @param int
	 *            i - the ith String in the colorSurfaces
	 * @return int - color id
	 */
	private int isLegalFormat(Shop shop, String[] colorSurfaces, int i) {
		int colorID = 0;
		try {
			colorID = Integer.parseInt(colorSurfaces[i - 1]);
		} catch (NumberFormatException e) {
			log.error("File format Exception: The color id '{}' is invalid", colorSurfaces[i - 1], e);
			return 0;
		}

		if (colorID < shop.getMinColorNum() || colorID > shop.getColorNum()) {
			log.error("File format Exception: The color id '{}' is out of the shop's range", colorID);
			return 0;
		}

		if (!colorSurfaces[i].equals("G") && !colorSurfaces[i].equals("M")) {
			log.error("File format Exception: The surface '{}' is invalid", colorSurfaces[i]);
			return 0;
		}
		return colorID;
	}

}
