package model.clover;

import java.util.Collection;
import java.util.LinkedList;

import model.clover.pageData.ClassSummary;
import model.clover.pageData.PageData;
import model.clover.sourceFileLines.SourceFileLines;
import model.clover.testTargets.TestTarget;
import model.clover.testTargets.TestTargets;

public class CompilationUnitCoverageSummary {

	//classes in this compilation unit
	private PageData pageData;
	//tests executing this compilation unit
	private TestTargets testTargets;
	//map of source lines executed by test cases
	private SourceFileLines sourceFileLines;
	
	public CompilationUnitCoverageSummary(PageData pageData, TestTargets testTargets, SourceFileLines sourceFileLines) {
		super();
		this.pageData = pageData;
		this.testTargets = testTargets;
		this.sourceFileLines = sourceFileLines;
	}
	
	public String toString(){
		return "PageData: "+ pageData.toString() +
			"TestTargets: "+ testTargets.toString() +
			"SourceFileLines: " + sourceFileLines.toString();
	}
	
	
	//delegators
	
	
	/**
	 * @return classes contained in this compilation unit
	 */
		
	public Collection<ClassSummary> getClasses(){
		return pageData.getClasses();
	}
	
	
	/**
	 * @return the list of the classes defined in this compilation unit
	 */
	
	public Collection<String> getClassNames(){
		LinkedList<String> result = new LinkedList<String>();
		for (ClassSummary summary: pageData.getClasses())
			result.add(summary.getClassName());
		return result;
	}
		
	/**
	 * @return the id of all those tests that execute this compilation unit
	 */
	public Collection<Integer> getTestIds(){
		return testTargets.getTestIds();
	}
	
	/**
	 * @param id of a test
	 * @return a the test corresponding to the given its id
	 */
	public TestTarget getTestTargetById(int id){
		return testTargets.getTestTargetById(id);
	}
	
	/**
	 * @param line of code of the compilation unit 
	 * @return all the tests that execute the given line of code 
	 */
	public int[] getTestIdsCoveringLine(int line) {
		return sourceFileLines.getTestIdsCoveringLine(line);
	}
	
	/**
	 * @return the first line of this compilation unit
	 */
	public int getFirstLine(){
		return 0;
	}
	
	/**
	 * @return the last line of this compilation unit
	 */
	public int getLastLine(){
		return sourceFileLines.size();
	}

}
