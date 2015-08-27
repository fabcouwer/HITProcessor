package org.sikuli.design;

import java.awt.Point;
import java.awt.Rectangle;

import org.sikuli.design.quadtree.Quadtree;
import org.sikuli.design.quadtree.QuadtreeFeatures;
import org.sikuli.design.structure.BinaryImageStructureFeatureComputer;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class QuadtreeFeatureComputer {

	static public double computeHorizontalSymmetry(Quadtree root){		
		return computeSymmetryAlongAxis(root,1);
	}
	
	static public double computeVerticalSymmetry(Quadtree root){		
		return computeSymmetryAlongAxis(root,0);
	}
	
	static private double computeSymmetryAlongAxis(Quadtree root, int axis){
		IplImage binary = root.createBinaryRepresentation();
		return BinaryImageStructureFeatureComputer.computeSymmetryAlongAxis(binary,axis);
	}
	
	
	static public double computeVerticalBalance(Quadtree root){
		IplImage binary = root.createBinaryRepresentation();
		return BinaryImageStructureFeatureComputer.computeVerticalBalance(binary);
	}
	
	static public double computeHorizontalBalance(Quadtree root){
		IplImage binary = root.createBinaryRepresentation();
		return BinaryImageStructureFeatureComputer.computeHorizontalBalance(binary);
	}
	
	static public double computeEquilibrium(Quadtree qt) {
		Rectangle roi = qt.getRoot().getROI();
		Point center = new Point((int)roi.getCenterX(), (int)roi.getCenterY());
		
		return computeEquilibrium(qt, center);
	}
	
	static public double computeEquilibrium(Quadtree root, Point centerOfImage) {
		IplImage binary = root.createBinaryRepresentation();
		Point centerOfMass = BinaryImageStructureFeatureComputer.computeMassCenter(binary);
		
		double width = centerOfImage.x * 2;
		double height = centerOfImage.y * 2;
		
		double equilibrium = 1 - (
				(2*(Math.abs(centerOfMass.x-centerOfImage.x)))/width + 
				(2*(Math.abs(centerOfMass.y-centerOfImage.y)))/height
				) / 2;
		
		return equilibrium;
	}

	
	static public QuadtreeFeatures computeAllFeatures(Quadtree root) {
		QuadtreeFeatures f = new QuadtreeFeatures();
		
		f.horizontalSymmetry = computeHorizontalSymmetry(root);
		f.verticalSymmetry = computeVerticalSymmetry(root);
		f.horizontalBalance = computeHorizontalBalance(root);
		f.verticalBalance = computeVerticalBalance(root);
		f.equilibrium = computeEquilibrium(root);
		f.numLeafs = root.countLeaves();
		
		return f;
	}
}

