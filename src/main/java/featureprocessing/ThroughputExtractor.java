package featureprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import entity.HITinstances;
import evaluator.HITEvaluator;

// Calculates throughput rate of HITgroups based on observations from HITinstance table
// We compare with 1439290800000L or August 11th 2015, 11:00:00 GMT,
// Because the last entry in the dataset is 10:34 GMT.
public class ThroughputExtractor {

	public static ArrayList<String> getThroughputData(ArrayList<String> groupIDs) {

		ArrayList<String> result = new ArrayList<String>();
		result.add("groupID,timestamp,throughput");

		ArrayList<Long> timeStamps = readTimeStamps();

		for (String groupID : groupIDs) {
			HITinstances currentHI = readInstances(groupID);
			for (Long ts : timeStamps) {
				double d = currentHI.getThroughput(ts);
				if (d > Double.MIN_VALUE) {
					result.add(groupID + "," + ts + "," + d);
				}
			}
		}

		return result;
	}

	public static HITinstances readInstances(String groupID) {

		HITinstances currentHI = new HITinstances(groupID);
		try {

			FileInputStream fis = new FileInputStream(
					HITEvaluator.HITinstanceCSVLocation);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				nextLine = line.split(",");
				if (nextLine[0].contains(groupID)) {
					// Remove quotes from the numbers
					currentHI.addTimestamp(
							Long.parseLong(nextLine[2].replaceAll("\"", "")),
							Integer.parseInt(nextLine[1].replaceAll("\"", "")),
							Integer.parseInt(nextLine[6].replaceAll("\"", "")));
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentHI;
	}

	public static ArrayList<Long> readTimeStamps() {
		ArrayList<Long> result = new ArrayList<Long>();

		// Read arrivalcompletions, returning a list of the timestamps
		try {
			FileReader fileReader = new FileReader(new File(
					HITEvaluator.baseDir + "data\\timestamps_hours.txt"));
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				result.add(new Long(Long.parseLong(line)));
			}
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
