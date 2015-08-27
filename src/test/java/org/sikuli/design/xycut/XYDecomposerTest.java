package org.sikuli.design.xycut;

import java.awt.image.BufferedImage;
import org.junit.Test;
import org.sikuli.core.logging.ImageExplainer;
import org.sikuli.design.structure.Block;
import org.sikuli.design.structure.ImageDecomposer;
import org.sikuli.design.xycut.DefaultXYDecompositionStrategy;
import org.sikuli.design.xycut.XYDecomposer;
import org.sikuli.design.xycut.XYTreePainter;

public class XYDecomposerTest {
	
	private BufferedImage input;


   @Test
	public void testXYDecomposerBasic() {
		ImageExplainer explainer = ImageExplainer.getExplainer(XYDecomposer.class);
				
		ImageDecomposer d = new XYDecomposer();		
		explainer.setLevel(ImageExplainer.Level.STEP);
		explainer.step(input, "input for xy decomposition");		

		explainer.setLevel(ImageExplainer.Level.OFF);
		Block root = d.decompose(input, new DefaultXYDecompositionStrategy(){

			@Override
			public int getMaxLevel() {
				return 4;
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

			
		});
		
		explainer.setLevel(ImageExplainer.Level.STEP);
		BufferedImage image = XYTreePainter.paintOnImage(input, root);
		explainer.step(image, "decomposition result");
		
	}
}
