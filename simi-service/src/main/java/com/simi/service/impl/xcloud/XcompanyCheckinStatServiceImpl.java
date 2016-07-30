package com.simi.service.impl.xcloud;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyCheckinStatMapper;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanyCheckinStat;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyCheckinStatService;
import com.simi.vo.xcloud.CheckinNetVo;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;

@Service
public class XcompanyCheckinStatServiceImpl implements XcompanyCheckinStatService {

	@Autowired
	UsersService userService;

	@Autowired
	XcompanyCheckinStatMapper xcompanyCheckinStatMapper;

	@Autowired
	private XcompanyCheckinService xcompanyCheckinService;

	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Override
	public XcompanyCheckinStat initXcompanyCheckinStat() {

		XcompanyCheckinStat record = new XcompanyCheckinStat();

		record.setId(0L);
		record.setCompanyId(0L);
		record.setStaffId(0L);
		record.setUserId(0L);
		record.setCyear(DateUtil.getYear());
		record.setCmonth(DateUtil.getMonth());
		record.setCday(TimeStampUtil.getBeginOfToday());
		record.setCdayAm(0L);
		record.setCdayPm(0L);
		record.setCdayAmId(0L);
		record.setCdayPmId(0L);
		record.setIsLate((short) 0);
		record.setIsEaryly((short) 0);
		record.setIsLeave((short) 0);
		record.setAdminId(0L);
		record.setRemarks("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public List<XcompanyCheckinStat> selectBySearchVo(CompanyCheckinSearchVo searchVo) {
		return xcompanyCheckinStatMapper.selectBySearchVo(searchVo);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {

		return xcompanyCheckinStatMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(XcompanyCheckinStat record) {
		return xcompanyCheckinStatMapper.insertSelective(record);
	}

	@Override
	public XcompanyCheckinStat selectByPrimarykey(Long id) {
		return xcompanyCheckinStatMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyCheckinStat record) {
		return xcompanyCheckinStatMapper.updateByPrimaryKeySelective(record);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> totalBySearchVo(CompanyCheckinSearchVo searchVo) {
		return xcompanyCheckinStatMapper.totalBySearchVo(searchVo);
	}

	@Override
	public Boolean checkInStat(Long id) {

		XcompanyCheckin record = xcompanyCheckinService.selectByPrimarykey(id);

		Long userId = record.getUserId();
		Long companyId = record.getCompanyId();

		CompanyCheckinSearchVo searchVo = new CompanyCheckinSearchVo();

		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStartTime(TimeStampUtil.getBeginOfToday());
		searchVo.setEndTime(TimeStampUtil.getEndOfToday());
		List<XcompanyCheckin> checkinList = xcompanyCheckinService.selectBySearchVo(searchVo);

		if (checkinList.isEmpty())
			return false;

		String today = DateUtil.getToday();
		int hour = DateUtil.getHours();

		// 测试时间为8点
		hour = 8;

		// 测试时间为18点
		// hour = 18;

		int flexMin = 0;
		// 计算上午的最早时间 注：时间为倒序
		Long cdayAm = 0L;
		Long cdayAmId = 0L;
		Short isLate = 1;
		XcompanyCheckin amCheckin = null;
		
		//确定早上打开是否有对应的出勤配置匹配
		Boolean isMatchId = false;
		for (int i = checkinList.size() - 1; i >= 0; i--) {
			amCheckin = checkinList.get(i);

			int checkHour = TimeStampUtil.getHour(amCheckin.getAddTime() * 1000);
			if (checkHour > 12) continue;

//			cdayAm = amCheckin.getAddTime();
//			cdayAmId = amCheckin.getSettingId();
			
			if (amCheckin.getSettingId().equals(0L)) continue;
			isMatchId = true;
			
			// 计算是否迟到
			XcompanySetting settingItem = xCompanySettingService.selectByPrimaryKey(amCheckin.getSettingId());
			JSONObject setValue = (JSONObject) settingItem.getSettingValue();
			CheckinNetVo vo = JSON.toJavaObject(setValue, CheckinNetVo.class);

			String beginCheckTimeStr = today + " " + vo.getStartTime() + ":00";
			Long beginCheckTime = TimeStampUtil.getMillisOfDayFull(beginCheckTimeStr) / 1000;
			Long beginFlexTime = beginCheckTime + vo.getFlexTime() * 60;
			Long firstCheckTime = amCheckin.getAddTime();
			// 是否为弹性时间.
			if (firstCheckTime > beginFlexTime) {
				isLate = 1;
				flexMin = 0;
			} else if (firstCheckTime > beginCheckTime && firstCheckTime < beginFlexTime) {
				flexMin = (int) ((beginFlexTime - firstCheckTime) / 60);
			}

		}

		// 计算下午的最晚时间
		Long cdayPm = 0L;
		Long cdayPmId = 0L;
		Short isEarly = 0;
		XcompanyCheckin pmCheckin = null;
		if (hour >= 13) {
			pmCheckin = checkinList.get(0);
			int checkHour = TimeStampUtil.getHour(pmCheckin.getAddTime() * 1000);

			if (checkHour >= 13) {
				cdayPm = pmCheckin.getAddTime();
				cdayPmId = pmCheckin.getSettingId();

				// 计算是否早退
				if (pmCheckin.getSettingId() > 0L) {
					XcompanySetting settingItem = xCompanySettingService.selectByPrimaryKey(pmCheckin.getSettingId());
					JSONObject setValue = (JSONObject) settingItem.getSettingValue();
					CheckinNetVo vo = JSON.toJavaObject(setValue, CheckinNetVo.class);

					String endCheckTimeStr = today + " " + vo.getEndTime() + ":00";
					Long endCheckTime = TimeStampUtil.getMillisOfDayFull(endCheckTimeStr) / 1000;
					Long endFlexTime = endCheckTime + flexMin * 60;
					Long lastCheckTime = pmCheckin.getAddTime();

					if (lastCheckTime < endFlexTime)
						isEarly = 1;
				}
			}
		}

		XcompanyCheckinStat stat = this.initXcompanyCheckinStat();

		int cyear = DateUtil.getYear();
		int cmonth = DateUtil.getMonth();
		Long cday = TimeStampUtil.getBeginOfToday();
		searchVo = new CompanyCheckinSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setCday(cday);

		List<XcompanyCheckinStat> list = this.selectBySearchVo(searchVo);
		if (!list.isEmpty())
			stat = list.get(0);

		stat.setCompanyId(companyId);
		stat.setUserId(userId);
		stat.setCyear(cyear);
		stat.setCmonth(cmonth);
		stat.setCday(cday);
		stat.setCdayAm(cdayAm);
		stat.setCdayAmId(cdayAmId);
		stat.setCdayPm(cdayPm);
		stat.setCdayPmId(cdayPmId);
		stat.setIsLate(isLate);
		stat.setIsEaryly(isEarly);

		if (stat.getId() > 0L) {
			stat.setUpdateTime(TimeStampUtil.getNowSecond());
			this.updateByPrimaryKeySelective(stat);
		} else {
			stat.setAddTime(TimeStampUtil.getNowSecond());
			stat.setUpdateTime(TimeStampUtil.getNowSecond());
			this.insertSelective(stat);
		}
		return true;
	}

}
