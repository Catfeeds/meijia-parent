package com.meijia.utils.weather;

import java.util.HashMap;
import java.util.Iterator;
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
	
//	//根据百度天气图标，获取自定义图标
//	public static String getCustomDayWeatherIcon(String imgUrl) {
//		String customIconUrl = "";
//		
//		switch (imgUrl) {
//			case "http://api.map.baidu.com/images/weather/day/baoxue.png" :
//				customIconUrl = "http://img.51xingzheng.cn/dca0a8f334dd0c65ba4238fa434ca1c2?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/baoyu.png" :
//				customIconUrl = "http://img.51xingzheng.cn/2bc8a409900fb115889190c4f81611fd?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/baoyuzhuandabaoyu.png" :
//				customIconUrl = "http://img.51xingzheng.cn/dca0a8f334dd0c65ba4238fa434ca1c2?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/dabaoyu.png" :
//				customIconUrl = "http://img.51xingzheng.cn/dca0a8f334dd0c65ba4238fa434ca1c2?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/dabaoyuzhuantedabaoyu.png" :
//				customIconUrl = "http://img.51xingzheng.cn/dca0a8f334dd0c65ba4238fa434ca1c2?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/daxue.png" :
//				customIconUrl = "http://img.51xingzheng.cn/01ade7d3938d139f405b4449286e3418?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/daxuezhuanbaoxue.png" :
//				customIconUrl = "http://img.51xingzheng.cn/01ade7d3938d139f405b4449286e3418?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/dayu.png" :
//				customIconUrl = "http://img.51xingzheng.cn/dca0a8f334dd0c65ba4238fa434ca1c2?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/" :
//				customIconUrl = "http://img.51xingzheng.cn/dca0a8f334dd0c65ba4238fa434ca1c2?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/" :
//				customIconUrl = "http://img.51xingzheng.cn/dca0a8f334dd0c65ba4238fa434ca1c2?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/" :
//				customIconUrl = "http://img.51xingzheng.cn/dca0a8f334dd0c65ba4238fa434ca1c2?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/" :
//				customIconUrl = "http://img.51xingzheng.cn/dca0a8f334dd0c65ba4238fa434ca1c2?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/" :
//				customIconUrl = "http://img.51xingzheng.cn/dca0a8f334dd0c65ba4238fa434ca1c2?p=0";
//				break;
//			case "http://api.map.baidu.com/images/weather/day/" :
//				customIconUrl = "http://img.51xingzheng.cn/dca0a8f334dd0c65ba4238fa434ca1c2?p=0";
//				break;	
//		}
//		
//		return customIconUrl;
//	}
//	
//	

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		String repo = WeatherUtil.getWeathInfo("北京市");
		System.out.println(repo);
		WeatherInfoVo weatherInfo = GsonUtil.GsonToObject(repo, WeatherInfoVo.class);
		
//		if (weatherInfo == null) return false;
//		if (!weatherInfo.getStatus().equals("success")) return false;
		
		String date = weatherInfo.getDate();
		List<WeatherResultVo> results = weatherInfo.getResults();
		WeatherResultVo result = results.get(0);
		
		List<WeatherDataVo> weatherDatas = result.getWeather_data();
		List<WeatherIndexVo> weatherIndexs = result.getIndex();
		
		System.out.println("weatherDatas count = " + weatherDatas.size());
		System.out.println("weatherIndexs count = " + weatherIndexs.size());
		
		String realTemp = "";
		WeatherDataVo curData = weatherDatas.get(0);
		realTemp = curData.getDate();

		realTemp = realTemp.substring(realTemp.indexOf("(") + 1, realTemp.indexOf(")"));
		realTemp = realTemp.substring(realTemp.indexOf("：") + 1);
		System.out.println("realTemp = " +realTemp);
		
		System.out.println(GsonUtil.GsonString(weatherIndexs));
		
		
		List<WeatherIndexVo> weatherIndexsObj = GsonUtil.GsonToList(GsonUtil.GsonString(weatherIndexs), WeatherIndexVo.class);
		
		for (Iterator it = weatherIndexsObj.iterator(); it.hasNext();) {
			WeatherIndexVo option = (WeatherIndexVo) it.next();
		    System.out.println(option.getTitle());
		}

	}
}
