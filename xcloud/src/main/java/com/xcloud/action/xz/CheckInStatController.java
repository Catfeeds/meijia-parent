package com.xcloud.action.xz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.meijia.utils.DateUtil;
import com.meijia.utils.Week;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyCheckinStatService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;

@Controller
@RequestMapping(value = "/xz/checkin")
public class CheckInStatController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyDeptService xcompanyDeptService;

	@Autowired
	private XcompanyCheckinStatService xcompanyCheckInStatService;

	@AuthPassport
	@RequestMapping(value = "stat-list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model, CompanyCheckinSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		int cyear = DateUtil.getYear();
		int cmonth = DateUtil.getMonth();
		searchVo.setCompanyId(companyId);
		if (searchVo.getCyear() == 0) searchVo.setCyear(cyear);
		if (searchVo.getCmonth() == 0) searchVo.setCmonth(cmonth);

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

		// 当月所有日期
		List<String> months = DateUtil.getAllDaysOfMonth(searchVo.getCyear(), searchVo.getCmonth());

		List<String> viewDays = new ArrayList<String>();
		for (int i = 0; i < months.size(); i++) {
			String dayStr = months.get(i);
			int day = Integer.valueOf(dayStr.substring(8));
			viewDays.add(String.valueOf(day));
		}
		model.addAttribute("viewDays", viewDays);

		// 日期对应的星期
		List<String> weeks = new ArrayList<String>();
		Date tmpDate = null;
		Week w = null;
		for (int i = 0; i < months.size(); i++) {
			tmpDate = DateUtil.parse(months.get(i));
			w = DateUtil.getWeek(tmpDate);
			String wName = w.getChineseName();
			weeks.add(wName.substring(2));
		}
		model.addAttribute("weeks", weeks);

		// 所有员工的统计情况

		List<HashMap<String, Object>> staffChekins = xcompanyCheckInStatService.getStaffCheckin(searchVo);
		model.addAttribute("staffChekins", staffChekins);
		model.addAttribute("searchModel", searchVo);
		return "xz/checkin-stat-list";
	}

}
