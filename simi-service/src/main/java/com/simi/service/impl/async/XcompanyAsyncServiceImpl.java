package com.simi.service.impl.async;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.common.Constants;
import com.simi.po.model.user.UserLeave;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanyCheckinStat;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.async.XcompanyAsyncService;
import com.simi.service.user.UserLeaveService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyCheckinStatService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.xcloud.CheckinNetVo;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Service
public class XcompanyAsyncServiceImpl implements XcompanyAsyncService {
	
	@Autowired
	private XcompanyStaffService xCompanyStaffService;		
	
	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private XcompanyCheckinService xcompanyCheckinService;
	
	@Autowired
	private XcompanyCheckinStatService xcompanyCheckinStatService;
	
	@Autowired
	private UserLeaveService userLeaveService;	
	
	@Autowired
	private NoticeAppAsyncService noticeAppAsyncService;


	/**
	 * 签到后的异步操作
	 * 1. 查找匹配的出勤配置，并计算出距离
	 */
	@Async
	@Override
	public Future<Boolean> checkin(Long id) {

		XcompanyCheckin record = xcompanyCheckinService.selectByPrimarykey(id);
		
		if (record == null) return new AsyncResult<Boolean>(true);

		//1. 找出匹配的考勤配置
		xcompanyCheckinService.matchCheckinSetting(id);
				
		return new AsyncResult<Boolean>(true);
	}
	
	/**
	 * 签到后的异步操作
	 * 1. 查找出是否迟到
	 */
	@Async
	@Override
	public Future<Boolean> checkinStatLate(Long companyId) {
		
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xCompanyStaffService.selectBySearchVo(searchVo);
		
		for (XcompanyStaff item : staffList) {
			xcompanyCheckinStatService.checkInStatLate(companyId, item.getUserId(), item.getDeptId());
		}
		
		return new AsyncResult<Boolean>(true);
	}	
	
	/**
	 * 签到后的异步操作
	 * 1. 查找出是否早退
	 */
	@Async
	@Override
	public Future<Boolean> checkinStatEarly(Long companyId) {

		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xCompanyStaffService.selectBySearchVo(searchVo);
		
		for (XcompanyStaff item : staffList) {
			xcompanyCheckinStatService.checkInStatEarly(companyId, item.getUserId(), item.getDeptId());
		}
				
		return new AsyncResult<Boolean>(true);
	}	
	
	
	/**
	 * 请假审批成功后的异步操作
	 * 1. 查找出是否早退
	 */
	@Async
	@Override
	public Future<Boolean> checkinStatLeave(Long leaveId) {
		
		UserLeave userLeave = userLeaveService.selectByPrimaryKey(leaveId);
		
		if (userLeave == null) return new AsyncResult<Boolean>(true);
		
		Long companyId = userLeave.getCompanyId();
		Long userId = userLeave.getUserId();
		
		Date startDate = userLeave.getStartDate();
		Date endDate = userLeave.getEndDate();
		String startDateStr = DateUtil.formatDate(startDate);
		String endDateStr = DateUtil.formatDate(endDate);
		int totalDays = DateUtil.getDateSpace(startDateStr, endDateStr);
		
		
		for (int i = 0; i < totalDays; i++) {
			String cdateStr = DateUtil.addDay(startDate, i, Calendar.DATE, DateUtil.DEFAULT_PATTERN);
			Date cdate = DateUtil.parse(cdateStr);
			int cyear = DateUtil.getYear(cdate);
			int cmonth = DateUtil.getMonth(cdate);
			Long cday = TimeStampUtil.getMillisOfDay(cdateStr) / 1000;
			//查找对应的考勤统计记录信息
			CompanyCheckinSearchVo ccsearchVo = new CompanyCheckinSearchVo();
			ccsearchVo.setCompanyId(companyId);
			ccsearchVo.setUserId(userId);
			ccsearchVo.setCday(cday);
	
			List<XcompanyCheckinStat> list = xcompanyCheckinStatService.selectBySearchVo(ccsearchVo);
	
			XcompanyCheckinStat stat = xcompanyCheckinStatService.initXcompanyCheckinStat();
	
			if (!list.isEmpty())
				stat = list.get(0);
	
			stat.setCompanyId(companyId);
			stat.setUserId(userId);
			stat.setCyear(cyear);
			stat.setCmonth(cmonth);
			stat.setCday(cday);
			stat.setLeaveId(leaveId);
			stat.setLeaveType(userLeave.getLeaveType());
			
			if (stat.getId() > 0L) {
				stat.setUpdateTime(TimeStampUtil.getNowSecond());
				xcompanyCheckinStatService.updateByPrimaryKeySelective(stat);
			} else {
				stat.setAddTime(TimeStampUtil.getNowSecond());
				stat.setUpdateTime(TimeStampUtil.getNowSecond());
				xcompanyCheckinStatService.insertSelective(stat);
			}
		}
				
		return new AsyncResult<Boolean>(true);
	}	
	
