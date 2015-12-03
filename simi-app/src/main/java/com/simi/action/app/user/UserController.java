package com.simi.action.app.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.IPUtil;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.huanxin.EasemobIMUsers;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.order.OrderSeniorService;
import com.simi.service.user.TagsService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.TagNameListVo;
import com.simi.vo.user.UserPushBindVo;
import com.simi.vo.user.UserIndexVo;
import com.simi.vo.user.UserViewVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserController extends BaseController {

	@Autowired
	private UsersService userService;

	@Autowired
	private TagsUsersService tagsUsersService;

	@Autowired
	private TagsService tagsService;

	@Autowired
	private UserSmsTokenService smsTokenService;

	@Autowired
	private OrderSeniorService orderSeniorService;

	@Autowired
	private UserCouponService userCouponService;

	@Autowired
	private UserPushBindService userPushBindService;

	@Autowired
	private UserRef3rdService userRef3rdService;

	@Autowired
	private UserRefSecService userRefSecService;
	
	@Autowired
	private UsersAsyncService usersAsyncService;

	// 5. 会员登陆接口
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public AppResultData<Object> login(
			HttpServletRequest request,
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_token") String smsToken,
			@RequestParam("login_from") Short loginFrom,
			@RequestParam(value = "sms_type", required = false, defaultValue = "0") Short smsType,
			@RequestParam(value = "user_type", required = false, defaultValue = "1") int userType,
			@RequestParam(value = "device_type", required = false, defaultValue = "ios") String deviceType) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// 判断验证码正确与否，测试账号13810002890 000000 不需要验证
		AppResultData<Object> validateResult = null;
		if (mobile.equals("13810002890") && smsToken.equals("000000")) {
			validateResult = result;
		} else {
			validateResult = smsTokenService.validateSmsToken(mobile, smsToken,
					smsType);
		}

		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			return validateResult;
		}

		Users u = userService.selectByMobile(mobile);

		if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
			u = userService.genUser(mobile, "", Constants.USER_APP);
		}
		
		// 根据mobile找到user_baidu_bind信息
		UserPushBind userPushBind = userPushBindService.selectByUserId(u.getId());

		UserPushBindVo vo = new UserPushBindVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);

		vo.setAppId("");
		vo.setChannelId("");
		vo.setAppUserId("");
		vo.setClientId("");
		if (userPushBind != null) {
			vo.setClientId(userPushBind.getClientId());
		}

		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, vo);
		
		//异步操作
		// 如果第一次登陆未注册时未成功注册环信，则重新注册
		usersAsyncService.genImUser(u.getId());
		
		// 记录用户登陆信息
		long ip = IPUtil.getIpAddr(request);
		usersAsyncService.userLogined(u.getId(), loginFrom, ip);
		
		return result;
	}

	// 判断验证码是否正确，进入注册页面进行注册
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public AppResultData<Object> register(
			HttpServletRequest request,
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_token") String smsToken,
			@RequestParam("name") String name,
			// @RequestParam("login_from") Short loginFrom,
			@RequestParam(value = "sms_type", required = false, defaultValue = "0") Short smsType,
			@RequestParam(value = "user_type", required = false, defaultValue = "1") int userType) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// 判断验证码正确与否，测试账号13810002890 000000 不需要验证
		AppResultData<Object> validateResult = null;
		if (mobile.equals("17090397818") && smsToken.equals("000000")||mobile.equals("17090397828") && smsToken.equals("000000")||mobile.equals("17090397822") && smsToken.equals("000000")
				||mobile.equals("13701187136") && smsToken.equals("000000")||
				mobile.equals("13810002890") && smsToken.equals("000000")||
				mobile.equals("18610807136") && smsToken.equals("000000")||
				mobile.equals("18612514665") && smsToken.equals("000000")||
				mobile.equals("13146012753") && smsToken.equals("000000")||
				mobile.equals("15727372986") && smsToken.equals("000000")) {
			validateResult = result;
	
		} else {
			validateResult = smsTokenService.validateSmsToken(mobile, smsToken,
					smsType);
		}
		
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			return validateResult;
		}
		if (!mobile.equals("17090397828")&&!mobile.equals("17090397818")&&!mobile.equals("17090397822")
				&&!mobile.equals("13701187136")
				&&!mobile.equals("13810002890")
				&&!mobile.equals("18610807136")
				&&!mobile.equals("18612514665")
				&&!mobile.equals("13146012753")
				&&!mobile.equals("15727372986")) {

			
			
			Users user = userService.selectByMobile(mobile);
			// 如果秘书或用户存在
			if (user != null) {
				// 秘书已存在
				if (user.getUserType() == 1) {
					result.setStatus(Constants.ERROR_999);
					result.setMsg(ConstantMsg.SEC_EXIST_MG);
					return result;
				}
				// 已注册过，是否注册秘书
				if (user.getUserType() == 0) {

					// result.setStatus(Constants.ERROR_998);
					// result.setMsg(ConstantMsg.USER_EXIST_SEC_MG);
					Users u = userService.initUsers();
					u.setMobile(mobile);
					u.setName(name);
					result = new AppResultData<Object>(Constants.ERROR_999, ConstantMsg.USER_EXIST_SEC_MG, u);

					return result;
				}
			}
		}
		Users u = userService.initUsers();
		u.setMobile(mobile);
		u.setName(name);

		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, u);

		return result;
	}

	/**
	 * 获得标签和学历的集合
	 * 
	 * @return
	 */
	@RequestMapping(value = "get_tag_list", method = RequestMethod.GET)
	public AppResultData<Object> getTagsList(
			@RequestParam("mobile") String mobile,
			@RequestParam("name") String name) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		TagNameListVo vo = new TagNameListVo();

		List<Tags> tagList = tagsService.selectAll();
		vo.setList(tagList);

		List<Long> tagIdList = new ArrayList<Long>();
		/*
		 * for (Tags item : tagList) { tagIdList.add(item.getTagId()); }
		 */

		vo.setTagList(tagIdList);

		List<String> degreeTypeList = MeijiaUtil.getDegreeType();

		vo.setDegreeTypeList(degreeTypeList);
		// 如果已注册用户，确定要注册秘书，则去表中查到对应的用户数据但是不在注册列表里展示信息
		// 只为了存用户的昵称（name）;
		if (!mobile.equals("17090397828")&&!mobile.equals("17090397818")&&!mobile.equals("17090397822")
			&&!mobile.equals("13701187136")&&!mobile.equals("13810002890")&&!mobile.equals("18610807136")
			&&!mobile.equals("18612514665")&&!mobile.equals("13146012753")&&!mobile.equals("15727372986")){
			Users user = userService.selectByMobile(mobile);
			if (user != null && user.getUserType() == 0) {
				user.setName(name);
				BeanUtilsExp.copyPropertiesIgnoreNull(user, vo);
			}
		}

		result.setData(vo);

		return result;
	}

	// 秘书注册信息提交
	@RequestMapping(value = "post_user_register", method = RequestMethod.POST)
	public AppResultData<Object> register2(
			HttpServletRequest request,
			@RequestParam("mobile") String mobile,
			@RequestParam("name") String name,
			@RequestParam("realName") String realName,
			@RequestParam("sex") String sex,
			@RequestParam("degreeId") Long degreeId,
			@RequestParam("major") String major,
			@RequestParam("idCard") String idCard,
			@RequestParam(value = "tag_ids", required = false, defaultValue = "") String tagIds) {
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		if (!mobile.equals("17090397828")&&!mobile.equals("17090397818")&&!mobile.equals("17090397822")
				&&!mobile.equals("13701187136")&&!mobile.equals("13810002890")&&!mobile.equals("18610807136")
				&&!mobile.equals("18612514665")&&!mobile.equals("13146012753")&&!mobile.equals("15727372986")) {

			Users users = userService.selectByMobile(mobile);
			// 若users不为空，则为已有用户注册秘书 ,修改用户为秘书（userType=1）
			if (users != null) {
				users.setName(name);
				users.setRealName(realName);
				users.setSex(sex);
				users.setIdCard(idCard);
				users.setMajor(major);
				users.setDegreeId(degreeId);
				users.setUserType((short) 1);
				users.setAddTime(TimeStampUtil.getNow() / 1000);
				users.setUpdateTime(TimeStampUtil.getNow() / 1000);
				userService.updateByPrimaryKeySelective(users);
				// 像tagUsers表中插入用户的标签记录
				if (!StringUtil.isEmpty(tagIds)) {

					String[] tagIdsAry = StringUtil.convertStrToArray(tagIds);

					for (int i = 0; i < tagIdsAry.length; i++) {
						TagUsers tagUsers = new TagUsers();

						tagUsers.setUserId(users.getId());
						tagUsers.setAddTime(TimeStampUtil.getNow() / 1000);

						if (StringUtil.isEmpty(tagIdsAry[i])) {
							continue;
						} else {
							tagUsers.setTagId(Long.valueOf(tagIdsAry[i]));

							tagsUsersService.insertByTagUsers(tagUsers);

						}
					}
				}

				// 用户注册秘书成功给运营人员推送短信通知
				userService.userOrderAmPushSms(users);
			} else {
				// 记录用户注册信息
				// long ip = IPUtil.getIpAddr(request);
				Users record = userService.initUsers();
				record.setId(0L);
				record.setMobile(mobile);
				record.setProvinceName("");
				record.setThirdType(" ");
				record.setOpenid("");
				record.setName(name);
				record.setRealName(realName);
				record.setIdCard(idCard);
				record.setSex(sex);
				record.setBirthDay(new Date());
				record.setDegreeId(degreeId);
				record.setMajor(major);
				record.setHeadImg(" ");
				record.setRestMoney(new BigDecimal(0));
				record.setUserType((short) 1);
				record.setIsApproval((short) 0);
				record.setAddFrom((short) 1);
				record.setScore(0);

				record.setAddTime(TimeStampUtil.getNow() / 1000);
				record.setUpdateTime(TimeStampUtil.getNow() / 1000);

				userService.insert(record);
				userService.updateByPrimaryKeySelective(record);
				// 如果第一次登陆未注册时未成功注册环信，则重新注册
				UserRef3rd userRef3rd = userRef3rdService
						.selectByUserIdForIm(record.getId());
				if (userRef3rd == null) {
					userService.genImUser(record);
				}
				Users least = userService.selectUserByIdCard(idCard);
				// 想tagUsers表中插入用户的标签记录
				if (!StringUtil.isEmpty(tagIds)) {

					String[] tagIdsAry = StringUtil.convertStrToArray(tagIds);

					for (int i = 0; i < tagIdsAry.length; i++) {
						TagUsers tagUsers = new TagUsers();

						tagUsers.setUserId(least.getId());
						tagUsers.setAddTime(TimeStampUtil.getNow() / 1000);

						if (StringUtil.isEmpty(tagIdsAry[i])) {
							continue;
						} else {
							tagUsers.setTagId(Long.valueOf(tagIdsAry[i]));

							tagsUsersService.insertByTagUsers(tagUsers);

						}
					}
				}
				userService.userOrderAmPushSms(record);
			}
		}
		return result;
	}

	// 6. 账号信息
	/**
	 * mobile:手机号 rest_money 余额 score会员积分
	 */
	@RequestMapping(value = "get_userinfo", method = RequestMethod.GET)
	public AppResultData<Object> getUserInfo(
			@RequestParam("user_id") Long userId) {

		UserViewVo vo = userService.getUserInfo(userId);

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, vo);
		return result;
	}

	/**
	 * 用户信息修改接口
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "post_userinfo", method = RequestMethod.POST)
	public AppResultData<Object> updateUserInfo(
			HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "mobile", required = false, defaultValue = "") String mobile,
			@RequestParam(value = "sex", required = false, defaultValue = "") String sex,
			@RequestParam(value = "head_img", required = false, defaultValue = "") String headImg)
			throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		// 要判断mobile不为空，并且手机号在其他用户上没有使用过;
		if (!StringUtil.isEmpty(mobile)) {
			Users user = userService.selectByMobile(mobile);
			if (user != null && !user.getId().equals(userId)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("该手机号已经在由其他用户注册.");
				return result;
			} else {
				u.setMobile(mobile);
			}
		}

		// 如果昵称name不为空，则对环信中昵称进行修改
		if (!StringUtil.isEmpty(name)) {
			u.setName(name);
		}

		if (!StringUtil.isEmpty(headImg)) {
			u.setHeadImg(headImg);
		}

		if (!StringUtil.isEmpty(sex)) {
			u.setSex(sex);
		}

		userService.updateByPrimaryKeySelective(u);

		if (!StringUtil.isEmpty(name)) {
			String username = "";
			UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(userId);
			// 如果该账号未绑定环信账号
			if (userRef3rd != null) {
				username = userRef3rd.getUsername();
				ObjectNode json2 = JsonNodeFactory.instance.objectNode();
				json2.put("nickname", name);
				EasemobIMUsers.modifyIMUserNickName(username, json2);
			}
		}

		result.setData(u);
		return result;

	}

	/**
	 * 个人主页接口
	 */
	@RequestMapping(value = "get_user_index", method = RequestMethod.GET)
	public AppResultData<Object> getUserIndex(
			@RequestParam("user_id") Long userId,
			@RequestParam("view_user_id") Long viewUserId) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		Users u = userService.selectByPrimaryKey(userId);
		Users viewUser = userService.selectByPrimaryKey(viewUserId);
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null || viewUser == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		UserIndexVo vo = userService.getUserIndexVoByUserId(u, viewUser);
		result.setData(vo);

		return result;
	}

}
