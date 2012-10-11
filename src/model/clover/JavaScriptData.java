package model.clover;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import model.clover.pageData.PageData;
import model.clover.sourceFileLines.SourceFileLines;
import model.clover.testTargets.TestTargets;

public class JavaScriptData {
	
	private BufferedReader reader;
	private CompilationUnitCoverageSummary resultCache;
	private Boolean isDataCache;
	
	public JavaScriptData(Reader reader) {
		this.reader = new BufferedReader(reader);
		this.resultCache = null;
		this.isDataCache = null;
	}

	/**
	 * @return the summary contained in the java-script source file
	 */
	public CompilationUnitCoverageSummary getSummary(){
		if (resultCache == null)
			resultCache = readFromJavascript();		
		return resultCache;
	}
	
	/**
	 * @return whether this script contains data
	 */
	public boolean isDataScript(){
		if (isDataCache == null) try{
			String firstLine = reader.readLine();
			isDataCache = firstLine.trim().matches("var clover = new Object\\(\\);");
		}
		catch(IOException e) {isDataCache = false;}
		return isDataCache;
	}
	
	
	/**
	 * @return the summary data contained in this java-script, if the script does
	 * not contain data, @code null is returned
	 */
	private CompilationUnitCoverageSummary readFromJavascript() {
		if (! isDataScript() ) return null;
		String line;
		int cas = 0;
		PageData pageData = null;
		TestTargets testTargets  = null;
		SourceFileLines sourceFileLines  = null;
		try {
			while ((line =reader.readLine())!= null){
				if (line.matches(".*clover\\..*=.*")){
					cas++;
					int pos = line.indexOf("{");
					if (pos<0) pos = line.indexOf("[");
					if (pos <0) continue; 
					String jsonString = line.substring(pos);
					switch(cas) {
					case 1:
						pageData = PageData.fromJson(jsonString);
						break;
					case 2:
						testTargets = TestTargets.fromJson(jsonString);
						break;
					case 3:
						sourceFileLines = SourceFileLines.fromJson(jsonString);
						break;
					}
				}
			}
			reader.close();
		} 
		catch (IOException e) { e.printStackTrace(); }
		//catch (IndexOutOfBoundsException e1) {	e1.printStackTrace(); }
		if (pageData != null && sourceFileLines != null && testTargets!= null)
			return new CompilationUnitCoverageSummary(pageData, testTargets, sourceFileLines);
		else 
			return null;
	}
	

}
