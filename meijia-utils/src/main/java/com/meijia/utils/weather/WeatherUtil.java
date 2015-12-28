package com.meijia.utils.weather;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.FileUtil;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.HttpClientUtil;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.baidu.DwzUtil;



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
	
//	
//	
	@SuppressWarnings("unchecked")
	public static void genWeatherIconList(String filePath) throws IOException {
		String imgServerUrl = "http://img.51xingzheng.cn";
		String url = imgServerUrl + "/upload/";
		File f = new File(filePath);
		File[] files = f.listFiles();
		List<Map> fileList = new ArrayList<Map>();
		InputStream in = null;
		for (int i=0;i< files.length;i++) {
			String fileName = files[i].getName();
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
			fileType = fileType.toLowerCase();
			
			byte[] fileBytes = FileUtil.getBytes(files[i].getAbsolutePath());
			System.out.println(fileBytes.length);
			String sendResult = ImgServerUtil.sendPostBytes(url, fileBytes, fileType);
			
			ObjectMapper mapper = new ObjectMapper();

			HashMap<String, Object> o = mapper.readValue(sendResult,HashMap.class);

			String ret = o.get("ret").toString();

			HashMap<String, String> info = (HashMap<String, String>) o.get("info");

			String imgUrl = imgServerUrl + "/" + info.get("md5").toString();
			
			String imgShortUrl  = imgUrl;
			
			String imgMinUrl = imgUrl + "?w=100&h=100";
			
			String imgMinShortUrl = imgMinUrl;
			System.out.println("-------------------------------start");
			System.out.println(fileName);
			
			HashMap<String, String> item = new HashMap<String, String>();
			item.put("imgName", fileName);
			item.put("imgUrl", imgUrl);
			item.put("imgShortUrl", imgShortUrl);
			item.put("imgMinUrl", imgMinUrl);
			item.put("imgMinShortUrl", imgMinShortUrl);
			fileList.add(item);
			
			System.out.println("+++++++++++++++++++++++++++++++++end");
		}
		
		String gsonString = GsonUtil.GsonString(fileList);
		System.out.println(gsonString);
//		List<HashMap> fileList1 = GsonUtil.GsonToList(gsonString, HashMap.class);
//		
//		
//		for (int i = 0; i < fileList1.size(); i++) {
//			HashMap<String, String> item = (HashMap<String, String>) fileList1.get(i);
//			
//			System.out.println(item.get("fileName").toString() + "----" +item.get("fileShortUrl").toString());
//		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		String dayPictureUrl = "http://api.map.baidu.com/images/weather/day/mai.png";
		dayPictureUrl = dayPictureUrl.replace("http://api.map.baidu.com", "http://123.57.173.36");
		dayPictureUrl = dayPictureUrl.replace("png", "jpg");
		System.out.println(dayPictureUrl);
//		String repo = WeatherUtil.getWeathInfo("北京市");
//		System.out.println(repo);
//		WeatherInfoVo weatherInfo = GsonUtil.GsonToObject(repo, WeatherInfoVo.class);
//		
////		if (weatherInfo == null) return false;
////		if (!weatherInfo.getStatus().equals("success")) return false;
//		
//		String date = weatherInfo.getDate();
//		List<WeatherResultVo> results = weatherInfo.getResults();
//		WeatherResultVo result = results.get(0);
//		
//		List<WeatherDataVo> weatherDatas = result.getWeather_data();
//		List<WeatherIndexVo> weatherIndexs = result.getIndex();
//		
//		System.out.println("weatherDatas count = " + weatherDatas.size());
//		System.out.println("weatherIndexs count = " + weatherIndexs.size());
//		
//		String realTemp = "";
//		WeatherDataVo curData = weatherDatas.get(0);
//		realTemp = curData.getDate();
//
//		realTemp = realTemp.substring(realTemp.indexOf("(") + 1, realTemp.indexOf(")"));
//		realTemp = realTemp.substring(realTemp.indexOf("：") + 1);
//		System.out.println("realTemp = " +realTemp);
//		
//		System.out.println(GsonUtil.GsonString(weatherIndexs));
//		
//		
//		List<WeatherIndexVo> weatherIndexsObj = GsonUtil.GsonToList(GsonUtil.GsonString(weatherIndexs), WeatherIndexVo.class);
//		
//		for (Iterator it = weatherIndexsObj.iterator(); it.hasNext();) {
//			WeatherIndexVo option = (WeatherIndexVo) it.next();
//		    System.out.println(option.getTitle());
//		}
		
		//测试用
//		WeatherUtil.genWeatherIconList("/Users/lnczx/Downloads/3d_180/night");
		
	}
}
