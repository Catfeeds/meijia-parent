package com.simi.action.app.xcloud;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.CompanySettingVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.simi.vo.xcloud.json.SettingJsonSettingValue;

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
		
		return result;
	}
	
	
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
	 * 			    pension : （录入时的数字,单位%, 转换为数字时需要 除以100） 	//养老 
					medical :		//医疗
			   unemployment :	    //失业
					 injury : 		//工伤
					 birth  : 		//生育
					 fund   :	 	//公积金
	 * 
	 * 			}	
	 * 		}
	 * 
	 */
	@RequestMapping(value = "get_insurance_setting.json",method = RequestMethod.GET)
	public AppResultData<Object> getInsurRanceForCityOrRegion(
			@RequestParam("city_id")Long cityId,
			@RequestParam(value="region_id",required=false,defaultValue="")Long regionId){
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if(cityId <= 0L || cityId == null){
			
			result.setData("cityId不存在");
			return result;
		}
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		
		//社保公积金基数，setting_type = "insurance"
		searchVo.setSettingType(Constants.SETTING_TYPE_INSURANCE);
		
		searchVo.setCityId(cityId.toString());
		
		if(regionId !=null && regionId > 0L ){
			searchVo.setRegionId(regionId.toString());
		}
		
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		//返回 json字段。集合
		List<SettingJsonSettingValue> jsonList = new ArrayList<SettingJsonSettingValue>();
		
		for (XcompanySetting xcompanySetting : list) {
			
			Object settingValue = xcompanySetting.getSettingValue();
			
			jsonList.add((SettingJsonSettingValue) settingValue);
		}
		
		result.setData(jsonList);
		
		return result;
	}
	
	
}
