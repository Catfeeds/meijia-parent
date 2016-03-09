package com.simi.action.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.DateUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictCity;
import com.simi.service.WeatherService;
import com.simi.service.dict.DictService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/job/weather")
public class JobWeatherController extends BaseController {

	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private DictService dictService;

	/**
	 * 定时获取天气预报数据
	 */
	@RequestMapping(value = "get_weather", method = RequestMethod.GET)
	public AppResultData<Object> getWeather(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			List<DictCity> citys = dictService.LoadCityData();
			
			for (DictCity item : citys) {
				weatherService.getWeather(item.getCityId(), item.getName());
			}
//			weatherService.getWeather(2L, "北京市");
		}
		return result;
	}
	
	@RequestMapping(value = "del_weather", method = RequestMethod.GET)
	public AppResultData<Object> delWeather(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			Date curDate = DateUtil.getNowOfDate();
			
			String threeDayAgo = DateUtil.addDay(curDate, -3, Calendar.DATE, "yyyy-MM-dd");
			
			Date threeDayAgoDate = DateUtil.parse(threeDayAgo);
			weatherService.deleteByDate(threeDayAgoDate);
		}
		return result;
	}	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "get_some_weather", method = RequestMethod.GET)
	public AppResultData<Object> getSomeWeather(HttpServletRequest request,
			@RequestParam(value = "city_id", required = false, defaultValue="2L") Long cityId
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			Date curDate = DateUtil.getNowOfDate();
			
			weatherService.getWeather(cityId, "北京市");
			
		}
		return result;
	}	


}
