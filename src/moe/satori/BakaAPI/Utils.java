package moe.satori.BakaAPI;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;

import com.google.gson.*;
import java.lang.reflect.Method;

public class Utils {

	public static String toJSON(HashMap<String, Object> map) {
		Gson json = new Gson();
		return json.toJson(map);
	}

	public static boolean isClass(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public static String stringMD5(String input) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] inputByteArray = input.getBytes();
			messageDigest.update(inputByteArray);
			byte[] resultByteArray = messageDigest.digest();
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}

	public static String HttpBuildQuery(HashMap<String, String> map) {
		final List<String> list = new ArrayList<>();
		for (String key : map.keySet()) {
			list.add(key + "=" + map.get(key) + "&");
		}
		final int size = list.size();
		final String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		final String result = sb.toString();
		final String content = result.substring(0, result.length() - 1);
		return content;
	}

	public static boolean checkToken(HashMap<String, String> params, String password, HashMap<String, String> headers) {
		String token = headers.get("x-authorizetoken");
		String query = HttpBuildQuery(params);
		String sign = stringMD5(query + "@" + password);
		return token.equals(sign);
	}

	public static HashMap<String, Object> invokeController(String action, String method, Map<String, String> params)
			throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		HashMap<String, Object> map = new HashMap<>();
		if (action == null) {
			map.put("status", 404);
			Bukkit.getServer().getLogger().info(action + "Not Found");
			return map;
		}
		if (method == null) {
			Bukkit.getServer().getLogger().info(method + "Not Found");
			map.put("status", 404);
			return map;
		}
		String classpath = "moe.satori.BakaAPI.Controller." + action;
		if (!isClass(classpath)) {
			Bukkit.getServer().getLogger().info(classpath + "Not Found");
			map.put("status", 404);
			return map;
		}
		Class<?> clz = Class.forName(classpath);
		Object obj = clz.newInstance();
		Method m = clz.getMethod(method, HashMap.class);
		HashMap<String, Object> result = (HashMap<String, Object>) m.invoke(obj, (HashMap<String, String>) params);
		return result;
	}
}
