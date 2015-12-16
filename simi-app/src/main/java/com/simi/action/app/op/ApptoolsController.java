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
import com.simi.po.model.op.AppTools;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.op.OpChannel;
import com.simi.service.op.AppToolsService;
import com.simi.service.op.OpAdService;
import com.simi.service.op.OpChannelService;
import com.meijia.utils.StringUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/op")
public class ApptoolsController extends BaseController {


	@Autowired
	private AppToolsService appToolsService;

	
	/**
	 * 获得应用中心列表接口
	 * @param appType
	 * @return
	 */
	@RequestMapping(value = "get_appTools", method = RequestMethod.GET)
	public AppResultData<Object> getAppTools(
			@RequestParam(value = "app_type", required = false, defaultValue="xcloud") String appType) {
		List<AppTools> appTools = appToolsService.selectByAppType(appType);

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, appTools);
		return result;
	}
	
}
