package org.sikuli.design.xycut;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import com.google.common.collect.Lists;

public class DefaultXYDecompositionStrategy implements XYDecompositionStrategy {		
	
	public interface SeparatorSelectionStrategy{
		List<SeparatorModel> select(List<SeparatorModel> separators);
	}
	
	public static class SeparatorSelectionStrategies {
		static final public SeparatorSelectionStrategy selectTheLargest = new SeparatorSelectionStrategy(){
			public List<SeparatorModel> select(List<SeparatorModel> separators) {
				SeparatorModel bestSeparator = Collections.max(separators, new Comparator<SeparatorModel>(){
					public int compare(SeparatorModel o1, SeparatorModel o2) {
						return Double.compare(o1.getWidth()*o1.getHeight(), o2.getWidth()*o2.getHeight());
					}			
				});
				return Lists.newArrayList(bestSeparator);		
			}
		};
		static final public SeparatorSelectionStrategy selectTheLargestAndSimilarOnes = new SeparatorSelectionStrategy(){
			public List<SeparatorModel> select(List<SeparatorModel> separators) {
							
				List<SeparatorModel> selectedSeparators = selectTheLargest.select(separators);
				SeparatorModel o = selectedSeparators.get(0);
				
				for (SeparatorModel s : separators){
					
					boolean isSimilarThickness = 
						1.0f * Math.min(s.getThickness(),o.getThickness()) / Math.max(s.getThickness(),o.getThickness())
						> 0.5;
					
					if (s != o && Math.abs(s.getLength() - o.getLength()) < 5 
							&& isSimilarThickness){						
						selectedSeparators.add(s);						
					}
				}				
				return selectedSeparators;		
			}
		};
		static final public SeparatorSelectionStrategy selectTheThickest = new SeparatorSelectionStrategy(){
			public List<SeparatorModel> select(List<SeparatorModel> separators) {
				SeparatorModel bestSeparator = Collections.max(separators, new Comparator<SeparatorModel>(){
					public int compare(SeparatorModel o1, SeparatorModel o2) {
						return o1.getThickness() - o2.getThickness();
					}			
				});
				return Lists.newArrayList(bestSeparator);		
			}
		};
	}	
		
	public int getMinSeperatorSize() {
		return 10;
	}
	
	public int getMinStdDev(){
		return 10;
	}
	
	public int getMaxLevel(){
		return 10;
	}
		
	public boolean isSplittingHorizontally(){
		return true;
	}
	
	public boolean isSplittingVertically(){
		return true;
	}
	
	public boolean isSplittingFurther(SeparatorModel largestSeperator, int level){
		return largestSeperator != null 
		&& level < getMaxLevel();
		//&& largestSeperator.getThickness() >= getMinSeperatorSize();
		//&& largestSeperator.getThickness()*largestSeparator.ge >= getMinSeperatorSize();
	}

	public boolean isRemovingBorder() {
		return true;
	}	
	
	public int getMinArea(){
		return 50;
	}
	
	public SeparatorSelectionStrategy getSeparatorSelectionStrategy(){
		return SeparatorSelectionStrategies.selectTheLargestAndSimilarOnes;
	}

	public int getMinHeight() {
		return 5;
	}

	public int getMinWidth() {
		return 5;
	}
	
}