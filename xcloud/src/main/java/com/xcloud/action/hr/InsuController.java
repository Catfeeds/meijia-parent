package com.xcloud.action.hr;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xcloud.action.BaseController;
import com.xcloud.auth.AuthPassport;


@Controller
@RequestMapping(value = "/hr")
public class InsuController extends BaseController {
	
	@AuthPassport
	@RequestMapping(value = "insu-index", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {

		return "hr/insu-index";
	}	

}
