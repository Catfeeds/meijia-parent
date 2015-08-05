package com.simi.action.partners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.action.BaseController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.partners.PartnerLinkMan;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.partners.SpiderPartner;
import com.simi.service.partners.PartnersService;
import com.simi.service.partners.SpiderPartnerService;
import com.simi.vo.partners.PartnerServiceTypeVo;
import com.simi.vo.partners.PartnersSearchVo;




/**
 * @description：采集的服务商列表展示和修改
 * @author： kerryg
 * @date:2015年8月5日 
 */
@Controller
@RequestMapping(value = "/spiderPartner")
public class SpiderPartnerController extends BaseController{
	
	@Autowired
	private SpiderPartnerService spiderPartnerService;
	
	@Autowired
	private PartnersService partnersService;

	/**
	 * 服务提供商列表 
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model,
			PartnersSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		PageInfo result = spiderPartnerService.searchVoListPage(searchVo, pageNo, pageSize);

		model.addAttribute("contentModel", result);
		return "partners/spiderPartnerList";
	}
	

	/**
	 * 跳转到编辑服务提供商的页面
	 * @param model
	 * @param id
	 * @return
	 * @throws IOException
	 */
   // @AuthPassport
	@RequestMapping(value = "/spiderPartnerForm", method = { RequestMethod.GET })
	public String spiderPartnerForm(Model model, 
			@RequestParam("id") Long id,HttpServletRequest response)  {
    	
    	if (id == null) {
    		id = 0L;
    	}
    	SpiderPartner spiderPartner = spiderPartnerService.initSpiderPartner();
    	Partners partners = partnersService.iniPartners();
    	
    	if (id != null && id > 0) {
    		spiderPartner = spiderPartnerService.selectByPrimaryKey(id);
    		partners = partnersService.selectBySpiderPartnerId(id);
    		if(partners == null){
    			 partners = partnersService.iniPartners();
    		}
    	}
		model.addAttribute("spiderPartner", spiderPartner);
		model.addAttribute("partners", partners);
		return "partners/spiderPartnerForm";
	}
    
}
