package model.clover;

import com.google.gson.Gson;

public class GsonSingleton {
	
	private static Gson gson;
	
	public static Gson getGsonInstance(){
		if (gson == null)  gson = new Gson();
		return gson;
	}

}
