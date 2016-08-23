package com.xcloud.action.company;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.service.xcloud.XCompanyService;
import com.simi.vo.AppResultData;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;

@Controller
@RequestMapping(value = "/company")
public class CompanyController extends BaseController {
	
	@Autowired
	private XCompanyService xCompanyService;

	//获得公司详情
	@RequestMapping(value = "company-form", method = RequestMethod.GET)
	public String getComapnyForm(
			HttpServletRequest request,Model model) {
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long xcompanyId = accountAuth.getCompanyId();

        Xcompany xcompany = xCompanyService.selectByPrimaryKey(xcompanyId);

    	model.addAttribute("contentModel", xcompany);
    	
		return "hr/company-form";

	}
	
	@RequestMapping(value = "company-form", method = RequestMethod.POST)
	public String saveComapnyForm(HttpServletRequest request,
			 Model model, @ModelAttribute("contentModel") Xcompany xcompany, BindingResult result) {
		
        Xcompany record = xCompanyService.selectByPrimaryKey(xcompany.getCompanyId());
        
        if (xcompany != null) {
			
        	record = xcompany;
		}
        xCompanyService.updateByPrimaryKeySelective(record);
    	
        return "hr/company-form";
	}
}
