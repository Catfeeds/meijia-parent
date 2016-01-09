package com.simi.action.app.xcloud;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserFriends;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserFriendViewVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Controller
@RequestMapping(value = "/app/company/")
public class StaffController extends BaseController {

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private XcompanyDeptService xCompanyDeptService;
	
	@Autowired
	private XcompanyStaffService xCompanyStaffService;	

	@Autowired
	private UserFriendService userFriendService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/get_staffs", method = { RequestMethod.GET })
	public AppResultData<Object> getStaffs(@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId,
			@RequestParam(value = "dept_id", required = false, defaultValue = "0") Long deptId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "name", required = false, defaultValue = "") String name) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		if (deptId > 0L) {
			searchVo.setDeptId(deptId);
		}

		PageInfo list = xCompanyStaffService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);

		List<XcompanyStaff> plist = list.getList();

		if (plist.isEmpty())
			return result;

		List<UserFriends> userFriends = new ArrayList<UserFriends>();

		for (XcompanyStaff item : plist) {
			UserFriends vo = userFriendService.initUserFriend();
			vo.setFriendId(item.getUserId());
			vo.setId(0L);
			vo.setUserId(userId);
			vo.setAddTime(0L);
			vo.setUpdateTime(0L);
			userFriends.add(vo);
		}

		List<UserFriendViewVo> userList = userFriendService.changeToUserFriendViewVos(userFriends);

		result.setData(userList);

		return result;
	}	
}
