package com.simi.service.impl.async;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.model.user.UserLeave;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanyCheckinStat;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.async.XcompanyAsyncService;
import com.simi.service.user.UserLeaveService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyCheckinStatService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;
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


}
