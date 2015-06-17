package com.simi.action.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserBaiduBind;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserBaiduBindService;
import com.simi.service.user.UsersService;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserBaiduBindController extends BaseController {

	@Autowired
	private UserBaiduBindService userBaiduBindService;

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "post_baidu_bind", method = RequestMethod.POST)
	public AppResultData<String> saveUserBaiduBind(
			@RequestParam("user_id") Long userId,
			@RequestParam("app_id") String appId,
			@RequestParam("channel_id") String channelId,
			@RequestParam("app_user_id") String appUserId,
			@RequestParam("device_type") String deviceType){

		Users user = usersService.getUserById(userId);
		AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (user == null) {
			return result;
		}
		UserBaiduBind userBaiduBind = userBaiduBindService.selectByUserId(user.getId());
		if (userBaiduBind == null) {
			userBaiduBind = new UserBaiduBind();
			userBaiduBind.setId(0L);
		}
		userBaiduBind.setUserId(user.getId());
		userBaiduBind.setAppId(appId);
		userBaiduBind.setAppUserId(appUserId);
		userBaiduBind.setChannelId(channelId);
		userBaiduBind.setMobile(user.getMobile());
		userBaiduBind.setDeviceType(deviceType);

		// 更新或者新增
		if (userBaiduBind != null && userBaiduBind.getId() > 0L) {

			userBaiduBindService.updateByPrimaryKeySelective(userBaiduBind);

		} else {
			userBaiduBind.setAddTime(TimeStampUtil.getNow() / 1000);
			userBaiduBindService.insertSelective(userBaiduBind);
		}
		return result;
	}

}
