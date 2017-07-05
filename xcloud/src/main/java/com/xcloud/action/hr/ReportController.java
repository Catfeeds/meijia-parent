package com.xcloud.action.hr;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xcloud.action.BaseController;
import com.xcloud.auth.AuthPassport;


@Controller
@RequestMapping(value = "/hr")
public class ReportController extends BaseController {
	
	@AuthPassport
	@RequestMapping(value = "hr-report", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {

		return "hr/hr-report";
	}	

}
