package com.xcloud.action.atools;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.op.AppTools;
import com.simi.po.model.op.UserAppTools;
import com.simi.service.op.AppToolsService;
import com.simi.service.op.UserAppToolsService;
import com.simi.vo.ApptoolsSearchVo;
import com.simi.vo.po.AppToolsVo;
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
	
	@Autowired
	private UserAppToolsService userAppToolsService;
	
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
		ApptoolsSearchVo searchVo = new ApptoolsSearchVo();
		searchVo.setAppType(appType);
		PageInfo result = appToolsService.selectByListPage(searchVo, pageNo, pageSize);
		
		List<AppTools> list = result.getList();

		for (int i = 0; i < list.size(); i++) {
			AppTools appTools = list.get(i);
			AppToolsVo vo = appToolsService.getAppToolsVo(appTools, userId);
			/*if (vo.getStatus() == null){
				vo.setStatus((short)0);
			}*/
			list.set(i, vo);
		}
		result = new PageInfo(list);
		
		
		
		model.addAttribute("contentModel", result);
		return "atools/atools-index";
	}	
	/**
	 * 应用状态改变更新
	 * @param tId
	 * @param userId
	 * @param status
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/user_app_tools_oa", method = RequestMethod.GET)
	public String list(
			@RequestParam("t_id") Long tId,
			@RequestParam("user_id") Long userId,
			@RequestParam("status") Short status)  {
		
		
		UserAppTools userAppTools = userAppToolsService.selectByUserIdAndTid(userId,tId);
		
		UserAppTools record = userAppToolsService.initUserAppTools();
		if (userAppTools == null) {
			record.settId(tId);
			record.setUserId(userId);
			record.setStatus(status);
			userAppToolsService.insert(record);
		}else {
			userAppTools.setStatus(status);
			userAppToolsService.updateByPrimaryKeySelective(userAppTools);
		}
		return "redirect:index";
	}
}
