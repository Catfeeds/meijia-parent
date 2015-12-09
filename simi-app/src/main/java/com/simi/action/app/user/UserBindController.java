package com.simi.action.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserBindController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserSmsTokenService smsTokenService;	


	/*
	 * 用户绑定手机号接口
	 * @param
	 *    user_id
	 *    mobile   手机号
	 */
	@RequestMapping(value = "bind_mobile", method = RequestMethod.POST)
	public AppResultData<Object> bindMobile(
			@RequestParam("user_id") Long userId,
			@RequestParam("mobile") String mobile,
			@RequestParam("name") String name,
			@RequestParam("sms_token") String smsToken
			) {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		//先判断用户手机号是否已经存在.
		Users existUser = userService.selectByMobile(mobile);
		if (existUser != null) {
			if (!existUser.getId().equals(userId)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("手机号码已经存在");
				return result;
			}
		}
		
		//验证手机验证码是否正确
		
		AppResultData<Object> validateResult = smsTokenService.validateSmsToken(mobile, smsToken, (short) 0);
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			return validateResult;
		}
		
		u.setMobile(mobile);
		u.setName(name);
		userService.updateByPrimaryKeySelective(u);

		return result;
	}
}
