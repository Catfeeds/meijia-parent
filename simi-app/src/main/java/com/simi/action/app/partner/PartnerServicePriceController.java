package com.simi.action.app.partner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/partner")
public class PartnerServicePriceController extends BaseController {


	
	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;

	
	/**
	 * 获取服务价格详情接口
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "get_partner_service_price_detail", method = RequestMethod.GET)
	public AppResultData<Object> getPartnerServicePriceDetail(Model model,
			@RequestParam("service_price_id") Long servicePriceId) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		PartnerServicePriceDetail record = partnerServicePriceDetailService.selectByServicePriceId(servicePriceId);

	/*	UserEditViewVo vo = usersService.getUserDetail(order.getOrderNo(),
				userId);

		result.setData(vo);*/
		result.setData(record);
		//model.addAttribute("contentModel", result);
		return result;
	}


}
