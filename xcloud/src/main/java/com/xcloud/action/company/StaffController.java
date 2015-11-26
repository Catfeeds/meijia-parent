package com.xcloud.action.company;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.vo.AppResultData;
import com.xcloud.action.BaseController;

@Controller
@RequestMapping(value = "/staff")
public class StaffController extends BaseController {
	
	@RequestMapping(value="/list", method = {RequestMethod.GET})
    public String staffTreeAndList(Model model){
		    
        return "/staffs/staff-list";
    }

	
	@RequestMapping(value="/userForm", method = {RequestMethod.GET})
    public String staffUserForm(Model model){
		    
        return "/staffs/userForm";
    }
	

	
}

