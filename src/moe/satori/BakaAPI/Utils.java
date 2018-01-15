package moe.satori.BakaAPI;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import moe.satori.BakaAPI.Controller.*;
import org.json.JSONObject;
import java.lang.reflect.Method;

public class Utils {
	
	public static String toJSON(HashMap<String, Object> map) {
		JSONObject json = new JSONObject();
		for (String key : map.keySet()) {
			json.put(key,map.get(key));
        }
		return json.toString();
	}
	public static boolean isClass(String className) {
	    try  {
	        Class.forName(className);
	        return true;
	    }  catch (ClassNotFoundException e) {
	        return false;
	    }
	}
	public static HashMap<String,Object> invokeController(String controller_name,Map<String,String> params) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		if(controller_name == null) return null;
		HashMap<String, Object> map = new HashMap<>();
		String classpath = "moe.satori.BakaAPI.Controller." + controller_name;
		if(!isClass(classpath)){
    			map.put("status", 200);
    			return map;
		}
		Class<?> clz = Class.forName(classpath);
		Object obj = clz.newInstance();
		Method method = clz.getMethod("getResult", HashMap.class);
        HashMap result = (HashMap) method.invoke(obj,(HashMap) params);
		return result;
	}
}
