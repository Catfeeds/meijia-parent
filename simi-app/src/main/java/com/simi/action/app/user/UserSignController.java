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

import com.alibaba.druid.util.StringUtils;
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
				
		//找出起始时间
		UserActionSearchVo searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("day_sign_lottery");
		List<UserActionRecord> rs = userActionRecordService.selectBySearchVo(searchVo);
		
		String firstDateStr = DateUtil.getToday();
		
		if (!rs.isEmpty()) {
			UserActionRecord daySignLottery = rs.get(0);
			firstDateStr = TimeStampUtil.timeStampToDateStr(daySignLottery.getAddTime() * 1000, DateUtil.DEFAULT_PATTERN);
		}
		
		Date firstDate = DateUtil.parse(firstDateStr);
		String endDateStr = DateUtil.addDay(firstDate, 7, Calendar.DATE, DateUtil.DEFAULT_PATTERN);
		
		//找出一周签到的情况
		Long startTime = TimeStampUtil.getMillisOfDayFull(firstDateStr + " 00:00:00") / 1000;
		Long endTime = TimeStampUtil.getMillisOfDayFull(endDateStr + " 23:59:59") / 1000;
		
		searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("day_sign");
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		
		rs = userActionRecordService.selectBySearchVo(searchVo);
		
		for (int i = 0; i < 7; i++) {
			Map<String, String> item = new HashMap<String, String>();
			String dd = DateUtil.addDay(firstDate, i, Calendar.DATE, DateUtil.DEFAULT_PATTERN);
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
		// 今日签到的情况
		UserActionSearchVo searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("day_sign");
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		
		List<UserActionRecord> rs = userActionRecordService.selectBySearchVo(searchVo);
		if (!rs.isEmpty()) {
			signed = 1;
		}
		
		//设置签到提醒的情况
		CompanySettingSearchVo searchVo1 = new CompanySettingSearchVo();
		searchVo1.setUserId(userId);
		searchVo1.setSettingType(Constants.SETTING_ALARM_DAY_SIGN);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo1);
		if (!list.isEmpty()) alarmDaySign = 1;
		
		//连续签到的次数
		int totalContinue = 0;
		searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("day_sign_continue");
		rs = userActionRecordService.selectBySearchVo(searchVo);
		if (!rs.isEmpty()) {
			UserActionRecord daySignContinue = rs.get(0);
			totalContinue = Integer.valueOf(daySignContinue.getParams());
		}
		
		
		resultData.put("signed", String.valueOf(signed));
		resultData.put("alarmDaySign", String.valueOf(alarmDaySign));
		resultData.put("totalContinue", String.valueOf(totalContinue));
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
		
		//记录用户连续签到的次数
		//1. 先判断昨天是否有签到
		startTime = TimeStampUtil.getBeginOfYesterDay();
		endTime = TimeStampUtil.getEndOfYesterDay();
		searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("day_sign");
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		rs = userActionRecordService.selectBySearchVo(searchVo);
		boolean isContinue = false;
		
		if (!rs.isEmpty()) isContinue = true;
		
		//2. 进行计算，如果为7的倍数，则设置day_sign_lottery 为1
		
		searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("day_sign_continue");
		rs = userActionRecordService.selectBySearchVo(searchVo);
		
		UserActionRecord totalDaySign = userActionRecordService.initUserActionRecord();
		if (!rs.isEmpty()) totalDaySign = rs.get(0);
		
		int total = 1;
		if (isContinue && !StringUtils.isEmpty(totalDaySign.getParams())) {
			total = Integer.valueOf(totalDaySign.getParams()) + 1;
		}
		
		totalDaySign.setUserId(userId);
		totalDaySign.setActionType("day_sign_continue");
		totalDaySign.setParams(String.valueOf(total));
		
		if (total == 1) totalDaySign.setAddTime(TimeStampUtil.getNowSecond());
		
		if (totalDaySign.getId() > 0L) {
			userActionRecordService.updateByPrimaryKey(totalDaySign);
		} else {
			userActionRecordService.insert(totalDaySign);
		}
		
		//如果为7的倍数，则设置day_sign_lottery 为1
		
		searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("day_sign_lottery");
		rs = userActionRecordService.selectBySearchVo(searchVo);
		
		UserActionRecord daySignLottery = userActionRecordService.initUserActionRecord();
		if (!rs.isEmpty()) daySignLottery = rs.get(0);
		
		daySignLottery.setUserId(userId);
		daySignLottery.setActionType("day_sign_lottery");
		
		int totalLottery = 1;
		if (isContinue && !StringUtils.isEmpty(daySignLottery.getParams())) {
			totalLottery = Integer.valueOf(daySignLottery.getParams()) + 1;
		}
		
		if (totalLottery > 7 ) totalLottery = 1;
		
		
		daySignLottery.setParams(String.valueOf(total));
		
		if (totalLottery == 1) daySignLottery.setAddTime(TimeStampUtil.getNowSecond());
		
		if (daySignLottery.getId() > 0L) {
			userActionRecordService.updateByPrimaryKey(daySignLottery);
		} else {
			userActionRecordService.insert(daySignLottery);
		}
		
		
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
