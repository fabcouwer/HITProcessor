package featureprocessing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import entity.HITinstances;
import evaluator.HITEvaluator;

// Calculates throughput rate of HITgroups based on observations from HITinstance table
// We compare with 1439290800000L or August 11th 2015, 11:00:00 GMT,
// Because the last entry in the dataset is 10:34 GMT.
public class ThroughputExtractor {

	public static HITinstances getInstances(String groupID) {
		HITinstances currentHI = readInstances(groupID);
		return currentHI;
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
}
