package com.simi.service.weather;

import java.util.List;

import com.simi.po.model.weather.WeatherArea;

public interface WeatherAreaService {

	WeatherArea selectByPrimaryKey(Long id);

	List<WeatherArea> selectByAll();

}
