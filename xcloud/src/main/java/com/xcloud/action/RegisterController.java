package com.xcloud.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xcloud.vo.LoginVo;

@Controller
@RequestMapping(value = "/")
public class RegisterController extends BaseController {

	
	@RequestMapping(value="/register", method = {RequestMethod.GET})
    public String register(Model model) {
        return "/home/register";
    }	
	
	
	@RequestMapping(value="/register", method = {RequestMethod.POST})
    public String doRegister(HttpServletRequest request, HttpServletResponse response,  Model model) {
        
		//获得注册的form信息
		
		
		return "/home/register";
    }	
	
}