	/**
	 * 签到后的异步操作
	 * 1. 查找出是否早退
	 */
	@Async
	@Override
	public Future<Boolean> checkinNotice(Long companyId) {
		
		// 1. 找出所有有配置出勤配置的公司
		CompanySettingSearchVo csearchVo = new CompanySettingSearchVo();
		csearchVo.setSettingType(Constants.SETTING_CHICKIN_NET);
		csearchVo.setCompanyId(companyId);
		csearchVo.setIsEnable((short) 1);
		List<XcompanySetting> checkinSettings = xCompanySettingService.selectBySearchVo(csearchVo);
		
		//2. 找出所有的员工
		UserCompanySearchVo ucSearchVo = new UserCompanySearchVo();
		ucSearchVo.setCompanyId(companyId);
		ucSearchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xCompanyStaffService.selectBySearchVo(ucSearchVo);
		
		String today = DateUtil.getToday();
		Long nowMin = TimeStampUtil.getNowMin();
		Long willDoMin = nowMin + 15 * 60;
		Long afterDoMin = nowMin - 15 * 60;
		for (XcompanySetting item : checkinSettings) {
			CheckinNetVo vo = null;
			if (item.getSettingValue() != null) {
				JSONObject setValue = (JSONObject) item.getSettingValue();
				vo = JSON.toJavaObject(setValue, CheckinNetVo.class);
			}

			if (vo == null) continue;
			
			//找出符合的所有userIds
			List<Long> userIds = new ArrayList<Long>();
			
			String deptIds = vo.getDeptIds();

			if (deptIds.equals("0")) {
				for (XcompanyStaff staff : staffList) {
					if (!userIds.contains(staff.getUserId())) userIds.add(staff.getUserId());
				}
			} else {
				if (deptIds.indexOf(",") <= 0)
					deptIds = deptIds + ",";
				
				for (XcompanyStaff staff : staffList) {
					if (deptIds.indexOf(staff.getDeptId().toString() + ",") >= 0) {
						if (!userIds.contains(staff.getUserId())) userIds.add(staff.getUserId());
					}
				}

			}
			
			
			String beginCheckTimeStr = today + " " + vo.getStartTime() + ":00";
			Long beginCheckTime = TimeStampUtil.getMillisOfDayFull(beginCheckTimeStr) / 1000;
			String msgContent = "";
			//判断早上打卡的时间，提前15分钟.
			if (willDoMin.equals(beginCheckTime) ) {
				for (Long userId : userIds) {
					// 发送给提醒签到推送消息
					msgContent = "还有15分钟,别忘了早上打卡哦";
					noticeAppAsyncService.pushMsgToDevice(userId, "考勤签到", msgContent, "app", "checkin", "", "");
					
				}
			}
			
			String endCheckTimeStr = today + " " + vo.getEndTime() + ":00";
			Long endCheckTime = TimeStampUtil.getMillisOfDayFull(endCheckTimeStr) / 1000;
			
			if (afterDoMin.equals(endCheckTime)) {
				for (Long userId : userIds) {
					msgContent = "已经下班了,别忘了下班打卡哦";
					noticeAppAsyncService.pushMsgToDevice(userId, "考勤签到", msgContent, "app", "checkin", "", "");
				}
			}
			
			
		}
				
		return new AsyncResult<Boolean>(true);
	}	


}
