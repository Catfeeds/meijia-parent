package com.simi.action.app.user;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.IPUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserBaiduBind;
import com.simi.po.model.user.UserLogined;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.UserSmsToken;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserBaiduBindService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserBaiduBindVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserRef3rdController extends BaseController {

	@Autowired
	private UsersService userService;


	@Autowired
	private UserLoginedService userLoginedService;


	@Autowired
	private UserCouponService userCouponService;
	
	@Autowired
	private UserRef3rdService userRef3rdService;
	
	@Autowired
	private UserBaiduBindService userBaiduBindService;
	
	@Autowired
	private UserSmsTokenService userSmsTokenService;
	


	// 1、第三方登录接口
	@RequestMapping(value = "login-3rd", method = RequestMethod.POST)
	public AppResultData<Object> login3rd(
			HttpServletRequest request,
			@RequestParam("pid") String pid,
			@RequestParam("3rd_type") String thirdType,
			@RequestParam("login_from") int loginFrom) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		UserRef3rd userRef3rd = userRef3rdService.selectByPidAnd3rdType(pid, thirdType);
		
		if(userRef3rd!=null){
			long ip = IPUtil.getIpAddr(request);
			//第三方登录成功，在user_logined表添加记录
			UserLogined userLogined = new UserLogined();
			userLogined.setMobile(userRef3rd.getMobile());
			userLogined.setLoginFrom((short)loginFrom);
			userLogined.setLoginIp(ip);
			userLogined.setUserId(userRef3rd.getUserId());
			userLogined.setAddTime(TimeStampUtil.getNow()/1000);
			userLoginedService.insertSelective(userLogined);
			
			Users users = userService.getUserByMobile(userRef3rd.getMobile());
			
			UserBaiduBind userBaiduBind = userBaiduBindService.selectByUserId(userRef3rd.getUserId());

			UserBaiduBindVo vo = new UserBaiduBindVo();

			try {
				BeanUtils.copyProperties(vo, users);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			vo.setAppId("");
			vo.setChannelId("");
			vo.setAppUserId("");

			if (userBaiduBind != null) {
				vo.setAppId(userBaiduBind.getAppId());
				vo.setChannelId(userBaiduBind.getChannelId());
				vo.setAppUserId(userBaiduBind.getAppUserId());
			}
			result.setData(vo);			
			return result;
			
		}else {
			result = new AppResultData<Object>(Constants.ERROR_999,
					ConstantMsg.USER_REF_3RD_NO_BIND, "");
			return result;
		}
	}
}
