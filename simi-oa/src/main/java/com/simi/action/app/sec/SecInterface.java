package com.simi.action.app.sec;

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
import com.simi.po.model.sec.Sec;
import com.simi.service.sec.SecService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/interface-sec")
public class SecInterface extends BaseController {

	@Autowired
	private SecService secService;
	
	/**
	 * 检查秘书名称是否重复
	 * @param request
	 * @param model
	 * @param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "check-name-dumplicate", method = RequestMethod.POST)
	public  AppResultData<Boolean> checkName(
			@RequestParam(value = "username", required = true, defaultValue = "") String username,
			@RequestParam(value = "sec", required = true, defaultValue = "0") Long sec

			) {

		AppResultData<Boolean> result = new AppResultData<Boolean>(
		Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, false);
		Sec record = secService.selectByUserNameAndOtherId(username, sec);
		if(record != null && record.getId() > 0){
			result.setMsg("名称已经存在");
			result.setData(true);
		}else{
			result.setData(false);
		}
		return result;
	}

}
