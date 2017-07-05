package com.xcloud.action.hr;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xcloud.action.BaseController;
import com.xcloud.auth.AuthPassport;


@Controller
@RequestMapping(value = "/hr")
public class EntryController extends BaseController {
	
	@AuthPassport
	@RequestMapping(value = "offer-form", method = RequestMethod.GET)
	public String offerForm(HttpServletRequest request) {

		return "hr/offer-form";
	}	
	
	@AuthPassport
	@RequestMapping(value = "offer-list", method = RequestMethod.GET)
	public String offerList(HttpServletRequest request) {

		return "hr/offer-list";
	}	
	
	@AuthPassport
	@RequestMapping(value = "staff-flow", method = RequestMethod.GET)
	public String offerFlow(HttpServletRequest request) {

		return "hr/staff-flow";
	}	

}
