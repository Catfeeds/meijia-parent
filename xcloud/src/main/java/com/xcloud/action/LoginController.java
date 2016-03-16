package com.xcloud.action;

import java.security.NoSuchAlgorithmException;

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
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;


@Controller
@RequestMapping(value = "/")
public class LoginController extends BaseController {
	
	@Autowired
	private XCompanyService xCompanyService;	
	
	@Autowired
	private UsersService usersService;		

	@RequestMapping(value="/login", method = {RequestMethod.GET})
    public String login(Model model){
		if(!model.containsAttribute("contentModel")) {
			LoginVo vo = new LoginVo();
//			vo.setUsername("13810002890");
//			vo.setPassword("000000");
			model.addAttribute("contentModel", vo);
		}
            
        return "/home/login";
    }

	@RequestMapping(value="/login", method = {RequestMethod.POST})
	public String login(HttpServletRequest request, Model model, 
			@Valid @ModelAttribute("contentModel") LoginVo loginVo ,
			BindingResult result) throws NoSuchAlgorithmException{
		//如果有验证错误 返回到form页面
        if (result.hasErrors())
            return login(model);

        String userName = loginVo.getUsername().trim();
        String password = loginVo.getPassword().trim();
        
        String passwordMd5 = StringUtil.md5(password.trim());
        Xcompany xCompany = xCompanyService.selectByUserNameAndPass(userName, passwordMd5);
        
        if ( xCompany == null ) {
        	result.addError(new FieldError("contentModel","username","用户名或密码错误。"));
//        	result.addError(new FieldError("contentModel","password","用户名或密码错误。"));
        	return login(model);
    	}
        
        
        Users u = usersService.selectByMobile(userName);
        
		Long companyId = xCompany.getCompanyId();
		Long userId = u.getId();
		String companyName = xCompany.getCompanyName();
		String shortName = xCompany.getShortName();
		String headImg = usersService.getHeadImg(u);


        AccountAuth accountAuth= new AccountAuth();
        accountAuth.setUserId(userId);
        accountAuth.setName(u.getName());
        accountAuth.setHeadImg(headImg);
        accountAuth.setMobile(u.getMobile());
        accountAuth.setCompanyId(companyId);
        accountAuth.setCompanyName(companyName);
        accountAuth.setShortName(shortName);

    	AuthHelper.setSessionAccountAuth(request, accountAuth);


        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="/index";
    	return "redirect:"+returnUrl;

	}

	@RequestMapping(value="/logout", method = {RequestMethod.GET})
	public String logout(HttpServletRequest request) {
		AuthHelper.removeSessionAccountAuth(request, "accountAuth");
		return "redirect:/login";
	}

}
