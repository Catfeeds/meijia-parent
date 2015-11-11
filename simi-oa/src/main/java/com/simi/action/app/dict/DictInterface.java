package com.simi.action.app.dict;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictRegion;
import com.simi.service.dict.AdService;
import com.simi.service.dict.DictService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/interface-dict")
public class DictInterface extends BaseController {

	@Autowired
	private DictService dictService;

    @Autowired
    private AdService adService;

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


}
