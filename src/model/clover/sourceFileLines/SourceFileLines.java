package model.clover.sourceFileLines;

import java.util.Arrays;
import java.util.Iterator;

import model.clover.GsonSingleton;

import com.google.gson.Gson;

public class SourceFileLines implements Iterable<int[]>{
	
	
	// [source line, test that cover such line]
	private int[][] sourceFileLines;
	
	private SourceFileLines(int[][] values){
		sourceFileLines = values;
	}

	public int[] getTestIdsCoveringLine(int line) {
		return sourceFileLines[line];
	}
	
	public int size(){
		return sourceFileLines.length;
	}

	public static SourceFileLines fromJson(String json) {
		Gson gson = GsonSingleton.getGsonInstance();
		int[][] data = new int[0][0];
		data = gson.fromJson(json, data.getClass());
		return new SourceFileLines(data);
	}
	
	public String toJson(){
		Gson gson = GsonSingleton.getGsonInstance();
		return gson.toJson(sourceFileLines);
	}

	@Override
	public Iterator<int[]> iterator() {
		return Arrays.asList(sourceFileLines).iterator();
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<sourceFileLines.length; i++){
			buffer.append(" " + i + "->[");
			for (int j:sourceFileLines[i])
				buffer.append(j+", ");
			buffer.append("]");
		}
		buffer.append("\n");
		return buffer.toString();
	}
	

}
