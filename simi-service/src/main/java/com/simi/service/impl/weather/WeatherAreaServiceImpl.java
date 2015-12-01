package com.simi.service.impl.weather;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.weather.WeatherAreaService;
import com.simi.po.model.weather.WeatherArea;
import com.simi.po.dao.weather.WeatherAreaMapper;

@Service
public class WeatherAreaServiceImpl implements WeatherAreaService {
	
	@Autowired
	WeatherAreaMapper weatherAreaMapper;

	@Override
	public WeatherArea selectByPrimaryKey(Long id) {
		return weatherAreaMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<WeatherArea> selectByAll() {
		return weatherAreaMapper.selectByAll();
	}	
}