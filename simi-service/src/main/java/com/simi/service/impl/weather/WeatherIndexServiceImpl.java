package com.simi.service.impl.weather;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.weather.WeatherIndexService;
import com.simi.po.model.weather.WeatherIndex;
import com.simi.po.dao.weather.WeatherIndexMapper;

@Service
public class WeatherIndexServiceImpl implements WeatherIndexService {
	
	@Autowired
	WeatherIndexMapper weatherIndexMapper;
		
	@Override
	public WeatherIndex initWeatherIndex() {
		WeatherIndex record = new WeatherIndex();
		record.setId(0L);
		record.setAreaId(0L);
		record.setAreaCn("");
		record.setPublicTime("");
		record.setI1("");
		record.setI2("");
		record.setI3("");
		record.setI4("");

		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return weatherIndexMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteByAreaIdAndPublishTime(Long areaId, Long publishTime) {
		return weatherIndexMapper.deleteByAreaIdAndPublishTime(areaId, publishTime);
	}	
	
	@Override
	public int deleteByAreaId(Long areaId) {
		return weatherIndexMapper.deleteByAreaId(areaId);
	}		
	
	@Override
	public int insert(WeatherIndex record) {
		return weatherIndexMapper.insert(record);
	}
	
	@Override
	public int insertSelective(WeatherIndex record) {
		return weatherIndexMapper.insertSelective(record);
	}

	@Override
	public WeatherIndex selectByPrimaryKey(Long id) {
		return weatherIndexMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKey(WeatherIndex record) {
		return weatherIndexMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(WeatherIndex record) {
		return weatherIndexMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<WeatherIndex> selectByAreaIdAndPublishTime(Long areaId, String publishTime) {
		return weatherIndexMapper.selectByAreaIdAndPublishTime(areaId, publishTime);
	}
	

}