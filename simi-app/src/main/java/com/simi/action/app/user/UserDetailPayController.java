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
import com.simi.po.model.user.UserDetailPay;
import com.simi.po.model.user.UserDetailScore;
import com.simi.service.user.UserDetailPayService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserDetailPayVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserDetailPayController extends BaseController {

	@Autowired
	private UserDetailPayService userDetailPayService;

	/*
	 * 用户消费明细接口 接口文档:
	 * http://182.92.160.194/trac/wiki/%E6%88%91%E7%9A%84%E4%BC%98
	 * %E6%83%A0%E5%88%B8%E6%8E%A5%E5%8F%A3
	 * 
	 * @param user_id 用户ID
	 */
	@RequestMapping(value = "get_detail_pay", method = RequestMethod.GET)
	public AppResultData<List<UserDetailPay>> getDetailPay(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		List<UserDetailPay> userAddrs = userDetailPayService
				.selectByUserIdPage(userId, page);
		if (userAddrs != null && userAddrs.size() > 0) {

			for (int i = 0; i < userAddrs.size(); i++) {

				UserDetailPayVo vo = userDetailPayService
						.getUserDetailPayVo(userAddrs.get(i));
				userAddrs.set(i, vo);

			}
			AppResultData<List<UserDetailPay>> result = new AppResultData<List<UserDetailPay>>(
					Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, userAddrs);

			return result;
		} else {
			AppResultData<List<UserDetailPay>> result = new AppResultData<List<UserDetailPay>>(
					Constants.ERROR_999, ConstantMsg.ERROR_999_MSG_10,
					new ArrayList<UserDetailPay>());
			return result;
		}
	}
}