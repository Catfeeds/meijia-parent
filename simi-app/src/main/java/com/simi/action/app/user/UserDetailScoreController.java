package com.simi.action.app.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.simi.po.model.user.UserDetailShare;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UserDetailShareService;
import com.simi.service.user.UsersService;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserDetailScoreController extends BaseController {

	@Autowired
	private UserDetailScoreService userDetailScoreService;
	@Autowired
	private UsersService userService;
	@Autowired
	private UserDetailShareService userDetailShareService;

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

	// 10. 分享好友记录接口
	/**
	 * mobile true string 手机号 share_type true string 分享类型: QQ weixin mobile
	 * share_account true string 分享的账号, 注意为urlencode
	 */
	@RequestMapping(value = "share", method = RequestMethod.POST)
	public AppResultData<Object> share(@RequestParam("mobile")
	String mobile, @RequestParam("share_type")
	String share_type, @RequestParam("share_account")
	String share_account) {
		// 1. 操作表 user_detail_share
		// 2. 如果分享的记录在 user_detail_share不存在，
		// 1) 在 user_detail_share不存在，增加一条记录.
		// 2) 产生积分，操作表为 user_detail_score
		// 3) 操作表users 字段score 累加.
		// 3. 如果存在则不做任何操作.
		AppResultData<Object> result_success = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG,"");
		AppResultData<Object> result_fail = new AppResultData<Object>(
				Constants.ERROR_999, ConstantMsg.USER_SOCRE_SHARE_FRIENDS_MSG,
				"");
		Users user = userService.getUserByMobile(mobile);
		//根据用户手机号和分型的类型，查询是否已做过类似分享获得积分
		List<UserDetailShare> list = userDetailShareService.selectByMobileAndShareType(mobile, share_type);
		if (!(list!= null && list.size()>0)) {
			//userDetailShare中增加记录
			UserDetailShare userDetailShare = new UserDetailShare();
			userDetailShare.setAddTime(TimeStampUtil.getNow() / 1000);
			userDetailShare.setMobile(mobile);
			userDetailShare.setUserId(user.getId());
			userDetailShare.setShareType(share_type);
			try {
				userDetailShare.setShareAccount(URLDecoder.decode(
						share_account, Constants.URL_ENCODE));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				AppResultData<Object> result = new AppResultData<Object>(
						Constants.ERROR_100, ConstantMsg.ERROR_100_MSG,"");
				return result;
			}
			//用户明细积分新增记录
			UserDetailScore userDetailScore = new UserDetailScore();
			userDetailScore.setAddTime(TimeStampUtil.getNow() / 1000);
			userDetailScore.setMobile(mobile);
			userDetailScore.setUserId(user.getId());
			userDetailScore.setScore(Constants.SHARE_CORE);
			userDetailScore.setActionId(Constants.ACTION_SHARE);
			userDetailScore.setIsConsume(Constants.CONSUME_SCORE_GET);
			//更新用户积分数
			user.setScore(user.getScore() + Constants.SHARE_CORE);// 增加积分
			userDetailShareService.insert(user, userDetailShare, userDetailScore);// 添加分享详情及积分
			return result_success;
		} else {// 如果已经做过分享，返回提醒
			return result_fail;
		}

	}
}
