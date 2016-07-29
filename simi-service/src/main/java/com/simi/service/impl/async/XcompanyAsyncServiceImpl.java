package com.simi.service.impl.async;

import java.util.HashMap;
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
import com.simi.vo.AppResultData;

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
	 * 2. 计算签到人的考勤统计信息.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Async
	@Override
	public Future<Boolean> checkin(Long id) {

		XcompanyCheckin record = xcompanyCheckinService.selectByPrimarykey(id);
		
		if (record == null) return new AsyncResult<Boolean>(true);
				
		Long settingId = 0L;
		int poiDistance = 0;
		//1. 找出匹配的考勤配置
		AppResultData<Object> matchDatas = xcompanyCheckinService.matchCheckinSetting(id);
		
		if (matchDatas.getData() != null) {
			
			HashMap<String, Object> matchData = (HashMap) matchDatas.getData();
			if (matchData != null && matchData.get("matchId") != null) {
				settingId = Long.valueOf(matchData.get("matchId").toString());
			}
			
			if (matchData != null && matchData.get("poiDistance") != null) {
				poiDistance = Integer.valueOf(matchData.get("poiDistance").toString());
			}
		}
		
		if (settingId > 0L && poiDistance > 0) {
			record.setSettingId(settingId);
			record.setPoiDistance(poiDistance);
			xcompanyCheckinService.updateByPrimaryKeySelective(record);
		}
		
		//计算签到人员的考勤统计信息.
		xcompanyCheckinStatService.checkInStat(id);

		return new AsyncResult<Boolean>(true);
	}


}
