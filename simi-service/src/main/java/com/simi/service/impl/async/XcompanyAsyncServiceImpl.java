package com.simi.service.impl.async;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.async.XcompanyAsyncService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyCheckinStatService;
import com.simi.service.xcloud.XcompanyStaffService;
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


}
