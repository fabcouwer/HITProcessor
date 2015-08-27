package org.sikuli.design;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.sikuli.design.color.ColorAnalyzer;
import org.sikuli.design.color.NamedColor;

public class ColorFeatureComputer {

	static public Map<NamedColor, Double> computeColorDistribution(BufferedImage input) {
		return ColorAnalyzer.computeColorDistribution(input);
	}
}
