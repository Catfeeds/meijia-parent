package com.simi.action.job;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meijia.utils.DateUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.service.dict.DictService;
import com.simi.service.record.RecordHolidayDayService;
import com.simi.service.record.RecordHolidayService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/job/holiday")
public class JobHolidayController extends BaseController {

	@Autowired
	private RecordHolidayService recordHolidayService;
	
	@Autowired
	private RecordHolidayDayService recordHolidayDayService;
	
	@Autowired
	private DictService dictService;

	/**
	 * 定时获取年度的节假日信息
	 */
	@RequestMapping(value = "get_holiday", method = RequestMethod.GET)
	public AppResultData<Object> getHoliday(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			int year = DateUtil.getYear();
			System.out.println("明年 = " + (year + 1));
			recordHolidayService.getHolidayByApi(year);
		}
		return result;
	}
	
	/**
	 * 定时获取年度的节假日信息
	 */
	@RequestMapping(value = "get_holiday_day", method = RequestMethod.GET)
	public AppResultData<Object> getHolidayDay(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			int year = DateUtil.getYear();
			System.out.println("明年 = " + (year + 1));
			recordHolidayDayService.getHolidayByApi(year);
		}
		return result;
	}
}
