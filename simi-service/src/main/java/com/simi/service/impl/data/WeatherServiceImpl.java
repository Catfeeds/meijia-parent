package com.simi.service.impl.data;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.DateUtil;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.weather.WeatherDataVo;
import com.meijia.utils.weather.WeatherIndexVo;
import com.meijia.utils.weather.WeatherInfoVo;
import com.meijia.utils.weather.WeatherResultVo;
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
		record.setPm25("");
		record.setWeatherData("");
		record.setWeatherDate(DateUtil.getNowOfDate());
		record.setWeatherIndex("");
		record.setLastTime("");
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
		
		WeatherInfoVo weatherInfo = GsonUtil.GsonToObject(repo, WeatherInfoVo.class);
		if (weatherInfo == null) return false;
		if (!weatherInfo.getStatus().equals("success")) return false;
		
		String weatherDateStr = weatherInfo.getDate();
		
		List<WeatherResultVo> results = weatherInfo.getResults();
		WeatherResultVo result = results.get(0);
		String pm25 = result.getPm25();
		List<WeatherIndexVo> weatherIndexs = result.getIndex();
		String weatherIndex = GsonUtil.GsonString(weatherIndexs);
		
		List<WeatherDataVo> weatherDatas = result.getWeather_data();
		
		for (int i =0; i < weatherDatas.size(); i++) {
			WeatherDataVo vo = weatherDatas.get(i);
			String dayPictureUrl = vo.getDayPictureUrl();

			dayPictureUrl = dayPictureUrl.replace("http://api.map.baidu.com", "http://123.57.173.36");
			dayPictureUrl = dayPictureUrl.replace("png", "jpg");
			
			vo.setDayPictureUrl(dayPictureUrl);
			
			String nightPictureUrl = vo.getNightPictureUrl();

			nightPictureUrl = nightPictureUrl.replace("http://api.map.baidu.com", "http://123.57.173.36");
			nightPictureUrl = nightPictureUrl.replace("png", "jpg");
			
			
			vo.setNightPictureUrl(nightPictureUrl);
			weatherDatas.set(i, vo);
		}
		
		String weatherData = GsonUtil.GsonString(weatherDatas);
		
		Date curDate = DateUtil.parse(weatherDateStr);
		Weathers record = selectByCityIdAndDate(cityId, curDate);
		if (record == null) {
			record = initWeather();
		}		
		
		record.setWeatherDate(curDate);
		record.setCityId(cityId);
		record.setCityName(cityName);
		record.setPm25(pm25);
		record.setWeatherIndex(weatherIndex);
		record.setWeatherData(weatherData);
		record.setLastTime(DateUtil.getNow("HH:mm"));
		if (record.getId().equals(0L)) {
			this.insert(record);
		} else {
			this.updateByPrimaryKeySelective(record);
		}
		
//		Date weatherDate = DateUtil.parse(weatherDateStr);

//		String nowDateStr = DateUtil.getToday();
//		for (int i =0; i < weatherDatas.size(); i++) {
//			
//			WeatherDataVo item = weatherDatas.get(i);
//			String curDateStr = DateUtil.addDay(weatherDate, i, Calendar.DATE, "yyyy-MM-dd");
//			Date curDate = DateUtil.parse(curDateStr);
//						
//			Weathers record = selectByCityIdAndDate(cityId, curDate);
//			if (record == null) {
//				record = initWeather();
//			}
//			record.setWeatherDate(curDate);
//			record.setCityId(cityId);
//			record.setCityName(cityName);
//			record.setDayPictureUrl(item.getDayPictureUrl());
//			record.setNightPictureUrl(item.getNightPictureUrl());
//			record.setWeather(item.getWeather());
//			record.setPm25(pm25);
//			record.setWind(item.getWind());
//			record.setTemperature(item.getTemperature());
//			
//			record.setRealTemp("");
//			
//			if (curDateStr.equals(nowDateStr)) {
//				String realTemp = item.getDate();
//
//				realTemp = realTemp.substring(realTemp.indexOf("(") + 1, realTemp.indexOf(")"));
//				realTemp = realTemp.substring(realTemp.indexOf("：") + 1);
//				record.setRealTemp(realTemp);
//				
//				record.setWeatherIndex(weatherIndex);
//			}
//			
//			if (record.getId().equals(0L)) {
//				this.insert(record);
//			} else {
//				this.updateByPrimaryKeySelective(record);
//			}
//		}
		
		return true;
	}
}