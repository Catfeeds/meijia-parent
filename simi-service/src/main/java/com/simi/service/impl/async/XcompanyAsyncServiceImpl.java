package com.simi.service.impl.async;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.service.async.XcompanyAsyncService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyCheckinStatService;

@Service
public class XcompanyAsyncServiceImpl implements XcompanyAsyncService {

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
	 * 1. 查找出是否迟到，早退，请假.
	 */
	@Async
	@Override
	public Future<Boolean> checkinStat(Long companyId, Long userId) {

//		XcompanyCheckin record = xcompanyCheckinService.selectByPrimarykey(id);
//		
//		if (record == null) return new AsyncResult<Boolean>(true);
//
//		//1. 找出匹配的考勤配置
//		xcompanyCheckinService.matchCheckinSetting(id);
				
		return new AsyncResult<Boolean>(true);
	}	


}
