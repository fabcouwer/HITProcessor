package org.sikuli.design.quadtree;

import java.awt.image.BufferedImage;

import org.sikuli.core.cv.ImagePreprocessor;
import org.sikuli.design.structure.EntropyComputer;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class ColorEntropyDecompositionStrategy implements QuadTreeDecompositionStrategy{

	public double computeNodeFeature(IplImage image) {
		return EntropyComputer.computeColorEntropy(image);
	}

	public boolean isNodeDecomposable(double featureValue) {
		return featureValue < 300;
	}
	
	public IplImage preprocess(BufferedImage image) {
		return ImagePreprocessor.createHSV(image);
	}	

}
