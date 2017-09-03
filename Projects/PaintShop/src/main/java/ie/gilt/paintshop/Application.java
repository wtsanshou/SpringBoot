package ie.gilt.paintshop;

import java.util.List;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ie.gilt.paintshop.model.Shop;
import ie.gilt.paintshop.util.FileManager;
import ie.gilt.paintshop.util.ResultPrinter;
import lombok.extern.slf4j.XSlf4j;

/** 
 * @desc this class will start the application. 
 * 	  The application tries to find the best combination of a paint shop
 * 	  to fulfill the requirements of all its customers  
 * @input a legal file path
 * @output the best combination solution or print out "No solution exists"
 * @exit enter "exit" to exit
 * @author Tao Wei
*/
@XSlf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		FileManager fileManager = new FileManager();
		ResultPrinter printer = new ResultPrinter();
				
//		ShopManager shopManger = new Solution1();
		ShopManager shopManger = new Solution2();


		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		printer.startPoint();
		String filePath;
		while (!(filePath = sc.nextLine()).equalsIgnoreCase("exit")) {
			log.info("The file '{}' is reading", filePath);
			Shop shop = fileManager.readFile(filePath);
			if (shop == null) {
				log.warn("The shop is null");
				printer.noSolution();
				continue;
			}
			
			log.info("The file '{}' is matching", filePath);
			List<String> res = shopManger.matchCustomersColor(shop);

			// TODO: delete me. show sorted content of the shop, just for test
			// printer.ContentOf(shop);

			if (res == null) {
				log.warn("The matching result is null");
				printer.noSolution();
				continue;
			}
			printer.printResult(res);
			printer.startPoint();
			log.info("The file '{}' is finished", filePath);
		}
	}
}
