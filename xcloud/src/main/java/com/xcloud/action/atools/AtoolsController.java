package com.xcloud.action.atools;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xcloud.action.BaseController;
import com.xcloud.auth.AuthPassport;


@Controller
@RequestMapping(value = "/atools")
public class AtoolsController extends BaseController {
	
	@AuthPassport
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		return "atools/atools-index";
	}	

}