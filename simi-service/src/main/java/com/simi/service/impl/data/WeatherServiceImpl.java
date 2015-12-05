package com.simi.service.impl.data;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.DateUtil;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.weather.WeatherData;
import com.meijia.utils.weather.WeatherIndex;
import com.meijia.utils.weather.WeatherInfo;
import com.meijia.utils.weather.WeatherResult;
import com.meijia.utils.weather.WeatherUtil;
import com.simi.po.dao.data.WeathersMapper;
import com.simi.po.model.data.Weathers;
import com.simi.service.data.WeatherService;


@Service
public class WeatherServiceImpl implements WeatherService {
		
	@Autowired
	WeathersMapper weathersMapper;
		
	@Override
	public Weathers initWeather() {
		Weathers record = new Weathers();
		record.setId(0L);
		record.setCityId(0L);
		record.setCityName("");
		record.setDayPictureUrl("");
		record.setNightPictureUrl("");
		record.setPm25("");
		record.setRealTemp("");
		record.setTemperature("");
		record.setWeather("");
		record.setWeatherDate(DateUtil.getNowOfDate());
		record.setWeatherIndex("");
		record.setWind("");
		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return weathersMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteByDate(Date weatherDate) {
		return weathersMapper.deleteByDate(weatherDate);
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
	public Weathers selectByCityIdAndDate(Long cityId, Date weatherDate) {
		return weathersMapper.selectByCityIdAndDate(cityId, weatherDate);
	}
	
	@Override
	public int updateByPrimaryKeySelective(Weathers record) {
		return weathersMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 定时获取天气api数据，并存入数据库
	 * http://www.cnblogs.com/txw1958/p/baidu-weather-forecast-api.html
	 * @param cityId,  cityName
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean getWeather(Long cityId, String cityName) {
		
		String repo = WeatherUtil.getWeathInfo(cityName);
		
		if (StringUtil.isEmpty(repo)) return false;
		
		WeatherInfo weatherInfo = GsonUtil.GsonToObject(repo, WeatherInfo.class);
		if (weatherInfo == null) return false;
		if (!weatherInfo.getStatus().equals("success")) return false;
		
		String weatherDateStr = weatherInfo.getDate();
		
		List<WeatherResult> results = weatherInfo.getResults();
		WeatherResult result = results.get(0);
		String pm25 = result.getPm25();
		List<WeatherIndex> weatherIndexs = result.getIndex();
		String weatherIndex = GsonUtil.GsonString(weatherIndexs);
		
		List<WeatherData> weatherDatas = result.getWeather_data();
		
		Date weatherDate = DateUtil.parse(weatherDateStr);

		String nowDateStr = DateUtil.getToday();
		for (int i =0; i < weatherDatas.size(); i++) {
			
			WeatherData item = weatherDatas.get(i);
			String curDateStr = DateUtil.addDay(weatherDate, i, Calendar.DATE, "yyyy-MM-dd");
			Date curDate = DateUtil.parse(curDateStr);
						
			Weathers record = selectByCityIdAndDate(cityId, curDate);
			if (record == null) {
				record = initWeather();
			}
			record.setWeatherDate(curDate);
			record.setCityId(cityId);
			record.setCityName(cityName);
			record.setDayPictureUrl(item.getDayPictureUrl());
			record.setNightPictureUrl(item.getNightPictureUrl());
			record.setWeather(item.getWeather());
			record.setPm25(pm25);
			record.setWind(item.getWind());
			record.setTemperature(item.getTemperature());
			
			record.setRealTemp("");
			
			if (curDateStr.equals(nowDateStr)) {
				String realTemp = item.getDate();

				realTemp = realTemp.substring(realTemp.indexOf("(") + 1, realTemp.indexOf(")"));
				realTemp = realTemp.substring(realTemp.indexOf("：") + 1);
				record.setRealTemp(realTemp);
				
				record.setWeatherIndex(weatherIndex);
			}
			
			if (record.getId().equals(0L)) {
				this.insert(record);
			} else {
				this.updateByPrimaryKeySelective(record);
			}
		}
		
		return true;
	}
}