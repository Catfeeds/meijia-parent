package com.simi.action.app.sec;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.IPUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserLogined;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.Users;
import com.simi.service.dict.CityService;
import com.simi.service.dict.DictService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.sec.SecService;
import com.simi.service.user.UserBaiduBindService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.UserSearchVo;
import com.simi.vo.order.OrderViewVo;
import com.simi.vo.sec.SecInfoVo;
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
	private CityService cityService;
	
	@Autowired
	private DictService dictService;

	@Autowired
	private SecService secService;

	@Autowired
	private UserRefSecService userRefSecService;

	@Autowired
	private UserBaiduBindService userBaiduBindService;

	@Autowired
	private UserSmsTokenService smsTokenService;

	@RequestMapping(value = "get_list", method = RequestMethod.GET)
	public AppResultData<Object> secList(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
			
			AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");

			Users u = userService.getUserById(userId);

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
				result.setData(secList);
			}
			return result;
	}
}