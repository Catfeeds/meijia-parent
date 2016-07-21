package com.xcloud.action.xz;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

@Controller
@RequestMapping(value = "/xz/checkin")
public class CheckInController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyDeptService xcompanyDeptService;

	@Autowired
	private XcompanyCheckinService xcompanyCheckInService;

	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model, CompanyCheckinSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();

		searchVo.setUserId(userId);

		PageInfo result = xcompanyCheckInService.selectByListPage(searchVo, pageNo, pageSize);

		model.addAttribute("contentModel", result);

		return "xz/checkin-list";
	}

	@AuthPassport
	@RequestMapping(value = "set", method = RequestMethod.GET)
	public String setting(HttpServletRequest request, Model model) {

		return "xz/checkin-set";
	}

	@AuthPassport
	@RequestMapping(value = "setForm", method = RequestMethod.GET)
	public String settingForm(HttpServletRequest request, Model model, @RequestParam("id") Long id) {

		// 1. 获取登录的用户/公司 id
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();
		Long companyId = accountAuth.getCompanyId();

		model.addAttribute("companyId", companyId);
		model.addAttribute("userId", userId);

		return "xz/checkin-set-form";
	}

	@AuthPassport
	@RequestMapping(value = "baseSet", method = RequestMethod.GET)
	public String baseSetting(HttpServletRequest request, Model model) {

		return "xz/check-base-set";
	}

}
