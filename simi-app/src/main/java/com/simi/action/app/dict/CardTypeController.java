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
import com.simi.service.dict.AdService;
import com.simi.service.dict.CardTypeService;

@Controller
@RequestMapping(value="/app/dict")
public class CardTypeController<T> {

	@Autowired
	private CardTypeService cardTypeService;
	
	@Autowired
	private AdService adService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "get_cards", method = RequestMethod.GET)
    public AppResultData<List> getCardType () {

    	//获得广告配置定义列表项
    	List<DictCardType> list = cardTypeService.getCardTypes();

    	AppResultData<List> result = null;
    	result = new AppResultData<List>(0, "ok", list);

    	return result;
    }
	
	@RequestMapping(value = "get_ads", method = RequestMethod.GET)
	public AppResultData<List<DictAd>> dictList(
		
			@RequestParam(value = "ad_type", required = false, defaultValue = "0") Short adType) {

		List<DictAd> dictAdList = new ArrayList<DictAd>();
		
		AppResultData<List<DictAd>> result = new AppResultData<List<DictAd>>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG,dictAdList);	
		if (dictAdList != null) {
			
			dictAdList = adService.selectByAdType(adType);
			
	        result.setData(dictAdList);
		}
	    		
		return result;
}

}
