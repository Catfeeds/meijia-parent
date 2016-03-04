package com.simi.action.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserTrailHistory;
import com.simi.po.model.user.UserTrailReal;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserTrailHistoryService;
import com.simi.service.user.UserTrailRealService;
import com.simi.service.user.UsersService;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserTrailController extends BaseController {

	@Autowired
	private UserTrailRealService userTrailRealService;

	@Autowired
	private UserTrailHistoryService userTrailHistoryService;

	@Autowired
	private UsersService userService;

	@RequestMapping(value = "post_trail", method = RequestMethod.POST)
	public AppResultData<String> trail(
			@RequestParam("user_id") Long userId, 
			@RequestParam("lat") String latitude, 
			@RequestParam("lng") String longitude,
			@RequestParam("poi_name") String poiName, 
			@RequestParam("city") String city) {

		AppResultData<String> result = new AppResultData<String>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		UserTrailReal userTrail = userTrailRealService.selectByPrimaryKey(userId);
		
		if (userTrail == null) {
			userTrail = userTrailRealService.initUserTrailReal();
		}
		
		userTrail.setUserId(userId);
		userTrail.setLatitude(latitude);
		userTrail.setLongitude(longitude);
		userTrail.setPoiName(poiName);
		userTrail.setCity(city);
		userTrail.setAddTime(TimeStampUtil.getNowSecond());
		
		if (userTrail.getId() > 0L) {
			userTrailRealService.updateByPrimaryKey(userTrail);
		} else {
			userTrailRealService.insert(userTrail);
		}
		
		//新增历史记录 
		UserTrailHistory userTrailHistory = userTrailHistoryService.initUserTrailHistory();
		userTrailHistory.setUserId(userId);
		userTrailHistory.setLatitude(latitude);
		userTrailHistory.setLongitude(longitude);
		userTrailHistory.setPoiName(poiName);
		userTrailHistory.setCity(city);
		userTrailHistoryService.insert(userTrailHistory);

		return result;
	}
}
