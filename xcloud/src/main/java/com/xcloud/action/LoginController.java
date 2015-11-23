package com.xcloud.action;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.vo.LoginVo;
import com.simi.service.xcloud.XCompanyService;


@Controller
@RequestMapping(value = "/")
public class LoginController extends BaseController {
	
	@Autowired
	private XCompanyService xCompanyService;	

	@RequestMapping(value="/login", method = {RequestMethod.GET})
    public String login(Model model){
		if(!model.containsAttribute("contentModel")) {
			LoginVo vo = new LoginVo();
			vo.setUsername("13810002890");
			vo.setPassword("000000");
			model.addAttribute("contentModel", vo);
		}
            
        return "/home/login";
    }

	@RequestMapping(value="/login", method = {RequestMethod.POST})
	public String login(HttpServletRequest request, Model model, 
			@Valid @ModelAttribute("contentModel") LoginVo loginVol ,
			BindingResult result){
		//如果有验证错误 返回到form页面
        if (result.hasErrors())
            return login(model);

//        String mobile = request.getParameter("mobile").trim();
//        String smsToken = request.getParameter("sms_token").trim();
        
		Long companyId = 1L;
		Long userId = 1L;
		String companyName = "北京美家生活科技有限公司";
		String name = "13810002890";
		name= "13810002890";

        AccountAuth accountAuth= new AccountAuth();
        accountAuth.setUserId(userId);
        accountAuth.setCompanyId(companyId);
        accountAuth.setCompanyName(companyName);
        accountAuth.setName(name);

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
