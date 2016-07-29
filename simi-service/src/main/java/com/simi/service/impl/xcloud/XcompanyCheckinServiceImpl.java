package com.simi.service.impl.xcloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.baidu.BaiduMapUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.dao.xcloud.XcompanyCheckinMapper;
import com.simi.po.model.record.RecordExpress;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyBenzTime;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyBenzTimeService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.xcloud.CheckinNetVo;
import com.simi.vo.xcloud.CheckinVo;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Service
public class XcompanyCheckinServiceImpl implements XcompanyCheckinService {

	@Autowired
	UsersService userService;

	@Autowired
	XcompanyCheckinMapper xcompanyCheckinMapper;

	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

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
		record.setCheckinNet("");
		record.setPoiName("");
		record.setPoiLat("");
		record.setPoiLng("");
		record.setSettingId(0L);
		record.setPoiDistance(0);
		record.setRemarks("");
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public CheckinNetVo initCheckinNetVo() {
		CheckinNetVo vo = new CheckinNetVo();
		vo.setId(0L);
		vo.setAddr("");
		vo.setLat("");
		vo.setLng("");
		vo.setDistance(500);
		vo.setStartTime("9:00");
		vo.setEndTime("18:00");
		vo.setFlexTime(10);
		vo.setWifis("");
		vo.setDeptIds("");
		vo.setDeptNames("");
		vo.setAddTimeStr("");
		vo.setUserName("");
		vo.setStatus((short) 1);

		return vo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageInfo selectByListPage(CompanyCheckinSearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);

		List<XcompanyCheckin> list = xcompanyCheckinMapper.selectByListPage(searchVo);

		PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public List<XcompanyCheckin> selectBySearchVo(CompanyCheckinSearchVo searchVo) {
		return xcompanyCheckinMapper.selectBySearchVo(searchVo);
	}

	@Override
	public List<CheckinVo> getVos(List<XcompanyCheckin> list) {
		List<CheckinVo> result = new ArrayList<CheckinVo>();

		if (list.isEmpty())
			return result;

		List<Long> userIds = new ArrayList<Long>();
		for (XcompanyCheckin item : list) {
			if (!userIds.contains(item.getUserId()))
				userIds.add(item.getUserId());
		}

		List<Users> users = new ArrayList<Users>();

		if (!userIds.isEmpty()) {
			UserSearchVo searchVo = new UserSearchVo();
			searchVo.setUserIds(userIds);
			users = userService.selectBySearchVo(searchVo);
		}

		for (int i = 0; i < list.size(); i++) {
			XcompanyCheckin item = list.get(i);
			CheckinVo vo = new CheckinVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);

			for (Users u : users) {
				if (u.getId().equals(vo.getUserId())) {
					vo.setName(u.getName());
					if (StringUtil.isEmpty(vo.getName()))
						vo.setName(u.getMobile());
					vo.setMobile(u.getMobile());
					break;
				}
			}

			Long addTime = vo.getAddTime();
			String addTimeStr = TimeStampUtil.timeStampToDateStr(addTime * 1000, "yyyy-MM-dd HH:MM:ss");
			vo.setAddTimeStr(addTimeStr);
			result.add(vo);
		}

		return result;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {

		return xcompanyCheckinMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insertSelective(XcompanyCheckin record) {
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
	 * 查找匹配的出勤配置，并计算出距离 返回参数data格式为
	 * 
	 * matchId poiDistance
	 */
	@Override
	public AppResultData<Object> matchCheckinSetting(Long id) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		XcompanyCheckin record = this.selectByPrimarykey(id);

		if (record == null)
			return result;

		Long userId = record.getUserId();
		Long companyId = record.getCompanyId();
		String checkinNet = record.getCheckinNet();
		String poiLat = record.getPoiLat();
		String poiLng = record.getPoiLng();

		// 1. 找出匹配的考勤配置
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setSettingType(Constants.SETTING_CHICKIN_NET);

		List<XcompanySetting> checkinSettings = xCompanySettingService.selectBySearchVo(searchVo);
		if (checkinSettings.isEmpty())
			return result;

		// 判断员工是否为团队一员
		UserCompanySearchVo companySearchVo = new UserCompanySearchVo();
		companySearchVo.setCompanyId(companyId);
		companySearchVo.setUserId(userId);
		companySearchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xcompanyStaffService.selectBySearchVo(companySearchVo);

		Long userDeptId = 0L;
		if (!staffList.isEmpty()) {
			XcompanyStaff userStaff = staffList.get(0);
			userDeptId = userStaff.getDeptId();
		}
		
		if (userDeptId.equals(0L)) return result;

		CheckinNetVo vo = null;
		Long matchId = 0L;
		Integer poiDistance = 0;
		// 1. 先找出匹配的部门
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

				if (deptIds.indexOf(userDeptId.toString() + ",") >= 0) {
					matchDepts.add(item);
				}
			}
		}
		
		if (matchDepts.isEmpty()) return result;

		List<XcompanySetting> matchWifis = new ArrayList<XcompanySetting>();
		// 2. 在符合部门的配置列表中匹配wifis是否符合
		if (!StringUtil.isEmpty(checkinNet)) {
			for (XcompanySetting item : matchDepts) {
				vo = null;
				if (item.getSettingValue() != null) {
					JSONObject setValue = (JSONObject) item.getSettingValue();
					vo = JSON.toJavaObject(setValue, CheckinNetVo.class);
				}

				if (vo == null)
					continue;
				String wifis = vo.getWifis();
				if (!StringUtil.isEmpty(wifis)) {
					String[] wifiAry = StringUtil.convertStrToArray(wifis);
					for (int i = 0; i < wifiAry.length; i++) {
						if (StringUtil.isEmpty(wifiAry[i]))
							continue;
						// 如果网络wifi相同,还需要计算距离
						if (checkinNet.toLowerCase().equals(wifiAry[i].toLowerCase())) {
							matchWifis.add(item);
						}
					}
				}
			}
		} else {
			matchWifis = matchDepts;
		}
		
		if (matchWifis.isEmpty()) return result;

		HashMap<String, Object> resultData = new HashMap<String, Object>();
		// 3.找出距离最短的
		List<HashMap<String, Object>> matchSettings = new ArrayList<HashMap<String, Object>>();
		for (XcompanySetting item : checkinSettings) {
			vo = null;
			if (item.getSettingValue() != null) {
				JSONObject setValue = (JSONObject) item.getSettingValue();
				vo = JSON.toJavaObject(setValue, CheckinNetVo.class);
				matchId = item.getId();
				poiDistance = BaiduMapUtil.poiDistance(poiLng, poiLat, vo.getLng(), vo.getLat());
				HashMap<String, Object> matchItem = new HashMap<String, Object>();
				matchItem.put("matchId", matchId);
				matchItem.put("poiDistance", poiDistance);
				matchSettings.add(matchItem);
			}
		}

		if (!matchSettings.isEmpty()) {
			Collections.sort(matchSettings, new Comparator<Map<String, Object>>() {
				public int compare(final Map<String, Object> o1, final Map<String, Object> o2) {
					return Integer.valueOf(o1.get("poiDistance").toString()).compareTo(Integer.valueOf(o2.get("poiDistance").toString()));
				}
			});

			resultData = matchSettings.get(0);
			result.setData(resultData);
			return result;
		}

		return result;
	}
	/**
	 * 根据考勤时间和班次对比 签到，返回考勤状态 正常或者迟到
	 * 
	 * 注意，都是换算为秒进行计算
	 */
	// @Override
	// public Map<String, Object> getCheckinStatus(Long benzTimeId, Long
	// checkinTime, Short checkinType) {
	// Map<String, Object> result = new HashMap<String, Object>();
	//
	// XcompanyBenzTime benzTime =
	// xCompanyBenzTimeService.selectByPrimaryKey(benzTimeId);
	//
	// if (benzTime == null) return result;
	//
	// String checkinStartTimeStr = benzTime.getCheckIn();
	// Integer flexibleMin = benzTime.getFlexibleMin();
	//
	// String checkInTimeStr = TimeStampUtil.timeStampToDateStr(checkinTime *
	// 1000, "yyyy-MM-dd");
	//
	// checkinStartTimeStr = checkInTimeStr + checkinStartTimeStr + "00";
	// Long checkinStartTime =
	// TimeStampUtil.getMillisOfDayFull(checkinStartTimeStr) / 1000;
	// Long checkinLimitTime = checkinStartTime + flexibleMin * 60;
	// String checkinStatusStr = "正常";
	// Short checkinStatus = 0;
	//
	// if (checkinTime > checkinLimitTime) {
	// checkinStatus = 1;
	//
	// Long laterSecond = checkinTime - checkinLimitTime;
	// if (laterSecond < 60) {
	// checkinStatusStr = "迟到" + laterSecond + "秒";
	// } else {
	// checkinStatusStr = "迟到" + laterSecond / 60 + "分钟";
	// }
	// }
	//
	// result.put("checkinStatus", checkinStatus);
	// result.put("checkinStatusStr", checkinStatusStr);
	//
	// return result;
	// }
}
