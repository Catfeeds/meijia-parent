package com.xcloud.action.atools;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageInfo;
import com.simi.service.op.AppToolsService;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

@Controller
@RequestMapping(value = "/atools")
public class AtoolsController extends BaseController {
	
	@Autowired
	private AppToolsService appToolsService;
	 /**
	  * 应用中心列表
	  * @param model
	  * @param request
	  * @return
	  */
	@AuthPassport
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		
		int pageNo = ServletRequestUtils.getIntParameter(request, Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long companyId = accountAuth.getCompanyId();
		Long userId = accountAuth.getUserId();
		model.addAttribute("companyId", companyId);
		model.addAttribute("userId", userId);
		
		String appType = "xcloud";
		PageInfo result = appToolsService.selectByListPage(appType, pageNo, pageSize,userId);
		
		model.addAttribute("contentModel", result);
		return "atools/atools-index";
	}	
	
}
