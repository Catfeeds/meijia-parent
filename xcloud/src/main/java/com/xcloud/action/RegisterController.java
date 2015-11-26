package com.xcloud.action;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;

@Controller
@RequestMapping(value = "/")
public class RegisterController extends BaseController {

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private UsersService usersService;	
	
	@Autowired
	private XcompanyDeptService xCompanyDeptService;
	
	@RequestMapping(value="/register", method = {RequestMethod.GET})
    public String register(Model model) {
		
		if (!model.containsAttribute("contentModel")) {
			Xcompany xCompany = xCompanyService.initXcompany();
			model.addAttribute("contentModel", xCompany);
		}
		
        return "/home/register";
    }	
	
	
	@RequestMapping(value="/register", method = {RequestMethod.POST})
    public String doRegister(HttpServletRequest request, 
    						 HttpServletResponse response,  
    						 Model model,
    						 @ModelAttribute("contentModel") Xcompany xCompanyVo, 
    						 BindingResult result) throws NoSuchAlgorithmException {
        
		//获得注册的form信息
		Long companyId = xCompanyVo.getCompanyId();
		
		String password = xCompanyVo.getPassword();
		
		String passwordMd5 = StringUtil.md5(password.trim());
		
		Xcompany xCompany = xCompanyService.initXcompany();
		if (companyId > 0L) {
			xCompany = xCompanyService.selectByPrimaryKey(companyId);
		}
		
		BeanUtilsExp.copyPropertiesIgnoreNull(xCompanyVo, xCompany);
		
		xCompany.setPassword(passwordMd5);
		
		if (companyId > 0L) {
			xCompany.setUpdateTime(TimeStampUtil.getNowSecond());
			xCompanyService.updateByPrimaryKeySelective(xCompany);
		} else {
			
			//生成公司唯一邀请码
			String invitationCode = StringUtil.generateShortUuid();
			xCompany.setInvitationCode(invitationCode);
			xCompanyService.insert(xCompany);
			companyId = xCompany.getCompanyId();
		}
		
		//然后判断用户是否已经存在，不存在则添加新用户
		String mobile = xCompany.getUserName();
		Users u = usersService.selectByMobile(mobile);
		
		if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
			u = usersService.genUser(mobile, "", Constants.User_XCOULD);
		}
		
		//公司部门预置信息.
		List<String> defaultDepts = MeijiaUtil.getDefaultDept();
		for (int i = 0 ; i < defaultDepts.size(); i++) {
			XcompanyDept dept = xCompanyDeptService.initXcompanyDept();
			dept.setName(defaultDepts.get(i));
			dept.setCompanyId(companyId);
			dept.setParentId(0L);
			xCompanyDeptService.insert(dept);
		}
		
		return "redirect:/home/login";
    }	
	
}
