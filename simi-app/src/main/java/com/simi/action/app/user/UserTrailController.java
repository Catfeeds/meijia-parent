package com.simi.action.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserTrail;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserTrailService;
import com.simi.service.user.UsersService;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserTrailController extends BaseController{

	@Autowired
	private UserTrailService userTrailService;

	@Autowired
	private UsersService userService;


	@RequestMapping(value = "post_trail", method = RequestMethod.POST)
	public AppResultData<String> trail(
			@RequestParam("user_id") Long userId,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude

			) {
		UserTrail userTrail=new UserTrail();
		Users u = userService.selectByPrimaryKey(userId);

		userTrail.setUserId(u.getId());
		userTrail.setMobile(u.getMobile());
		userTrail.setLatitude(latitude);
		userTrail.setLongitude(longitude);
		userTrail.setAddTime(TimeStampUtil.getNow() / 1000);


		AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		userTrailService.insertSelective(userTrail);


		return result;
	}
}
