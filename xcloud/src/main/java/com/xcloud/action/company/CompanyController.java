package com.xcloud.action.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.simi.service.xcloud.XCompanyService;
import com.xcloud.action.BaseController;

@Controller
@RequestMapping(value = "/company")
public class CompanyController extends BaseController {
	
	@Autowired
	private XCompanyService xCompanyService;
	
}
