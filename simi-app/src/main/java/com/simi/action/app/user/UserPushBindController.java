package com.simi.action.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UsersService;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserPushBindController extends BaseController {

	@Autowired
	private UserPushBindService userPushBindService;

	@Autowired
	private UsersService usersService;
	
	@RequestMapping(value = "post_push_bind", method = RequestMethod.POST)
	public AppResultData<String> pushBind(
			@RequestParam("user_id") Long userId,
			@RequestParam("client_id") String clientId,
			@RequestParam("device_type") String deviceType){

		Users u = usersService.selectByPrimaryKey(userId);
		AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		UserPushBind userPushBind = userPushBindService.selectByUserId(userId);

		if (userPushBind == null) {
			userPushBind = new UserPushBind();
			userPushBind.setId(0L);
		}
		userPushBind.setUserId(u.getId());
		userPushBind.setClientId(clientId);
		userPushBind.setDeviceType(deviceType);

		// 更新或者新增
		if (userPushBind != null && userPushBind.getId() > 0L) {
			
			userPushBindService.updateByPrimaryKeySelective(userPushBind);

		} else {
			userPushBind.setAddTime(TimeStampUtil.getNow() / 1000);
			userPushBindService.insertSelective(userPushBind);
		}
		return result;
	}	

}
