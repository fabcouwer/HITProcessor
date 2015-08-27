package org.sikuli.design.examples;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.sikuli.design.QuadtreeFeatureComputer;
import org.sikuli.design.quadtree.QuadTreeDecomposer;
import org.sikuli.design.quadtree.Quadtree;

public class QuadtreeDecompositionExample {

	public static void main(String[] args) throws IOException {

		File folder = new File(
				"C:\\Users\\Friso\\Desktop\\sikuli-design\\sikuli-design\\src\\test\\resources");
		File[] files = folder.listFiles();

		for (File file : files) {
			if (file.getAbsolutePath().endsWith(".png")) {
				BufferedImage inputImage = ImageIO.read(file);

				QuadTreeDecomposer qtd = QuadTreeDecomposer
						.newColorDecomposer();
				Quadtree root = qtd.decompose(inputImage);

				double hSymmetry = QuadtreeFeatureComputer
						.computeHorizontalSymmetry(root), vSymmetry = QuadtreeFeatureComputer
						.computeVerticalSymmetry(root), hBalance = QuadtreeFeatureComputer
						.computeHorizontalBalance(root), vBalance = QuadtreeFeatureComputer
						.computeVerticalBalance(root);

				System.out.println(file.getName() + "," + hSymmetry + ","
						+ vSymmetry + "," + hBalance + "," + vBalance);
			}
		}

	}
}
