package com.simi.action.job;

import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meijia.utils.MobileUtil;
import com.meijia.utils.StringUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/job")
public class JobUserController extends BaseController {
	
	@Autowired
	private UsersService userService;
	
	/**
	 * 用户的手机号所在地批量更新,仅提供某个特定参数下使用
	 */
	@RequestMapping(value = "gen_user_province", method = RequestMethod.GET)
	public AppResultData<Object> genUserProvince(HttpServletRequest request) {


		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {

			List<Users> userList = userService.selectByAll();
			Users record = null;
			
			for (int i = 0; i < userList.size(); i++) {
				record = userList.get(i);
				String mobile = record.getMobile();
				String provinceName = "";
				if (StringUtil.isEmpty(mobile)) continue;
				if (!StringUtil.isEmpty(record.getProvinceName())) continue;
				try {
					provinceName = MobileUtil.calcMobileCity(mobile);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				record.setProvinceName(provinceName);
				userService.updateByPrimaryKeySelective(record);
			}
		}	
		return result;
	}
}
