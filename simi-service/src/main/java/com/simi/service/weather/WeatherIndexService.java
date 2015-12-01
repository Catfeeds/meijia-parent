package com.simi.service.weather;

import java.util.List;

import com.simi.po.model.weather.WeatherIndex;

public interface WeatherIndexService {

	WeatherIndex initWeatherIndex();

	int deleteByPrimaryKey(Long id);

	int insert(WeatherIndex record);

	int insertSelective(WeatherIndex record);

	WeatherIndex selectByPrimaryKey(Long id);

	int updateByPrimaryKey(WeatherIndex record);

	int updateByPrimaryKeySelective(WeatherIndex record);

	int deleteByAreaIdAndPublishTime(Long areaId, Long publishTime);

	List<WeatherIndex> selectByAreaIdAndPublishTime(Long areaId, String publishTime);

	int deleteByAreaId(Long areaId);

}
