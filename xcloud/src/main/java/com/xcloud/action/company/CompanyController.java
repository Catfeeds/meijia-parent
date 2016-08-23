package com.xcloud.action.company;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meijia.utils.StringUtil;
import com.meijia.utils.baidu.BaiduConfigUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyAdmin;
import com.simi.service.xcloud.XCompanyService;
import com.simi.vo.AppResultData;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;

@Controller
@RequestMapping(value = "/company")
public class CompanyController extends BaseController {
	
	@Autowired
	private XCompanyService xCompanyService;

	//获得公司详情
	@AuthPassport
	@RequestMapping(value = "company-form", method = RequestMethod.GET)
	public String getComapnyForm(
			HttpServletRequest request,Model model) {
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long xcompanyId = accountAuth.getCompanyId();
		String companyIdStr = request.getParameter("companyId");
		if (!StringUtil.isEmpty(companyIdStr)) {
			Long companyId = Long.valueOf(companyIdStr);
			List<XcompanyAdmin> companyList = accountAuth.getCompanyList();
			
			
			for (XcompanyAdmin item : companyList) {
				if (item.getCompanyId().equals(companyId)) {
					xcompanyId = companyId;
					break;
				}
			}
		}
		
        Xcompany xcompany = xCompanyService.selectByPrimaryKey(xcompanyId);
        
        if (!xcompanyId.equals(accountAuth.getCompanyId())) {
	        accountAuth.setCompanyId(xcompanyId);
			accountAuth.setCompanyName(xcompany.getCompanyName());
			accountAuth.setShortName(xcompany.getShortName());
			
			AuthHelper.setSessionAccountAuth(request, accountAuth);
        }
        
        
    	model.addAttribute("contentModel", xcompany);
    	// 百度地图ak
    	model.addAttribute("ak", BaiduConfigUtil.getInstance().getRb().getString("ak"));
		return "hr/company-form";

	}
	
	@AuthPassport
	@RequestMapping(value = "company-form", method = RequestMethod.POST)
	public String saveComapnyForm(HttpServletRequest request,
			 Model model, @ModelAttribute("contentModel") Xcompany xcompany, BindingResult result) {
		
        Xcompany record = xCompanyService.selectByPrimaryKey(xcompany.getCompanyId());
        
        if (xcompany != null) {
			
        	record = xcompany;
		}
        xCompanyService.updateByPrimaryKeySelective(record);
    	
        model.addAttribute("contentModel", record);
        model.addAttribute("ak", BaiduConfigUtil.getInstance().getRb().getString("ak"));
        return "hr/company-form";
	}
}
