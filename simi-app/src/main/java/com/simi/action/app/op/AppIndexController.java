package com.simi.action.app.op;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.op.AppIndex;
import com.simi.service.op.AppIndexService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/op")
public class AppIndexController extends BaseController {


	@Autowired
	private AppIndexService appIndexService;

	
	/**
	 * 获得导航列表接口
	 * @param appType
	 * @return
	 */
	@RequestMapping(value = "get_appIndexList", method = RequestMethod.GET)
	public AppResultData<Object> getAppIndexList(
			@RequestParam(value = "app_type", required = false, defaultValue="xcloud") String appType) {
		
		List<AppIndex> appIndex = appIndexService.selectByAppType(appType);
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, appIndex);
		
		return result;
	}
	
}
