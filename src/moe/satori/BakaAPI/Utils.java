package moe.satori.BakaAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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

	public static String getFileContent(String filePath) {
		try {
			File file = new File(filePath);
			byte bt[] = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(bt);
			fis.close();

			return new String(bt, "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.print("error:" + e.getMessage());
		} catch (IOException e) {
			System.out.print("error:" + e.getMessage());
		} catch (Exception e) {
			System.out.print("error:" + e.getMessage());
		}

		return "";
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

//	@SuppressWarnings("rawtypes")
//	public static String HttpBuildQuery(HashMap<String, String> array) {
//		String reString = null;
//		// 遍历数组形成akey=avalue&bkey=bvalue&ckey=cvalue形式的的字符串
//		Iterator it = array.entrySet().iterator();
//		while (it.hasNext()) {
//			Map.Entry<String, String> entry = (Map.Entry) it.next();
//			String key = entry.getKey();
//			String value = entry.getValue();
//			reString += key + "=" + value + "&";
//		}
//		reString = reString.substring(0, reString.length() - 1);
//		// 将得到的字符串进行处理得到目标格式的字符串
//		try {
//			reString = java.net.URLEncoder.encode(reString, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		reString = reString.replace("%3D", "=").replace("%26", "&");
//		return reString;
//	}
	 public static String HttpBuildQuery(HashMap<String, String> map) {
		 final List<String> list = new ArrayList<>();
		 for (String key : map.keySet()) {
			 list.add(key + "=" + map.get(key)+ "&");
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

	@SuppressWarnings("deprecation")
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
