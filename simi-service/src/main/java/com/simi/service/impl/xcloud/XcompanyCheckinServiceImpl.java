package com.simi.service.impl.xcloud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyCheckinMapper;
import com.simi.po.model.xcloud.XcompanyBenzTime;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.service.xcloud.XcompanyBenzTimeService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;

@Service
public class XcompanyCheckinServiceImpl implements XcompanyCheckinService {

	@Autowired
	XcompanyCheckinMapper xcompanyCheckinMapper;
	
	@Autowired
	private XcompanyBenzTimeService xCompanyBenzTimeService;		

	@Override
	public XcompanyCheckin initXcompanyCheckin() {

		XcompanyCheckin record = new XcompanyCheckin();

		record.setId(0L);
		record.setCompanyId(0L);
		record.setStaffId(0L);
		record.setUserId(0L);
		record.setCheckinFrom((short) 0);
		record.setCheckinType((short) 0);
		record.setCheckinDevice("");
		record.setCheckinSn("");
		record.setPoiName("");
		record.setPoiLat("");
		record.setPoiLng("");
		record.setPoiDistance(0);
		record.setRemarks("");
		record.setBenzTimeId(0L);
		record.setCheckinStatus((short) 0);
		record.setCheckinRemarks("");
		record.setCheckIn("");
		record.setCheckOut("");
		record.setFlexibleMin(0);
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public List<XcompanyCheckin> selectBySearchVo(CompanyCheckinSearchVo searchVo) {
		return xcompanyCheckinMapper.selectBySearchVo(searchVo);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {

		return xcompanyCheckinMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(XcompanyCheckin record) {
		return xcompanyCheckinMapper.insertSelective(record);
	}

	@Override
	public XcompanyCheckin selectByPrimarykey(Long id) {
		return xcompanyCheckinMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyCheckin record) {
		return xcompanyCheckinMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 根据考勤时间和班次对比 签到，返回考勤状态 正常或者迟到
	 * 
	 * 注意，都是换算为秒进行计算
	 */
	@Override
	public Map<String, Object> getCheckinStatus(Long benzTimeId, Long checkinTime, Short checkinType) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		XcompanyBenzTime benzTime = xCompanyBenzTimeService.selectByPrimaryKey(benzTimeId);
		
		if (benzTime == null) return result;
		
		String checkinStartTimeStr = benzTime.getCheckIn();
		Integer flexibleMin = benzTime.getFlexibleMin();
		
		String checkInTimeStr = TimeStampUtil.timeStampToDateStr(checkinTime * 1000, "yyyy-MM-dd");
		
		checkinStartTimeStr = checkInTimeStr + checkinStartTimeStr + "00";
		Long checkinStartTime = TimeStampUtil.getMillisOfDayFull(checkinStartTimeStr) / 1000;
		Long checkinLimitTime = checkinStartTime + flexibleMin * 60;
		String checkinStatusStr = "正常";
		Short checkinStatus = 0;
		
		if (checkinTime > checkinLimitTime) {
			checkinStatus = 1;
			
			Long laterSecond = checkinTime - checkinLimitTime;
			if (laterSecond < 60) {
				checkinStatusStr = "迟到" + laterSecond + "秒";
			} else {
				checkinStatusStr = "迟到" + laterSecond / 60 + "分钟";
			}
		}
		
		result.put("checkinStatus", checkinStatus);
		result.put("checkinStatusStr", checkinStatusStr);
		
		return result;
	}
	
	/**
	 * 根据考勤时间和班次对比 签退，返回考勤状态 正常或者早退
	 */
	@Override
	public Map<String, Object> getCheckOutStatus(Long benzTimeId, Long checkOutTime, Short checkinType) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		XcompanyBenzTime benzTime = xCompanyBenzTimeService.selectByPrimaryKey(benzTimeId);
		
		if (benzTime == null) return result;
		
		String checkOutStartTimeStr = benzTime.getCheckOut();
		Integer flexibleMin = benzTime.getFlexibleMin();

		String checkOutTimeStr = TimeStampUtil.timeStampToDateStr(checkOutTime * 1000, "yyyy-MM-dd");
		
		checkOutStartTimeStr = checkOutTimeStr + checkOutStartTimeStr + "00";
		Long checkOutStartTime = TimeStampUtil.getMillisOfDayFull(checkOutStartTimeStr) / 1000;
		
		
		
		return result;
	}	
}
