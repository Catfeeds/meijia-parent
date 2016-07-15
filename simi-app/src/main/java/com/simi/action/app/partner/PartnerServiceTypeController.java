package com.simi.action.app.partner;

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
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.service.partners.PartnerRefServiceTypeService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.vo.AppResultData;
import com.simi.vo.partners.PartnersSearchVo;

@Controller
@RequestMapping(value = "/app/partner")
public class PartnerServiceTypeController extends BaseController {

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private PartnerUserService partnerUserService;

	@Autowired
	private PartnersService partnersService;

	@Autowired
	private PartnerRefServiceTypeService partnerRefServiceTypeService;


	/**
	 * 服务大类接口(通过服务人员ID获取本公司的服务大类查询)
	 * 
	 * @param partnerUserId
	 * @param page
	 * @return
	 */

	@RequestMapping(value = "get_partner_service_type_list", method = RequestMethod.GET)
	public AppResultData<Object> list(@RequestParam("partner_user_id") Long partnerUserId) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		PartnerUsers partnerUsers = partnerUserService.selectByUserId(partnerUserId);

		Long partnerId = partnerUsers.getPartnerId();
		
		
		PartnersSearchVo searchVo = new PartnersSearchVo();
		searchVo.setPartnerId(partnerId);
		List<PartnerRefServiceType> list = partnerRefServiceTypeService.selectBySearchVo(searchVo);
		
		if (list.isEmpty()) return result;
		
		List<Long> serviceTypeIds = new ArrayList<Long>();
		for (PartnerRefServiceType item : list) {
			if (!serviceTypeIds.contains(item.getServiceTypeId()))
				serviceTypeIds.add(item.getServiceTypeId());
		}
		
		List<PartnerServiceType> datas = partnerServiceTypeService.selectByIds(serviceTypeIds);
		result.setData(datas);
		return result;
	}	
}
