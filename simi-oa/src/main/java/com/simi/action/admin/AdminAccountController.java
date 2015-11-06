package com.simi.action.admin;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simi.oa.common.ConstantOa;
import com.simi.oa.common.ValidatException;
import com.simi.oa.vo.account.AccountRegisterVo;
import com.simi.po.model.admin.AdminAccount;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.admin.AdminAuthorityService;
import com.simi.service.admin.AdminRoleService;
import com.simi.vo.admin.AccountSearchVo;
import com.meijia.utils.StringUtil;

@Controller
@RequestMapping(value = "/account")
public class AdminAccountController extends AdminController {

	@Autowired
	private AdminAccountService adminAccountService;

	@Autowired
	private AdminRoleService adminRoleService;

	@Autowired
	private AdminAuthorityService adminAuthorityService;
		
	@RequestMapping(value = "/register", method = { RequestMethod.GET })
	public String register(HttpServletRequest request, Model model) {
		Long ids = Long.valueOf(request.getParameter("id"));

		if (ids == null) {
			ids = 0L;
		}
		AdminAccount account = adminAccountService.initAccount();
		
		AccountRegisterVo accountRegisterVo = new AccountRegisterVo();
		if (ids != null && ids > 0L) {
			account = adminAccountService.selectByPrimaryKey(ids);
			BeanUtils.copyProperties(account, accountRegisterVo);
			
		}		
		accountRegisterVo.setId(account.getId());
				
		model.addAttribute("adminAccount", accountRegisterVo);
		model.addAttribute(selectDataSourceName,
				adminRoleService.getSelectSource());
		
		return "account/adminForm";
	}

	@RequestMapping(value = "/adminForm", method = { RequestMethod.POST })
	public String register(
			HttpServletRequest request,
			Model model,
			@Valid @ModelAttribute("adminAccount") AccountRegisterVo accountRegisterVo,
			BindingResult result) throws ValidatException,
			NoSuchAlgorithmException {  
		Long ids = Long.valueOf(request.getParameter("id"));	 // 主键id
		
		Long roleId = Long.valueOf(request.getParameter("roleId"));//选择的角色
		
		
		AdminAccount account = null;
		
		//新增
		if (ids == null||ids == 0) {
			account = adminAccountService.initAccount();
			
		} else {
			
			account = adminAccountService.selectByPrimaryKey(ids);
		}
		
		
		BeanUtils.copyProperties(accountRegisterVo, account);
		
			String passwordMd5 = StringUtil.md5(account.getPassword().trim());
		account.setPassword(passwordMd5);
		if (accountRegisterVo == null) {
			return register(request, model);
		}
		String username = accountRegisterVo.getUsername();
		String password = accountRegisterVo.getPassword();
		String confirmPassword = accountRegisterVo.getConfirmPassword();
		if (!password.equals(confirmPassword)) {
			result.addError(new FieldError("adminAccount", "confirmPassword",
					"确认密码与密码输入不一致。"));
			return register(request, model);
		}

		AdminAccount adminAccount = adminAccountService.selectByUsername(username);

		if (adminAccount != null && adminAccount.getId() > 0 
				&& adminAccount.getId() != accountRegisterVo.getId()) {
			result.addError(new FieldError("adminAccount", "username",
					"该用户名已被注册。"));
			return register(request, model);
		}
		if (ids == null||ids == 0) {
			account.setId(0L);
			adminAccountService.insertSelective(account);
		} else {
			adminAccountService.updateByPrimaryKeySelective(account);				
		}		
		
		AdminAccount account2 = adminAccountService.selectByUsername(account.getUsername());
						
		model.addAttribute("adminAccount", account);
		
		String returnUrl = ServletRequestUtils.getStringParameter(request,
				"returnUrl", null);
		if (returnUrl == null)
			returnUrl = "/account/list";
		return "redirect:/account/list";
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model,
			AccountSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		model.addAttribute("contentModel",
				adminAccountService.listPage(searchVo, pageNo, pageSize));

		return "account/adminList";
	}

	/**
	 * 根据id跳转到用户绑定权限的界面
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	// @AuthPassport
	@RequestMapping(value = "/authorize/{id}", method = { RequestMethod.GET })
	public String authorize(HttpServletRequest request, Model model,
			@PathVariable(value = "id") String id) {
		Long ids = Long.valueOf(id.trim());
		if (!model.containsAttribute("contentModel")) {
			AdminAccount adminAccount = adminAccountService
					.selectByPrimaryKey(ids);
			model.addAttribute("contentModel", adminAccount);
		}
		model.addAttribute(selectDataSourceName,
				adminRoleService.getSelectSource());
		return "account/authorize";
	}

	/**
	 * 根据id更新改用户的权限
	 * 
	 * @param request
	 * @param model
	 * @param adminAccount
	 * @param id
	 * @param result
	 * @return
	 */
	// @AuthPassport
	@RequestMapping(value = "/authorize/{id}", method = { RequestMethod.POST })
	public String authorize(HttpServletRequest request, Model model,
			@Valid @ModelAttribute("contentModel") AdminAccount adminAccount,
			@PathVariable(value = "id") String id, BindingResult result) {
		Long ids = Long.valueOf(id.trim());
		if (result.hasErrors())
			return authorize(request, model, id);
		if (adminAccount != null) {
			adminAccountService.updateBind(ids, adminAccount.getRoleId(),
					adminAccount.getOrganizationId(),
					adminAccount.getImUsername());
		}
		String returnUrl = ServletRequestUtils.getStringParameter(request,
				"returnUrl", null);
		if (returnUrl == null)
			returnUrl = "/account/list";
		return "redirect:" + returnUrl;
	}

	/*
	 * 根据id将该用户置为可用，跳转到用户的list页面
	 */
	// @AuthPassport
	@RequestMapping(value = "/enable/{id}", method = { RequestMethod.GET })
	public String enableAdminRole(Model model,
			@PathVariable(value = "id") String id, HttpServletRequest response) {
		Long ids = Long.valueOf(id.trim());
		AdminAccount adminAccount = adminAccountService.selectByPrimaryKey(ids);
		if (adminAccount != null) {
			adminAccount.setEnable(ConstantOa.ROLE_ENABLE);
			adminAccountService.updateByPrimaryKeySelective(adminAccount);
		}
		return "redirect:/account/list";
	}

	/*
	 * 根据id将该用户置为不可用，跳转到用户的list页面
	 */
	// @AuthPassport
	@RequestMapping(value = "/disable/{id}", method = { RequestMethod.GET })
	public String disableAdminRole(Model model,
			@PathVariable(value = "id") String id, HttpServletRequest response) {
		Long ids = Long.valueOf(id.trim());
		AdminAccount adminAccount = adminAccountService.selectByPrimaryKey(ids);
		if (adminAccount != null) {
			adminAccount.setEnable(ConstantOa.ROLE_DISABLE);
			adminAccountService.updateByPrimaryKeySelective(adminAccount);
		}
		return "redirect:/account/list";
	}

	/*
	 * 根据id删除选中的用户，跳转到用户的list页面
	 */
	// @AuthPassport
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.GET })
	public String deleterAdminRole(Model model,
			@PathVariable(value = "id") String id, HttpServletRequest response) {
		Long ids = Long.valueOf(id.trim());
		String path = "redirect:/account/list";
		int result = adminAccountService.deleteByPrimaryKey(ids);
		if (result >= 0) {
			return path;
		} else {
			return "error";
		}
	}

}
