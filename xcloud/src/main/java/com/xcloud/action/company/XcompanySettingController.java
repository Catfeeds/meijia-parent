package com.xcloud.action.company;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xcloud.action.BaseController;

@Controller
@RequestMapping(value = "/atools")
public class XcompanySettingController extends BaseController {
	
	
	/*
	 *  社保公积金计算 form
	 * 
	 */
	@RequestMapping(value = "insurance_calculate_form",method = RequestMethod.GET)
	public String insuranceCalculForm(){
		return "atools/insurance-form";
	}
	
	
}
