package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import evaluator.HITEvaluator;

public class ArrivalCompletionsToTimestamps {

	// Converts an ArrivalCompletions file to a sorted list of timestamps
	public static void main(String[] args) {
		TreeMap<Long, String> entries = new TreeMap<Long, String>();

		// Get the entries
		try {

			String header = "timestamp,hitGroupsArrived,hitGroupsCompleted,hitsAvailableUI,hitsCompleted,rewardsCompleted,rewardsArrived,hitsArrived,hitGroupsAvailableUI";

			entries.put(new Long(0L), header);

			FileInputStream fis = new FileInputStream(HITEvaluator.baseDir
					+ "ArrivalCompletions.csv");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				nextLine = line.split(",");
				Long timestamp = new Long(nextLine[3]);
				String entry = nextLine[3] + "," + nextLine[1] + ","
						+ nextLine[2] + "," + nextLine[4] + "," + nextLine[5]
						+ "," + nextLine[6] + "," + nextLine[7] + ","
						+ nextLine[8] + "," + nextLine[11];
				entries.put(timestamp, entry);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Write entries to file
		ArrayList<String> sortedEntries = new ArrayList<String>();
		for (Map.Entry<Long, String> entry : entries.entrySet()) {
			sortedEntries.add(entry.getValue());
		}

		HITEvaluator.outputToCsv(sortedEntries, "timestamps.csv");
		System.out.println("Successfully wrote to file.");

	}
}
