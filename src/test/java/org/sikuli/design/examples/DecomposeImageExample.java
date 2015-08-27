package org.sikuli.design.examples;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.net.URL;

import javax.imageio.ImageIO;

//import org.sikuli.core.cv.VisionUtils;
import org.sikuli.design.structure.Block;
import org.sikuli.design.structure.ImageDecomposer;
import org.sikuli.design.xycut.XYDecomposer;
import org.sikuli.design.xycut.DefaultXYDecompositionStrategy;
//import org.sikuli.design.structure.ImageDecomposer;
import org.sikuli.design.xycut.XYDecompositionStrategy;
import org.sikuli.design.xycut.XYTextDetector;

//import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class DecomposeImageExample {
	
	public static void main(String[] args) throws IOException {
		
		String inputFileName = "E:/UMD/Research/dataset/english/0.png";
		String outputFileName = "E:/UMD/Research/dataset/xml/0-png.xml";
		
		decomposeSingleImage(inputFileName, outputFileName);
		
	}
	
	public static void decomposeSingleImage(String inputFileName, String outputFileName) throws IOException {
		
		BufferedImage inputImage = ImageIO.read(new File(inputFileName));
//		IplImage gray = VisionUtils.createGrayImageFrom(IplImage.createFrom(inputImage));
					
		XYDecompositionStrategy strategy = new DefaultXYDecompositionStrategy(){

			@Override
			public int getMaxLevel() {
				return 10;
			}

			@Override
			public boolean isSplittingHorizontally() {
				return true;
			}

			@Override
			public boolean isSplittingVertically() {
				return true;
			}

			@Override
			public int getMinSeperatorSize() {
				return 4;
			}
		};
		
		XYDecomposer d = new XYDecomposer();
		Block root = d.decompose(inputImage, strategy);
		
		root.filterOutSmallBlocks();
		
		XYTextDetector td = new XYTextDetector(root,inputImage);
		Block rootWithTextdetected = td.detect();
		
		//rootWithTextdetected.filterOutSmallBlocks();
		
		rootWithTextdetected.toXML(inputFileName, outputFileName);
	}
	
	public static void decomposeImageSet(String inputDir, String outputDir) {
		
		File folder = new File(inputDir);
		File[] files = folder.listFiles();
		
		XYDecompositionStrategy strategy = new DefaultXYDecompositionStrategy(){

			@Override
			public int getMaxLevel() {
				return 10;
			}

			@Override
			public boolean isSplittingHorizontally() {
				return true;
			}

			@Override
			public boolean isSplittingVertically() {
				return true;
			}

			@Override
			public int getMinSeperatorSize() {
				return 4;
			}
		};
		
		ImageDecomposer d = new XYDecomposer();
		
		for (int i=9000; i < files.length; i++)
		{	
			File f = files[i];
			
			System.out.println("Processing " + f.getName());
			//URL imageURL = DecomposeImageExample.class.getResource(f.getAbsolutePath());
			try {
				
				BufferedImage inputImage = ImageIO.read(f);
				
				Block root = d.decompose(inputImage, strategy);
				
				root.filterOutSmallBlocks();
				
				XYTextDetector td = new XYTextDetector(root,inputImage);
				Block rootWithTextdetected = td.detect();
				
				String outputFileName = outputDir+ f.getName().replace('.','-') +".xml";
				rootWithTextdetected.toXML(f.getAbsolutePath(), outputFileName);
				
			} catch (IOException ioe) {
				
				System.out.println("Failed to process: " + f.getName());
			} catch (Exception e) {
				
				System.out.println("Failed to process: " + f.getName() + ". Error: " + e.getMessage());
			}
			
		}
	}
}
