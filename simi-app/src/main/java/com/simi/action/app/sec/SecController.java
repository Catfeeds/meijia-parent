package com.simi.action.app.sec;

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
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.Users;
import com.simi.service.dict.CityService;
import com.simi.service.dict.DictService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.sec.SecService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.UserSearchVo;
import com.simi.vo.sec.SecViewVo;
import com.simi.vo.user.UserViewVo;


@Controller
@RequestMapping(value = "/app/sec")
public class SecController extends BaseController {

	@Autowired
	private UserLoginedService userLoginedService;

	@Autowired
	private OrderQueryService orderQueryService;
	
	@Autowired
	private UsersService userService;

	@Autowired
	private SecService secService;

	@Autowired
	private UserRefSecService userRefSecService;

	@Autowired
	private UserRef3rdService userRef3rdService;

	/**
	 * 获取可用的秘书列表
	 * @param userId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "get_list", method = RequestMethod.GET)
	public AppResultData<Object> secList(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
			
			AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");

			Users u = userService.selectByPrimaryKey(userId);

			// 判断是否为注册用户，非注册用户返回 999
			if (u == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
				return result;
			}
			
			//获取秘书列表
			UserSearchVo searchVo = new UserSearchVo();
			searchVo.setUserType((short) 1);
			PageInfo userList = userService.searchVoListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
			List<Users> users = userList.getList();
			List<SecViewVo> secList = new ArrayList<SecViewVo>();
			if (!users.isEmpty()) {
				secList = secService.changeToSecViewVos(users);
				
				//排除本身
				SecViewVo item = null;
				for (int i =0; i < secList.size(); i++) {
					item = secList.get(i);
					if (userId.equals(item.getSecId())) {
						secList.remove(i);
					}
				}				
				
				result.setData(secList);
			}
			

			return result;
	}
	
	/**
	 * 秘书获取绑定客户信息
	 * @param userId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "get_users", method = RequestMethod.GET)
	public AppResultData<Object> getSecUsers(@RequestParam("sec_id") Long secId) {
			
			AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");

			Users u = userService.selectByPrimaryKey(secId);

			// 判断是否为注册用户，非注册用户返回 999
			if (u == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
				return result;
			}
			
			//获取用户列表
			List<UserRefSec> userRefSecs = userRefSecService.selectBySecId(secId);
			List<Long> userIds = new ArrayList<Long>();
			
			for (UserRefSec item : userRefSecs) {
				if (!userIds.contains(item.getUserId())) {
					userIds.add(item.getUserId());
				}
			}
			
			UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(secId);
			
			List<UserViewVo> users = userService.getUserInfos(userIds, u, userRef3rd);
			
			result.setData(users);
			return result;
	}	
}