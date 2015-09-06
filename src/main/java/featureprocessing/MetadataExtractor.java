package featureprocessing;

public class MetadataExtractor {

	public static String getMetaDataString(String groupID) {
		// 1. Get CSV line for this HITgroup
		String[] csvLine = null;

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

	private static String getReward(String[] csvLine) {

		return "TODO";
	}

	private static String getTimeAllotted(String[] csvLine) {

		return "TODO";
	}

	private static String getLocation(String[] csvLine) {

		return "TODO";
	}

	private static String getMaster(String[] csvLine) {

		return "TODO";
	}

	private static String getTotalApproved(String[] csvLine) {

		return "TODO";
	}

	private static String getApprovalRate(String[] csvLine) {

		return "TODO";
	}

	private static String getTitleLength(String[] csvLine) {

		return "TODO";
	}

	private static String getDescriptionLength(String[] csvLine) {

		return "TODO";
	}

	private static String getAmountKeywords(String[] csvLine) {

		return "TODO";
	}

}
