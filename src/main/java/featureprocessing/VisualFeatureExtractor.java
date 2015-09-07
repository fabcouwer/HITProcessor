package featureprocessing;

//import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
//import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
//import static com.googlecode.javacv.cpp.opencv_core.cvSize;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import org.sikuli.design.XYFeatureComputer;
import org.sikuli.design.color.ColorAnalyzer;
import org.sikuli.design.color.NamedColor;
import org.sikuli.design.structure.Block;

import com.googlecode.javacv.cpp.opencv_core.CvScalar;
//import com.googlecode.javacv.cpp.opencv_core.IplImage;

import evaluator.HITEvaluator;

//Gets visual features using the sikuli packages
public class VisualFeatureExtractor {

	// Returns a String representing the visual attributes of HITgroup
	// Format:
	// textGroupCount,imageAreaCount,visualAreaCount,textArea,nonTextArea,w3cPct,hsvPct,colorfulness1,colorfulness2,
	public static String getVisualFeatureString(String groupID) {

		String result = "";
		String inputFileName = HITEvaluator.screenshotFolder + groupID + ".png";
		String xmlFileName = HITEvaluator.xmlFolder + groupID + ".xml";
		try {
			// 1. Decompose image to XML and write result
			File xmlFile = new File(xmlFileName);
			if (!xmlFile.exists()) {
				// Only do this if image has not yet been converted to XML
				ImageDecomposition.decomposeSingleImage(inputFileName,
						xmlFileName);
				System.out.println("Decomposed image.");
			} else {
				System.out
						.println("Image was already decomposed. Continuing..");
			}

			// 2. Read result and get features from it
			result += computeXYFeatures(xmlFileName);

			System.out.println("Computed XY features.");

			// 3. Read other features from image
			result += getColorFeatures(inputFileName);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 4. Return combined visual feature string
		return result;
	}

	private static String getColorFeatures(String inputFileName) {
		String result = "";
		try {
			BufferedImage inputImage = ImageIO.read(new File(inputFileName));

			// w3cPct,hueAvg,satAvg,valAvg,colorfulness1,colorfulness2,

			Double w3c = getW3Cpct(inputImage);

			CvScalar hsvAvg = ColorAnalyzer
					.computeAverageHueSaturationValue(inputImage);
			System.out.println("CvScalar: " + hsvAvg.toString());
			// h = [0], s = [1], v = [2], and [3] is unused.
			String[] hsvResult = hsvAvg.toString().replaceAll("[()]", "")
					.split(",");

			double colorfulness1 = ColorAnalyzer
					.computeColorfulness(inputImage);
			double colorfulness2 = ColorAnalyzer
					.computeColorfulness2(inputImage);

			result += w3c.toString() + "," + hsvResult[0] + "," + hsvResult[1]
					+ "," + hsvResult[2] + "," + colorfulness1 + ","
					+ colorfulness2 + ",";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static double getW3Cpct(BufferedImage inputImage) {
		Map<NamedColor, Double> standardColorMap = ColorAnalyzer
				.computeColorDistribution(inputImage);

		Double total = 0d;
		for (Iterator<NamedColor> i = standardColorMap.keySet().iterator(); i
				.hasNext();) {
			NamedColor key = (NamedColor) i.next();
			total += standardColorMap.get(key);
		}
		return total;
	}

	private static String computeXYFeatures(String xmlFile) {
		// read the file
		File f = new File(xmlFile);
		String result = "";

		try {

			Block rootWithTextdetected = Block.loadFromXml(f.getAbsolutePath());

			int textArea = XYFeatureComputer
					.computeTextArea(rootWithTextdetected);

			int nonTextLeavesArea = XYFeatureComputer
					.computeNonTextLeavesArea(rootWithTextdetected);

			int numOfLeaves = XYFeatureComputer
					.countNumberOfLeaves(rootWithTextdetected);

			int numOfTextGroup = XYFeatureComputer
					.countNumberOfTextGroup(rootWithTextdetected);

			int numOfImageArea = XYFeatureComputer
					.countNumberOfImageArea(rootWithTextdetected);

			/*
			 * Currently unused features
			 * 
			 * int numOf1stLevelNodes = XYFeatureComputer
			 * .countNumberOfNodesInLevel(rootWithTextdetected, 1);
			 * 
			 * int numOf2ndLevelNodes = XYFeatureComputer
			 * .countNumberOfNodesInLevel(rootWithTextdetected, 2);
			 * 
			 * int numOf3rdLevelNodes = XYFeatureComputer
			 * .countNumberOfNodesInLevel(rootWithTextdetected, 3);
			 * 
			 * double percentageLeafArea = XYFeatureComputer
			 * .computePercentageOfLeafArea(rootWithTextdetected);
			 * 
			 * double percentageLeafArea2 = XYFeatureComputer
			 * .computePercentageOfLeafArea2(rootWithTextdetected);
			 * 
			 * int maxLevel = XYFeatureComputer
			 * .computeMaximumDecompositionLevel(rootWithTextdetected);
			 * 
			 * double avgLevel = XYFeatureComputer
			 * .computeAverageDecompositionLevel(rootWithTextdetected);
			 */

			// Returns
			// 'textGroupCount,imageAreaCount,visualAreaCount,textArea,nonTextArea,'
			result += numOfTextGroup + "," + numOfImageArea + "," + numOfLeaves
					+ "," + textArea + "," + nonTextLeavesArea + ",";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}

/*
 * Currently unused inner class
 * 
 * class FeaturesExtractionResult { // Color features double colorfulness1;
 * double colorfulness2; Map<NamedColor, Double> colorDist; double
 * averageHSVHue; double averageHSVSaturation; double averageHSVValue;
 * 
 * // Quadtree decomposition features int quadtreeColorNumberOfLeaves; double
 * quadtreeColorHorizontalSymmetry; double quadtreeColorVerticalSymmetry; double
 * quadtreeColorHorizontalBalance; double quadtreeColorVerticalBalance; double
 * quadtreeColorEquilibrium; int quadtreeIntensityNumberOfLeaves; double
 * quadtreeIntensityHorizontalSymmetry; double
 * quadtreeIntensityVerticalSymmetry; double quadtreeIntensityHorizontalBalance;
 * double quadtreeIntensityVerticalBalance; double quadtreeIntensityEquilibrium;
 * 
 * // XY Decomposition features double textArea; double leavesAreaNoText; double
 * maximumDepth; double averageDepth; int numberOfLeaves; int
 * numberOf1stLevelNodes; int numberOf2ndLevelNodes; int numberOf3rdLevelNodes;
 * double percentageOfLeavesArea; int numberOfTextGroups; int
 * numberOfImageGroups; }
 */