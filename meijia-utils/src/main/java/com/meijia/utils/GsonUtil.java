package com.meijia.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


public class GsonUtil {
	private static Gson gson = null;
	static {
		if (gson == null) {
			gson = new Gson();
		}
	}

	private GsonUtil() {
	}

	/**
	 * 转成json
	 * 
	 * @param object
	 * @return
	 */
	public static String GsonString(Object object) {
		String gsonString = null;
		if (gson != null) {
			gsonString = gson.toJson(object);
		}
		return gsonString;
	}

	/**
	 * 转成bean
	 * 
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T> T GsonToObject(String gsonString, Class<T> cls) {
		T t = null;
		if (gson != null) {
			String utf8 = "";
			try {
				utf8 = new String(gsonString.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			t = gson.fromJson(utf8, cls);
		}
		return t;
	}

	/**
	 * 转成list
	 * 
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
//		List<T> list = null;
//		if (gson != null) {
//			list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
//			}.getType());
//		}
//		return list;

		List<T> list = new ArrayList<T>();
		
		String utf8 = "";
		try {
			utf8 = new String(gsonString.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JsonArray array = new JsonParser().parse(utf8).getAsJsonArray();
		for (final JsonElement elem : array) {
			list.add(new Gson().fromJson(elem, cls));
		}
		
		return list;
	}

	/**
	 * 转成list中有map的
	 * 
	 * @param gsonString
	 * @return
	 */
	public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
		List<Map<String, T>> list = null;
		if (gson != null) {
			list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
			}.getType());
		}
		return list;
	}

	/**
	 * 转成map的
	 * 
	 * @param gsonString
	 * @return
	 */
	public static <T> Map<String, T> GsonToMaps(String gsonString) {
		Map<String, T> map = null;
		if (gson != null) {
			map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
			}.getType());
		}
		return map;
	}
	
	
	public static Map<String, Object> sortMapByKey(Map<String, Object> oriMap) {  
	    if (oriMap == null || oriMap.isEmpty()) {  
	        return null;  
	    }  
	    Map<String, Object> sortedMap = new TreeMap<String, Object>(new Comparator<String>() {  
	        public int compare(String key1, String key2) {  
	            int intKey1 = 0, intKey2 = 0;  
	            try {  
	                intKey1 = getInt(key1);  
	                intKey2 = getInt(key2);  
	            } catch (Exception e) {  
	                intKey1 = 0;   
	                intKey2 = 0;  
	            }  
	            return intKey1 - intKey2;  
	        }});  
	    sortedMap.putAll(oriMap);  
	    return sortedMap;  
	}  
	
	private static int getInt(String str) {  
	    int i = 0;  
	    try {  
	        Pattern p = Pattern.compile("^\\d+");  
	        Matcher m = p.matcher(str);  
	        if (m.find()) {  
	            i = Integer.valueOf(m.group());  
	        }  
	    } catch (NumberFormatException e) {  
	        e.printStackTrace();  
	    }  
	    return i;  
	}  

	public static void main(String[] args) {
		String jsonStr = "{\"attrName\": \"\", \"findPatten\": \"title\", \"removeRegex\": \"\", \"resultIndex\": 0, \"resultRegex\": \"\", \"matchCorrect\": \"智联简历\"}";
		
		Map<String, String> result = GsonUtil.GsonToMaps(jsonStr);
		
		System.out.println(result.toString());
	}
}
