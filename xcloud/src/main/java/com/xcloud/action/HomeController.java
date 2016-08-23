package com.xcloud.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.simi.po.model.xcloud.XcompanyAdmin;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;


@Controller
@RequestMapping(value = "/")
public class HomeController extends BaseController {

    @AuthPassport
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request) {
    	
    	AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
    	List<XcompanyAdmin> companyList = accountAuth.getCompanyList();
    	
    	for (XcompanyAdmin item : companyList) {
    		System.out.println(item.getCompanyName());
    	}
    	
        return "/home/index";
    }
    
//    @AuthPassport
//    @RequestMapping(value = "/main")
//    public String main() {
//
//        return "/home/main";
//    }    

    @RequestMapping(value = "/notfound")
    public ModelAndView notfound() {

    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("404");
    	return mv;
    }
}
