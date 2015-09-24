package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

import com.opencsv.CSVWriter;

import evaluator.HITEvaluator;

public class CheckOverfitting {

	private static String baseDir = "D:\\Friso\\Dropbox\\Studie\\Afstuderen\\Jie\\";

	public static void main(String[] args) {
		ArrayList<String> testEntries = getTestEntries();

		// Compare to training entries
		ArrayList<String> entriesToRemove = compareTrainingWithTest(testEntries);

		outputWithRemovedDuplicates("test_withsemantic.csv",
				"test_withsemantic_pruned.csv", entriesToRemove);
		outputWithRemovedDuplicates("training_withsemantic.csv",
				"training_withsemantic_pruned.csv", entriesToRemove);
	}

	private static void outputWithRemovedDuplicates(String filename,
			String targetFileName, ArrayList<String> duplicates) {

		ArrayList<String> resultSet = new ArrayList<String>();

		// Get the entries
		try {
			FileInputStream fis = new FileInputStream(baseDir + filename);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				nextLine = line.split(",");
				String entry = nextLine[0] + "," + nextLine[1];
				if (!duplicates.contains(entry)) {
					resultSet.add(line);
				}
			}
			reader.close();
			outputToCsv(resultSet, targetFileName);
			System.out.println("Output " + targetFileName + ".");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static ArrayList<String> compareTrainingWithTest(
			ArrayList<String> testSet) {

		ArrayList<String> resultSet = new ArrayList<String>();

		// Get the entries
		int foundCount = 0;
		try {
			FileInputStream fis = new FileInputStream(baseDir
					+ "training_withsemantic.csv");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			reader.readLine();
			int lineCount = 1;
			while ((line = reader.readLine()) != null) {
				lineCount++;
				nextLine = line.split(",");
				String entry = nextLine[0] + "," + nextLine[1];
				if (testSet.contains(entry)) {
					foundCount++;
					System.out
							.println("Entry found on line " + lineCount + ".");
					if (!resultSet.contains(entry)) {
						resultSet.add(entry);
					}
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("Total found: " + foundCount + ".");
		System.out.println("Amount to be removed: " + resultSet.size() + ".");

		return resultSet;
	}

	private static ArrayList<String> getTestEntries() {

		ArrayList<String> testEntries = new ArrayList<String>();

		// Get the entries
		try {
			FileInputStream fis = new FileInputStream(baseDir
					+ "test_withsemantic.csv");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				nextLine = line.split(",");
				String entry = nextLine[0] + "," + nextLine[1];
				testEntries.add(entry);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return testEntries;
	}

	public static void outputToCsv(ArrayList<String> output, String fileName) {
		try {
			CSVWriter writer = new CSVWriter(
					new FileWriter(baseDir + fileName), ',');
			for (String entry : output) {
				writer.writeNext(new String[] { entry });
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
