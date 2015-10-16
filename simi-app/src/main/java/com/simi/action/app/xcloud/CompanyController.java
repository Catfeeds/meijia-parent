package com.simi.action.app.xcloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.StringUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.service.xcloud.XCompanyService;
import com.simi.vo.AppResultData;


@Controller
@RequestMapping(value = "/app/company")
public class CompanyController extends BaseController {
	
	@Autowired
	private XCompanyService xCompanyService;
	
	@RequestMapping(value="/check-duplicate", method = {RequestMethod.GET})
	public AppResultData<Object> checkDuplicate(
			@RequestParam("company_name") String companyName,
			@RequestParam(value = "company_id", required = false, defaultValue = "0") Long companyId
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, "");
		
		if (StringUtil.isEmpty(companyName)) return result;
		
		Xcompany xCompany = xCompanyService.selectByCompanyName(companyName);
		
		if (xCompany != null) {
			if (companyId > 0L && xCompany.getCompanyId().equals(companyId)) {
				return result;
			}
			result.setStatus(Constants.ERROR_999);
			result.setMsg("公司名称已经存在,请重新输入。");
			return result;
		}
		
		return result;
	}

	
}
