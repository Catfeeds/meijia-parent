package com.simi.service.impl.xcloud;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.Week;
import com.meijia.utils.baidu.BaiduMapUtil;
import com.simi.common.Constants;
import com.simi.po.dao.xcloud.XcompanyCheckinStatMapper;
import com.simi.po.model.record.RecordHolidayDay;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanyCheckinStat;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.record.RecordHolidayDayService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyCheckinStatService;
import com.simi.service.xcloud.XcompanyDeptService;
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
	
	@Autowired
	private RecordHolidayDayService recordHolidayDayService;
	
	@Autowired
	private XcompanyDeptService xcompanyDeptService;

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
		record.setLeaveId(0L);
		record.setLeaveType((short) 0);
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
		
		//设置昨天下午是否已经打开
		Boolean isCheckin = false;
		
		// 4. 循环集合C，找出与此配置项是否符合规则，注：时间为倒序
		String today = DateUtil.getYesterday();

		// 时间匹配标识
		Boolean timeMatch = false;

		// 网络wifi匹配标识
		Boolean wifiMatch = false;

		// 距离匹配标识
		Boolean distanceMatch = false;

		XcompanyCheckin pmCheckin = null;
		XcompanyCheckin matchPmCheckin = null;
		XcompanySetting matchSetting = null;
		for (int i = 0; i < checkinList.size(); i++) {
			pmCheckin = checkinList.get(i);

			vo = null;

			int checkHour = TimeStampUtil.getHour(pmCheckin.getAddTime() * 1000);
			if (checkHour < 13)
				continue;
			
			//下午有打过卡
			isCheckin = true;
			
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
					matchPmCheckin = pmCheckin;
					matchSetting = item;
					break;
				}
			}

			// 最后如果三个都匹配，则可以记录并退出循环
			if (timeMatch == true && wifiMatch == true && distanceMatch == true)
				break;
		}

		Long cday = TimeStampUtil.getBeginOfYesterDay();
		Date cdate = TimeStampUtil.timeStampToDate(cday * 1000);
		int cyear = DateUtil.getYear(cdate);
		int cmonth = DateUtil.getMonth(cdate);
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
			stat.setCdayPm(matchPmCheckin.getAddTime());
			stat.setCdayPmId(matchSetting.getId());
		} else if (isCheckin == true) {
			stat.setIsEaryly((short) 1);
			stat.setCdayPm(checkinList.get(0).getAddTime());
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
	
	/**
	 * 获取每个员工的月度考勤明细表
	 */
	@Override
	public List<HashMap<String, Object>> getStaffCheckin(CompanyCheckinSearchVo searchVo) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

		int cyear = searchVo.getCyear();
		int cmonth = searchVo.getCmonth();
		// 当月所有日期
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
		
		//全年度的假日列表
		List<RecordHolidayDay> holidays = recordHolidayDayService.selectByYear(cyear);
		
		int no = 1;
		for (XcompanyStaff staff : staffList) {
			HashMap<String, Object> data = new HashMap<String, Object>();
			Long userId = staff.getUserId();
			String name = "";
			for (Users u : users) {
				if (staff.getUserId().equals(u.getId())) {
					name = u.getName();
					if (StringUtil.isEmpty(name))
						name = u.getMobile();
				}
			}
			data.put("companyId", companyId.toString());
			data.put("userId", userId.toString());
			data.put("no", String.valueOf(no));
			data.put("name", name);
			no++;

			List<HashMap<String, String>> dataAm = new ArrayList<HashMap<String, String>>();
			List<HashMap<String, String>> dataPm = new ArrayList<HashMap<String, String>>();

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
				
				Boolean isHoliday = this.getIsHoliday(dayStr, holidays);
				HashMap<String, String> am = new HashMap<String, String>();
				HashMap<String, String> pm = new HashMap<String, String>();
				
				am.put("leaveId", "0");
				pm.put("leaveId", "0");
				
				if (isHoliday) {
					checkinAmStatus = "/";
					checkinPmStatus = "/";
				} else {
					if (checkinStat != null) {
						
						// 判断上午的情况
						checkinAmStatus = this.getCheckinAmStatus(checkinStat);
						
						// 判断下午的情况
						checkinPmStatus = this.getCheckinPmStatus(checkinStat);
						
						am.put("leaveId", checkinStat.getLeaveId().toString());
						pm.put("leaveId", checkinStat.getLeaveId().toString());
					}
				}

				
				am.put("cday", dayStr);
				am.put("status", checkinAmStatus);
				dataAm.add(am);
				
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
	 * 获取每个员工的月度考勤统计表
	 * 具备的数据有
	 * 1. 月份日期列表
	 * 2. 月份的公休日期列表
	 * 3. 员工列表
	 * 4. 员工考勤列表
	 * 计算前的数据有
	 * 应总考勤天数 totalDays = 月份天数 - 周末天数 - 公休天数
	 * 总休息日totalHolidays = 周末天数 + 公休天数
	 * 员工总考勤天数 totalCheckin = 员工考勤统计列表 - totalCheckin
	 * 总迟到次数totalLate = 员工考勤列表循环计算得出. - totalLate
	 * 总早退次数totalEarly = 员工考勤列表循环计算得出. - totalEarly
	 * 总请假次数totalLeave = 员工考勤列表循环计算得出.- totalLeave
	 * 各个请假类别的总数  = 员工考勤列表循环计算得出.
	 */
	@Override
	public List<HashMap<String, Object>> getStaffTotalCheckin(CompanyCheckinSearchVo searchVo) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

		int cyear = searchVo.getCyear();
		int cmonth = searchVo.getCmonth();
		// 当月所有日期
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
		
		//部门列表
		List<XcompanyDept> deptList = xcompanyDeptService.selectByXcompanyId(companyId);
		
		//全年度的假日列表
		List<RecordHolidayDay> holidays = recordHolidayDayService.selectByYear(cyear);
		
		//周末总数
		int totalWeekends = 0;
		int totalPHolidays = 0;
		for (int i = 0; i < months.size(); i++) {
			Date tmpDate = DateUtil.parse(months.get(i));
			Week w = DateUtil.getWeek(tmpDate);
			if (w.getNumber() == 6 || w.getNumber() == 7) 
				totalWeekends++;
			
			//公共假日数
			for (RecordHolidayDay p : holidays) {
				if (p.getCday().equals(months.get(i))) {
					totalPHolidays++;
					break;
				}
			}
		}
		
		//应总考勤天数
		int totalDays = months.size() - totalWeekends - totalPHolidays;
		
		int no = 1;
		for (XcompanyStaff staff : staffList) {
			HashMap<String, Object> data = new HashMap<String, Object>();
			Long userId = staff.getUserId();
			String name = "";
			for (Users u : users) {
				if (staff.getUserId().equals(u.getId())) {
					name = u.getName();
					if (StringUtil.isEmpty(name))
						name = u.getMobile();
				}
			}
			data.put("companyId", companyId.toString());
			data.put("userId", userId.toString());
			data.put("no", String.valueOf(no));
			data.put("name", name);
			no++;
			
			//部门名称
			data.put("deptName", "");
			for (XcompanyDept xd : deptList) {
				if (xd.getDeptId().equals(staff.getDeptId())) {
					data.put("deptName", xd.getName());
					break;
				}
			}
			
			//循环考勤记录，并计算各个数值.
			
			//总考勤天数
			int totalCheckinDays = 0;
						
			//总迟到次数
			int totalLate = 0;
			
			//总早退次数
			int totalEarly = 0;
			
			//总旷工次数
			int totalAbsence = 0;
			

			
			//总未打卡次数
			int totalNoCheckin = 0;
			
			//是否满勤
			String isAllCheckin = "";
			
			//总请假次数
			int totalLeave = 0;
			
			//总病假次数
			int totalLeavelType0 = 0;
			//总事假次数
			int totalLeavelType1 = 0;
			//总婚假次数
			int totalLeavelType2 = 0;
			//总丧假次数
			int totalLeavelType3 = 0;
			//总产假次数
			int totalLeavelType4 = 0;
			//总年假次数
			int totalLeavelType5 = 0;
			//总其他次数
			int totalLeavelType6 = 0;
			
			for (XcompanyCheckinStat stat : checkinStats) {
				if (!userId.equals(stat.getUserId())) continue;
				

				
				if (stat.getCdayAm() > 0L || stat.getCdayPm() > 0L) totalCheckinDays++;
				
				if (stat.getIsLate() == 1) totalLate++;
				if (stat.getIsEaryly() == 1) totalEarly++;
				
				if (stat.getCdayAm().equals(0L) && stat.getCdayPm().equals(0L)) totalAbsence++;
				
				if (stat.getLeaveId() > 0) {
					totalLeave++;
					Short leaveType = stat.getLeaveType();
					switch (leaveType) {
					case 0:
						totalLeavelType0++;
						break;
					case 1:
						totalLeavelType1++;
						break;
					case 2:
						totalLeavelType2++;
						break;
					case 3:
						totalLeavelType3++;
						break;
					case 4:
						totalLeavelType4++;
						break;
					case 5:
						totalLeavelType5++;
						break;
					case 6:
						totalLeavelType6++;
						break;
					}
				}

			}
			
			
			//旷工天数
			int totalNoCheckinDays = totalDays - totalCheckinDays - totalLeave;
			totalAbsence = totalAbsence + totalNoCheckinDays;
			
			//未打卡次数 = 旷工天数 * 2；
			totalNoCheckin = totalAbsence * 2;
			
			//是否满勤
			if (totalDays == totalCheckinDays && totalLate == 0 && totalEarly == 0 && totalLeave == 0) {
				isAllCheckin = "√";
			}
			
			data.put("totalCheckinDays", totalCheckinDays);
			data.put("totalRestDays", totalWeekends + totalPHolidays);
			data.put("totalOverTimeDays", 0);
			data.put("totalLeavelType0", totalLeavelType0);
			data.put("totalLeavelType1", totalLeavelType1);
			data.put("totalLeavelType2", totalLeavelType2);
			data.put("totalLeavelType3", totalLeavelType3);
			data.put("totalLeavelType4", totalLeavelType4);
			data.put("totalLeavelType5", totalLeavelType5);
			data.put("totalLeavelType6", totalLeavelType6);
			
			data.put("totalLate", totalLate);
			data.put("totalEarly", totalEarly);
			data.put("totalAbsence", totalAbsence);
			data.put("totalNoCheckin", totalNoCheckin);
			data.put("isAllCheckin", isAllCheckin);
			
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
		String endCheckTimeStr = d + " " + vo.getEndTime() + ":00";
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
	
	/**
	 * 判断是否为周末或者假日
	 */
	public Boolean getIsHoliday(String cday, List<RecordHolidayDay> holidayList) {
		
		Boolean isHoliday = false;
		
		//1. 先判断是否为假日
		for (RecordHolidayDay item : holidayList) {
			if (item.getCday().equals(cday)) {
				isHoliday = true;
				break;
			}
		}
		
		//2. 在判断是否为周六，周日.
		if (isHoliday == false) {
			Date date = DateUtil.parse(cday);
			Week d = DateUtil.getWeek(date);
			
			if (d.getNumber() == 6 || d.getNumber() == 7) isHoliday = true;
		}
		
		
		return isHoliday;
	}

	/**
	 * 获得考勤状态 1. leaveId > 0 ,则根据leaveType 
	 * 判断为请假类型 病假 = 病 事假 = 事 婚假 = 婚 丧假 = 丧 产假 = 产 年休假 = 休 其他 = 其 
	 * 2. cdayAm == 0 && cdayPm == 0 && leaveId == 0 ,则为旷工 
	 *   旷工 = 旷 
	 * 3. cdayAm > 0 && lsLate == 0 则为正常打卡. 
	 *    日勤 = √ 
	 * 4. cdayAm > 0 && lsLate = 1 ,则为迟到 
	 *    迟到 = 迟 
	 * 5. cdayAm = 0 && lsLate = 0 , 则为上午未打卡 
	 *    未打卡 = - 
	 * 6. cdayPM > 0 && isEarly = 0 . 则为正常打卡. 
	 *    日勤 = √ 
	 * 7. cdayPm > 0 && lsEarly = 1, 则为早退 
	 *    早退 = 退 
	 * 8. cdayPm == 0 && lsEarly == 0 , 则为下午未打卡 
	 *    未打卡 = -
	 * 
	 * 
	 * @param checkinStat
	 * @return
	 */
	private String getCheckinAmStatus(XcompanyCheckinStat checkinStat) {
		String checkinStatus = "";

		// 1. leaveId > 0 ,则根据leaveType 判断为请假类型
		Long leaveId = checkinStat.getLeaveId();
		Short leaveType = checkinStat.getLeaveType();
		if (leaveId > 0L) {
			switch (leaveType) {
			case 0:
				checkinStatus = "病";
				break;
			case 1:
				checkinStatus = "事";
				break;
			case 2:
				checkinStatus = "婚";
				break;
			case 3:
				checkinStatus = "丧";
				break;
			case 4:
				checkinStatus = "产";
				break;
			case 5:
				checkinStatus = "年";
				break;
			case 6:
				checkinStatus = "其";
				break;

			default:
				checkinStatus = "";
			}

			if (!StringUtil.isEmpty(checkinStatus))
				return checkinStatus;
		}

		Long cdayAm = checkinStat.getCdayAm();
		Long cdayPm = checkinStat.getCdayPm();
		Short isLate = checkinStat.getIsLate();
		// 2. cdayAm == 0 && cdayPm == 0 && leaveId == 0 , 则为旷工
		if (cdayAm.equals(0L) && cdayPm.equals(0L) && leaveId.equals(0L)) {
			checkinStatus = "旷";
			return checkinStatus;
		}

		// 3. cdayAm > 0 && lsLate == 0 则为正常打卡.
		if (cdayAm > 0L && isLate == 0) {
			checkinStatus = "√";
			return checkinStatus;
		}

		// 4. cdayAm > 0 && lsLate = 1 ,则为迟到
		if (cdayAm > 0L && isLate == 1) {
			checkinStatus = "迟";
			return checkinStatus;
		}

		// 5. cdayAm = 0 && lsLate = 0 , 则为上午未打卡
		if (cdayAm.equals(0L) && isLate == 0) {
			checkinStatus = "-";
			return checkinStatus;
		}

		return checkinStatus;
	}

	/**
	 * 获得考勤状态 1. leaveId > 0 ,则根据leaveType 
	 * 判断为请假类型 病假 = 病 事假 = 事 婚假 = 婚 丧假 = 丧 产假 = 产 年休假 = 休 其他 = 其 
	 * 2. cdayAm == 0 && cdayPm == 0 && leaveId == 0 ,则为旷工 
	 *   旷工 = 旷 
	 * 3. cdayAm > 0 && lsLate == 0 则为正常打卡. 
	 *    日勤 = √ 
	 * 4. cdayAm > 0 && lsLate = 1 ,则为迟到 
	 *    迟到 = 迟 
	 * 5. cdayAm = 0 && lsLate = 0 , 则为上午未打卡 
	 *    未打卡 = - 
	 * 6. cdayPM > 0 && isEarly = 0 . 则为正常打卡. 
	 *    日勤 = √ 
	 * 7. cdayPm > 0 && lsEarly = 1, 则为早退 
	 *    早退 = 退 
	 * 8. cdayPm == 0 && lsEarly == 0 , 则为下午未打卡 
	 *    未打卡 = -
	 * 
	 * 
	 * @param checkinStat
	 * @return
	 */
	private String getCheckinPmStatus(XcompanyCheckinStat checkinStat) {
		String checkinStatus = "";
		
		// 1. leaveId > 0 ,则根据leaveType 判断为请假类型
		Long leaveId = checkinStat.getLeaveId();
		Short leaveType = checkinStat.getLeaveType();
		if (leaveId > 0L) {
			switch (leaveType) {
			case 0:
				checkinStatus = "病";
				break;
			case 1:
				checkinStatus = "事";
				break;
			case 2:
				checkinStatus = "婚";
				break;
			case 3:
				checkinStatus = "丧";
				break;
			case 4:
				checkinStatus = "产";
				break;
			case 5:
				checkinStatus = "年";
				break;
			case 6:
				checkinStatus = "其";
				break;

			default:
				checkinStatus = "";
			}

			if (!StringUtil.isEmpty(checkinStatus))
				return checkinStatus;
		}
		
		//如果为今天时间，则不出现下午考勤情况.
		if (checkinStat.getCday().equals(TimeStampUtil.getBeginOfToday())) return checkinStatus;

		Long cdayAm = checkinStat.getCdayAm();
		Long cdayPm = checkinStat.getCdayPm();
		Short isLate = checkinStat.getIsLate();
		Short isEarly = checkinStat.getIsEaryly();
		// 2. cdayAm == 0 && cdayPm == 0 && leaveId == 0 , 则为旷工
		if (cdayAm.equals(0L) && cdayPm.equals(0L) && leaveId.equals(0L)) {
			checkinStatus = "旷";
			return checkinStatus;
		}

		// 6. cdayPM > 0 && isEarly = 0 . 则为正常打卡.
		if (cdayPm > 0L && isEarly == 0) {
			checkinStatus = "√";
			return checkinStatus;
		}

		// 7. cdayPm > 0 && lsEarly = 1, 则为早退 
		if (cdayPm > 0L && isEarly == 1) {
			checkinStatus = "退";
			return checkinStatus;
		}

		// 8. cdayPm == 0 && lsEarly == 0 , 则为下午未打卡 
		if (cdayPm.equals(0L) && isLate == 0) {
			checkinStatus = "-";
			return checkinStatus;
		}

		return checkinStatus;
	}

}
