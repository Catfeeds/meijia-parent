package com.simi.action.app.job;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meijia.utils.vo.AppResultData;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;
import com.simi.vo.user.UserDetailScoreSearchVo;
import com.simi.vo.user.UserMsgSearchVo;

@Controller
@RequestMapping(value = "/app/job/cleanup")
public class CleanupUserScoreController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserDetailScoreService userDetailScoreService;
	
	/**
	 * 重建用户注册的积分赠送。判断是否已经赠送过，如果已经赠送过则跳过.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "userScore", method = RequestMethod.GET)
	public AppResultData<Object> cleanupUserOrderCard(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		String reqHost = request.getRemoteHost();// 如果用的 IPV6 得到的 将是 0:0.。。。。1

		// String reqHost = request.getRemoteAddr();
		// 限定只有localhost能访问
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			List<Users> list = userService.selectByAll();
			for (Users u : list) {
				UserDetailScoreSearchVo searchVo = new UserDetailScoreSearchVo();
				searchVo.setUserId(u.getId());
				searchVo.setAction("new_user");
				List<UserDetailScore> scores = userDetailScoreService.selectBySearchVo(searchVo);
				if (scores.isEmpty()) {
					
					//记录积分明细
					UserDetailScore record = userDetailScoreService.initUserDetailScore();
					record.setUserId(u.getId());
					record.setMobile(u.getMobile());
					record.setScore(Constants.SCORE_USER_REG);
					record.setAction("new_user");
					record.setParams(u.getId().toString());
					record.setRemarks("新用户注册");
					record.setAddTime(u.getAddTime());
					
					userDetailScoreService.insert(record);
				}
			}
		}
		return result;
	}

}
