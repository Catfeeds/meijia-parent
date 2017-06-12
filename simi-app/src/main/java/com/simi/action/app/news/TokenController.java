package com.simi.action.app.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.wx.utils.WxUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.CompanySettingSearchVo;

@Controller
@RequestMapping(value = "/news")
public class TokenController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private XCompanySettingService xCompanySettingService;
	
	@RequestMapping(value = "do_token", method = RequestMethod.GET)
	public AppResultData<Object> genToken() {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		CompanySettingSearchVo searchVo1 = new CompanySettingSearchVo();
		searchVo1.setSettingType(Constants.SETTING_TOKEN);
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo1);
		
		XcompanySetting record = xCompanySettingService.initXcompanySetting();
		if (!list.isEmpty()) {
			record = list.get(0);
		}
		
		String token =  WxUtil.getNonceStr();
		record.setUserId(0L);
		record.setSettingJson(token);
		record.setSettingType(Constants.SETTING_TOKEN);
		
		if (record.getId() > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			xCompanySettingService.updateByPrimaryKeySelective(record);
		} else {
			xCompanySettingService.insert(record);
		}
		return result;
	}
	
	@RequestMapping(value = "token")
	public AppResultData<Object> getToken(
			@RequestParam(value = "app", required = false, defaultValue="hinge") String appType) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		CompanySettingSearchVo searchVo1 = new CompanySettingSearchVo();
		searchVo1.setSettingType(Constants.SETTING_TOKEN);
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo1);
		
		XcompanySetting record = xCompanySettingService.initXcompanySetting();
		if (list.isEmpty()) {
			String token =  WxUtil.getNonceStr();
			record.setUserId(0L);
			record.setSettingJson(token);
			record.setSettingType(Constants.SETTING_TOKEN);
			xCompanySettingService.insert(record);
		} else {
			record = list.get(0);
		}
		
		String token = record.getSettingJson();
		result.setData(token);
		return result;
	}
	
	

}
