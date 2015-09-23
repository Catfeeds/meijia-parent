package com.xcloud.action;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.quartz.xml.ValidationException;
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
import com.meijia.utils.MD5Util;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.service.xcloud.XCompanyService;


@Controller
@RequestMapping(value = "/")
public class LoginController extends BaseController {
	
	@Autowired
	private XCompanyService xCompanyService;	

	@RequestMapping(value="/login", method = {RequestMethod.GET})
    public String login(Model model){
		if(!model.containsAttribute("contentModel"))
            model.addAttribute("contentModel", new LoginVo());
        return "/home/login";
    }

	@RequestMapping(value="/login-by-username", method = {RequestMethod.POST})
	public String login(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") LoginVo accountLoginVo, BindingResult result) throws ValidationException, NoSuchAlgorithmException{
		//如果有验证错误 返回到form页面
        if (result.hasErrors())
            return login(model);

        String userName = accountLoginVo.getUsername().trim();
        String password = accountLoginVo.getPassword().trim();
        String passMd5 = MD5Util.MD5Encode(password+"xcloud", "utf-8");
        Xcompany xcompany = xCompanyService.selectByUserNameAndPass(userName, passMd5);

    	if ( xcompany == null ) {
        	result.addError(new FieldError("contentModel","username","用户名或密码错误。"));
        	result.addError(new FieldError("contentModel","password","用户名或密码错误。"));
        	return login(model);
    	}

		Long companyId = xcompany.getCompanyId();
		String companyName = xcompany.getCompanyName();

        AccountAuth accountAuth= new AccountAuth(companyId, companyName, userName);

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
