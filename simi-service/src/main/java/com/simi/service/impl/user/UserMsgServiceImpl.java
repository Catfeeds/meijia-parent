package com.simi.service.impl.user;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.WeatherService;
import com.simi.service.card.CardService;
import com.simi.service.dict.DictUtil;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;
import com.simi.utils.CardUtil;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.user.UserMsgSearchVo;
import com.simi.vo.user.UserMsgVo;
import com.simi.po.dao.user.UserMsgMapper;
import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.Cards;
import com.simi.po.model.common.Weathers;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.Users;
import com.meijia.utils.DateUtil;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.Week;
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

	@Autowired
	private CardService cardService;

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
		record.setServiceTime(TimeStampUtil.getNowSecond());
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

		// 如果没有地理位置信息，默认则为北京市
		String cityName = "北京市";
		if (StringUtil.isEmpty(lat) || StringUtil.isEmpty(lng)) {
			cityName = "北京市";
		} else {
			cityName = BaiduMapUtil.getCityByPoi(lat, lng);
		}

		if (StringUtil.isEmpty(cityName))
			cityName = "北京市";

		Long cityId = 0L;
		cityId = DictUtil.getCityId(cityName);

		if (cityId.equals(0L))
			cityId = 2L;

		Date today = DateUtil.getNowOfDate();
		Weathers weatherInfo = weatherService.selectByCityIdAndDate(cityId, today);

		if (weatherInfo == null)
			return vo;

		List<WeatherDataVo> weatherDatas = GsonUtil.GsonToList(weatherInfo.getWeatherData(), WeatherDataVo.class);

		if (weatherDatas.size() < days)
			return vo;
		WeatherDataVo curItem = weatherDatas.get(days);

		vo.setMsgId(0L);
		vo.setUserId(0L);
		vo.setCategory("h5");
		vo.setAction("weather");
		vo.setParams("");
		vo.setGotoUrl("http://m.weather.com.cn/mweather");
		vo.setTitle(cityName);

		String temperature = curItem.getTemperature();
		temperature = temperature.replaceAll("/", " ~ ");
		vo.setSummary(curItem.getWeather() + "," + temperature + "," + curItem.getWind());

		int hour = DateUtil.getHours();
		if (hour >= 6 && hour < 18) {
			vo.setIconUrl(curItem.getDayPictureUrl());
		} else {
			vo.setIconUrl(curItem.getNightPictureUrl());
		}

		vo.setMsgTime(weatherInfo.getLastTime());

		return vo;
	}

	@Override
	public List<HashMap> totalByMonth(UserMsgSearchVo vo) {
		return userMsgMapper.totalByMonth(vo);
	}

	@Override
	public boolean checkPeriod(Users u, String startDate) {

		Long userId = u.getId();
		// 查找所有重复性的卡片
		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setUserId(userId);
		searchVo.setUserType(u.getUserType());

		List<Short> periods = new ArrayList<Short>();
		periods.add((short) 1);
		periods.add((short) 2);
		periods.add((short) 3);
		periods.add((short) 4);
		periods.add((short) 5);
		searchVo.setPeriods(periods);

		List<Cards> periodCards = cardService.selectBySearchVo(searchVo);

		if (periodCards.isEmpty())
			return true;

		Date date = DateUtil.parse(startDate);
		
		for (Cards card : periodCards) {
			Long serviceTime = card.getServiceTime();
			Long realServiceTime = 0L;
			Date serviceDate = null;
			Boolean isNeedInsert = false;
			try {
				serviceDate = DateUtil.timeStampToDate(serviceTime * 1000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (card.getPeriod().equals((short) 1)) isNeedInsert = true;
			
			if (card.getPeriod().equals((short) 2) ||
				card.getPeriod().equals((short) 3)) {
				Week sw = DateUtil.getWeek(serviceDate);
				int sweekday = sw.getNumber();

				Week w = DateUtil.getWeek(date);
				int weekday = w.getNumber();

				if (sweekday == weekday) isNeedInsert = true;
			}
			
			if (card.getPeriod().equals((short)4)) {
				String cd = TimeStampUtil.timeStampToDateStr(serviceTime * 1000, "dd");
				
				String d = DateUtil.format(date, "dd");
				
				if (cd.equals(d)) isNeedInsert = true;
				
			}
			
			if (card.getPeriod().equals((short)5)) {
				String cd = TimeStampUtil.timeStampToDateStr(serviceTime * 1000, "MM-dd");
				
				String d = DateUtil.format(date, "MM-dd");
				
				if (cd.equals(d)) isNeedInsert = true;
			}
			
			
			if (isNeedInsert) {
				String t = TimeStampUtil.timeStampToDateStr(serviceTime * 1000, "HH:mm:ss");
				String at = startDate + " " + t;
				realServiceTime = TimeStampUtil.getMillisOfDayFull(at) / 1000;
				insertByCard(card, realServiceTime);
			}
				

		}

		return true;
	}
	
	
	private Boolean insertByCard(Cards card, Long serviceTime) {
		Long cardId = card.getCardId();
		Long userId = card.getUserId();
		Long createUserId = card.getCreateUserId();
		
		//检测是否已经插入
		UserMsgSearchVo searchVo = new UserMsgSearchVo();
		searchVo.setUserId(userId);
		searchVo.setAction("card");
		searchVo.setParams(cardId.toString());
		List<UserMsg> usermsgs = this.selectBySearchVo(searchVo);
		
		if (!usermsgs.isEmpty()) return true;
		
		

		String serviceContent = card.getServiceContent();
		if (serviceContent != null && serviceContent.length() > 20) {
			serviceContent = serviceContent.substring(0, 20);
		}

		if (serviceContent.length() == 0) {
			serviceContent = "你创建了新的日程安排";
		}

		
		UserMsg record = this.initUserMsg();

		record.setUserId(userId);
		record.setFromUserId(createUserId);
		record.setToUserId(userId);
		record.setCategory("app");
		record.setAction("card");
		record.setParams(cardId.toString());
		record.setGotoUrl("");
		record.setTitle(CardUtil.getCardTypeName(card.getCardType()));
		record.setServiceTime(serviceTime);
		record.setSummary(serviceContent);
		record.setIconUrl(CardUtil.getCardIcon(card.getCardType()));
		this.insert(record);
		
		return true;
	}

}