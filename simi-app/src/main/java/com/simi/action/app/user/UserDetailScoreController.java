package com.simi.action.app.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserDetailScore;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserDetailScoreController extends BaseController {

	@Autowired
	private UserDetailScoreService userDetailScoreService;
	@Autowired
	private UsersService userService;


	// 7. 我的积分明细接口
	/**
	 * addr_id地址ID mobile手机号
	 */
	@RequestMapping(value = "get_score", method = RequestMethod.GET)
	public AppResultData<List<UserDetailScore>> myScore(@RequestParam("mobile")
	String mobile, @RequestParam("page")
	int page) {
		List<UserDetailScore> userAddrs = userDetailScoreService.selectByPage(
				mobile, page);
		if (userAddrs != null && userAddrs.size() > 0) {
			AppResultData<List<UserDetailScore>> result = new AppResultData<List<UserDetailScore>>(
					Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, userAddrs);
			return result;
		} else {
			AppResultData<List<UserDetailScore>> result = new AppResultData<List<UserDetailScore>>(
					Constants.ERROR_999, ConstantMsg.ERROR_999_MSG_10,
					new ArrayList<UserDetailScore>());
			return result;
		}
	}
}
