package com.simi.action.app.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.vo.AppResultData;
import com.simi.po.model.dict.DictAd;
import com.simi.service.dict.AdService;
import com.simi.service.dict.ServiceTypeService;

@Controller
@RequestMapping(value="/app/dict")
public class AdController<T> {

	@Autowired
	private ServiceTypeService serviceTypeService;

	@Autowired
	private AdService adService;

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

}
