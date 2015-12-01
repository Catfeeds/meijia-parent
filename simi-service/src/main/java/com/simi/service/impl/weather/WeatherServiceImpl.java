package com.simi.service.impl.weather;

import java.io.IOException;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.StringUtil;
import com.meijia.utils.WeatherUtil;
import com.simi.service.weather.WeatherIndexService;
import com.simi.service.weather.WeatherService;
import com.simi.po.model.weather.WeatherIndex;
import com.simi.po.model.weather.Weathers;
import com.simi.po.dao.weather.WeathersMapper;

@Service
public class WeatherServiceImpl implements WeatherService {
	
	@Autowired
	WeatherIndexService weatherIndexService;
	
	@Autowired
	WeathersMapper weathersMapper;
		
	@Override
	public Weathers initWeather() {
		Weathers record = new Weathers();
		record.setId(0L);
		record.setAreaId(0L);
		record.setAreaCn("");
		record.setFlDay("");
		record.setFlNight("");
		record.setFxDay("");
		record.setFxNight("");
		record.setPublicTime("");
		record.setSunRiseDown("");
		record.setTempDay("");
		record.setTempNight("");
		record.setWeatherDay("");
		record.setWeatherNight("");
		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return weathersMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int insert(Weathers record) {
		return weathersMapper.insert(record);
	}
	
	@Override
	public int insertSelective(Weathers record) {
		return weathersMapper.insertSelective(record);
	}

	@Override
	public Weathers selectByPrimaryKey(Long id) {
		return weathersMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKey(Weathers record) {
		return weathersMapper.updateByPrimaryKey(record);
	}

	@Override
	public Weathers selectByAreaIdAndPublishTime(Long areaId, String publishTime) {
		return weathersMapper.selectByAreaIdAndPublishTime(areaId, publishTime);
	}
	
	@Override
	public Weathers selectByAreaId(Long areaId) {
		return weathersMapper.selectByAreaId(areaId);
	}	

	@Override
	public int updateByPrimaryKeySelective(Weathers record) {
		return weathersMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 定时获取天气api数据，并存入数据库
	 * http://openweather.weather.com.cn/Home/Help/zhishu.html
	 * @param areaId,  areaCn
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean getWeather(String areaId, String areaCn) {
		
		String weatherInfo = WeatherUtil.getWeathInfo(areaId, "forecast_v");
		
		if (StringUtil.isEmpty(weatherInfo)) return false;
		
		
		weatherInfo = "[" + weatherInfo + "]";
		JSONArray cArray = JSONArray.fromObject(weatherInfo);
		
		JSONObject fObject = cArray.getJSONObject(0);
		
//		System.out.println("f = " + fObject.get("f").toString());
		JSONArray fArray = JSONArray.fromObject("[" + fObject.get("f").toString() + "]");
//		
		JSONObject f = fArray.getJSONObject(0);
//		System.out.println("f1 = " + f.get("f1").toString());
//		System.out.println("f0 = " + f.get("f0").toString());
		
		String f0 = f.get("f0").toString();
		JSONArray f1Array = JSONArray.fromObject( f.get("f1").toString());
		
//		System.out.println(f1Array.size());
		JSONObject f1 = f1Array.getJSONObject(0);
//		System.out.println(item.get("fa").toString());		
			
		Weathers weather = selectByAreaId(Long.valueOf(areaId));
			
		if (weather == null) {
			weather = initWeather();
		}

		weather.setAreaId(Long.valueOf(areaId));
		weather.setAreaCn(areaCn);
		weather.setPublicTime(f0);
			
		if (!StringUtil.isEmpty(f1.get("fa").toString())) {
			weather.setWeatherDay(f1.get("fa").toString());
		}
		
		if (!StringUtil.isEmpty(f1.get("fb").toString())) {
			weather.setWeatherNight(f1.get("fb").toString());
		}
		
		if (!StringUtil.isEmpty(f1.get("fc").toString())) {
			weather.setTempDay(f1.get("fc").toString());
		}
		
		
		if (!StringUtil.isEmpty(f1.get("fd").toString())) {
			weather.setTempNight(f1.get("fd").toString());
		}
		
		if (!StringUtil.isEmpty(f1.get("fe").toString())) {
			weather.setFxDay(f1.get("fe").toString());
		}
		
		if (!StringUtil.isEmpty(f1.get("ff").toString())) {
			weather.setFxNight(f1.get("ff").toString());
		}
		
		if (StringUtil.isEmpty(f1.get("fg").toString())) {
			weather.setFlDay(f1.get("fg").toString());
		}
		
		if (!StringUtil.isEmpty(f1.get("fh").toString())) {
			weather.setFlNight(f1.get("fh").toString());
		}
		
		if (!StringUtil.isEmpty(f1.get("fi").toString())) {
			weather.setSunRiseDown(f1.get("fi").toString());
		}
		
		if (weather.getId().equals(0L)) {
			insert(weather);
		} else {
			updateByPrimaryKeySelective(weather);
		}
			
			
		String weatherIndex = WeatherUtil.getWeathInfo(areaId, "index_v");
		
		if (StringUtil.isEmpty(weatherIndex)) return false;
		
		weatherIndexService.deleteByAreaId(Long.valueOf(areaId));
		
		weatherIndex = "[" + weatherIndex + "]";

		JSONArray array = JSONArray.fromObject(weatherIndex);

        // 获取JSONArray的JSONObject对象，便于读取array里的键值对
        JSONObject i = array.getJSONObject(0); 
			        
        JSONArray array1 = JSONArray.fromObject(i.get("i").toString());
        
        for (int j = 0 ; j < array1.size(); j++) {
        	JSONObject item = array1.getJSONObject(j);
        	
        	WeatherIndex vo = weatherIndexService.initWeatherIndex();
        	vo.setAreaId(Long.valueOf(areaId));
        	vo.setAreaCn(areaCn);
        	vo.setPublicTime(f0);
        	vo.setI1(item.get("i1").toString());
        	vo.setI2(item.get("i2").toString());
        	vo.setI3(item.get("i3").toString());
        	vo.setI4(item.get("i4").toString());

        	weatherIndexService.insert(vo);
        	
        }
		return true;
	}
}