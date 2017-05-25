package com.resume.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.StringUtil;
import com.resume.po.model.dict.HrDicts;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.service.resume.HrDictsService;
import com.simi.vo.AppResultData;
import com.simi.vo.resume.HrDictSearchVo;

@Controller
@RequestMapping(value = "/app/hrDict")
public class HrDictController extends BaseController {
	
	@Autowired
	private HrDictsService hrDictService;

	@RequestMapping(value = "getParents", method = RequestMethod.GET)
	public AppResultData<Object> getParents(@RequestParam("type") String type) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, null);
		
		
		HrDictSearchVo searchVo = new HrDictSearchVo();
		searchVo.setType(type);
		searchVo.setPid("0");
		
		List<HrDicts> list = hrDictService.selectBySearchVo(searchVo);
		
		result.setData(list);
		return result;
		
	}
	
	@RequestMapping(value = "getByOption", method = RequestMethod.GET)
	public AppResultData<Object> getParentByPid(
			@RequestParam(value = "pids", required = false, defaultValue = "") String pids,
			@RequestParam(value = "type", required = false, defaultValue = "") String type,
			@RequestParam(value = "from_id", required = false, defaultValue = "") Long fromId,
			@RequestParam(value = "not_code", required = false, defaultValue = "") String notCode,
			@RequestParam(value = "order_by_str", required = false, defaultValue = "") String orderByStr
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, new String());
		
		HrDictSearchVo searchVo = new HrDictSearchVo();
		if (!StringUtil.isEmpty(pids)) {
		
			String[] pidAry = StringUtil.convertStrToArray(pids);
			List<String> pidList = new ArrayList<String>();
			for (int i = 0; i < pidAry.length; i++) {
				pidList.add(pidAry[i]);
			}
			searchVo.setPids(pidList);
		}
		
		if (!StringUtil.isEmpty(type)) searchVo.setType(type);
		
		if (fromId != null && fromId > 0L) searchVo.setFromId(fromId);
		
		if (!StringUtil.isEmpty(notCode)) searchVo.setNotCode(notCode);
		
		if (!StringUtil.isEmpty(notCode)) {
			searchVo.setOrderByStr(orderByStr);
		} else {
			searchVo.setOrderByStr("id asc");
		}
		
		List<HrDicts> list = hrDictService.selectBySearchVo(searchVo);
		
		result.setData(list);
		return result;
		
	}
}
