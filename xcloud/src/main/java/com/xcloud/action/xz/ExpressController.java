package com.xcloud.action.xz;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.utils.CardUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.card.Cards;
import com.simi.po.model.order.OrderExtClean;
import com.simi.po.model.record.RecordExpress;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardService;
import com.simi.service.record.RecordExpressService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.AppResultData;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.order.OrdersCleanAddOaVo;
import com.simi.vo.order.RecordExpressXcloudVo;
import com.simi.vo.record.RecordExpressSearchVo;
import com.simi.vo.user.UserAddrVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.StaffListVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

@Controller
@RequestMapping(value = "/xz/express/")
public class ExpressController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private RecordExpressService recordExpressService;

	// 查询与登记
	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model,
			RecordExpressSearchVo searchVo,
			@RequestParam(value = "express_no",required = false,defaultValue = "") String expressNo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		searchVo.setExpressNo(expressNo);
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();

		searchVo.setUserId(userId);

		PageInfo result = recordExpressService.selectByPage(searchVo, pageNo,
				pageSize);

		model.addAttribute("contentModel", result);

		return "xz/express-list";
	}


	@AuthPassport
	@RequestMapping(value = "/express-form", method = { RequestMethod.GET })
	public String expressForm(Model model, HttpServletRequest request) {
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long userId = accountAuth.getUserId();
		model.addAttribute("userId", userId);
		Users users = usersService.selectByPrimaryKey(userId);

		RecordExpressXcloudVo vo = new RecordExpressXcloudVo();
		RecordExpress express = recordExpressService.initRecordExpress();
		BeanUtilsExp.copyPropertiesIgnoreNull(express, vo);
		vo.setRemarks("");

		model.addAttribute("contentModel", vo);
		return "xz/express-form";
	}

	// 快递结算
	@AuthPassport
	@RequestMapping(value = "close", method = RequestMethod.GET)
	public String setting(HttpServletRequest request, Model model) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		return "xz/express-close";

	}

	// 快递服务商
	@AuthPassport
	@RequestMapping(value = "service", method = RequestMethod.GET)
	public String doSetting(HttpServletRequest request, Model model) {

		return "xz/express-service";
	}
}
