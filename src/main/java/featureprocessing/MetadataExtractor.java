package featureprocessing;

import java.io.FileReader;

import com.opencsv.CSVReader;

import evaluator.HITEvaluator;

public class MetadataExtractor {

	public static String getMetaDataString(String groupID) {
		// 1. Get CSV line for this HITgroup
		System.out.println("Evaluating metadata for " + groupID);
		String[] csvLine = getCsvLine(groupID);

		// 2. Get all attributes and combine
		String result = getReward(csvLine) + "," + getTimeAllotted(csvLine)
				+ "," + getLocation(csvLine) + "," + getMaster(csvLine) + ","
				+ getTotalApproved(csvLine) + "," + getApprovalRate(csvLine)
				+ "," + getTitleLength(csvLine) + ","
				+ getDescriptionLength(csvLine) + ","
				+ getAmountKeywords(csvLine) + ",";

		// 3. Return resulting string
		return result;
	}

	// Gets the line for this HITgroup from the HITgroup CSV
	private static String[] getCsvLine(String groupID) {
		String[] result = new String[] {};
		try {
			CSVReader reader = new CSVReader(new FileReader(
					HITEvaluator.HITgroupCSVLocation));
			String[] nextLine;
			boolean done = false;
			while ((nextLine = reader.readNext()) != null && !done) {
				if (nextLine[0].contains(groupID)) {
					result = nextLine[0].split(",");
					done = true;
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String getReward(String[] csvLine) {
		return csvLine[5];
	}

	private static String getTimeAllotted(String[] csvLine) {
		return csvLine[9];
	}

	private static int getLocation(String[] csvLine) {
		if (csvLine[10].contains("Location is"))
			return 1;
		else
			return 0;
	}

	private static int getMaster(String[] csvLine) {
		if (csvLine[10].contains("Masters has been granted"))
			return 1;
		else
			return 0;
	}

	private static String getTotalApproved(String[] csvLine) {
		String qualifications = csvLine[10];
		if (qualifications.contains("Total approved HITs is greater than")) {
			// Split the String starting at this qualification -
			// the value is at index 6
			String[] split = qualifications.substring(
					qualifications
							.indexOf("Total approved HITs is greater than"))
					.split(" ");
			return split[6];
		} else
			return "0";
	}

	private static String getApprovalRate(String[] csvLine) {
		String qualifications = csvLine[10];
		if (qualifications.contains("HIT approval rate (%) is greater than")) {
			// Split the String starting at this qualification -
			// the value is at index 7
			String[] split = qualifications.substring(
					qualifications
							.indexOf("HIT approval rate (%) is greater than"))
					.split(" ");
			return split[7];
		} else
			return "0";
	}

	private static int getTitleLength(String[] csvLine) {
		return csvLine[2].length();
	}

	private static int getDescriptionLength(String[] csvLine) {
		return csvLine[6].length();
	}

	// Multiple words may be a single keyword if they aren't input correctly by
	// the requester.
	private static int getAmountKeywords(String[] csvLine) {
		return csvLine[3].split(";").length;
	}

}
