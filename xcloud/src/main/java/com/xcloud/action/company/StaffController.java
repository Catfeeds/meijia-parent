package com.xcloud.action.company;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;

@Controller
@RequestMapping(value = "/staff")
public class StaffController extends BaseController {
	
	@AuthPassport
	@RequestMapping(value="/list", method = {RequestMethod.GET})
    public String staffTreeAndList(HttpServletRequest request, Model model){
		
		
		//获取登录的用户
    	AccountAuth accountAuth=AuthHelper.getSessionAccountAuth(request);
    	
    	model.addAttribute("companyId", accountAuth.getCompanyId());
    	model.addAttribute("companyName", accountAuth.getCompanyName());
    	model.addAttribute("shortName", accountAuth.getShortName());

		
        return "/staffs/staff-list";
    }

	
	@RequestMapping(value="/userForm", method = {RequestMethod.GET})
    public String staffUserForm(Model model){
		    
        return "/staffs/userForm";
    }
	

	
}

