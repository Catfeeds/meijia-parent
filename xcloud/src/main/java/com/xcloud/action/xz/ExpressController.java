package com.xcloud.action.xz;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;


@Controller
@RequestMapping(value = "/xz/express/")
public class ExpressController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XCompanyService xCompanyService;
	
	@Autowired
	private XCompanySettingService xCompanySettingService;
		
	//查询与登记
	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {

		return "xz/express-list";
	}	
	
	//快递结算
	@AuthPassport
	@RequestMapping(value = "close", method = RequestMethod.GET)
	public String setting(HttpServletRequest request, Model model) {
		
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();
				
		return "xz/express-close";

	}
	
	//快递服务商
	@AuthPassport
	@RequestMapping(value = "service", method = RequestMethod.GET)
	public String doSetting(HttpServletRequest request, Model model) {

		return "xz/express-service";
	}	
	
	
	@AuthPassport
	@RequestMapping(value = "/express-form", method = { RequestMethod.GET })
	public String expressForm(Model model, HttpServletRequest request,
			@RequestParam(value = "id", required = false) Long id) {
				
		return "xz/express-form";
	}	
	
}
