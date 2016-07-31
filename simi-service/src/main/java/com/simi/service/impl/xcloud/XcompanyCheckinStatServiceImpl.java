package com.simi.service.impl.xcloud;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.baidu.BaiduMapUtil;
import com.simi.common.Constants;
import com.simi.po.dao.xcloud.XcompanyCheckinStatMapper;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanyCheckinStat;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyCheckinStatService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.xcloud.CheckinNetVo;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

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

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

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

	/**
	 * 判断员工是否迟到 1. 找出公司所有的出勤配置信息，得到集合A 2. 找出员工与出勤配置信息一致的配置项,依靠部门ID来查找.得到集合B 3.
	 * 找出当天员工所有的考勤记录，得到集合C 4. 循环集合C，找出与此配置项是否符合规则， 1) 如果符合则正常打卡，记录最早符合的上班打卡时间 2)
	 * 2) 如果循环找不到符合的规则，则记录最早的上班打卡时间，并设置迟到标识.
	 * 
	 */
	@Override
	public Boolean checkInStatLate(Long companyId, Long userId, Long deptId) {

		// 1. 找出公司所有的出勤配置信息，得到集合A
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setSettingType(Constants.SETTING_CHICKIN_NET);
		searchVo.setIsEnable((short) 1);
		List<XcompanySetting> checkinSettings = xCompanySettingService.selectBySearchVo(searchVo);

		if (checkinSettings.isEmpty())
			return false;

		// 2.找出员工与出勤配置信息一致的配置项,依靠部门ID来查找.得到集合B
		CheckinNetVo vo = null;
		List<XcompanySetting> matchDepts = new ArrayList<XcompanySetting>();
		for (XcompanySetting item : checkinSettings) {
			vo = null;
			if (item.getSettingValue() != null) {
				JSONObject setValue = (JSONObject) item.getSettingValue();
				vo = JSON.toJavaObject(setValue, CheckinNetVo.class);
			}

			if (vo == null)
				continue;
			String deptIds = vo.getDeptIds();

			if (deptIds.equals("0")) {
				matchDepts.add(item);
			} else {
				if (deptIds.indexOf(",") <= 0)
					deptIds = deptIds + ",";

				if (deptIds.indexOf(deptId.toString() + ",") >= 0) {
					matchDepts.add(item);
				}
			}
		}

		if (matchDepts.isEmpty()) {
			return true;
		}

		// 3. 找出当天员工所有的考勤记录，得到集合C
		CompanyCheckinSearchVo csearchVo = new CompanyCheckinSearchVo();
		csearchVo.setCompanyId(companyId);
		csearchVo.setUserId(userId);
		csearchVo.setStartTime(TimeStampUtil.getBeginOfToday());
		csearchVo.setEndTime(TimeStampUtil.getEndOfToday());
		List<XcompanyCheckin> checkinList = xcompanyCheckinService.selectBySearchVo(csearchVo);

		if (checkinList.isEmpty())
			return false;
		// 4. 循环集合C，找出与此配置项是否符合规则，注：时间为倒序
		String today = DateUtil.getToday();

		// 时间匹配标识
		Boolean timeMatch = false;

		// 网络wifi匹配标识
		Boolean wifiMatch = false;

		// 距离匹配标识
		Boolean distanceMatch = false;

		// 是否早上已经打卡.
		Boolean chekined = false;

		XcompanyCheckin amCheckin = null;
		XcompanySetting matchSetting = null;
		for (int i = checkinList.size() - 1; i >= 0; i--) {
			amCheckin = checkinList.get(i);

			vo = null;

			int checkHour = TimeStampUtil.getHour(amCheckin.getAddTime() * 1000);
			if (checkHour > 12)
				continue;
			chekined = true;
			// 找出符合规则的打卡记录
			// 1. 判断时间是否符合
			// 2. 如果有wifi查看wifi是否符合
			// 3. 如果有距离，则查看距离是否符合.
			for (XcompanySetting item : matchDepts) {

				// 时间匹配标识
				timeMatch = false;

				// 网络wifi匹配标识
				wifiMatch = false;

				// 距离匹配标识
				distanceMatch = false;

				if (item.getSettingValue() == null)
					continue;
				JSONObject setValue = (JSONObject) item.getSettingValue();
				vo = JSON.toJavaObject(setValue, CheckinNetVo.class);

				// 1.判断时间是否符合
				timeMatch = checkStartTimeMatch(vo, amCheckin.getAddTime(), today);

				// 2.判断wifi是否符合
				wifiMatch = this.checkWifiMatch(vo, amCheckin.getCheckinNet());

				// 3.判断距离是否符合
				distanceMatch = this.checkDistanceMatch(vo, amCheckin.getPoiLat(), amCheckin.getPoiLng());

				// 最后如果三个都匹配，则可以记录并退出循环
				if (timeMatch == true && wifiMatch == true && distanceMatch == true) {
					matchSetting = item;
					break;
				}
			}

			// 最后如果三个都匹配，则可以记录并退出循环
			if (timeMatch == true && wifiMatch == true && distanceMatch == true)
				break;
		}

		int cyear = DateUtil.getYear();
		int cmonth = DateUtil.getMonth();
		Long cday = TimeStampUtil.getBeginOfToday();
		CompanyCheckinSearchVo ccsearchVo = new CompanyCheckinSearchVo();
		ccsearchVo.setCompanyId(companyId);
		ccsearchVo.setUserId(userId);
		ccsearchVo.setCday(cday);

		List<XcompanyCheckinStat> list = this.selectBySearchVo(ccsearchVo);

		XcompanyCheckinStat stat = this.initXcompanyCheckinStat();

		if (!list.isEmpty())
			stat = list.get(0);

		stat.setCompanyId(companyId);
		stat.setUserId(userId);
		stat.setCyear(cyear);
		stat.setCmonth(cmonth);
		stat.setCday(cday);

		// 最后如果三个都匹配，如果符合则正常打卡，记录最早符合的上班打卡时间
		if (timeMatch == true && wifiMatch == true && distanceMatch == true) {
			stat.setCdayAm(amCheckin.getAddTime());
			stat.setCdayAmId(matchSetting.getId());
		} else {
			if (chekined) {
				stat.setIsLate((short) 1);
				stat.setUpdateTime(TimeStampUtil.getNowSecond());
			}
		}

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

	@Override
	public Boolean checkInStatEarly(Long companyId, Long userId, Long deptId) {

		// 1. 找出公司所有的出勤配置信息，得到集合A
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setSettingType(Constants.SETTING_CHICKIN_NET);
		searchVo.setIsEnable((short) 1);
		List<XcompanySetting> checkinSettings = xCompanySettingService.selectBySearchVo(searchVo);

		if (checkinSettings.isEmpty())
			return false;

		// 2.找出员工与出勤配置信息一致的配置项,依靠部门ID来查找.得到集合B
		CheckinNetVo vo = null;
		List<XcompanySetting> matchDepts = new ArrayList<XcompanySetting>();
		for (XcompanySetting item : checkinSettings) {
			vo = null;
			if (item.getSettingValue() != null) {
				JSONObject setValue = (JSONObject) item.getSettingValue();
				vo = JSON.toJavaObject(setValue, CheckinNetVo.class);
			}

			if (vo == null)
				continue;
			String deptIds = vo.getDeptIds();

			if (deptIds.equals("0")) {
				matchDepts.add(item);
			} else {
				if (deptIds.indexOf(",") <= 0)
					deptIds = deptIds + ",";

				if (deptIds.indexOf(deptId.toString() + ",") >= 0) {
					matchDepts.add(item);
				}
			}
		}

		if (matchDepts.isEmpty()) {
			return true;
		}

		// 3. 找出当天员工所有的考勤记录，得到集合C
		CompanyCheckinSearchVo csearchVo = new CompanyCheckinSearchVo();
		csearchVo.setCompanyId(companyId);
		csearchVo.setUserId(userId);
		csearchVo.setStartTime(TimeStampUtil.getBeginOfYesterDay());
		csearchVo.setEndTime(TimeStampUtil.getEndOfYesterDay());
		List<XcompanyCheckin> checkinList = xcompanyCheckinService.selectBySearchVo(csearchVo);

		// 4. 循环集合C，找出与此配置项是否符合规则，注：时间为倒序
		String today = DateUtil.getYesterday();

		// 时间匹配标识
		Boolean timeMatch = false;

		// 网络wifi匹配标识
		Boolean wifiMatch = false;

		// 距离匹配标识
		Boolean distanceMatch = false;

		XcompanyCheckin pmCheckin = null;
		XcompanySetting matchSetting = null;
		for (int i = 0; i < checkinList.size(); i++) {
			pmCheckin = checkinList.get(i);

			vo = null;

			int checkHour = TimeStampUtil.getHour(pmCheckin.getAddTime() * 1000);
			if (checkHour < 13)
				continue;
			// 找出符合规则的打卡记录
			// 1. 判断时间是否符合
			// 2. 如果有wifi查看wifi是否符合
			// 3. 如果有距离，则查看距离是否符合.
			for (XcompanySetting item : matchDepts) {

				// 时间匹配标识
				timeMatch = false;

				// 网络wifi匹配标识
				wifiMatch = false;

				// 距离匹配标识
				distanceMatch = false;

				if (item.getSettingValue() == null)
					continue;
				JSONObject setValue = (JSONObject) item.getSettingValue();
				vo = JSON.toJavaObject(setValue, CheckinNetVo.class);

				// 1.判断时间是否符合
				timeMatch = checkendTimeMatch(vo, pmCheckin.getAddTime(), today);
				// 2.判断wifi是否符合
				wifiMatch = this.checkWifiMatch(vo, pmCheckin.getCheckinNet());
				// 3.判断距离是否符合
				distanceMatch = this.checkDistanceMatch(vo, pmCheckin.getPoiLat(), pmCheckin.getPoiLng());

				// 最后如果三个都匹配，则可以记录并退出循环
				if (timeMatch == true && wifiMatch == true && distanceMatch == true) {
					matchSetting = item;
					break;
				}
			}

			// 最后如果三个都匹配，则可以记录并退出循环
			if (timeMatch == true && wifiMatch == true && distanceMatch == true)
				break;
		}

		Long cday = TimeStampUtil.getBeginOfYesterDay();
		Date cdate = TimeStampUtil.timeStampToDate(cday);
		int cyear = cdate.getYear();
		int cmonth = cdate.getMonth() + 1;
		CompanyCheckinSearchVo ccsearchVo = new CompanyCheckinSearchVo();
		ccsearchVo.setCompanyId(companyId);
		ccsearchVo.setUserId(userId);
		ccsearchVo.setCday(cday);

		List<XcompanyCheckinStat> list = this.selectBySearchVo(ccsearchVo);

		XcompanyCheckinStat stat = this.initXcompanyCheckinStat();

		if (!list.isEmpty())
			stat = list.get(0);

		stat.setCompanyId(companyId);
		stat.setUserId(userId);
		stat.setCyear(cyear);
		stat.setCmonth(cmonth);
		stat.setCday(cday);

		// 最后如果三个都匹配，如果符合则正常打卡，记录最早符合的上班打卡时间
		if (timeMatch == true && wifiMatch == true && distanceMatch == true) {
			stat.setCdayPm(pmCheckin.getAddTime());
			stat.setCdayPmId(matchSetting.getId());
		} else {
			stat.setIsEaryly((short) 1);
			stat.setUpdateTime(TimeStampUtil.getNowSecond());
		}

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

	@Override
	public Boolean setCheckinFirst(Long companyId, Long userId) {

		CompanyCheckinSearchVo csearchVo = new CompanyCheckinSearchVo();
		csearchVo.setCompanyId(companyId);
		csearchVo.setUserId(userId);
		csearchVo.setStartTime(TimeStampUtil.getBeginOfToday());
		csearchVo.setEndTime(TimeStampUtil.getEndOfToday());
		List<XcompanyCheckin> checkinList = xcompanyCheckinService.selectBySearchVo(csearchVo);

		if (checkinList.isEmpty())
			return false;

		XcompanyCheckin amCheckin = checkinList.get(checkinList.size() - 1);

		int checkHour = TimeStampUtil.getHour(amCheckin.getAddTime() * 1000);
		if (checkHour > 12)
			return false;

		Long cdayAm = amCheckin.getAddTime();
		Long cdayAmId = amCheckin.getSettingId();

		int cyear = DateUtil.getYear();
		int cmonth = DateUtil.getMonth();
		Long cday = TimeStampUtil.getBeginOfToday();
		CompanyCheckinSearchVo searchVo = new CompanyCheckinSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setCday(cday);

		List<XcompanyCheckinStat> list = this.selectBySearchVo(searchVo);

		XcompanyCheckinStat stat = this.initXcompanyCheckinStat();

		if (!list.isEmpty())
			stat = list.get(0);

		stat.setCompanyId(companyId);
		stat.setUserId(userId);
		stat.setCyear(cyear);
		stat.setCmonth(cmonth);
		stat.setCday(cday);
		stat.setCdayAm(cdayAm);
		stat.setCdayAmId(cdayAmId);

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

	@Override
	public Boolean setCheckinLast(Long companyId, Long userId) {

		CompanyCheckinSearchVo csearchVo = new CompanyCheckinSearchVo();
		csearchVo.setCompanyId(companyId);
		csearchVo.setUserId(userId);
		csearchVo.setStartTime(TimeStampUtil.getBeginOfToday());
		csearchVo.setEndTime(TimeStampUtil.getEndOfToday());
		List<XcompanyCheckin> checkinList = xcompanyCheckinService.selectBySearchVo(csearchVo);

		if (checkinList.isEmpty())
			return false;

		XcompanyCheckin pmCheckin = checkinList.get(0);

		int checkHour = TimeStampUtil.getHour(pmCheckin.getAddTime() * 1000);
		if (checkHour < 13)
			return false;

		Long cdayPm = pmCheckin.getAddTime();
		Long cdayPmId = pmCheckin.getSettingId();

		int cyear = DateUtil.getYear();
		int cmonth = DateUtil.getMonth();
		Long cday = TimeStampUtil.getBeginOfToday();
		CompanyCheckinSearchVo searchVo = new CompanyCheckinSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setCday(cday);

		List<XcompanyCheckinStat> list = this.selectBySearchVo(searchVo);

		XcompanyCheckinStat stat = this.initXcompanyCheckinStat();

		if (!list.isEmpty())
			stat = list.get(0);

		stat.setCompanyId(companyId);
		stat.setUserId(userId);
		stat.setCyear(cyear);
		stat.setCmonth(cmonth);
		stat.setCday(cday);
		stat.setCdayPm(cdayPm);
		stat.setCdayPmId(cdayPmId);

		if (stat.getId() > 0L) {
			stat.setUpdateTime(TimeStampUtil.getNowSecond());
			this.updateByPrimaryKeySelective(stat);
		} else {
			stat.setAddTime(TimeStampUtil.getNowSecond());
			stat.setUpdateTime(TimeStampUtil.getNowSecond());
			this.insertSelective(stat);
		}

		return false;
	}

	@Override
	public List<HashMap<String, Object>> getStaffCheckin(CompanyCheckinSearchVo searchVo) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		
		int cyear = searchVo.getCyear();
		int cmonth = searchVo.getCmonth();
		//当月所有日期
		List<String> months = DateUtil.getAllDaysOfMonth(cyear, cmonth);
		
		Long companyId = searchVo.getCompanyId();
		UserCompanySearchVo ucSearchVo = new UserCompanySearchVo();
		ucSearchVo.setCompanyId(companyId);
		ucSearchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xcompanyStaffService.selectBySearchVo(ucSearchVo);

		// 所有员工的统计情况

		List<XcompanyCheckinStat> checkinStats = this.selectBySearchVo(searchVo);

		List<Long> userIds = new ArrayList<Long>();

		for (XcompanyStaff staff : staffList) {
			if (!userIds.contains(staff.getUserId()))
				userIds.add(staff.getUserId());
		}

		List<Users> users = new ArrayList<Users>();

		if (!userIds.isEmpty()) {
			UserSearchVo userSearchVo = new UserSearchVo();
			userSearchVo.setUserIds(userIds);
			users = userService.selectBySearchVo(userSearchVo);
		}
		
		int no = 1;
		for (XcompanyStaff staff : staffList) {
			HashMap<String, Object> data = new HashMap<String, Object>();
			Long userId = staff.getUserId();
			String name = "";
			for(Users u : users) {
				if (staff.getUserId().equals(u.getId())) {
					name = u.getName();
					if (StringUtil.isEmpty(name)) name = u.getMobile();
				}
			}
			data.put("companyId", companyId.toString());
			data.put("userId", userId.toString());
			data.put("no", String.valueOf(no));
			data.put("name", name);
			no++;
			
			List<HashMap<String,String>> dataAm = new ArrayList<HashMap<String,String>>();
			List<HashMap<String,String>> dataPm = new ArrayList<HashMap<String,String>>();
			
			for (int i = 0; i < months.size(); i++) {
				String dayStr = months.get(i);
				Long day = TimeStampUtil.getMillisOfDay(dayStr) / 1000;
				
				XcompanyCheckinStat checkinStat = null;
				for (XcompanyCheckinStat stat : checkinStats) {
					if (userId.equals(stat.getUserId()) && day.equals(stat.getCday())) {
						checkinStat = stat;
						break;
					}
				}
				
				String checkinAmStatus = "";
				String checkinPmStatus = "";
				if (checkinStat != null) {
					
					//判断上午的情况
					if (checkinStat.getCdayAm() > 0L && checkinStat.getIsLate().equals((short)1)) {
						checkinAmStatus = "迟";
					}
					
					if (checkinStat.getCdayAm() > 0l && checkinStat.getIsLate().equals((short)0)) {
						checkinAmStatus = "√";
					}
					
					if (checkinStat.getCdayAm().equals(0L)) checkinAmStatus = "";
					
					//判断下午的情况
					if(checkinStat.getCdayPm() > 0L && checkinStat.getIsEaryly().equals((short)1)) {
						checkinPmStatus = "退";
					}
					
					if(checkinStat.getCdayPm() > 0L && checkinStat.getIsEaryly().equals((short)0)) {
						checkinPmStatus = "√";
					}
					
					if (checkinStat.getCdayPm().equals(0L)) checkinPmStatus = "";
				}
				
				HashMap<String, String> am = new HashMap<String, String>();
				am.put("cday", dayStr);
				am.put("status", checkinAmStatus);
				dataAm.add(am);
				HashMap<String, String> pm = new HashMap<String, String>();
				pm.put("cday", dayStr);
				pm.put("status", checkinPmStatus);
				dataPm.add(pm);
			}
			
			data.put("dataAm", dataAm);
			data.put("dataPm", dataPm);
			
			result.add(data);
		}
		
		return result;
	}

	/**
	 * 判断时间 是否符合
	 */
	private Boolean checkStartTimeMatch(CheckinNetVo vo, Long checkinTime, String d) {
		boolean timeMatch = false;
		String beginCheckTimeStr = d + " " + vo.getStartTime() + ":00";
		Long beginCheckTime = TimeStampUtil.getMillisOfDayFull(beginCheckTimeStr) / 1000;
		Long beginFlexTime = beginCheckTime + vo.getFlexTime() * 60;

		if (checkinTime <= beginFlexTime) {
			timeMatch = true;
		}

		return timeMatch;
	}

	/**
	 * 判断时间 是否符合
	 */
	private Boolean checkendTimeMatch(CheckinNetVo vo, Long checkinTime, String d) {
		boolean timeMatch = false;
		String endCheckTimeStr = d + " " + vo.getStartTime() + ":00";
		Long endCheckTime = TimeStampUtil.getMillisOfDayFull(endCheckTimeStr) / 1000;
		Long beginFlexTime = endCheckTime - vo.getFlexTime() * 60;

		if (checkinTime >= beginFlexTime) {
			timeMatch = true;
		}

		return timeMatch;
	}

	/**
	 * 判断wifi 是否符合
	 */
	private Boolean checkWifiMatch(CheckinNetVo item, String checkinNet) {
		Boolean wifiMatch = false;

		if (StringUtil.isEmpty(item.getWifis())) {
			wifiMatch = true;
		} else {
			String wifis = item.getWifis();
			if (!StringUtil.isEmpty(wifis)) {
				String[] wifiAry = StringUtil.convertStrToArray(wifis);
				for (int j = 0; j < wifiAry.length; j++) {
					if (StringUtil.isEmpty(wifiAry[j]))
						continue;
					// 如果网络wifi相同,还需要计算距离
					if (checkinNet.toLowerCase().equals(wifiAry[j].toLowerCase())) {
						wifiMatch = true;
						break;
					}
				}
			}
		}

		return wifiMatch;
	}

	/**
	 * 判断距离是否符合
	 */
	private Boolean checkDistanceMatch(CheckinNetVo vo, String checkinLat, String checkinLng) {
		Boolean distanceMatch = false;

		int poiDistance = BaiduMapUtil.poiDistance(checkinLng, checkinLat, vo.getLng(), vo.getLat());
		int distance = vo.getDistance();

		if (poiDistance <= distance)
			distanceMatch = true;

		return distanceMatch;
	}

}
