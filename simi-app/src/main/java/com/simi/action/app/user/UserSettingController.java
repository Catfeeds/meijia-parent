package com.simi.action.app.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.baidu.vo.BaiduPoiVo;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.ValidateService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.AppResultData;
import com.simi.vo.setting.CommonToolsVo;
import com.simi.vo.setting.InsuranceVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.CompanySettingVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

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
	 * 设置常用提醒
	 */
	@RequestMapping(value = "/get_default_subscribe_tags", method = RequestMethod.POST)
	public AppResultData<Object> getDefaultSubScribeTags() {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(0L);
		searchVo.setUserId(0L);
		searchVo.setSettingType("subscribe-tags");

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);

		if (list.isEmpty())
			return result;

		XcompanySetting item = list.get(0);
		result.setData(item.getSettingJson());

		return result;
	}

	@RequestMapping(value = "/get_user_subscribe_tags", method = RequestMethod.GET)
	public AppResultData<Object> getUserSubScribeTags(@RequestParam("user_id") Long userId) {

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

		if (list.isEmpty())
			return result;

		XcompanySetting item = list.get(0);
		result.setData(item.getSettingJson());

		return result;
	}

	@RequestMapping(value = "/set_user_subscribe_tags", method = RequestMethod.POST)
	public AppResultData<Object> setUserSubScribeTags(@RequestParam("user_id") Long userId, @RequestParam("subscribe_tags") String subscribeTags) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		// 判断是否为注册用户，非注册用户返回 999
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}

		// if (StringUtil.isEmpty(subscribeTags)) {
		// result.setStatus(Constants.ERROR_999);
		// result.setMsg("标签设置不能为空.");
		// return result;
		// }

		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(0L);
		searchVo.setUserId(userId);
		searchVo.setSettingType("subscribe-tags");

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);

		XcompanySetting record = xCompanySettingService.initXcompanySetting();

		if (!list.isEmpty()) {
			record = list.get(0);
		}
		record.setCompanyId(0L);
		record.setUserId(userId);
		record.setSettingType("subscribe-tags");
		record.setSettingJson(subscribeTags);

		if (record.getId() > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			xCompanySettingService.updateByPrimaryKeySelective(record);
		} else {
			xCompanySettingService.insert(record);
		}

		return result;
	}

	/**
	 * 设置常用提醒
	 */
	@RequestMapping(value = "/set_alarm", method = RequestMethod.POST)
	public AppResultData<Object> setAlarm(@RequestParam("user_id") Long userId, @RequestParam("setting_id") Long setttingId,
			@RequestParam("alarm_day") String alarmDay) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		// 判断是否为注册用户，非注册用户返回 999
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}

		XcompanySetting vo = xCompanySettingService.selectByPrimaryKey(setttingId);
		String name = vo.getName();
		String settingType = vo.getSettingType();

		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setUserId(userId);
		searchVo.setSettingType(settingType);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);

		XcompanySetting record = xCompanySettingService.initXcompanySetting();

		if (!list.isEmpty()) {
			record = list.get(0);
		}
		record.setName(name);
		record.setUserId(userId);
		record.setSettingType(settingType);
		record.setSettingJson(alarmDay);

		if (record.getId() > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			xCompanySettingService.updateByPrimaryKeySelective(record);
		} else {
			xCompanySettingService.insert(record);
		}

		return result;
	}

	/**
	 * 设置常用提醒
	 */
	@RequestMapping(value = "/get_alarm", method = RequestMethod.GET)
	public AppResultData<Object> getAlarm(@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		List<String> settingTypes = new ArrayList<String>();
		settingTypes.add("alarm-holiday");
		settingTypes.add("alarm-shebao");

		// 默认的设置项
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setUserId(0L);
		searchVo.setSettingTypes(settingTypes);

		List<XcompanySetting> defaultList = xCompanySettingService.selectBySearchVo(searchVo);

		// 用户已经配置过的设置项
		searchVo = new CompanySettingSearchVo();
		searchVo.setUserId(userId);
		searchVo.setSettingTypes(settingTypes);
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);

		List<Map> datas = new ArrayList<Map>();
		for (XcompanySetting item : defaultList) {
			Map<String, String> vo = new HashMap<String, String>();
			vo.put("setting_id", item.getId().toString());
			vo.put("name", item.getName());

			String alarmDay = item.getSettingJson();
			for (XcompanySetting x : list) {
				if (x.getSettingType().equals(item.getSettingType())) {
					alarmDay = x.getSettingJson();
					break;
				}
			}

			vo.put("alarm_day", alarmDay);
			datas.add(vo);
		}
		result.setData(datas);
		return result;
	}

	/**
	 * 团队配置信息接口
	 * 
	 * @param settingType
	 * @param userId
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "get_setting", method = RequestMethod.GET)
	public AppResultData<Object> getSetting(@RequestParam("setting_type") String settingType) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setSettingType(settingType);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		
		if (settingType.equals("common-tools")) {
			 
			List<CommonToolsVo> vos = new ArrayList<CommonToolsVo>();
			for (XcompanySetting item : list) {
				JSONObject setValue = (JSONObject) item.getSettingValue();
	
				CommonToolsVo vo = JSON.toJavaObject(setValue, CommonToolsVo.class);
				
				BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
				
				vos.add(vo);
			}
			
			Collections.sort(vos, new Comparator<CommonToolsVo>() {
				@Override
				public int compare(CommonToolsVo s1, CommonToolsVo s2) {
					return Integer.valueOf(s1.getNo()).compareTo(s2.getNo());
				}
			});
			

			
			
			
			result.setData(vos);
			return result;
		}
		
		
		result.setData(list);

		return result;
	}
}
