package com.xcloud.action.xz;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AuthPassport;


@Controller
@RequestMapping(value = "/xz/water/")
public class WaterController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XCompanyService xCompanyService;
	
	@Autowired
	private XCompanySettingService xCompanySettingService;
		
	//一键送水
	@AuthPassport
	@RequestMapping(value = "water-form", method = RequestMethod.GET)
	public String waterForm(Model model, HttpServletRequest request) {
		
		model.addAttribute("contentModel", "");
		return "xz/water-form";
	}
	
	//查询与登记
	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {

		return "xz/water-list";
	}		
	
	//快递服务商
	@AuthPassport
	@RequestMapping(value = "service", method = RequestMethod.GET)
	public String doSetting(HttpServletRequest request, Model model) {

		return "xz/water-service";
	}	
}
