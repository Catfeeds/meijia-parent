package com.xcloud.action;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.vo.LoginVo;
import com.meijia.utils.StringUtil;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyAdmin;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyAdminService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.CompanyAdminSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Controller
@RequestMapping(value = "/")
public class LoginController extends BaseController {

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyAdminService xCompanyAdminService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UserSmsTokenService smsTokenService;	

	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String login(Model model) {
		if (!model.containsAttribute("contentModel")) {
			LoginVo vo = new LoginVo();
			vo.setLoginType("tab-login-pass");
			model.addAttribute("contentModel", vo);
		}

		return "/home/login";
	}

	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public String login(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") LoginVo loginVo, BindingResult result)
			throws NoSuchAlgorithmException {
		// 如果有验证错误 返回到form页面
		if (result.hasErrors())
			return login(model);

		String userName = loginVo.getUsername().trim();
		String password = loginVo.getPassword().trim();

		String passwordMd5 = StringUtil.md5(password.trim());

		CompanyAdminSearchVo searchVo1 = new CompanyAdminSearchVo();
		searchVo1.setUserName(userName);
		searchVo1.setPassMd5(passwordMd5);

		XcompanyAdmin xCompanyAdmn = null;
		List<XcompanyAdmin> rs = xCompanyAdminService.selectBySearchVo(searchVo1);

		Long companyId = 0L;
		Xcompany xcompany = null;
		if (rs.isEmpty()) {
			result.addError(new FieldError("contentModel", "username", "用户名或密码错误。"));
			model.addAttribute("contentModel", loginVo);
			return login(model);
		} else {
			if (rs.size() == 1) {
				xCompanyAdmn = rs.get(0);
			} else {
				Users u = usersService.selectByMobile(userName);
				UserCompanySearchVo searchVo = new UserCompanySearchVo();
				searchVo.setUserId(u.getId());
				// searchVo.setIsDefault((short) 1);
				List<XcompanyStaff> list = xcompanyStaffService.selectBySearchVo(searchVo);

				for (XcompanyStaff item : list) {
					if (item.getIsDefault().equals((short) 1)) {
						companyId = item.getCompanyId();
						break;
					}
				}

				if (companyId.equals(0L)) {
					xCompanyAdmn = rs.get(0);
				} else {
					for (XcompanyAdmin item : rs) {
						if (item.getCompanyId().equals(companyId)) {
							xCompanyAdmn = item;
							break;
						}
					}
				}

			}
		}

		if (xCompanyAdmn == null) {
			result.addError(new FieldError("contentModel", "username", "用户名或密码错误。"));
			model.addAttribute("contentModel", loginVo);
			return login(model);
		}

		companyId = xCompanyAdmn.getCompanyId();
		xcompany = xCompanyService.selectByPrimaryKey(companyId);

		Users u = usersService.selectByMobile(userName);

		Long userId = u.getId();
		String companyName = xcompany.getCompanyName();
		String shortName = xcompany.getShortName();
		String headImg = usersService.getHeadImg(u);

		// 员工信息
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);

		List<XcompanyStaff> rsList = xcompanyStaffService.selectBySearchVo(searchVo);
		XcompanyStaff xcompanyStaffExist = null;
		if (!rsList.isEmpty()) {
			xcompanyStaffExist = rsList.get(0);
		}

		AccountAuth accountAuth = new AccountAuth();
		accountAuth.setUserId(userId);
		accountAuth.setName(u.getName());
		accountAuth.setHeadImg(headImg);
		accountAuth.setRealName(u.getRealName());
		accountAuth.setMobile(u.getMobile());
		accountAuth.setCompanyId(companyId);
		accountAuth.setCompanyName(companyName);
		accountAuth.setShortName(shortName);
		accountAuth.setCompanyList(rs);

		if (xcompanyStaffExist != null) {
			accountAuth.setStaffId(xcompanyStaffExist.getId());
		}

		AuthHelper.setSessionAccountAuth(request, accountAuth);

		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		if (returnUrl == null)
			returnUrl = "/index";
		return "redirect:" + returnUrl;

	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/login-sms", method = { RequestMethod.POST })
	public String loginSms(
			HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") LoginVo loginVo, BindingResult result)
			throws NoSuchAlgorithmException {
		// 如果有验证错误 返回到form页面

		String mobile = request.getParameter("mobile");
		if (StringUtil.isEmpty(mobile)) {
			result.addError(new FieldError("contentModel","password","手机号不能为空"));
			return login(model);
		}
		
		String smsToken = request.getParameter("sms_token");

		AppResultData<Object> validateResult = smsTokenService.validateSmsToken(mobile, smsToken, (short) 3);
		
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			result.addError(new FieldError("contentModel","password","验证码错误"));
			loginVo.setLoginType("tab-login-pass");
			model.addAttribute("contentModel", loginVo);
			return login(model);
		}
		
		Users u = usersService.selectByMobile(mobile);
		Long userId = u.getId();
		
		if (u == null) {
			result.addError(new FieldError("contentModel","password","用户不存在"));
			loginVo.setLoginType("tab-login-pass");
			model.addAttribute("contentModel", loginVo);
			return login(model);
		}

		CompanyAdminSearchVo searchVo1 = new CompanyAdminSearchVo();
		searchVo1.setUserId(u.getId());
		

		XcompanyAdmin xCompanyAdmn = null;
		List<XcompanyAdmin> rs = xCompanyAdminService.selectBySearchVo(searchVo1);

		Long companyId = 0L;
		Xcompany xcompany = null;
		if (rs.isEmpty()) {
			result.addError(new FieldError("contentModel", "password", "用户名或密码错误。"));
			loginVo.setLoginType("tab-login-sms");
			model.addAttribute("contentModel", loginVo);
			return login(model);
		} else {
			if (rs.size() == 1) {
				xCompanyAdmn = rs.get(0);
			} else {
				
				UserCompanySearchVo searchVo = new UserCompanySearchVo();
				searchVo.setUserId(u.getId());
				// searchVo.setIsDefault((short) 1);
				List<XcompanyStaff> list = xcompanyStaffService.selectBySearchVo(searchVo);

				for (XcompanyStaff item : list) {
					if (item.getIsDefault().equals((short) 1)) {
						companyId = item.getCompanyId();
						break;
					}
				}

				if (companyId.equals(0L)) {
					xCompanyAdmn = rs.get(0);
				} else {
					for (XcompanyAdmin item : rs) {
						if (item.getCompanyId().equals(companyId)) {
							xCompanyAdmn = item;
							break;
						}
					}
					
					if (xCompanyAdmn == null) xCompanyAdmn = rs.get(0);
				}

			}
		}

		if (xCompanyAdmn == null) {
			result.addError(new FieldError("contentModel", "password", "用户名或密码错误。"));
			loginVo.setLoginType("tab-login-sms");
			model.addAttribute("contentModel", loginVo);
			return login(model);
		}

		companyId = xCompanyAdmn.getCompanyId();
		xcompany = xCompanyService.selectByPrimaryKey(companyId);

		
		String companyName = xcompany.getCompanyName();
		String shortName = xcompany.getShortName();
		String headImg = usersService.getHeadImg(u);

		// 员工信息
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);

		List<XcompanyStaff> rsList = xcompanyStaffService.selectBySearchVo(searchVo);
		XcompanyStaff xcompanyStaffExist = null;
		if (!rsList.isEmpty()) {
			xcompanyStaffExist = rsList.get(0);
		}

		AccountAuth accountAuth = new AccountAuth();
		accountAuth.setUserId(userId);
		accountAuth.setName(u.getName());
		accountAuth.setHeadImg(headImg);
		accountAuth.setRealName(u.getRealName());
		accountAuth.setMobile(u.getMobile());
		accountAuth.setCompanyId(companyId);
		accountAuth.setCompanyName(companyName);
		accountAuth.setShortName(shortName);
		accountAuth.setCompanyList(rs);

		if (xcompanyStaffExist != null) {
			accountAuth.setStaffId(xcompanyStaffExist.getId());
		}

		AuthHelper.setSessionAccountAuth(request, accountAuth);

		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		if (returnUrl == null)
			returnUrl = "/index";
		return "redirect:" + returnUrl;

	}
	

	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	public String logout(HttpServletRequest request) {
		AuthHelper.removeSessionAccountAuth(request, "accountAuth");
		return "redirect:/login";
	}

}
