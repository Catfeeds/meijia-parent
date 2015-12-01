package com.meijia.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.cloopen.rest.sdk.utils.encoder.BASE64Encoder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 云行政的常用方法
 *
 */
public class WeatherUtil {

	private static final String appId = "c77709132bf12a7d";

	private static final String privateKey = "73aaa3_SmartWeatherAPI_50d4247";

	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";

	private static final String url_header = "http://open.weather.com.cn/data/?";

	/**
	 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
	 * 
	 * @param encryptText
	 *            被签名的字符串
	 * @param encryptKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] HmacSHA1Encrypt(String url, String privatekey) throws Exception {
		byte[] data = privatekey.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);

		byte[] text = url.getBytes(ENCODING);
		// 完成 Mac 操作
		return mac.doFinal(text);
	}

	/**
	 * 获取URL通过privatekey加密后的码
	 * 
	 * @param url
	 * @param privatekey
	 * @return
	 * @throws Exception
	 */
	private static String getKey(String url, String privatekey) throws Exception {
		byte[] key_bytes = HmacSHA1Encrypt(url, privatekey);
		return URLEncoder.encode(new BASE64Encoder().encode(key_bytes), ENCODING);
	}

	/**
	 * 组装url的地址
	 * 
	 * @param areaid
	 *            地区id
	 * @param type
	 *            数据类型
	 * @param date
	 *            时间
	 * @return
	 * @throws Exception
	 */
	private static String getInstanceURL(String areaid, String type, String date) throws Exception {
		String keyurl = url_header + "areaid=" + areaid + "&type=" + type + "&date=" + date + "&appid=";
		String key = getKey(keyurl + appId, privateKey);
		String appid6 = appId.substring(0, 6);
		return keyurl + appid6 + "&key=" + key;
	}

