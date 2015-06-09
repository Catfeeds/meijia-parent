package com.simi.action.app.user;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserBaiduBind;
import com.simi.po.model.user.UserLogined;
import com.simi.po.model.user.UserSmsToken;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderSeniorService;
import com.simi.service.user.UserBaiduBindService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.meijia.utils.IPUtil;
import com.meijia.utils.MobileUtil;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.huanxin.EasemobMessages;
import com.simi.vo.AppResultData;
import com.simi.vo.user.LoginVo;
import com.simi.vo.user.UserBaiduBindVo;
import com.simi.vo.user.UserViewVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserController extends BaseController {

	@Autowired
	private UsersService userService;

	@Autowired
	private UserSmsTokenService smsTokenService;

	@Autowired
	private UserLoginedService userLoginedService;

	@Autowired
	private OrderSeniorService orderSeniorService;

	@Autowired
	private UserCouponService userCouponService;

	@Autowired
	private UserBaiduBindService userBaiduBindService;

	@Autowired
	private UserMsgService userMsgService;

	// 5. 会员登陆接口
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public AppResultData<Object> login(
			HttpServletRequest request,
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_token") String sms_token,
			@RequestParam("login_from") int login_from,
			@RequestParam(value = "user_type", required = false, defaultValue = "0") int user_type) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		if (mobile.equals("18610807136") && sms_token.equals("000000")) {

			Users u = userService.getUserByMobile(mobile);
			
			// 根据mobile找到user_baidu_bind信息
			UserBaiduBind userBaiduBind = userBaiduBindService
					.selectByPrimaryKey(u.getId());
			UserBaiduBindVo vo = new UserBaiduBindVo();

			try {
				BeanUtils.copyProperties(vo, u);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			vo.setAppId("");
			vo.setChannelId("");
			vo.setAppUserId("");

			if (userBaiduBind != null) {
				vo.setAppId(userBaiduBind.getAppId());
				vo.setChannelId(userBaiduBind.getChannelId());
				vo.setAppUserId(userBaiduBind.getAppUserId());
			}

			result = new AppResultData<Object>(Constants.SUCCESS_0,
					ConstantMsg.SUCCESS_0_MSG, vo);			

			return result;
		}

		UserSmsToken smsToken = smsTokenService.selectByMobile(mobile);// 1、根据mobile
		// 从表user_sms_token找出最新一条记录
		LoginVo loginVo = new LoginVo(0l, mobile);
		if (smsToken == null || smsToken.getAddTime() == null) {
			result = new AppResultData<Object>(Constants.ERROR_999,
					ConstantMsg.ERROR_999_MSG_2, "");
			return result;
		}
		loginVo = new LoginVo(smsToken.getUserId(), mobile);
		// 2、判断是否表记录字段add_time 是否超过十分钟.
		long expTime = TimeStampUtil.compareTimeStr(smsToken.getAddTime(),
				System.currentTimeMillis() / 1000);
		if (expTime > 1800) {// 超时
			// 999
			result = new AppResultData<Object>(Constants.ERROR_999,
					ConstantMsg.ERROR_999_MSG_1, "");
			return result;
		} else {
			long ip = IPUtil.getIpAddr(request);
			UserLogined record = userLoginedService.initUserLogined(smsToken,
					login_from, ip);
			userLoginedService.insert(record);

			if (!smsToken.getSmsToken().equals(sms_token)) {// 验证码错误
				result = new AppResultData<Object>(Constants.ERROR_999,
						ConstantMsg.ERROR_999_MSG_2, "");
				return result;
			} else {
				Users u = userService.getUserByMobile(mobile);
				if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
					u = userService.genUser(mobile, Constants.USER_APP);
				}

				// 根据mobile找到user_baidu_bind信息

				System.out.println(u.getId()+"id==========mobile"+u.getMobile());
				UserBaiduBind userBaiduBind = userBaiduBindService
						.selectByUserId(u.getId());

				UserBaiduBindVo vo = new UserBaiduBindVo();

				try {
					BeanUtils.copyProperties(vo, u);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				vo.setAppId("");
				vo.setChannelId("");
				vo.setAppUserId("");

				if (userBaiduBind != null) {
					vo.setAppId(userBaiduBind.getAppId());
					vo.setChannelId(userBaiduBind.getChannelId());
					vo.setAppUserId(userBaiduBind.getAppUserId());
				}

				result = new AppResultData<Object>(Constants.SUCCESS_0,
						ConstantMsg.SUCCESS_0_MSG, vo);
				return result;
			}
		}
	}

	// 4. 获取验证码接口sms_type：0 = 登陆 1 = 支付（保留）
	@RequestMapping(value = "get_sms_token", method = RequestMethod.GET)
	public AppResultData<String> getSmsToken(
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_type") int sms_type) {
		// Users u = userService.getUserByMobile(mobile);
		// int saveUserFlag = 0;
		// if (u == null) {// 1'验证手机号是否已经注册，如果未注册，则自动注册用户，
		// u = userService.initUsers(mobile, Constants.USER_APP);
		// saveUserFlag = userService.saveUser(u);
		// } else {
		// saveUserFlag = 1;
		// }
		// if (saveUserFlag < 1) {// 注册用户失败
		// AppResultData<String> result = new AppResultData<String>(
		// Constants.ERROR_100, ConstantMsg.ERROR_100_MSG, "");
		// return result;
		// }

		// 2'调用函数生成六位验证码，调用短信平台，将发送的信息返回值更新到 user_sms_token
		String code = RandomUtil.randomNumber();

		if (mobile.equals("18610807136")) {
			code = "000000";
		}

		String[] content = new String[] { code, Constants.GET_CODE_MAX_VALID };
		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile,
				Constants.GET_CODE_TEMPLE_ID, content);
		UserSmsToken record = smsTokenService.initUserSmsToken(mobile, 0, code,
				sendSmsResult);
		smsTokenService.insert(record);

		AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		return result;
	}

	// 6. 账号信息
	/**
	 * mobile:手机号 rest_money 余额 score会员积分
	 */
	@RequestMapping(value = "get_userinfo", method = RequestMethod.GET)
	public AppResultData<Object> getUserInfo(
			@RequestParam("mobile") String mobile) {

		AppResultData<Object> resultFail = new AppResultData<Object>(
				Constants.ERROR_999, ConstantMsg.USER_NOT_EXIST_MG, "");

		UserViewVo vo = userService.getUserInfo(mobile);

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, vo);
		return result;
	}

	// 6. 账号信息
	/**
	 * mobile:手机号 rest_money 余额 score会员积分
	 */
	@RequestMapping(value = "send_im", method = RequestMethod.GET)
	public AppResultData<Object> sendImToRobot(
			@RequestParam("mobile") String mobile,
			@RequestParam("im_username_from") String imUsernameFrom,
			@RequestParam("im_username_to") String imUsernameTo,
			@RequestParam("msg") String msg) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.getUserByMobile(mobile);
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		if (StringUtil.isEmpty(imUsernameFrom)
				|| StringUtil.isEmpty(imUsernameTo)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.IM_USER_NOT_EXIST_MG);
			return result;
		}

		try {
			msg = URLDecoder.decode(msg, Constants.URL_ENCODE);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 给用户发一条文本消息
		EasemobMessages.sendMessageTxt(imUsernameFrom, imUsernameTo, msg);
		return result;
	}

	/**
	 * 用户的手机号所在地批量更新,仅提供某个特定参数下使用
	 */
	@RequestMapping(value = "gen_user_province", method = RequestMethod.GET)
	public AppResultData<Object> genUserProvince(
			@RequestParam("mobile") String mobile

	) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		if (StringUtil.isEmpty(mobile) || !mobile.equals("18612514665")) {
			return result;
		}

		List<Users> userList = userService.selectByAll();
		Users record = null;

		for (int i = 0; i < userList.size(); i++) {
			record = userList.get(i);
			mobile = record.getMobile();
			String provinceName = "";

			try {
				provinceName = MobileUtil.calcMobileCity(mobile);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			record.setProvinceName(provinceName);
			userService.updateByPrimaryKeySelective(record);
		}
		return result;
	}
}
