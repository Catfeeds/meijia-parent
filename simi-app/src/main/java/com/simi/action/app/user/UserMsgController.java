package com.simi.action.app.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserMsgController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserMsgService userMsgService;	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "get_msg", method = RequestMethod.GET)
	public AppResultData<Object> getMsg(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page
			) {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		PageInfo list = userMsgService.selectByListPage(userId, page, 20);
		List<UserMsg> msgList = list.getList();
		
		result.setData(msgList);
		return result;
	}
}
