package com.xcloud.action.xz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.baidu.BaiduConfigUtil;
import com.meijia.wx.utils.JsonUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserLeave;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.user.UserLeavePassService;
import com.simi.service.user.UserLeaveService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserLeaveListVo;
import com.simi.vo.user.UserLeaveSearchVo;
import com.simi.vo.xcloud.CheckinNetVo;
import com.simi.vo.xcloud.CheckinVo;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.company.DeptSearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

@Controller
@RequestMapping(value = "/xz/leave")
public class LeaveController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private UserLeaveService userLeaveService;

	@Autowired
	private UserLeavePassService userLeavePassService;

	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model, UserLeaveSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request, Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		searchVo.setCompanyId(companyId);

		int cyear = DateUtil.getYear();
		int cmonth = DateUtil.getMonth();
		
		
		//处理接收参数一天的情况，selectDay
		String selectDay = request.getParameter("selectDay");
		if (!StringUtil.isEmpty(selectDay)) {
			Date selectDate = DateUtil.parse(selectDay);
			cyear = DateUtil.getYear(selectDate);
			cmonth = DateUtil.getMonth(selectDate);
		}

		// 年度选择框
		List<Integer> selectYears = new ArrayList<Integer>();
		selectYears.add(cyear - 1);
		selectYears.add(cyear);
		model.addAttribute("selectYears", selectYears);
		// 月份选择框
		List<Integer> selectMonths = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			if (i > cmonth)
				break;
			selectMonths.add(i);
		}
		model.addAttribute("selectMonths", selectMonths);

		if (searchVo.getCyear() == 0)
			searchVo.setCyear(cyear);
		if (searchVo.getCmonth() == 0)
			searchVo.setCmonth(cmonth);

		String monthBeginDay = DateUtil.getFirstDayOfMonth(cyear, cmonth);
		String monthEndDay = DateUtil.getLastDayOfMonth(cyear, cmonth);
		
		Date startDate = DateUtil.parse(monthBeginDay);
		Date endDate = DateUtil.parse(monthEndDay);
		
		searchVo.setStartDate(startDate);
		searchVo.setEndDate(endDate);
		
		
		model.addAttribute("searchModel", searchVo);

		PageInfo result = userLeaveService.selectByListPage(searchVo, pageNo, pageSize);

		List<UserLeave> list = result.getList();

		List<UserLeaveListVo> listVo = userLeaveService.changeToListVo(list);

		model.addAttribute("contentModel", result);
		model.addAttribute("listVo", listVo);

		return "xz/leave-list";
	}

}