	/**
	 * 获取访问URL
	 * 
	 * @param areaid
	 *            地区编号
	 * @param type
	 *            获取类型数: 天气指数：index_f(基础) 、 index_v(常规) 3 天常规预报 (24 小时
	 *            ):forecast_f(基础 ) 、forecast_v (常规)
	 * @return
	 */
	public static String getActionURL(String areaid, String type) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmm");
		String date = dateFormat.format(new Date());
		try {
			return getInstanceURL(areaid, type, date);
		} catch (Exception e) {
		}
		return null;
	}

	// 风向编码
	public static String getFl(String code) {
		String name = "";

		switch (code) {
		case "0":
			name = "无持续风向";
			break;
		case "1":
			name = "东北风";
			break;
		case "2":
			name = "东风";
			break;
		case "3":
			name = "东南风";
			break;
		case "4":
			name = "南风";
			break;
		case "5":
			name = "西南风";
			break;
		case "6":
			name = "西风";
			break;
		case "7":
			name = "西北风";
			break;
		case "8":
			name = "北风";
			break;
		case "9":
			name = "旋转风";
			break;
		default:
			name = "";
		}
		return name;
	}

	// 风力编码
	public static String getFx(String code) {
		String name = "";

		switch (code) {
		case "0":
			name = "微风";
			break;
		case "1":
			name = "3-4级";
			break;
		case "2":
			name = "4-5级";
			break;
		case "3":
			name = "5-6级";
			break;
		case "4":
			name = "6-7级";
			break;
		case "5":
			name = "7-8级";
			break;
		case "6":
			name = "8-9级";
			break;
		case "7":
			name = "9-10级";
			break;
		case "8":
			name = "10-11级";
			break;
		case "9":
			name = "11-12级";
			break;
		default:
			name = "";
		}
		return name;
	}

	// 天气现象编码表
	public static String getWheaterName(String code) {
		String name = "";

		switch (code) {
		case "0":
			name = "晴";
			break;
		case "1":
			name = "多云";
			break;
		case "2":
			name = "阴";
			break;
		case "3":
			name = "阵雨";
			break;
		case "4":
			name = "雷阵雨";
			break;
		case "5":
			name = "雷阵雨伴有冰雹";
			break;
		case "6":
			name = "雨夹雪";
			break;
		case "7":
			name = "小雨";
			break;
		case "8":
			name = "中雨";
			break;
		case "9":
			name = "大雨";
			break;
		case "10":
			name = "暴雨";
			break;
		case "11":
			name = "大暴雨";
			break;
		case "12":
			name = "特大暴雨";
			break;
		case "13":
			name = "阵雪";
			break;
		case "14":
			name = "小雪";
			break;
		case "15":
			name = "中雪";
			break;
		case "16":
			name = "大雪";
			break;
		case "17":
			name = "暴雪";
			break;
		case "18":
			name = "雾";
			break;
		case "19":
			name = "冻雨";
			break;
		case "20":
			name = "沙尘暴";
			break;
		case "21":
			name = "小到中雨";
			break;
		case "22":
			name = "中到大雨";
			break;
		case "23":
			name = "大到暴雨";
			break;
		case "24":
			name = "暴雨到大暴雨";
			break;
		case "25":
			name = "大暴雨到特大暴雨";
			break;
		case "26":
			name = "小到中雪";
			break;
		case "27":
			name = "中到大雪";
			break;
		case "28":
			name = "大到暴雪";
			break;
		case "29":
			name = "浮尘";
			break;
		case "30":
			name = "扬沙";
			break;
		case "31":
			name = "强沙尘暴";
			break;
		case "53":
			name = "霾";
			break;
		case "99":
			name = "无";
			break;
		default:
			name = "";
		}
		return name;
	}
	
	//获得天气预报数据
	public static String getWeathInfo(String areaId, String type) {
		String result = "";
		String interfaceURL = getActionURL(areaId, type);
    	CloseableHttpClient client = HttpClientBuilder.create().build(); 
    	try {
    		HttpGet request = new HttpGet(interfaceURL);  
    		request.addHeader(HttpHeaders.ACCEPT, "application/json;charset=utf-8");
    		CloseableHttpResponse response = client.execute(request);
    		result = EntityUtils.toString(response.getEntity(),"utf-8");  
            response.close();
           
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return result;
	}
	
	

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		//天气指数
		String weatherIndex = WeatherUtil.getWeathInfo("101010200", "index_v");
		
		System.out.println(weatherIndex);
		
		weatherIndex = "[" + weatherIndex + "]";

		JSONArray array = JSONArray.fromObject(weatherIndex);

        // 获取JSONArray的JSONObject对象，便于读取array里的键值对
        JSONObject i = array.getJSONObject(0); 
		
        System.out.println(i.get("i").toString());
        
        JSONArray array1 = JSONArray.fromObject(i.get("i").toString());
        
        for (int j = 0 ; j < array1.size(); j++) {
        	JSONObject item = array1.getJSONObject(j);
        	System.out.println(item.get("i2").toString());
        }
		
		//天气预报数据
		String weatherInfo = WeatherUtil.getWeathInfo("101010200", "forecast_v");
		System.out.println(weatherInfo);
		
		weatherInfo = "[" + weatherInfo + "]";
		JSONArray cArray = JSONArray.fromObject(weatherInfo);
		
		JSONObject fObject = cArray.getJSONObject(0);
		
		System.out.println("f = " + fObject.get("f").toString());
		JSONArray fArray = JSONArray.fromObject("[" + fObject.get("f").toString() + "]");
//		
		JSONObject f = fArray.getJSONObject(0);
		System.out.println("f1 = " + f.get("f1").toString());
		System.out.println("f0 = " + f.get("f0").toString());
		
		JSONArray f1Array = JSONArray.fromObject( f.get("f1").toString());
		
		System.out.println(f1Array.size());
		JSONObject item = f1Array.getJSONObject(0);
		System.out.println(item.get("fa").toString());
		

		
		
		
		
	}
}
