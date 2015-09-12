package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import evaluator.HITEvaluator;
import featureprocessing.ThroughputExtractor;

public class GenerateThroughputTrainingData {

	public static void main(String[] args) {

		// 0. Result setup
		String header1 = "#### training ####";
		String headerGeneral = "timestamp,groupID,marketTimestamp,throughput,";
		String headerMarket = "hitGroupsArrived,hitGroupsCompleted,hitsAvailableUI,hitsCompleted,rewardsCompleted,rewardsArrived,hitsArrived,hitGroupsAvailableUI,";
		String headerTask = "reward,timeAllotted,location,master,totalApproved,approvalRate,titleLength,descLength,"
				+ "amountKeywords,linkCount,wordCount,imageCount,bodyTextPct,emphTextPct,cssCount,scriptCount,"
				+ "inputCount,textGroupCount,imageAreaCount,visualAreaCount,w3cPct,hueAvg,satAvg,valAvg,colorfulness1,colorfulness2,initialHits";
		String header3 = "#### test ####";

		// 1. Get timestamps for all 5-hour blocks
		ArrayList<String> blocks = getTimestampBlocks();

		ArrayList<Long> timestamps = ThroughputExtractor.readTimeStamps();

		// 2. For every timestamp past the first four, get the data
		String[] currentBlock;
		for (String block : blocks) {
			ArrayList<String> output = new ArrayList<String>();
			output.add(header1);
			output.add(headerGeneral + headerMarket + headerTask);

			currentBlock = block.split(";");

			ArrayList<String> hitsAtTime;
			for (int i = 0; i < 5; i++) {
				if (i == 4) {
					output.add(header3);
				}

				// Data = general information, market information, HIT info

				// Get HITs for the timestamp under evaluation.
				hitsAtTime = getHITsAtTime(currentBlock[i]);

				// Add market data for this hour to the basic line.
				String timestampLine = getMarketDataStringAtTime(currentBlock[i]);

				// Construct result lines for every HIT in this timestamp
				String resultLine = "";
				for (String hit : hitsAtTime) {
					resultLine += currentBlock[4] + hit;

					String hitString = getHITstring(hit.split(",")[0]);
					if (!hitString.isEmpty()) {
						resultLine += timestampLine + hitString;
						output.add(resultLine);
					}
				}

			}

			// 4. Output the results to file
			// Only include output with test data
			String last = output.get(output.size() - 1);
			if (!last.equals(header3)) {
				HITEvaluator.outputToCsv(output, currentBlock[4] + ".csv");
				System.out.println("Wrote " + currentBlock[4] + ".csv.");
			}
		}

	}

	// Divide given timestamps using sliding window of 5 hours
	private static ArrayList<String> getTimestampBlocks() {
		// Get all timestamps
		ArrayList<Long> timestamps = ThroughputExtractor.readTimeStamps();

		// Split up into blocks of 5: 4 training, 1 test
		ArrayList<String> blocks = new ArrayList<String>();

		int i = 0;
		String current = "";
		for (Long l : timestamps) {
			i++;
			current += l.toString();
			if (i != 5) {
				current += ";";
			} else if (i == 5) {
				// System.out.println(current);
				blocks.add(current);
				current = current.substring(current.indexOf(";") + 1) + ";";
				i = 4;
			}
		}

		return blocks;
	}

	// Returns HITs at timestamp in format (groupID,timestamp,throughput)
	private static ArrayList<String> getHITsAtTime(String timestamp) {

		ArrayList<String> result = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream(HITEvaluator.baseDir
					+ "data\\throughput_data.csv");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			while ((line = reader.readLine()) != null) {
				nextLine = line.split(",");
				if (nextLine[1].contains(timestamp)) {
					result.add(line.replaceAll("\"", ""));
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private static String getMarketDataStringAtTime(String timestamp) {
		String result = "";

		try {
			FileInputStream fis = new FileInputStream(HITEvaluator.baseDir
					+ "data\\timestamps_sorted.csv");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			while ((line = reader.readLine()) != null) {
				nextLine = line.split(",");
				if (nextLine[0].equals(timestamp)) {
					result = line.substring(line.indexOf(",")).replaceAll("\"",
							"");
					break;
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private static String getHITstring(String groupID) {
		String result = "";
		try {
			FileInputStream fis = new FileInputStream(HITEvaluator.baseDir
					+ "data\\attributes_final_v2_nohead_noquote.csv");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			while ((line = reader.readLine()) != null) {
				nextLine = line.split(",");
				// exclude 0 (groupid), 21 (textarea),
				// 22 (nontextarea), 30 (old throughput)
				if (nextLine[0].equals(groupID)) {
					for (int i = 1; i < nextLine.length; i++) {
						if (i < 30 && i != 21 && i != 22) {
							result += nextLine[i] + ",";
						}
					}
					break;
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result.isEmpty()) {
			return "";
		} else
			return result.substring(0, result.length() - 1);
	}

}
