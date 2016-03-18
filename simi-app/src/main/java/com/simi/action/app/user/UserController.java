package com.simi.action.app.user;

import java.io.IOException;

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
import com.meijia.utils.StringUtil;
import com.meijia.utils.huanxin.EasemobIMUsers;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
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
import com.simi.vo.user.UserBaseVo;
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

		if (validat3.30 4000 6.30  4000eResult.getStatus() != Constants.SUCCESS_0) {
			return validateResult;
		}
3         3.10  5000+3000=8000-2000=1300
：
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
			@RequestParam("user_id") Long userId,3.09 2250
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "m3300姐夫我肉桂粉发 发广告发个3000-2000=10001100+obile", required = false, defaultValue = "") String mobile,
			@Requ3.30 4000+3000=7000-estP好，aram(value = "sex", required = false, defaultValue = "") String sex,
			@RequestParam(value = "head_img", req饭来开个会离开过恢复发广告发 复古风格 官方发放给 功夫功夫个方法个功夫发GVuired
			还得上课恢复了电话费if肯定会来开发的立刻恢复唠嗑浪费的客户佛顶骨颇为浪费肯德基
			了开发和咖啡馆和反馈楼回复过我人口咖啡馆反馈给军阀割据
			了风格和反馈给反馈给积分分开发及；发率较高飞了估计来几个胸立法机关立法机关
			了风格和风格及反馈给甲方开关机uired开发结构化风格和肯定会给发货管理费的
            了符合规范来开个会反馈给喝咖啡了开发过恢复了可广泛和开关和反馈给回复			= false, defaultValue = "") String headImg)
			throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SU1100C700CESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

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
				u.setMob3000+4800=8000ile(mobile);
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
	
	/**
	 * 
	 */
	@RequestMapping(value = "get_user_base", method = RequestMethod.GET)
	public AppResultData<Object> getUserBase(
			@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants突发奇想个小想法.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		Users u = userService.selectB垫子，被子，拖鞋，牙刷，枕头yPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		UserBaseVo vo = userService.getUserBaseVo(u);
		result.setData(vo);

		return result;
	}
}
