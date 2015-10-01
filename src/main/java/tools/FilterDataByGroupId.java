package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public class FilterDataByGroupId {

	public static String baseDir = "D:\\Friso\\Dropbox\\Studie\\Afstuderen\\DATA\\Selected Task Dataset 2\\tofilter\\";
	private static String targetDir = "D:\\Friso\\Dropbox\\Studie\\Afstuderen\\DATA\\Selected Task Dataset 2\\data\\output\\";
	private static String IDfileLocation = "D:\\Friso\\Dropbox\\Studie\\Afstuderen\\DATA\\Selected Task Dataset 2\\hitgroup_ids_2.txt";

	private static ArrayList<String> groupIDs;

	public static void main(String[] args) {
		groupIDs = readAcceptedIDs();
		
		//filterFile(groupIDs, "test_withsemantic_v2"  )
	}

	public static void filterFile(ArrayList<String> acceptedIDs,
			String fileName, String targetFileName) {
		ArrayList<String> resultSet = new ArrayList<String>();

		// Get the entries
		int foundCount = 0;
		try {
			FileInputStream fis = new FileInputStream(baseDir
					+ fileName);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			reader.readLine();
			int lineCount = 1;
			while ((line = reader.readLine()) != null) {
				nextLine = line.split(",");
				String entry = nextLine[0] + "," + nextLine[1];
				if (acceptedIDs.contains(entry)) {
					resultSet.add(entry);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		outputToCsv(resultSet, targetFileName);
	}

	private static ArrayList<String> readAcceptedIDs() {
		ArrayList<String> result = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					IDfileLocation));
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void outputToCsv(ArrayList<String> output, String fileName) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(targetDir
					+ fileName), ',');
			for (String entry : output) {
				writer.writeNext(new String[] { entry });
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
