package com.xcloud.action.xz;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xcloud.action.BaseController;


@Controller
@RequestMapping(value = "/xz")
public class XzIndexController extends BaseController {

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {

		return "xz/xz-index";

	}	
}
