package org.sikuli.design.xycut;

import org.sikuli.design.structure.DecompositionStrategy;
import org.sikuli.design.xycut.DefaultXYDecompositionStrategy.SeparatorSelectionStrategy;

public interface XYDecompositionStrategy extends DecompositionStrategy {

	public abstract int getMinSeperatorSize();

	public abstract int getMinStdDev();
	
	public abstract int getMinArea();
	
	public abstract int getMinHeight();
	
	public abstract int getMinWidth();

	public abstract boolean isSplittingHorizontally();

	public abstract boolean isSplittingVertically();

	public abstract boolean isSplittingFurther(SeparatorModel largestSeperator,
			int level);

	public abstract boolean isRemovingBorder();

	public abstract SeparatorSelectionStrategy getSeparatorSelectionStrategy();
	

}