package com.simi.action.partners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.MeijiaUtil;
import com.simi.action.BaseController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.Users;
import com.simi.service.dict.CityService;
import com.simi.service.dict.RegionService;
import com.simi.service.partners.PartnerRefCityService;
import com.simi.service.partners.PartnerRefRegionService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.TagsService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UsersService;
import com.simi.vo.partners.PartnerFormVo;
import com.simi.vo.partners.PartnerServicePriceListVo;
import com.simi.vo.partners.PartnerUserDetailVo;
import com.simi.vo.partners.PartnerUserSearchVo;


/**
 * @description：
 * @author： kerryg
 * @date:2015年8月11日 
 */
@Controller
@RequestMapping(value = "/partners")
public class PartnerUsersController extends BaseController{

	@Autowired
	private UsersService userService;
	
	@Autowired
	private TagsService tagsService;
	
	@Autowired
	private TagsUsersService tagsUsersService;	
	
	@Autowired
	private PartnersService partnersService;

	@Autowired
	private PartnerUserService partnerUserService;
	
	@Autowired
	private PartnerRefRegionService partnerRefRegionService;
	
	@Autowired
	private PartnerRefCityService partnerRefCityService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private RegionService regionService;

	/**
	 * 服务人员列表 
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/user_list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model,
			@RequestParam("partnerId") Long partnerId,
			PartnerUserSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		
		if (searchVo == null) {
			searchVo = new PartnerUserSearchVo();
		}
		searchVo.setPartnerId(partnerId);
		
		model.addAttribute("searchModel", searchVo);
		
		model.addAttribute("partnerId", partnerId);
		
		String companyName = "";
		if (partnerId > 0L) {
			Partners parnter = partnersService.selectByPrimaryKey(partnerId);
			companyName = parnter.getCompanyName();
		}
		
		model.addAttribute("companyName", companyName);
		
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		PageInfo result = partnerUserService.searchVoListPage(searchVo, pageNo, pageSize);

		model.addAttribute("contentModel", result);
		return "partners/partnerUserList";
	}
	

	/**
	 * 跳转到编辑服务人员的页面
	 * @param model
	 * @param id
	 * @return
	 * @throws IOException
	 */
   // @AuthPassport
	@RequestMapping(value = "/user_form", method = { RequestMethod.GET })
	public String spiderPartnerForm(Model model, HttpServletRequest request,
			@RequestParam("id") Long id, @RequestParam("partnerId") Long partnerId,
			HttpServletRequest response)  {
    	
    	if (id == null) {
    		id = 0L;
    	}
    	
    	PartnerUsers partnerUser = partnerUserService.iniPartnerUsers();
    	Users u = userService.initUsers();
    	Long userId = u.getId();
    	
    	List<Tags> tags = tagsService.selectByTagType((short) 2);
    	List<Tags> userTags = new ArrayList<Tags>();
    	String  tagIds = "";
    	if (id > 0L) {
    		partnerUser = partnerUserService.selectByPrimaryKey(id);
    		userId = partnerUser.getUserId();
    		u = userService.selectByPrimaryKey(userId);
    		
    		List<TagUsers> tagUsers = tagsUsersService.selectByUserId(userId);
    		
    		for (TagUsers item : tagUsers) {
    			tagIds += item.getTagId() + ",";
    		}
    		
    	}
    	
    	Partners partner = partnersService.selectByPrimaryKey(partnerId);
    	
    	PartnerUserDetailVo vo = new PartnerUserDetailVo();
    	vo.setId(id);
    	vo.setPartnerId(partnerId);
    	vo.setCompanyName(partner.getCompanyName());
    	vo.setUserId(u.getId());
    	vo.setIntroduction(u.getIntroduction());
    	vo.setHeadImg(u.getHeadImg().trim());
    	vo.setName(u.getName());
    	vo.setMobile(u.getMobile());
    	vo.setServicePrices(new ArrayList<PartnerServicePriceListVo>());
    	vo.setResponseTime(partnerUser.getResponseTime());
    	vo.setServiceTypeId(partnerUser.getServiceTypeId());
    	vo.setUserTags(userTags);
    	
    	model.addAttribute("contentModel", vo);
    	model.addAttribute("tags", tags);
    	model.addAttribute("tagIds", tagIds);
    	
    	//服务大类
    	List<PartnerServiceType> partnerServiceType =   partnerServiceTypeService.selectByParentId(0L);
    	model.addAttribute("partnerServiceType", partnerServiceType);
    	
		return "partners/partnerUserForm";
	}
    
	
	/**
	 * 添加/修改服务人员
	 *
	 * @param request
	 * @param model
	 * @param partners
	 * @param result
	 * @return
	 */
	//@AuthPassport
	@RequestMapping(value = "/user_form", method = { RequestMethod.POST })
	public String doPartnerForm(HttpServletRequest request, Model model,
			@ModelAttribute("partners") PartnerFormVo partners, 
			BindingResult result) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		
		return "redirect:user_list";
	} 
}
