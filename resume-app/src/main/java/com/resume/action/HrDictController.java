package com.resume.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
}
