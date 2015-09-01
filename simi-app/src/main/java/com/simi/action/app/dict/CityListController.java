package com.simi.action.app.dict;

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
import com.simi.po.model.dict.DictCity;
import com.simi.service.dict.CityService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/city")
public class CityListController extends BaseController {
	
	@Autowired
	private CityService cityService;
	/**
	 * 查出所有城市的信息
	 * @return
	 */
	@RequestMapping(value = "get_list", method = RequestMethod.GET)
	public AppResultData<Object> cityList(
		@RequestParam(value = "t", required = false, defaultValue = "0") Long t
		) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		List<DictCity> cityList  =  new ArrayList<DictCity>();
		
		cityList = cityService.selectByT(t);
	
		if(cityList !=null){
			result.setData(cityList);
		}
		return result;
	}
}
