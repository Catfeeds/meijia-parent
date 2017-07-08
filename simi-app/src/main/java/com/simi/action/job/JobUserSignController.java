package com.simi.action.job;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meijia.utils.DateUtil;
import com.meijia.utils.MobileUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserActionRecord;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.user.UserActionRecordService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserActionSearchVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;

@Controller
@RequestMapping(value = "/app/job")
public class JobUserSignController extends BaseController {
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private XCompanySettingService xCompanySettingService;	
	
	@Autowired
	private UserPushBindService userPushBindService;
	
	@Autowired
	private NoticeAppAsyncService noticeAppAsyncService;
	
	@Autowired
	private UserActionRecordService userActionRecordService;
	
	
	/**
	 * 每天早上10点将设置提醒签到的进行推送提醒.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "alarm_day_sign", method = RequestMethod.GET)
	public AppResultData<Object> alarmDaySign(HttpServletRequest request) {


		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {

			//获取需要签到提醒的用户列表
			CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
			searchVo.setSettingType(Constants.SETTING_ALARM_DAY_SIGN);
			List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
			
			//找出已经签到的用户
			Long startTime = TimeStampUtil.getBeginOfToday();
			Long endTime = TimeStampUtil.getEndOfToday();
			
			UserActionSearchVo userActionSearchVo = new UserActionSearchVo();
			userActionSearchVo.setActionType("day_sign");
			userActionSearchVo.setStartTime(startTime);
			userActionSearchVo.setEndTime(endTime);
			
			List<UserActionRecord> rs = userActionRecordService.selectBySearchVo(userActionSearchVo);
			List<Long> userIds = new ArrayList<Long>();
			for (UserActionRecord item: rs) {
				if (!userIds.contains(item.getUserId())) userIds.add(item.getUserId());
			}

			String title = " 亲，记得签到哦，坚持签到有奖励呢~~";
			String msgContent = "";
			String category = "h5";
			String action = "";
			String params = "";
			String gotoUrl = "http://app.bolohr.com/simi-h5/show/sign-today.html?user_id=";
			for (XcompanySetting item : list) {
				Long userId = item.getUserId();
				gotoUrl+=userId;
				
				boolean isNeedNotice = true;
				//检测是否已经签到.
				for (Long signedUserId : userIds) {
					if (userId.equals(signedUserId)) {
						isNeedNotice = false;
						break;
					}
				}
				
				if (isNeedNotice) {
					noticeAppAsyncService.pushMsgToDevice(userId, title, msgContent, category, action, params, gotoUrl);
				}
			}
		}	
		return result;
	}
	
	/**
	 * 完成两件事情
	 * 1. 将昨日没有签到的情况，进行连续7天记录的置为0
	 * 2. 将连续7天已经签到，也置为0.
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "reset_day_sign_lottery", method = RequestMethod.GET)
	public AppResultData<Object> setDaySignLottery(HttpServletRequest request) {


		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			//1. 将昨日没有签到的情况，进行连续7天记录的置为0, 查找最近8天的记录即可.
			String today = DateUtil.getToday();
			
			Date firstDate = DateUtil.parse(today);
			String endDateStr = DateUtil.addDay(firstDate, -8, Calendar.DATE, DateUtil.DEFAULT_PATTERN);
			Long startTime = TimeStampUtil.getMillisOfDayFull(endDateStr + " 00:00:00") / 1000;
			Long endTime = TimeStampUtil.getMillisOfDayFull(today + " 23:59:59") / 1000;
			
			UserActionSearchVo userActionSearchVo = new UserActionSearchVo();
			userActionSearchVo.setActionType("day_sign_lottery");
			userActionSearchVo.setStartTime(startTime);
			userActionSearchVo.setEndTime(endTime);
			List<UserActionRecord> rs = userActionRecordService.selectBySearchVo(userActionSearchVo);
			List<Long> userIds = new ArrayList<Long>();
			for (UserActionRecord item : rs) {
				if (!userIds.contains(item.getUserId())) userIds.add(item.getUserId());
			}
			
			List<UserActionRecord> daySignYestoday = new ArrayList<UserActionRecord>();
			if (!userIds.isEmpty()) {
				
				userActionSearchVo = new UserActionSearchVo();
				userActionSearchVo.setActionType("day_sign");
				userActionSearchVo.setUserIds(userIds);
				userActionSearchVo.setStartTime(TimeStampUtil.getBeginOfYesterDay());
				userActionSearchVo.setEndTime(TimeStampUtil.getEndOfYesterDay());
				daySignYestoday = userActionRecordService.selectBySearchVo(userActionSearchVo);
				
				
				for (UserActionRecord item : rs) {
					Boolean signed = false;
					for (UserActionRecord yitem : daySignYestoday) {
						if (item.getUserId().equals(yitem.getUserId())) {
							signed = true;
							break;
						}
					}
					
					//如果昨日没有签，则设置连续签到为0， 涉及到 day_sign_lottery  day_sign_continue
					if (!signed) {
						item.setParams("0");
						item.setAddTime(TimeStampUtil.getNowSecond());
						userActionRecordService.updateByPrimaryKey(item);
						
						userActionSearchVo = new UserActionSearchVo();
						userActionSearchVo.setActionType("day_sign_continue");
						List<UserActionRecord> totalDaySignList = userActionRecordService.selectBySearchVo(userActionSearchVo);
						if (!totalDaySignList.isEmpty()) {
							UserActionRecord totalDaySign = totalDaySignList.get(0);
							totalDaySign.setParams("0");
							totalDaySign.setAddTime(TimeStampUtil.getNowSecond());
							userActionRecordService.updateByPrimaryKey(totalDaySign);
						}
					}
				}
			}
			
			
			
			//2. 将连续7天已经签到，也置为0.
			userActionSearchVo = new UserActionSearchVo();
			userActionSearchVo.setActionType("day_sign_lottery");
			userActionSearchVo.setParams("7");
			
			rs = userActionRecordService.selectBySearchVo(userActionSearchVo);
			for (UserActionRecord item : rs) {
				item.setParams("0");
				item.setAddTime(TimeStampUtil.getNowSecond());
				userActionRecordService.updateByPrimaryKeySelective(item);
			}
		}	
		return result;
	}
}
