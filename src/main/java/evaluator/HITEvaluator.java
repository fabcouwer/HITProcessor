package evaluator;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

// Main entry point for HIT evaluator
public class HITEvaluator {

	private static String baseDir = "TODO";

	public static void main(String[] args) {
		// 1. Take filename as input
		String fileName = "test.html";

		// 2. Append to baseDir and retrieve Document
		File input = new File(baseDir + "\\" + fileName);

		try {
			Document doc = Jsoup.parse(input, "UTF-8", "");

			// 3. Extract data from the page

			// 4. Output the results

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
