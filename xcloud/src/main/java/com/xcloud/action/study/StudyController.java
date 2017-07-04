package com.xcloud.action.study;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.utils.CardUtil;
import com.simi.vo.user.UserMsgSearchVo;
import com.simi.vo.xcloud.StaffListVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;

@Controller
@RequestMapping(value = "/study")
public class StudyController extends BaseController {


	@AuthPassport
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String setting(HttpServletRequest request, Model model) {
		
		
		return "/study/study-index";
	}
}
