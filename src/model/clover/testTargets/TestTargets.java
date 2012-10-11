package model.clover.testTargets;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import model.clover.GsonSingleton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestTargets {

	//<test id, test data>
	private HashMap<String ,TestTarget> testTargets;
	private transient Set<Integer> translatedKey;
	private transient static final String prefix = "test_";
	private transient static final int prefixLength = 5;
	
	private TestTargets(HashMap<String ,TestTarget> data){
		testTargets = data;
	}
	
	public static TestTargets fromJson(String json){
		Type mapType = new TypeToken<HashMap<String, TestTarget>>(){}.getType();
		Gson gson = GsonSingleton.getGsonInstance();
		HashMap<String ,TestTarget> data = gson.fromJson(json, mapType);
		return new TestTargets(data);
	}
	
	public String toJson(){
		Gson gson = GsonSingleton.getGsonInstance();
		return gson.toJson(testTargets);
	}

	public TestTarget getTestTargetById(Integer id) {
		return testTargets.get(prefix + id);
	}
	
	public Collection<Integer> getTestIds(){
		if (translatedKey == null) {
			translatedKey = new HashSet<Integer>();
			for (String entry: testTargets.keySet()){
				Integer newEntry = Integer.valueOf(entry.substring(prefixLength));
				translatedKey.add(newEntry);
			}
		}
		return translatedKey;
	}
	
	private Collection<TestTarget> getTestTargets(){
		return testTargets.values();
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		for (Integer name: getTestIds())
			buf.append("(" +prefix + name + ")" + getTestTargetById(name).toString() + "; ");
		buf.append("\n");
		return buf.toString();
	}
	
}
