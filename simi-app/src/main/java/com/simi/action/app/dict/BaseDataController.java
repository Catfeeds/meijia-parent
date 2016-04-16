package com.simi.action.app.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.vo.AppResultData;
import com.simi.vo.ApptoolsSearchVo;
import com.simi.vo.po.AdSearchVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictExpress;
import com.simi.po.model.op.AppTools;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.dict.CityService;
import com.simi.service.dict.ExpressService;
import com.simi.service.op.AppToolsService;
import com.simi.service.op.OpAdService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;

@Controller
@RequestMapping(value = "/app/")
public class BaseDataController<T> {

	@Autowired
	UsersService userService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private AppToolsService appToolsService;
	
	@Autowired
	private ExpressService expressService;
	
	@Autowired
	private XCompanySettingService xCompanySettingService;
	
	@Autowired
	private OpAdService opAdService;

	/**
	 * 
	 * @param t_user  用户信息
	 * @param t_city  城市信息
	 * @param t_apptools 应用中心信息.
	 * @param t_express  快递供应商
	 * @param t_assets   资产类型
	 * @return
	 */
	@RequestMapping(value = "get_base_datas", method = RequestMethod.GET)
	public AppResultData<Object> baseDatas(
//			@RequestParam(value = "user_id", required = false, defaultValue = "0") Long userId,
//			@RequestParam(value = "t_user", required = false, defaultValue = "0") Long tUser,
			@RequestParam(value = "t_city", required = false, defaultValue = "0") Long tCity,
			@RequestParam(value = "t_apptools", required = false, defaultValue = "0") Long tApptools,
			@RequestParam(value = "t_express", required = false, defaultValue = "0") Long tExpress,
			@RequestParam(value = "t_assets", required = false, defaultValue = "0") Long tAssets,
			@RequestParam(value = "t_channel", required = false, defaultValue = "0") Long tChannel
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Map<String, Object> datas = new HashMap<String, Object>();
				
		datas.put("city", "");
		if (tCity > 0L) {
			List<DictCity> cityList = new ArrayList<DictCity>();
			cityList = cityService.selectByT(tCity);
			datas.put("city", cityList);
		}
		
		datas.put("apptools", "");
		if (tApptools > 0L) {
			ApptoolsSearchVo asearchVo = new ApptoolsSearchVo();
			List<AppTools> apptools = appToolsService.selectBySearchVo(asearchVo);
			if (!apptools.isEmpty()) datas.put("apptools", apptools);
		}
		
		datas.put("express", "");
		
		if (tExpress > 0L) {
			List<DictExpress> expressList = expressService.selectByT(tExpress);
			if (!expressList.isEmpty()) {
				datas.put("express", expressList);
			}
		}
		
		datas.put("asset_types", "") ;
		if (tAssets > 0L) {
			CompanySettingSearchVo s = new CompanySettingSearchVo();
			s.setSettingType("asset_type");
			List<XcompanySetting> assetTypeList = xCompanySettingService.selectBySearchVo(s);
			if (!assetTypeList.isEmpty()) {
				datas.put("asset_types", assetTypeList) ;
			}
		}
		
		datas.put("channels", "");
		if (tChannel > 0L) {
			AdSearchVo searchVo = new AdSearchVo();
			searchVo.setUpdateTime(tChannel);
			searchVo.setAdType("99");
			List<OpAd> channelList = opAdService.selectBySearchVo(searchVo);
			if (!channelList.isEmpty()) {
				datas.put("channels", channelList) ;
			}
		}
		
		result.setData(datas);
		
		return result;
	}

}
