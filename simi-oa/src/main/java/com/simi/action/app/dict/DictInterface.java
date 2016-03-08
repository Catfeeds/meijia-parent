package com.simi.action.app.dict;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.dao.partners.PartnerServiceTypeMapper;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictRegion;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.Users;
import com.simi.service.dict.AdService;
import com.simi.service.dict.DictService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;

@Controller
@RequestMapping(value = "/interface-dict")
public class DictInterface extends BaseController {

	@Autowired
	private DictService dictService;

    @Autowired
    private AdService adService;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private PartnerServiceTypeService partnerServiceTypeService;
    
    @Autowired
    private PartnerServiceTypeMapper partnerServiceTypeMapper;

	/**
	 * 列表显示城市
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "get-region-by-cityid", method = RequestMethod.GET)
    public AppResultData<List> getRegions(
    		@RequestParam(value = "cityId", required = true, defaultValue = "0") Long cityId) {

		List<DictRegion> listRegion =  dictService.getRegionByCityId(cityId);

		AppResultData<List> result = new AppResultData<List>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, listRegion);

    	return result;
    }

	/**
	 * 根据省份查询城市
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "get-city-by-provinceid", method = RequestMethod.GET)
	public AppResultData<List> getCitys(
			@RequestParam(value = "provinceId", required = true, defaultValue = "0") Long provinceId) {
		
		List<DictCity> listCity =  dictService.getCityByProvinceId(provinceId);
		
		AppResultData<List> result = new AppResultData<List>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, listCity);
		return result;
	}
	/**
	 * 根据serviceTypeId（大类Id）查询小类
	 * @param serviceTypeId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "get-price-by-type",method = RequestMethod.GET)
	public AppResultData<List> getPrice(
			@RequestParam(value = "serviceTypeId", required = true, defaultValue = "0") Long serviceTypeId){
		
		
		List<Long> partnerIds = new ArrayList<Long>();
		partnerIds.add(0L);

		PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
		searchVo.setParentId(serviceTypeId);
		searchVo.setViewType((short) 1);
		searchVo.setPartnerIds(partnerIds);
		
		List<PartnerServiceType> list = partnerServiceTypeService.selectBySearchVo(searchVo);
		
		AppResultData<List> result = new AppResultData<List>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, list);
		
		return result;
	}
	/**
	 * 检查用户是否存在
	 * @param mobile
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "check-user-by-mobile", method = RequestMethod.GET)
    public AppResultData<Object> getUser(
    		@RequestParam(value = "mobile", required = true, defaultValue = "") String mobile) {
		
		Users users = usersService.selectByMobile(mobile);
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, users);
    	return result;
    }

}
