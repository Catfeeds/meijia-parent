package com.xcloud.action.hr;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xcloud.action.BaseController;
import com.xcloud.auth.AuthPassport;


@Controller
@RequestMapping(value = "/hr")
public class HrIndexController extends BaseController {
	
	@AuthPassport
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {

		return "hr/hr-index";

	}	
	
	@AuthPassport
	@RequestMapping(value = "service", method = RequestMethod.GET)
	public String hrService(HttpServletRequest request) {

		return "hr/hr-service";

	}	
}
