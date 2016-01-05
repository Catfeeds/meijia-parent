package com.simi.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.StringUtil;
import com.simi.action.app.BaseController;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;

@Controller
@RequestMapping(value = "/app/")
public class OController extends BaseController {

	@Autowired
	private UserDetailScoreService userDetailScoreService;
	@Autowired
	private UsersService userService;
	
	//跳转到对应的h5页面
	/**
	 * 
	 * @param t 即服务类型现有6项，包括：
				company，公司注册或人员加入
				meeting，会议或会议室
				express，快递
				water，送水
				clean，保洁
				green，绿植
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "o", method = RequestMethod.GET)
	public String redirectUrl(
			@RequestParam(value = "t", required = false, defaultValue = "") String t,
			@RequestParam(value = "a", required = false, defaultValue = "") String a,
			@RequestParam(value = "uid", required = false, defaultValue = "") String uid,
			@RequestParam(value = "p", required = false, defaultValue = "") String p
		) {
		
		if (StringUtil.isEmpty(t) || StringUtil.isEmpty(a) || StringUtil.isEmpty(uid)) {
			return "";
		}
		
		Long userId = Long.valueOf(uid);
		Users users = userService.selectByPrimaryKey(userId);
		
		// 判断是否为注册用户，非注册用户返回 999
		if (users == null) {
			return "";
		}		
		String mobile = users.getMobile();
		String url = "http://123.57.173.36/simi-h5/show/";
		
		//处理参数p;
		
		switch (t) {
			case "company" :
				url = "http://123.57.173.36/simi-h5/show/company-reg.html";
				url = url + "?uid="+uid;
				break;
			case "company-join" :
				url = "http://123.57.173.36/simi-h5/show/company-reg.html#tab2";
				url = url + "?uid="+uid;
				url = url + "&mobile="+mobile;
				url = url + "&invitation_code="+p;
				break;
			case "meeting" :
				url = "http://123.57.173.36/simi-h5/show/order-meeting.html";
				url = url + "?uid="+uid;
				break;
			case "express" :
				url = "http://m.kuaidi100.com/";
				break;
			case "water" :
				url = "http://123.57.173.36/simi-h5/show/order-water.html";
				url = url + "?uid="+uid;
				break;
			case "clean" :
				url = "http://light.yunjiazheng.com/oncecleaning/";
				break;
			case "green" :
				url = "http://123.57.173.36/simi-h5/show/order-green.html";
				url = url + "?uid="+uid;
				break;
			case "checkin" :
				url = "http://123.57.173.36/simi-h5/show/order-checkin.html";
				url = url + "?uid="+uid;
				break;
			case "store-my" :
				url = "http://123.57.173.36/simi-h5/show/store-my-index.html";
				url = url + "?uid="+uid;
				break;
		}

		return "redirect:"+url;
		
	}
	

}
