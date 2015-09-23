package com.xcloud.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xcloud.auth.AuthPassport;


@Controller
@RequestMapping(value = "/")
public class HomeController extends BaseController {

    @AuthPassport
    @RequestMapping(value = "/index")
    public String index() {

        return "/home/index";
    }

    @RequestMapping(value = "/notfound")
    public ModelAndView notfound() {

    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("404");
    	return mv;
    }
}
