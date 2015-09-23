package com.xcloud.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xcloud.vo.LoginVo;

@Controller
@RequestMapping(value = "/")
public class RegisterController extends BaseController {

	
	@RequestMapping(value="/register-step-1", method = {RequestMethod.GET})
    public String registerStep1(Model model) {
        return "/home/register";
    }	
	
}
