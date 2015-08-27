package org.sikuli.design.xycut;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import org.junit.Test;
import org.sikuli.design.xycut.LineSeparatorExtractor;
import org.sikuli.design.xycut.SeparatorExtractor;

public class SeparatorModelTest {
	private BufferedImage input;	
	
	@Test
	public void testIdentifyLineSeparators(){
		SeparatorExtractor se = new LineSeparatorExtractor(input);
		se.extractFromRegion(new Rectangle(120,60,input.getWidth()/2,input.getHeight()-330));
		
	}
}
