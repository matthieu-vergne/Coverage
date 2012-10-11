package model.clover.pageData;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import model.clover.GsonSingleton;

import com.google.gson.Gson;


public class PageData implements Iterable<ClassSummary>{
	
	private ClassSummary[] classes;
	
	public ClassSummary getClassAt(int index) {
		return classes[index];
	}
	
	public Collection<ClassSummary> getClasses() {
		return Arrays.asList(classes);
	}

	public PageData(){}
	
	public static PageData fromJson(String json){
		Gson gson = GsonSingleton.getGsonInstance();
		return gson.fromJson(json, PageData.class);
	}
	
	public String toJson(){
		Gson gson = GsonSingleton.getGsonInstance();
		return gson.toJson(this);
	}

	@Override
	public Iterator<ClassSummary> iterator() {
		return Arrays.asList(classes).iterator();
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for (ClassSummary summary: this)
			buffer.append(summary.toString());
		//buffer.append("\n");
		return buffer.toString();
	}

}
