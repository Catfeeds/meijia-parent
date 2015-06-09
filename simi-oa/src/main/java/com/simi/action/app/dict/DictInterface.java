package com.simi.action.app.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictAd;
import com.simi.po.model.dict.DictRegion;
import com.simi.po.model.dict.DictServiceTypes;
import com.simi.service.dict.AdService;
import com.simi.service.dict.DictService;
import com.simi.service.dict.ServiceTypeService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/interface-dict")
public class DictInterface extends BaseController {

	@Autowired
	private DictService dictService;

	@Autowired
	private ServiceTypeService serviceTypeService;

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

		if (cityId > 0) {
			listRegion = dictService.getRegionByCityId(cityId);
		}

		AppResultData<List> result = new AppResultData<List>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, listRegion);

    	return result;
    }

	/**
	 * 检查服务类型名称是否重复
	 * @param request
	 * @param model
	 * @param
	 * @return
	 */
	// @AuthPassport Map<String, Object>
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "check-name-dumplicate", method = RequestMethod.POST)
	public  AppResultData<Boolean> checkName(
			@RequestParam(value = "name", required = true, defaultValue = "") String name,
			@RequestParam(value = "serviceType", required = true, defaultValue = "0") Long serviceType

			) {

		AppResultData<Boolean> result = new AppResultData<Boolean>(
		Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, false);
		DictServiceTypes record = serviceTypeService.selectByNameAndOtherId(name, serviceType);

		if(record != null && record.getId() > 0){
			result.setMsg("名称已经存在");
			result.setData(true);
		}else{
			result.setData(false);
		}
		return result;
	}

}
