package com.simi.action.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageInfo;
import com.simi.action.admin.AdminController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;
import com.simi.vo.user.UserDetailScoreVo;
import com.simi.vo.user.UserMsgSearchVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.user.UserStatVo;

@Controller
@RequestMapping(value = "/user")
public class UserScoreController extends AdminController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
	private UserDetailScoreService userDetailScoreService;
	
	@AuthPassport
	@RequestMapping(value = "/scoreList", method = { RequestMethod.GET })
	public String userList(HttpServletRequest request, Model model,
			UserMsgSearchVo searchVo, Long userId) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		if (userId != null && userId > 0L) searchVo.setUserId(userId);
		
		PageInfo result = userDetailScoreService.selectByListPage(searchVo, pageNo, pageSize);
		
		List<UserDetailScore> list = result.getList();
		
		for (int i = 0; i < list.size(); i++) {
			UserDetailScore item = list.get(i);
			UserDetailScoreVo vo = userDetailScoreService.getVo(item);
			list.set(i, vo);
		}
		
		result = new PageInfo(list);
		
		model.addAttribute("contentModel", result);

		return "user/userScoreList";
	}
	
}
