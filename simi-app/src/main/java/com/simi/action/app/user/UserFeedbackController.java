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
			@RequestParam("user_id") Long userId,
			@RequestParam("content") String content)
			throws UnsupportedEncodingException {

		AppResultData<String> result = new AppResultData<String>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		String contentDecoder = URLDecoder.decode(content,"utf-8");
		if (contentDecoder != null
				&& contentDecoder.length() > 200) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("意见反馈内容为空或者超长");
			return result;
		}
		Users users = userService.selectVoByUserId(userId);
		
		// 判断是否为注册用户，非注册用户返回 999
		if (users == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		UserFeedback userFeedback = new UserFeedback();
		userFeedback.setUserId(users.getId());
		userFeedback.setMobile(users.getMobile());
		userFeedback.setContent(contentDecoder);
		userFeedback.setAddTime(TimeStampUtil.getNow() / 1000);
		userFeedbackService.insert(userFeedback);
		
		return result;
	}

}
