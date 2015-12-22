package com.simi.action.app.op;

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
import com.simi.po.model.op.AppCardType;
import com.simi.po.model.op.AppTools;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.op.OpChannel;
import com.simi.po.model.order.Orders;
import com.simi.service.op.AppCardTypeService;
import com.simi.service.op.AppToolsService;
import com.simi.service.op.OpAdService;
import com.simi.service.op.OpChannelService;
import com.meijia.utils.StringUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.order.OrderListVo;
import com.simi.vo.po.AppCardTypeVo;
import com.simi.vo.po.AppToolsVo;

@Controller
@RequestMapping(value = "/app/op")
public class AppCardTypeController extends BaseController {


	@Autowired
	private AppCardTypeService appCardTypeService;

	
	/**
	 * 获得应用中心列表接口
	 * @param appType
	 * @return
	 */
	@RequestMapping(value = "get_appCardType", method = RequestMethod.GET)
	public AppResultData<Object> getAppCardType(
			@RequestParam(value = "app_type", required = false, defaultValue="xcloud") String appType) {
		
		List<AppCardType> appCardType = appCardTypeService.selectByAppType(appType);
         
		List<AppCardTypeVo> vo = new ArrayList<AppCardTypeVo>();
		
		for (AppCardType item : appCardType) {
			
			AppCardTypeVo listVo = new AppCardTypeVo();
			listVo = appCardTypeService.getAppCardTypeVo(item);
			vo.add(listVo);
		}
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, vo);
		return result;
	}
	
}
