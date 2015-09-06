package evaluator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import featureprocessing.HTMLFeatureExtractor;
import featureprocessing.MetadataExtractor;
import featureprocessing.VisualFeatureExtractor;

// Main entry point for HIT evaluator
public class HITEvaluator {

	// REPLACE THESE if you want to run the evaluator yourself!
	private static String baseDir = "D:\\Friso\\Dropbox\\Studie\\Afstuderen\\DATA\\Selected Task Dataset\\";
	private static String targetDir = "D:\\Friso\\Dropbox\\Studie\\Afstuderen\\DATA\\Selected Task Dataset\\data\\output\\";
	public static String HITgroupCSVLocation = baseDir
			+ "data\\HITgroups_smallset_edited.csv";
	public static String HITinstanceCSVLocation = baseDir
			+ "data\\HITinstances_smallset.csv";
	public static String hitcontentLocation = "D:\\Afstuderen\\hitcontent\\hitcontent\\";
	private static String IDfileLocation = baseDir + "\\hitgroup_ids.txt";

	private static ArrayList<String> groupIDs;

	public static void main(String[] args) {

		System.setProperty("jsse.enableSNIExtension", "false");

		// 1. Fill list of IDs and prepare output list
		groupIDs = readAcceptedIDs();

		ArrayList<String> results = new ArrayList<String>();
		results.add("groupID,reward,timeAllotted,location,master,totalApproved,approvalRate,titleLength,descLength,amountKeywords,"
				+ "linkCount,wordCount,imageCount,bodyTextPct,emphTextPct,cssCount,scriptCount,inputCount,"
				+ "textGroupCount,imageAreaCount,visualAreaCount,textAreaCount,nonTextAreaCount,w3cPct,hsvPct,colorfulness1,colorfulness2");

		// 2. For each ID, evaluate that hitgroup
		String currentResult;
		for (String id : groupIDs) {
			currentResult = evaluateHITgroup(id);
			results.add(currentResult);
		}

		// 3. Output the results to file
		// outputToCsv(results, "test.csv");
	}

	// Evaluates features for a single HIT group
	private static String evaluateHITgroup(String groupID) {

		// 1. Initialise result string - we add to this as we find values
		String result = groupID + ",";

		// 2. Retrieve metadata attributes for this HITgroup
		result += MetadataExtractor.getMetaDataString(groupID);

		// 3. Retrieve the HTML page for this HITgroup
		Document doc = null;
		try {
			File f = new File(hitcontentLocation + groupID + "\\" + groupID
					+ ".html");
			doc = Jsoup.parse(f, "UTF-8", "");

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 4. Retrieve content features from this
		if (doc == null) {
			result = "ERROR RETRIEVING HTML FOR ID " + groupID;
		} else {
			result += HTMLFeatureExtractor.getContentFeatureString(doc);

			// 5. Get visual features from the screenshot
			result += VisualFeatureExtractor.getVisualFeatureString(groupID);

			// 6. Retrieve completion statistics from HITinstance CSV
			// TODO

		}

		// 7. Output results as a single CSV-line
		return result;
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
