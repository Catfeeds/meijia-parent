package com.simi.action.app.partners;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.partners.Partners;
import com.simi.service.partners.PartnersService;
import com.simi.vo.AppResultData;
import com.simi.vo.partners.PartnersSearchVo;

@Controller
@RequestMapping(value = "/interface-partners")
public class PartnersInterface extends BaseController {


	@Autowired
	private PartnersService partnersService;
	
	/**
	 * 根据团队名称进行排重
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "check-companyName-dumplicate", method = RequestMethod.GET)
    public AppResultData<List> getRegions(
    		//@RequestParam("name") String name,
    	//	@RequestParam(value = "start", required = true, defaultValue = "" )
    		@RequestParam(value = "companyName" , required = true, defaultValue = "") String companyName) {
		
		PartnersSearchVo searchVo = new PartnersSearchVo();
		searchVo.setCompanyName(companyName);
		List<Partners> listPartners = partnersService.selectBySearchVo(searchVo);

		AppResultData<List> result = new AppResultData<List>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, listPartners);

    	return result;
    }


}
