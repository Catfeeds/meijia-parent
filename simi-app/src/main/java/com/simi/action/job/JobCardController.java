package com.simi.action.job;

import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meijia.utils.MobileUtil;
import com.meijia.utils.StringUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.service.card.CardService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/job")
public class JobCardController extends BaseController {
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private CardService cardService;
	
	/**
	 * 已超过服务时间，则设置为已完成.
	 */
	@RequestMapping(value = "set_card_finish", method = RequestMethod.GET)
	public AppResultData<Object> genUserProvince(HttpServletRequest request) {


		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			cardService.updateFinishByOvertime();
		}	
		return result;
	}
}
