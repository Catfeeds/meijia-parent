package com.simi.action.job;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.weather.WeatherArea;
import com.simi.service.weather.WeatherAreaService;
import com.simi.service.weather.WeatherIndexService;
import com.simi.service.weather.WeatherService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/job/weather")
public class JobWeatherController extends BaseController {

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private WeatherIndexService weatherIndexService;
	
	@Autowired
	private WeatherAreaService weatherAreaService;	

	/**
	 * 已超过服务时间，则设置为已完成.
	 */
	@RequestMapping(value = "get_weather", method = RequestMethod.GET)
	public AppResultData<Object> getWeather(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			List<WeatherArea> weatherAreas = weatherAreaService.selectByAll();
			
			for (WeatherArea item : weatherAreas) {
				String areaId = item.getAreaId().toString();
				String areaCn = item.getNameCn();
				
				weatherService.getWeather(areaId, areaCn);
			}
			
		}
		return result;
	}


}
