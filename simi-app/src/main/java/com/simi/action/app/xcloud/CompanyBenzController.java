package com.simi.action.app.xcloud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyBenz;
import com.simi.po.model.xcloud.XcompanyBenzTime;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.po.model.xcloud.XcompanyStaffBenz;
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
public class CompanyBenzController extends BaseController {

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

	@RequestMapping(value = "/get_benz", method = { RequestMethod.GET })
	public AppResultData<Object> getByBenz(
			@RequestParam("company_id") Long companyId,
			@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		if (companyId.equals(0L)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("团队不存在");
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
			result.setMsg("数据有错误：团队与员工未绑定");
			return result;
		}

		searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		List<XcompanyStaffBenz> rsList = xCompanyStaffBenzService.selectBySearchVo(searchVo);
		
		Long benzId = 0L;
		XcompanyStaffBenz staffBenz = null;
		
		if (rsList.isEmpty()) {
			//如果没有班次，则默认设置一个班次。测试用.
			benzId = xCompanyBenzService.setDefaultBenz(companyId);

		} else {
			staffBenz = rsList.get(0);
			benzId = staffBenz.getBenzId();
		}
		
		XcompanyBenz benz = xCompanyBenzService.selectByPrimaryKey(benzId);
		
		List<XcompanyBenzTime> benzTimeList = xCompanyBenzTimeService.selectByBenzId(benzId);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("benzId", benzId);
		resultMap.put("name", benz.getName());
		resultMap.put("benzTime", benzTimeList);
		
		//找出今天的考勤记录,并且为签到和签退的记录.
		CompanyCheckinSearchVo checkinSearchVo = new CompanyCheckinSearchVo();
		checkinSearchVo.setCompanyId(companyId);
		checkinSearchVo.setUserId(userId);
		checkinSearchVo.setCheckinType((short) 1);
		List<XcompanyCheckin> checkinVo = xCompanyCheckinService.selectBySearchVo(checkinSearchVo);
		
		result.setData(resultMap);

		return result;
	}
	
}
