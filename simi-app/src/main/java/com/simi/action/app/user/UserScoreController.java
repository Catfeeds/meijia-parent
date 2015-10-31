package com.simi.action.app.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.duiba.credits.sdk.CreditTool;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserScoreController extends BaseController {

	@Autowired
	private UserDetailScoreService userDetailScoreService;
	@Autowired
	private UsersService userService;
	
	//跳转到兑吧商城接口
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "score_shop", method = RequestMethod.GET)
	public String scoreShop(@RequestParam("user_id") Long userId) {
		
		Users users = userService.selectByPrimaryKey(userId);
		
		// 判断是否为注册用户，非注册用户返回 999
		if (users == null) {
			return "";
		}		
		
		CreditTool tool=new CreditTool("uxPf5BT5hfJ5NyUMehAoaFcs2EL", "2CByiHjX9U17oC6K73DDGqUBigtP");
		
		Map params=new HashMap();
		params.put("uid", userId.toString());
		params.put("credits", users.getScore().toString());
//		if(redirect!=null){
//		    //redirect是目标页面地址，默认积分商城首页是：http://www.duiba.com.cn/chome/index
//		    //此处请设置成一个外部传进来的参数，方便运营灵活配置
//		    params.put("redirect",redirect);
//		}
		String url=tool.buildUrlWithSign("http://www.duiba.com.cn/autoLogin/autologin?",params);
		
		return "redirect:"+url;
		
	}
	
	//兑吧积分消费的兑换接口
	
	//兑吧处理结果通知接口.

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
