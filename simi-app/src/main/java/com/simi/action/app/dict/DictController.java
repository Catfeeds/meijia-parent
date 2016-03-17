package com.simi.action.app.dict;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.vo.AppResultData;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictAd;
import com.simi.po.model.dict.DictCardType;
import com.simi.po.model.dict.DictProvince;
import com.simi.po.model.dict.DictSeniorType;
import com.simi.po.model.dict.DictTrade;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.Tags;
import com.simi.service.dict.AdService;
import com.simi.service.dict.CardTypeService;
import com.simi.service.dict.CityService;
import com.simi.service.dict.DictSeniorTypeService;
import com.simi.service.dict.ProvinceService;
import com.simi.service.dict.RegionService;
import com.simi.service.dict.TradeService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.TagsService;
import com.simi.service.user.UsersService;

@Controller
@RequestMapping(value="/app/dict")
public class DictController<T> {

	@Autowired
	private AdService adService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private PartnersService partnersService;
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private CardTypeService cardTypeService;
	
	@Autowired
	private DictSeniorTypeService seniorTypeService;	
	
	@Autowired
	private ProvinceService provinceService;	
	
	@Autowired
	private CityService cityService;	
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private TagsService tagsService;
	
	@Autowired
	private TradeService tradeService;	

	@RequestMapping(value = "get_ads", method = RequestMethod.GET)
    public AppResultData<Object> getAds(
    		@RequestParam(value = "ad_type", required = false, defaultValue = "0") Short adType
    		) {

    	AppResultData<Object> result = null;
    	result = new AppResultData<Object>(0, "ok", "");
    	
    	List<DictAd> adList = adService.selectByAdType(adType);
    	
    	result.setData(adList);
    	return result;
    }
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "get_cards", method = RequestMethod.GET)
    public AppResultData<List> getCardType () {

    	//获得广告配置定义列表项
    	List<DictCardType> list = cardTypeService.getCardTypes();

    	AppResultData<List> result = null;
    	result = new AppResultData<List>(0, "ok", list);

    	return result;
    }	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "get_seniors", method = RequestMethod.GET)
    public AppResultData<List> getSeniors () {

    	//获得广告配置定义列表项
    	List<DictSeniorType> list = seniorTypeService.getSeniorTypes();

    	AppResultData<List> result = null;
    	result = new AppResultData<List>(0, "ok", list);

    	return result;
    }	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "get_trades", method = RequestMethod.GET)
    public AppResultData<List> getTrades () {

    	//获得广告配置定义列表项
    	List<DictTrade> list = tradeService.selectAll();

    	AppResultData<List> result = null;
    	result = new AppResultData<List>(0, "ok", list);

    	return result;
    }		

	/**
	 * 获得标签列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "get_tag_list", method = RequestMethod.GET)
	public AppResultData<Object> getTagsList(
			@RequestParam("tag_type") Short tagType
			//,@RequestParam("name") String name
			) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		List<Tags> tagList = tagsService.selectByTagType(tagType);
		result.setData(tagList);

		return result;
	}
	
	@RequestMapping(value = "get_service_type_list", method = RequestMethod.GET)
	public AppResultData<Object> getServiceTypeList(
			@RequestParam("parent_id") Long parentId) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
	    List<PartnerServiceType> listBig = partnerServiceTypeService.selectByParentId(parentId);
		/*List<String> bigServiceTypeName = new ArrayList<String>();
		for (Iterator iterator = listBig.iterator(); iterator.hasNext();) {
			PartnerServiceType partnerServiceType = (PartnerServiceType) iterator.next();
			bigServiceTypeName.add(partnerServiceType.getName());
		}*/
		
		result.setData(listBig);

		return result;
	}
	@RequestMapping(value = "get_service_type_by_partnerId_list", method = RequestMethod.GET)
	public AppResultData<Object> getServiceTypeByPartnerIdList(
			@RequestParam("partner_id") Long partnerId) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		//服务大类，该团队的服务大类
		List<PartnerServiceType> partnerServiceType = new ArrayList<PartnerServiceType>();
		
		
		List<PartnerRefServiceType> partnerRefServiceType = partnersService.selectServiceTypeByPartnerIdAndParentId(partnerId, 0L);
		
		if (!partnerRefServiceType.isEmpty()) {
	    	List<Long> serviceTypeIds = new ArrayList<Long>();
	    	
	    	for (PartnerRefServiceType item : partnerRefServiceType) {
	    		if (!serviceTypeIds.contains(item.getServiceTypeId())) {
	    			serviceTypeIds.add(item.getServiceTypeId());
	    		}
	    	}
	    	
	    	partnerServiceType =   partnerServiceTypeService.selectByIds(serviceTypeIds);
		}
		result.setData(partnerServiceType);

		return result;
	}
	
	/**
	 * 获得全部省份
	 * @return
	 */
	@RequestMapping(value = "get_dict_province_list", method = RequestMethod.GET)
	public AppResultData<Object> getProvinceList() {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
	   List<DictProvince> list = provinceService.selectAll();
		/*List<String> bigServiceTypeName = new ArrayList<String>();
		for (Iterator iterator = listBig.iterator(); iterator.hasNext();) {
			PartnerServiceType partnerServiceType = (PartnerServiceType) iterator.next();
			bigServiceTypeName.add(partnerServiceType.getName());
		}*/
		result.setData(list);

		return result;
	}

	
	
	
}
