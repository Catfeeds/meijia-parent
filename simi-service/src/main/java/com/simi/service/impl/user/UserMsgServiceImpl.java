package com.simi.service.impl.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.WeatherService;
import com.simi.service.dict.DictUtil;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;
import com.simi.vo.UserMsgSearchVo;
import com.simi.vo.user.UserMsgVo;
import com.simi.po.dao.user.UserMsgMapper;
import com.simi.po.model.common.Weathers;
import com.simi.po.model.user.UserMsg;
import com.meijia.utils.DateUtil;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.baidu.BaiduMapUtil;
import com.meijia.utils.weather.WeatherDataVo;

@Service
public class UserMsgServiceImpl implements UserMsgService {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserMsgMapper userMsgMapper;
	
	@Autowired
	private WeatherService weatherService;	
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userMsgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserMsg record) {
		return userMsgMapper.insert(record);
	}

	@Override
	public int insertSelective(UserMsg record) {
		return userMsgMapper.insertSelective(record);
	}

	@Override
	public UserMsg selectByPrimaryKey(Long id) {
		return userMsgMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UserMsg record) {
		return userMsgMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserMsg record) {
		return userMsgMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public UserMsg initUserMsg() {
		UserMsg record = new UserMsg();
		record.setMsgId(0L);
		record.setUserId(0L);
		record.setFromUserId(0L);
		record.setToUserId(0L);
		record.setCategory("");
		record.setAction("");
		record.setParams("");
		record.setGotoUrl("");
		record.setTitle("");
		record.setSummary("");
		record.setIconUrl("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		return record;
	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(UserMsgSearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<UserMsg> list = userMsgMapper.selectByListPage(searchVo);
		PageInfo result = new PageInfo(list);
		return result;
	}
	
	@Override
	public List<UserMsg> selectByUserId(Long userId) {
		List<UserMsg> result = userMsgMapper.selectByUserId(userId);
		return result;
	}
	
	@Override
	public List<UserMsg> selectBySearchVo(UserMsgSearchVo searchVo) {
		List<UserMsg> result = userMsgMapper.selectBySearchVo(searchVo);
		return result;
	}
	
	@Override
	public UserMsgVo getWeather(String serviceDate, String lat, String lng) {
		
		UserMsgVo vo = new UserMsgVo();
		
		Date weatherDate = DateUtil.parse(serviceDate);
		String todayStr = DateUtil.getToday();
		
		int days = DateUtil.getDateSpace(todayStr, serviceDate);
		
		if (days < 0 || days > 3) {
			return vo;
		}
		
		//如果没有地理位置信息，默认则为北京市
		String cityName = "北京市";
		if (StringUtil.isEmpty(lat) || StringUtil.isEmpty(lng)) {
			cityName = "北京市";
		} else {
			cityName = BaiduMapUtil.getCityByPoi(lat, lng);
		}
		
		if (StringUtil.isEmpty(cityName)) cityName = "北京市";
		
		Long cityId = 0L;
		cityId = DictUtil.getCityId(cityName);
		
		if (cityId.equals(0L)) cityId = 2L;
		
		Date today = DateUtil.getNowOfDate();
		Weathers weatherInfo = weatherService.selectByCityIdAndDate(cityId, today);
		
		if (weatherInfo == null) return vo;
		
		List<WeatherDataVo> weatherDatas =GsonUtil.GsonToList(weatherInfo.getWeatherData(), WeatherDataVo.class);
		
		if (weatherDatas.size() < days) return vo;
		WeatherDataVo curItem = weatherDatas.get(days);
		
		vo.setMsgId(0L);
		vo.setUserId(0L);
		vo.setCategory("h5");
		vo.setAction("weather");
		vo.setParams("");
		vo.setGotoUrl("http://m.weather.com.cn/mweather");
		vo.setTitle(cityName);
		
		String temperature = curItem.getTemperature();
		temperature =  temperature.replaceAll("/", " ~ ");
		vo.setSummary(curItem.getWeather() + "," + temperature + "," + curItem.getWind());
		
		int hour = DateUtil.getHours();
		if (hour >=6 && hour < 18 ) {
			vo.setIconUrl(curItem.getDayPictureUrl());
		} else {
			vo.setIconUrl(curItem.getNightPictureUrl());
		}

		vo.setMsgTime(weatherInfo.getLastTime());
		
		
		return vo;
	}
		
}