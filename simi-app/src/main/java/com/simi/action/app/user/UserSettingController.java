package com.simi.action.app.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.UserTrailReal;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.ValidateService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UserTrailRealService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserMsgSearchVo;
import com.simi.vo.user.UserMsgVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserSettingController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private XCompanySettingService xCompanySettingService;
	
	@Autowired
	private ValidateService validateService;	
	
	/** 
	 *  设置常用提醒
	 */
	@RequestMapping(value = "/get_default_subscribe_tags", method = RequestMethod.POST)
	public AppResultData<Object> getDefaultSubScribeTags() {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(0L);
		searchVo.setUserId(0L);
		searchVo.setSettingType("subscribe-tags");
		
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		if (list.isEmpty()) return result;
		
		XcompanySetting item = list.get(0);
		result.setData(item.getSettingJson());
		
		return result;
	}
	
	@RequestMapping(value = "/get_user_subscribe_tags", method = RequestMethod.POST)
	public AppResultData<Object> getUserSubScribeTags(
			@RequestParam("user_id") Long userId) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		// 判断是否为注册用户，非注册用户返回 999		
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(0L);
		searchVo.setUserId(userId);
		searchVo.setSettingType("subscribe-tags");
		
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		if (list.isEmpty()) return result;
		
		XcompanySetting item = list.get(0);
		result.setData(item.getSettingJson());
		
		return result;
	}
	
	@RequestMapping(value = "/set_user_subscribe_tags", method = RequestMethod.POST)
	public AppResultData<Object> setUserSubScribeTags(
			@RequestParam("user_id") Long userId,
			@RequestParam("subscribe_tags") String subscribeTags
			) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		// 判断是否为注册用户，非注册用户返回 999		
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		if (StringUtil.isEmpty(subscribeTags)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("标签设置不能为空.");
			return result;
		}
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(0L);
		searchVo.setUserId(userId);
		searchVo.setSettingType("subscribe-tags");
		
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		XcompanySetting record = xCompanySettingService.initXcompanySetting();
		
		if (!list.isEmpty()) {
			record = list.get(0);
		}
		
		record.setUserId(userId);
		record.setSettingType("subscribe-tags");
		record.setSettingJson(subscribeTags);
		
		return result;
	}
	
	
	/** 
	 *  设置常用提醒
	 */
	@RequestMapping(value = "/set_alarm", method = RequestMethod.POST)
	public AppResultData<Object> setAlarm(
		@RequestParam("user_id") Long userId,
		@RequestParam("alarm") String alarm
		) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		return result;
	}
	
	/** 
	 *  设置常用提醒
	 */
	@RequestMapping(value = "/get_alarm", method = RequestMethod.POST)
	public AppResultData<Object> getAlarm(
		@RequestParam("user_id") Long userId
		) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		return result;
	}
}
