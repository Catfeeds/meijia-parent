package com.xcloud.action.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.StringUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.service.xcloud.XCompanyService;
import com.simi.vo.AppResultData;
import com.xcloud.action.BaseController;
import com.xcloud.vo.LoginVo;

@Controller
@RequestMapping(value = "/company")
public class CompanyController extends BaseController {
	
	@Autowired
	private XCompanyService xCompanyService;
	
}
