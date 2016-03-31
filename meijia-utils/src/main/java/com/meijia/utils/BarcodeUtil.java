package com.meijia.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * String常用工具类.. 持续更新ing..
 * 
 * @author dylan
 *
 */
public class BarcodeUtil {

	public static String barcodeKey = "8cca3859e9b222f51c51698f8016e293";
	
	public static String barcodeUrl = "http://apis.baidu.com/3023/barcode/barcode";
	
	@SuppressWarnings("rawtypes")
	public static Map baiduBarCode(String barcode) {
		Map<String, String> result = new HashMap<String, String>();
		
		if (StringUtil.isEmpty(barcode)) return result;
		String httpArg = "barcode="+ barcode;
		String res = requestBarCode(barcodeUrl, httpArg);
		
		System.out.println(res);
		if (res.indexOf("error") >= 0 || res.indexOf("invalid") >=0) return result;
		
		result = GsonUtil.GsonToMaps(res);
		result.put("res", res);
		
		return result;
	}
	
	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String requestBarCode(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  barcodeKey);
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	public static void main(String[] args) 
			throws Exception {
//		System.out.println(BarcodeUtil.baiduBarCode("6922711074757"));
	}
	

}
