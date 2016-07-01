package com.simi.action.app.setting;

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
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.MathToolsService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.setting.InsuranceBaseVo;
import com.simi.vo.setting.InsuranceVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;

@Controller
@RequestMapping(value = "/app/insurance")
public class InsuranceController extends BaseController {


	@Autowired
	private XCompanySettingService xCompanySettingService;
	
	@Autowired
	private MathToolsService mathToolsService;

	/**
	 * 
	 *  获得 城市、区县社保公积金基数 接口
	 * 
	 * @param cityId	 城市id
	 * @param regionId	区县id
	 * @return
	 * 
	 * 		{
	 * 			status:xx
	 * 			msg: xx
	 * 			data:{
	 * 				cityId  : xx,
	 * 			  regionId  : xx,
	 * 
	 * 			    pensionP(个人) : （录入时的数字,单位%, 转换为数字时需要 除以100） 	//养老 
	 * 				pensionC（公司） 
	 * 				...
	 * 
	 * 			}	
	 * 		}
	 * 
	 */
	@RequestMapping(value = "get_insurance.json",method = RequestMethod.GET)
	public AppResultData<Object> getInsurRance(
			@RequestParam("city_id")Long cityId){
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		
		if(cityId <= 0L || cityId == null){
			
			result.setStatus(Constants.ERROR_999);
			result.setMsg("城市不存在");
			return result;
		}
		
//		if(regionId <= 0L || regionId == null){
//			
//			result.setStatus(Constants.ERROR_999);
//			result.setMsg("区县不存在");
//			return result;
//		}
//		
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		
		//社保公积金基数，setting_type = "insurance"
		searchVo.setSettingType(Constants.SETTING_TYPE_INSURANCE);
		
		searchVo.setCityId(cityId);
		
		
//		searchVo.setRegionId(regionId);
		
		
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		if (list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("地区数据不存在");
			return result;
		}
		// 返回 json字段。集合

		XcompanySetting xcompanySetting = list.get(0);

		// SettingJsonSettingValue settingValue = (SettingJsonSettingValue)
		// xcompanySetting.getSettingValue();

		JSONObject setValue = (JSONObject) xcompanySetting.getSettingValue();

		InsuranceVo settingValue = JSON.toJavaObject(setValue, InsuranceVo.class);

		result.setData(settingValue);
		
		return result;
	}
	
	@RequestMapping(value = "get_insurance_base.json",method = RequestMethod.GET)
	public AppResultData<Object> getInsurRanceBase(@RequestParam("city_id")Long cityId){
		
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		if(cityId <= 0L || cityId == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("城市不存在");
			return result;
		}
				
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		
		//社保公积金基数，setting_type = "insurance"
		searchVo.setSettingType(Constants.SETTING_TYPE_INSURANCE_BASE);
		
		searchVo.setCityId(cityId);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		if (list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("地区数据不存在");
			return result;
		}

		XcompanySetting xcompanySetting = list.get(0);
		
		JSONObject setValue = (JSONObject) xcompanySetting.getSettingValue();
		
		InsuranceBaseVo settingValue = JSON.toJavaObject(setValue, InsuranceBaseVo.class);
		
		result.setData(settingValue);

		return result;
	}
	
	//计算五险一金的方法
	@RequestMapping(value = "math_insurance.json",method = RequestMethod.POST)
	public AppResultData<Object> mathInsurance(
			@RequestParam("city_id") Long cityId,
			@RequestParam("shebao") int shebao,
			@RequestParam("gjj") int gjj
			){
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		result = mathToolsService.mathInsurance(cityId, shebao, gjj);
		
		return result;
	}
	
	
	
	/*
	 * 个人所得税 基数
	 * 
	 * 	工资、薪金（含税/不含税）。。年终奖。。 劳务（含税/不含税）
	 */
	@RequestMapping(value = "get_tax.json",method = RequestMethod.GET)
	public AppResultData<Object> getTaxPersion(
			@RequestParam(value = "setting_type", required = false, defaultValue = "tax_persion") String settingType){
		
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if(StringUtil.isEmpty(settingType)){
			result.setMsg("个税类型不存在");
			result.setStatus(Constants.ERROR_999);
			return result;
		}
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		
		searchVo.setSettingType(settingType);
		
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		if (list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("数据不存在!");
		}

		XcompanySetting xcompanySetting = list.get(0);		
		Object value = xcompanySetting.getSettingValue();
		
		//传 json字符串
		String jsonString = JSON.toJSONString(value);		
		result.setData(jsonString);

		return result;
	}
	
}
