package com.simi.action.app.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.RegexUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserFriends;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.UserFriendSearchVo;
import com.simi.vo.user.UserFriendViewVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserFriendController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserFriendService userFriendService;
	/**
	 * 添加好友功能
	 * @param userId  用户ID
	 * @param mobile  添加好友的手机号
	 * @param name    添加好友名称
	 * @return
	 */
	@RequestMapping(value = "post_friend", method = RequestMethod.POST)
	public AppResultData<Object> login(
			HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam("mobile") String mobile,
			@RequestParam("name") String name
			) {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		//1. 判断当前好友的情况，如果已经是好友，则直接返回。 
		Users u = userService.getUserById(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		//判断是否为正确手机号:
		if (!RegexUtil.isMobile(mobile)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.MIBILE_IS_INVALID_MG);
			return result;			
		}
		
		Users friendUser = userService.getUserByMobile(mobile);
		
		if (friendUser == null) {
			//1. 注册为用户
			friendUser = userService.genUser(mobile, name, (short) 2);
			
			//2. 发出邀请短信.
		}

		userFriendService.addFriends(u, friendUser);
		
		return result;
	}
	
	/**
	 * 获取好友列表功能
	 * @param userId  用户ID
	 * @return
	 */
	@RequestMapping(value = "get_friends", method = RequestMethod.GET)
	public AppResultData<Object> getFriend(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		List<UserFriendViewVo> list = new ArrayList<UserFriendViewVo>();
		
		//1. 判断当前好友的情况，如果已经是好友，则直接返回。 
		Users u = userService.getUserById(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		UserFriendSearchVo searchVo = new UserFriendSearchVo();
		searchVo.setUserId(u.getId());
		List<UserFriends> userFriends = new ArrayList<UserFriends>();
		PageInfo userFriendPage = userFriendService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		userFriends = userFriendPage.getList();
		
		if (userFriends.isEmpty()) return result;
		
		list = userFriendService.changeToUserFriendViewVos(userFriends);
		
		result.setData(list);
		
		
		return result;
	}



}
