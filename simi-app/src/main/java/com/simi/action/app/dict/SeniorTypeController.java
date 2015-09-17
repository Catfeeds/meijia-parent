package com.simi.action.app.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simi.vo.AppResultData;
import com.simi.po.model.dict.DictSeniorType;
import com.simi.service.dict.AdService;
import com.simi.service.dict.DictSeniorTypeService;

@Controller
@RequestMapping(value="/app/dict")
public class SeniorTypeController<T> {

	@Autowired
	private DictSeniorTypeService seniorTypeService;
	
	@Autowired
	private AdService adService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "get_seniors", method = RequestMethod.GET)
    public AppResultData<List> getSeniors () {

    	//获得广告配置定义列表项
    	List<DictSeniorType> list = seniorTypeService.getSeniorTypes();

    	AppResultData<List> result = null;
    	result = new AppResultData<List>(0, "ok", list);

    	return result;
    }

}
