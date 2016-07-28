package com.simi.action.app.xcloud;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meijia.utils.StringUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.setting.InsuranceVo;
import com.simi.vo.xcloud.CheckinNetVo;
import com.simi.vo.xcloud.CompanySettingVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Controller
@RequestMapping(value = "/app/company")
public class CompanySeetingController extends BaseController {


	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	/**
	 * 团队配置信息接口
	 * @param settingType
	 * @param userId
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "get_company_setting", method = RequestMethod.GET)
	public AppResultData<Object> getAppTools(
			@RequestParam("setting_type") String settingType,
			@RequestParam("user_id") Long userId,
			@RequestParam("company_id") Long companyId) {
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		//若不是对用团队的员工责返回提示不再查询
		UserCompanySearchVo companysearchVo = new UserCompanySearchVo();
		companysearchVo.setUserId(userId);
		companysearchVo.setCompanyId(companyId);
		List<XcompanyStaff> xcompanyStaff = xcompanyStaffService.selectBySearchVo(companysearchVo);		
		if (xcompanyStaff.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_COMPANY);
			return result;
		}
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setSettingType(settingType);
		//searchVo.setName(name);
		
		List<CompanySettingVo> vo = new ArrayList<CompanySettingVo>();
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		for (XcompanySetting item : list) {
			CompanySettingVo listVo = new CompanySettingVo();
			listVo = xCompanySettingService.getCompantSettingVo(item);
			vo.add(listVo);
		}
		result.setData(vo);
		
		//针对出勤地点的配置返回
		if (settingType.equals(Constants.SETTING_CHICKIN_NET)) {
			List<XcompanySetting> resultList = new ArrayList<XcompanySetting>();
			for (XcompanySetting item : list) {
				if (item.getSettingValue() != null) {
					JSONObject setValue = (JSONObject) item.getSettingValue();

					CheckinNetVo checkinNetVo = JSON.toJavaObject(setValue, CheckinNetVo.class);

					String wifis = checkinNetVo.getWifis();
					if (!StringUtil.isEmpty(wifis)) {
						String[] wifiAry = StringUtil.convertStrToArray(wifis);
						for (int i =0 ; i < wifiAry.length; i++) {
							if (StringUtil.isEmpty(wifiAry[i])) continue;
							XcompanySetting v = item;
							v.setName(wifiAry[i]);
							v.setSettingValue(null);
							resultList.add(v);
						}
					}
				}
			}
			result.setData(resultList);
		}
		
		
		
		
		return result;
	}
}
