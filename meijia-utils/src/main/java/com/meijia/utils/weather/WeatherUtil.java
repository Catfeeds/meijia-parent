package com.meijia.utils.weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meijia.utils.GsonUtil;
import com.meijia.utils.HttpClientUtil;


/**
 * 云行政的常用方法
 *
 */
public class WeatherUtil {

	private static final String ak = "94db56011c206b4857a65b32e1d0f8b5";

	private static final String weatherUrl = "http://api.map.baidu.com/telematics/v3/weather";

	//获得天气预报数据
	public static String getWeathInfo(String areaCn) {
		String result = "";
		Map<String, String> params = new HashMap<String,String>();
		params.put("location", areaCn);
		params.put("output", "json");
		params.put("ak", ak);
    	
		result = HttpClientUtil.get(weatherUrl, params);
    	return result;
	}
	
	

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		String repo = WeatherUtil.getWeathInfo("安康市");
		System.out.println(repo);
		WeatherInfo weatherInfo = GsonUtil.GsonToObject(repo, WeatherInfo.class);
		
//		if (weatherInfo == null) return false;
//		if (!weatherInfo.getStatus().equals("success")) return false;
		
		String date = weatherInfo.getDate();
		List<WeatherResult> results = weatherInfo.getResults();
		WeatherResult result = results.get(0);
		
		List<WeatherData> weatherDatas = result.getWeather_data();
		List<WeatherIndex> weatherIndexs = result.getIndex();
		
		System.out.println("weatherDatas count = " + weatherDatas.size());
		System.out.println("weatherIndexs count = " + weatherIndexs.size());
		
		String realTemp = "";
		WeatherData curData = weatherDatas.get(0);
		realTemp = curData.getDate();

		realTemp = realTemp.substring(realTemp.indexOf("(") + 1, realTemp.indexOf(")"));
		realTemp = realTemp.substring(realTemp.indexOf("：") + 1);
		System.out.println("realTemp = " +realTemp);
		
		System.out.println(GsonUtil.GsonString(weatherIndexs));
	}
}
