package com.simi.action.app.xcloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyBenz;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.po.model.xcloud.XcompanyStaffBenz;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.async.UserScoreAsyncService;
import com.simi.service.async.XcompanyAsyncService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyBenzService;
import com.simi.service.xcloud.XcompanyBenzTimeService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyStaffBenzService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Controller
@RequestMapping(value = "/app/company")
public class CompanyCheckinController extends BaseController {

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private XcompanyStaffService xCompanyStaffService;		

	@Autowired
	private XcompanyBenzService xCompanyBenzService;	
	
	@Autowired
	private XcompanyBenzTimeService xCompanyBenzTimeService;		
	
	@Autowired
	private XcompanyStaffBenzService xCompanyStaffBenzService;

	@Autowired
	private XcompanyCheckinService xCompanyCheckinService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	
	@Autowired
	private UserScoreAsyncService userScoreAsyncService;
	
	@Autowired
	private XcompanyAsyncService xcompanyAsyncService;
	
	@RequestMapping(value = "/checkin", method = { RequestMethod.POST })
	public AppResultData<Object> checkin(
			@RequestParam("company_id") Long companyId,
			@RequestParam("user_id") Long userId,
			@RequestParam("poi_name") String poiName,
			@RequestParam("poi_lat") String poiLat,
			@RequestParam("poi_lng") String poiLng,
			@RequestParam(value = "remarks", required = false, defaultValue = "") String remarks,
			@RequestParam(value = "checkin_net", required = false, defaultValue = "") String checkinNet,
			@RequestParam(value = "checkin_type", required = false, defaultValue = "0") Short checkinType,
			@RequestParam(value = "checkin_from", required = false, defaultValue = "0") Short checkinFrom
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		//判断员工是否为团队一员
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xCompanyStaffService.selectBySearchVo(searchVo);
		
		if (staffList.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("团队不存在");
			return result;
		}

		XcompanyStaff staff = staffList.get(0);
		
		Long staffId = staff.getId();
		
		Long checkinTime = TimeStampUtil.getNowSecond();
		
		//测试时间为 8：59：59秒
		String testDay = DateUtil.getToday();
		String testTimeStr = testDay + " 08:59:59";
		checkinTime = TimeStampUtil.getMillisOfDayFull(testTimeStr) / 1000;
		
		//测试时间为 18:00:01
//		String testDay = DateUtil.getToday();
//		String testTimeStr = testDay + " 18:00:10";
//		checkinTime = TimeStampUtil.getMillisOfDayFull(testTimeStr) / 1000;
		
		//记录考勤信息
		XcompanyCheckin record = xCompanyCheckinService.initXcompanyCheckin();

		record.setCompanyId(companyId);
		record.setStaffId(staffId);
		record.setUserId(userId);
		record.setCheckinFrom(checkinFrom);
		record.setCheckinType(checkinType);
		record.setCheckinNet(checkinNet);
		record.setPoiName(poiName);
		record.setPoiLat(poiLat);
		record.setPoiLng(poiLng);
		//todo .根据团队的地址计算距离团队的距离数（单位：米）;
		record.setPoiDistance(0);
		
		record.setRemarks(remarks);
		
		record.setAddTime(checkinTime);
		
		xCompanyCheckinService.insertSelective(record);
		
		//计算员工每天考勤信息，并且计算是否匹配到出勤设置记录.
		Long id = record.getId();
		xcompanyAsyncService.checkin(id);
		
		Map<String, String> datas = new HashMap<String, String>();
		String checkinTimeStr = TimeStampUtil.timeStampToDateStr(checkinTime * 1000, "HH:mm");
		datas.put("checkinTime", checkinTimeStr);
		datas.put("poiName", poiName);
		
		result.setData(datas);
		
		//生成消息
		userMsgAsyncService.newCheckinMsg(userId, record.getId());
		
		//赠送积分
		CompanyCheckinSearchVo searchVo1 = new CompanyCheckinSearchVo();
		searchVo1.setUserId(userId);
		searchVo1.setCompanyId(companyId);
		Long startTime = TimeStampUtil.getBeginOfToday();
		Long endTime = TimeStampUtil.getEndOfToday();
		searchVo1.setStartTime(startTime);
		searchVo1.setEndTime(endTime);
		List<XcompanyCheckin> list = xCompanyCheckinService.selectBySearchVo(searchVo1);
		if (list.size() <= 10) {
			userScoreAsyncService.sendScore(userId, Constants.SCORE_CHECKIN, "checkin", record.getId().toString(), "云考勤");
		}
		
		

		return result;
	}
	
	
	@RequestMapping(value = "/get_checkins", method = { RequestMethod.POST })
	public AppResultData<Object> getList(
//			@RequestParam("company_id") Long companyId,
			@RequestParam("user_id") Long userId,
			
			//date_range =  today  week month;
			@RequestParam(value = "date_range", required = false, defaultValue = "today") String dateRange
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		//找出用户的默认团队
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xCompanyStaffService.selectBySearchVo(searchVo);
		
		if (staffList.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("团队不存在");
			return result;
		}
		
		Long companyId = staffList.get(0).getCompanyId();
		for (XcompanyStaff item : staffList) {
			if (item.getIsDefault().equals((short)1)) {
				companyId = item.getCompanyId();
				break;
			}
		}
		
		Xcompany company = xCompanyService.selectByPrimaryKey(companyId);
		
		Map<String, Object> datas = new HashMap<String, Object>();
		
		datas.put("companyId", companyId);
		datas.put("companyName", company.getCompanyName());
		
		//先找出班次
		searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		List<XcompanyStaffBenz> benzList = xCompanyStaffBenzService.selectBySearchVo(searchVo);
		String benzName = "";
		if (!benzList.isEmpty()) {
			Long benzId = benzList.get(0).getBenzId();
			
			XcompanyBenz benz = xCompanyBenzService.selectByPrimaryKey(benzId);
			
			benzName = benz.getName();
		}
		datas.put("benz", benzName);
		
		CompanyCheckinSearchVo searchVo1 = new CompanyCheckinSearchVo();
		searchVo1.setUserId(userId);
		searchVo1.setCompanyId(companyId);
		
		//生成日期访问
		
		Long startTime = 0L;
		Long endTime = TimeStampUtil.getEndOfToday();
		
		if (dateRange.equals("today")) {
			startTime = TimeStampUtil.getBeginOfToday();
		}
		
		if (dateRange.equals("week")) {
			startTime = TimeStampUtil.getBeginOfWeek();
		}	
		
		if (dateRange.equals("month")) {
			startTime = TimeStampUtil.getBeginOfMonth(DateUtil.getYear(), DateUtil.getMonth());
		}
		
		
		searchVo1.setStartTime(startTime);
		searchVo1.setEndTime(endTime);

		List<XcompanyCheckin> list = xCompanyCheckinService.selectBySearchVo(searchVo1);
		
		List<Map> checkinList = new ArrayList<Map>();
		for (int i = 0; i < list.size(); i++) {
			XcompanyCheckin item = list.get(i);
			String checkinTimeStr = TimeStampUtil.timeStampToDateStr(item.getAddTime() * 1000, "HH:mm");
			Map<String, String> checkinItem = new HashMap<String, String>();
			checkinItem.put("checkinTime", checkinTimeStr);
			checkinItem.put("poiName", item.getPoiName());
			checkinItem.put("remarks", "");
			checkinItem.put("addTime", item.getAddTime().toString());
			checkinItem.put("checkintNet", item.getCheckinNet());
			checkinList.add(checkinItem);
		}
		datas.put("list", checkinList);
		result.setData(datas);
		
		return result;
	}	
	
}
