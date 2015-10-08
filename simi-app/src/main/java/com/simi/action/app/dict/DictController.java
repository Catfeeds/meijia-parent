package com.simi.action.app.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.vo.AppResultData;
import com.simi.po.model.dict.DictAd;
import com.simi.po.model.dict.DictCardType;
import com.simi.po.model.dict.DictSeniorType;
import com.simi.po.model.dict.DictTrade;
import com.simi.service.dict.AdService;
import com.simi.service.dict.CardTypeService;
import com.simi.service.dict.DictSeniorTypeService;
import com.simi.service.dict.TradeService;

@Controller
@RequestMapping(value="/app/dict")
public class DictController<T> {

	@Autowired
	private AdService adService;
	
	@Autowired
	private CardTypeService cardTypeService;
	
	@Autowired
	private DictSeniorTypeService seniorTypeService;	
	
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

}
