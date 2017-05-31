package com.simi.action.app.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.Week;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserActionRecord;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.async.UserScoreAsyncService;
import com.simi.service.user.UserActionRecordService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserActionSearchVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.CompanySettingVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserSignController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserActionRecordService userActionRecordService;
	
	@Autowired
	private UserScoreAsyncService userScoreAsyncService;
	
	@Autowired
	private XCompanySettingService xCompanySettingService;	

	/**
	 * 
	 * @param userId
	 * @return
	 *   week 
	 *   day
	 *   dayStr
	 *   signed
	 *  
	 */
	@RequestMapping(value = "day_sign_list", method = RequestMethod.GET)
	public AppResultData<Object> daySignList(
			@RequestParam("user_id") Long userId) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		String firstWeek = DateUtil.getFirstDayOfWeek();
		Date d = DateUtil.parse(firstWeek);
		String endWeek = DateUtil.addDay(d, 7, Calendar.DATE, DateUtil.DEFAULT_PATTERN);
		
		//找出一周签到的情况
		Long startTime = TimeStampUtil.getMillisOfDayFull(firstWeek + " 00:00:00") / 1000;
		Long endTime = TimeStampUtil.getMillisOfDayFull(endWeek + " 23:59:59") / 1000;
		
		UserActionSearchVo searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("day_sign");
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		
		List<UserActionRecord> rs = userActionRecordService.selectBySearchVo(searchVo);
		
		for (int i = 0; i < 7; i++) {
			Map<String, String> item = new HashMap<String, String>();
			String dd = DateUtil.addDay(d, i, Calendar.DATE, DateUtil.DEFAULT_PATTERN);
			Date weekDate = DateUtil.parse(dd);
			String dayStr = DateUtil.format(weekDate, "MM月dd日");
			Week we = DateUtil.getWeek(weekDate);
			System.out.println(we.getShortNameCn()+ "---" + dd);
			
			item.put("week", we.getShortNameCn());
			item.put("day", String.valueOf(we.getNumber()));
			item.put("dayStr", dayStr);
			item.put("signed", "");
			for (UserActionRecord uar : rs) {
				Long addTime = uar.getAddTime();
				String signDay = TimeStampUtil.timeStampToDateStr(addTime * 1000, DateUtil.DEFAULT_PATTERN);
				
				if (signDay.equals(dd)) {
					item.put("signed", "已签");
				}
			}
			
			list.add(item);
		}
		result.setData(list);
		return result;
	}
	
	/**
	 * 获取今日签到情况和设置签到提醒情况
	 * @param userId
	 * @return
	 * 		signed = 0/ 1   
	 *      alarm_day_sign = 0 / 1
	 */
	@RequestMapping(value = "get_day_sign", method = RequestMethod.GET)
	public AppResultData<Object> getDaySignStatus(
			@RequestParam("user_id") Long userId) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (userId.equals(0L)) return result;
		
		int signed = 0;
		int alarmDaySign = 0;
		Map<String, String> resultData = new HashMap<String, String>();
		
		Long startTime = TimeStampUtil.getBeginOfToday();
		Long endTime = TimeStampUtil.getEndOfToday();
		
		UserActionSearchVo searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("day_sign");
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		
		List<UserActionRecord> rs = userActionRecordService.selectBySearchVo(searchVo);
		if (!rs.isEmpty()) {
			signed = 1;
		}
		
		CompanySettingSearchVo searchVo1 = new CompanySettingSearchVo();
		searchVo1.setUserId(userId);
		searchVo1.setSettingType(Constants.SETTING_ALARM_DAY_SIGN);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo1);
		if (!list.isEmpty()) alarmDaySign = 1;
		
		resultData.put("signed", String.valueOf(signed));
		resultData.put("alarmDaySign", String.valueOf(alarmDaySign));
		result.setData(resultData);
		
		
		return result;
		
	}
	
	
	@RequestMapping(value = "day_sign", method = RequestMethod.POST)
	public AppResultData<Object> daySing(
			@RequestParam("user_id") Long userId) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (userId.equals(0L)) return result;
		
		Long startTime = TimeStampUtil.getBeginOfToday();
		Long endTime = TimeStampUtil.getEndOfToday();
		
		UserActionSearchVo searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("day_sign");
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		
		List<UserActionRecord> rs = userActionRecordService.selectBySearchVo(searchVo);
		if (!rs.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("您今日已签到");
			return result;
		}
		
		UserActionRecord record = userActionRecordService.initUserActionRecord();
		record.setUserId(userId);
		record.setActionType("day_sign");
		
		userActionRecordService.insert(record);
		
		//积分赠送
		userScoreAsyncService.sendScore(userId, Constants.SCORE_DAY_SIGN, "day_sign", record.getId().toString(), "签到");
		
		result.setMsg("签到成功");
		result.setData(Constants.SCORE_DAY_SIGN);
		
		return result;
	}
	
	
	
	@RequestMapping(value = "set_alarm_day_sign", method = RequestMethod.POST)
	public AppResultData<Object> setAlarmDaySign(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "action", required = false, defaultValue = "add") String action
			) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (userId.equals(0L)) return result;
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setUserId(userId);
		searchVo.setSettingType(Constants.SETTING_ALARM_DAY_SIGN);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		if (action.equals("add")) {
			if (!list.isEmpty()) return result;
		
			XcompanySetting record = xCompanySettingService.initXcompanySetting();
			record.setUserId(userId);
			record.setSettingType(Constants.SETTING_ALARM_DAY_SIGN);
			xCompanySettingService.insert(record);
		
		}
		
		if (action.equals("remove")) {
			XcompanySetting x = list.get(0);
			Long id = x.getId();
			xCompanySettingService.deleteByPrimaryKey(id);
		}
		return result;
		
	}
	

}
