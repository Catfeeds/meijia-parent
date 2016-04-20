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
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserFriendReq;
import com.simi.po.model.user.UserFriends;
import com.simi.po.model.user.UserRef;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.user.UserFriendReqService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserFriendReqVo;
import com.simi.vo.user.UserFriendSearchVo;
import com.simi.vo.user.UserFriendViewVo;
import com.simi.vo.user.UserRefSearchVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserFriendController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserFriendService userFriendService;
	
	@Autowired
	private UserFriendReqService userFriendReqService;
	
	@Autowired
	private UserRef3rdService userRef3rdService;	
	
	@Autowired
	private UserRefService userRefService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	
	
	/**
	 * 添加好友功能
	 * @param userId  用户ID
	 * @param mobile  添加好友的手机号
	 * @param name    添加好友名称
	 * @return
	 */
	@RequestMapping(value = "add_friend", method = RequestMethod.GET)
	public AppResultData<Object> addFriend(
			HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam("friend_id") Long  friendId
			) {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		//1. 判断当前好友的情况，如果已经是好友，则直接返回。 
		Users u = userService.selectByPrimaryKey(userId);
		Users friendUser = userService.selectByPrimaryKey(friendId);
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null || friendUser == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		if (userId.equals(friendId)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("不能添加自己.");
			return result;
		}

		return userFriendService.addFriendReq(u, friendUser);
		
	}
	/**
	 * 添加好友功能
	 * @param userId  用户ID
	 * @param mobile  添加好友的手机号
	 * @param name    添加好友名称
	 * @return
	 */
	@RequestMapping(value = "post_friend", method = RequestMethod.POST)
	public AppResultData<Object> postFriend(
			HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam("mobile") String mobile,
			@RequestParam("name") String name
			) {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		//1. 判断当前好友的情况，如果已经是好友，则直接返回。 
		Users u = userService.selectByPrimaryKey(userId);

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
		
		Users friendUser = userService.selectByMobile(mobile);
		
		if (friendUser == null) {
			//1. 注册为用户
			friendUser = userService.genUser(mobile, name, (short) 2, "");
			
			//如果第一次登陆未注册时未成功注册环信，则重新注册
			UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(friendUser.getId());
			if(userRef3rd == null){
				userRef3rdService.genImUser(friendUser);
			}
			//2. 发出邀请短信.
		}

		return userFriendService.addFriendReq(u, friendUser);
		
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
		Users u = userService.selectByPrimaryKey(userId);

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

	/**
	 * 获取秘书接口
	 * @param userId  用户ID
	 * @return
	 */
	@RequestMapping(value = "get_sec", method = RequestMethod.GET)
	public AppResultData<Object> getSecs(@RequestParam("user_id") Long userId) {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		List<UserFriendViewVo> list = new ArrayList<UserFriendViewVo>();
		
		List<UserFriends> userFriends = new ArrayList<UserFriends>();
		
		
		UserRefSearchVo searchVo = new UserRefSearchVo();
		searchVo.setUserId(userId);
		searchVo.setRefType("sec");
		List<UserRef> rs  = userRefService.selectBySearchVo(searchVo);
		
		UserRef userRef = null;
		if (!rs.isEmpty()) userRef = rs.get(0);
		
		if (userRef == null) return result;
		
		UserFriends vo = userFriendService.initUserFriend();
		vo.setUserId(userId);
		vo.setFriendId(userRef.getRefId());
		vo.setAddTime(userRef.getAddTime());
		vo.setUpdateTime(userRef.getAddTime());
		
		userFriends.add(vo);
		
		list = userFriendService.changeToUserFriendViewVos(userFriends);
		
		result.setData(list);
		
		
		return result;
	}
	
	
	/**
	 * 好友申请通过或拒绝接口
	 * @param request
	 * @param userId
	 * @param friendId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "post_friend_req", method = RequestMethod.POST)
	public AppResultData<Object> postFriendReq(
			HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam("friend_id") Long friendId,
			@RequestParam("status") Short status
			) {
	     // 1. 更新表 user_friend_req 表的状态 status
	     // 2. 如果状态 = 1 ，说明同意为好友，则需要去操作 user_friends 插入相应的好友记录
		UserFriendSearchVo searchVo = new UserFriendSearchVo();
		if (status.equals((short)1)) {

			searchVo.setUserId(userId);
			searchVo.setFriendId(friendId);
			UserFriends userFriends = userFriendService.selectByIsFirend(searchVo);
			if (userFriends == null) {
				userFriends = userFriendService.initUserFriend();
				userFriends.setUserId(userId);
				userFriends.setFriendId(friendId);
				userFriends.setAddTime(TimeStampUtil.getNowSecond());
				userFriends.setUpdateTime(TimeStampUtil.getNowSecond());
				userFriendService.insert(userFriends);
			}
			
			searchVo = new UserFriendSearchVo();
			searchVo.setUserId(friendId);
			searchVo.setFriendId(userId);
			userFriends = userFriendService.selectByIsFirend(searchVo);
			
			if (userFriends == null) {
				userFriends = userFriendService.initUserFriend();
				userFriends.setUserId(friendId);
				userFriends.setFriendId(userId);
				userFriends.setAddTime(TimeStampUtil.getNowSecond());
				userFriends.setUpdateTime(TimeStampUtil.getNowSecond());
				userFriendService.insert(userFriends);
			}
			
			searchVo = new UserFriendSearchVo();
			searchVo.setUserId(friendId);
			searchVo.setFriendId(userId);
			UserFriendReq userFriendReq = userFriendReqService.selectByIsFirend(searchVo);
			if (userFriendReq != null) {
				userFriendReq.setStatus((short)1);
			    userFriendReqService.updateByPrimaryKeySelective(userFriendReq);	
			}
			
		}
		
		//3如果状态=2，说明被拒绝，则往userFriendReq表里面查入记录
		if (status.equals((short)2)) {
			searchVo = new UserFriendSearchVo();
			searchVo.setUserId(friendId);
			searchVo.setFriendId(userId);
			UserFriendReq userFriendReq = userFriendReqService.selectByIsFirend(searchVo);
//			UserFriendReq userFriendReq = userFriendReqService.initUserFriendReq();
			if (userFriendReq != null) {
				userFriendReq.setUserId(userId);
				userFriendReq.setStatus((short)2);
				userFriendReq.setFriendId(friendId);
				userFriendReq.setAddTime(TimeStampUtil.getNowSecond());
				userFriendReq.setUpdateTime(TimeStampUtil.getNowSecond());
				userFriendReqService.updateByPrimaryKeySelective(userFriendReq);	
			}
			
		}
		//生成好友同意或拒绝的消息
		userMsgAsyncService.newFriendReqMsg(friendId, userId, status);
		
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
	
	    return result;
	}
	
	/**
	 * 用户-获取好友申请列表
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "get_friend_reqs", method = RequestMethod.GET)
	public AppResultData<Object> getFriendReqs(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		List<UserFriendReqVo> listVo = new ArrayList<UserFriendReqVo>();
		
		List<UserFriendReq> list = new ArrayList<UserFriendReq>();
		
		
		UserFriendSearchVo searchVo = new UserFriendSearchVo();
		searchVo.setFriendId(userId);
		searchVo.setUserId(userId);
		PageInfo userFriendPage = userFriendReqService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		list = userFriendPage.getList();
		
		for (UserFriendReq item : list) {
			UserFriendReqVo vo = new UserFriendReqVo();
			vo = userFriendReqService.getFriendReqVo(item, userId);
			listVo.add(vo);
		}
		result.setData(listVo);

		return result;
	}
}
