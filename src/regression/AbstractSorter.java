package regression;

import java.util.LinkedList;
import java.util.List;

import model.clover.CompilationUnitCoverageSummary;
import model.clover.JavaScriptData;

public abstract class AbstractSorter {
	
	private List<CompilationUnitCoverageSummary> data;
	
	/**
	 * Run the sorting algorithm 
	 */
	public void run(){
		collectCoverageData("../jabref/report");
		prioritize();
		printSorted();
	}
	

	/**
	 * This method read clover report and store it into a  java data structure 
	 * @param dir directory where to search for clover results
	 */
	public void collectCoverageData(String dir){
		data = new LinkedList<CompilationUnitCoverageSummary>();
		for (JavaScriptData script: collectJavascript(dir))
			data.add( script.getSummary() );
	}
	
	/**
	 * @param dir directory where to search for clover results
	 * @return a list of the java-script containing data
	 */
	public abstract List<JavaScriptData> collectJavascript(String dir);
	
	
	
	/**
	 * Prioritization of the test cases   
	 */
	public abstract void prioritize() ;
	
	/**
	 * Print the sorted tests  
	 */
	public abstract void printSorted() ;


	public List<CompilationUnitCoverageSummary> getSummaries() {
		return data;
	}
}



