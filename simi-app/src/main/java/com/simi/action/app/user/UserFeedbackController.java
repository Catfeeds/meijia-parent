package com.simi.action.app.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserFeedback;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserFeedbackService;
import com.simi.service.user.UsersService;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping("/app/user")
public class UserFeedbackController  extends BaseController {
	@Autowired
	private UserFeedbackService userFeedbackService;
	@Autowired
	private UsersService userService;

	@RequestMapping(value = "post_feedback", method = RequestMethod.POST)
	public AppResultData<String> saveFeedback(
			@RequestParam("mobile") String mobile,
			@RequestParam("content") String content)
			throws UnsupportedEncodingException {
		String contentDecoder = URLDecoder.decode(content,"utf-8");
		if (contentDecoder != null
				&& contentDecoder.length() > 200) {
			AppResultData<String> result = new AppResultData<String>(
					Constants.ERROR_100, ConstantMsg.FEED_BACK_FALSE, "");
			return result;
		}
		Users users = userService.getUserByMobile(mobile);
		UserFeedback userFeedback = new UserFeedback();
		userFeedback.setUserId(users.getId());
		userFeedback.setMobile(mobile);
		userFeedback.setContent(contentDecoder);
		userFeedback.setAddTime(TimeStampUtil.getNow() / 1000);
		int temp = userFeedbackService.insert(userFeedback);
		if(temp>0) {
			AppResultData<String> result = new AppResultData<String>(
					Constants.SUCCESS_0, ConstantMsg.FEED_BACK_SUCCESS, "");
			return result;
		}else {
			AppResultData<String> result = new AppResultData<String>(
					Constants.ERROR_100, ConstantMsg.FEED_BACK_FALSE, "");
			return result;
		}
	}

}
