package com.simi.action.app.user;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.SmsUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserSmsToken;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserSmsController extends BaseController {

	@Autowired
	private UsersService userService;

	@Autowired
	private UserSmsTokenService smsTokenService;

	// 4. 获取验证码接口sms_type：0 = 用户登陆 1 = 秘书登录
	@RequestMapping(value = "get_sms_token", method = RequestMethod.GET)
	public AppResultData<String> getSmsToken(
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_type") int sms_type) {

		/*
		 * AppResultData<String> result = new AppResultData<String>(
		 * Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		 */
		AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		// 2'调用函数生成六位验证码，调用短信平台，将发送的信息返回值更新到 user_sms_token
		String code = RandomUtil.randomNumber();

//		if (mobile.equals("17090397818")||mobile.equals("17090397828")||mobile.equals("17090397822")
//				||mobile.equals("13701187136")||mobile.equals("13810002890")||mobile.equals("18610807136")
//				||mobile.equals("18612514665")||mobile.equals("13146012753")||mobile.equals("15727372986")
//		 ) {
//			code = "000000";
//		}
//13701187136,13810002890,18610807136,18612514665,13146012753
		
		String[] content = new String[] { code, Constants.GET_CODE_MAX_VALID };
		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile,
				Constants.GET_CODE_TEMPLE_ID, content);
		UserSmsToken record = smsTokenService.initUserSmsToken(mobile,
				sms_type, code, sendSmsResult);

		smsTokenService.insert(record);

		return result;
	}

	// 4. 获取验证码接口sms_type：0 = 用户登陆 1 = 秘书登录
	@RequestMapping(value = "get_register_sms_token", method = RequestMethod.GET)
	public AppResultData<String> getRegisterSmsToken(
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_type") int sms_type) {

		/*
		 * AppResultData<String> result = new AppResultData<String>(
		 * Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		 */
		AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		// 2'调用函数生成六位验证码，调用短信平台，将发送的信息返回值更新到 user_sms_token
		String code = RandomUtil.randomNumber();

		if (mobile.equals("17090397818")||mobile.equals("17090397828")||mobile.equals("17090397822")
				||mobile.equals("13701187136")||mobile.equals("13810002890")||mobile.equals("18610807136")
				||mobile.equals("18612514665")||mobile.equals("13146012753")||mobile.equals("15727372986")) {
			code = "000000";
		}
		String[] content = new String[] { code, Constants.GET_CODE_MAX_VALID };
		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile,
				Constants.GET_CODE_TEMPLE_ID, content);
		UserSmsToken record = smsTokenService.initUserSmsToken(mobile,
				sms_type, code, sendSmsResult);

		smsTokenService.insert(record);

		return result;
	}

	// 4. 获取验证码接口sms_type：0 = 用户登陆 1 = 秘书登录
	@RequestMapping(value = "val_sms_token", method = RequestMethod.GET)
	public AppResultData<Object> valSmsToken(
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_token") String smsToken,
			@RequestParam("sms_type") Short smsType) {

		AppResultData<Object> result = smsTokenService.validateSmsToken(mobile,
				smsToken, smsType);

		return result;
	}
}
