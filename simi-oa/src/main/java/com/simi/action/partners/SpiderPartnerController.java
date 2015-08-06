package com.simi.action.partners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.BeanUtils;
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
import com.meijia.utils.StringUtil;
import com.meijia.utils.common.extension.ArrayHelper;
import com.meijia.utils.common.extension.StringHelper;
import com.simi.action.BaseController;
import com.simi.models.TreeModel;
import com.simi.models.extention.TreeModelExtension;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.partners.PartnerLinkMan;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.partners.SpiderPartner;
import com.simi.service.partners.PartnerLinkManService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnersService;
import com.simi.service.partners.SpiderPartnerService;
import com.simi.vo.partners.PartnerFormVo;
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
	
	@Autowired
	private PartnerLinkManService partnerLinkManService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

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
	public String spiderPartnerForm(Model model, HttpServletRequest request,
			@RequestParam("id") Long id,HttpServletRequest response)  {
    	
    	if (id == null) {
    		id = 0L;
    	}
    	SpiderPartner spiderPartner = spiderPartnerService.initSpiderPartner();
    	Partners partners = partnersService.iniPartners();
    	PartnerFormVo partnerFormVo = new PartnerFormVo();
    	if (id != null && id > 0) {
    		spiderPartner = spiderPartnerService.selectByPrimaryKey(id);
    		partners = partnersService.selectBySpiderPartnerId(id);
    		if(partners == null){
    			 partners = partnersService.iniPartners();
    		}
    	}
		try {
			BeanUtils.copyProperties(partnerFormVo, partners);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//获得服务商的联系人
		List<PartnerLinkMan> linkMan = partnerLinkManService.selectByPartnerId(partners.getPartnerId());
		//保证至少有一个，默认为空的列表
		if (linkMan == null || linkMan.size() == 0) {
			PartnerLinkMan linkManVo = partnerLinkManService.initPartnerLinkMan();
			linkMan.add(linkManVo);
		}
		partnerFormVo.setLinkMan(linkMan);
    	partners.setSpiderPartnerId(spiderPartner.getSpiderPartnerId());
    	
    	
    	
    	PartnerFormVo partnerFormVoItem  = partnersService.selectPartnerFormVoByPartnerFormVo(partnerFormVo);
    	
    	List<Long> checkedPartnerTypeIds = new ArrayList<Long>();
		List<Integer> checkedPartnerTypeIntegers = new ArrayList<Integer>();

		if(partnerFormVoItem.getChildList()!=null){
			List<PartnerServiceType> list = partnerFormVoItem.getChildList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				PartnerServiceType partnerServiceType = (PartnerServiceType) iterator.next();
				if(partnerServiceType !=null){
					checkedPartnerTypeIds.add(partnerServiceType.getId());
					checkedPartnerTypeIntegers.add(partnerServiceType.getId().intValue());
				}
			}
		}
		
	
		if(!model.containsAttribute("partners")){
			Long[] checkedAuthorityIdsArray=new Long[checkedPartnerTypeIds.size()];
			checkedPartnerTypeIds.toArray(checkedAuthorityIdsArray);
			partnerFormVo.setPartnerTypeIds(checkedAuthorityIdsArray);
			model.addAttribute("partners", partnerFormVo);
		}

    	
    	String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
		List<TreeModel> children=TreeModelExtension.ToTreeModels(partnerServiceTypeService.listChain(), null, checkedPartnerTypeIntegers, StringHelper.toIntegerList( expanded, ","));
		List<TreeModel> treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel(null,null,"根节点",false,false,false,children)));
		model.addAttribute("treeDataSource", JSONArray .fromObject(treeModels, new JsonConfig()).toString());
		
		model.addAttribute("spiderPartner", spiderPartner);
		model.addAttribute("partners", partnerFormVo);
		return "partners/spiderPartnerForm";
	}
    
	
	/**
	 * 添加服务提供商
	 *
	 * @param request
	 * @param model
	 * @param partners
	 * @param result
	 * @return
	 */
	//@AuthPassport
	@RequestMapping(value = "/spiderPartnerForm", method = { RequestMethod.POST })
	public String doPartnerForm(HttpServletRequest request, Model model,
			@ModelAttribute("partners") PartnerFormVo partners, 
			BindingResult result) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		Long partnerId = partners.getPartnerId();
		SpiderPartner spiderPartner = spiderPartnerService.selectByPrimaryKey(partners.getSpiderPartnerId());
		
		Partners partnersItem = partnersService.iniPartners();
    	if (partnerId == null) {
    		partnerId = 0L;
    	}
    	
		if (partnerId != null && partnerId > 0) {
			partnersService.updateByPrimaryKeySelective(partners);
		} else {
			try {
				BeanUtils.copyProperties(partnersItem, spiderPartner);
			} catch (Exception e) {
				e.printStackTrace();
			}
			partnersItem.setShortName(partners.getShortName());
			partnersItem.setCompanySize(partners.getCompanySize());
			partnersItem.setIsDoor(partners.getIsDoor());
			partnersItem.setKeywords(partners.getKeywords());
			partnersItem.setStatus(partners.getStatus());
			partnersItem.setBusinessDesc(partners.getBusinessDesc());
			partnersItem.setWeixin(partners.getWeixin());
			partnersItem.setQq(partners.getQq());
			partners.setEmail(partners.getEmail());
			partners.setFax(partners.getFax());
			partnersItem.setPayType(partners.getPayType());
			partnersItem.setDiscout(partners.getDiscout());
			partnersService.insertSelective(partnersItem);
		}
		

		partnersService.savePartnerToPartnerType(partnerId, ArrayHelper.removeArrayLongItem(partners.getPartnerTypeIds(), new Integer(0)));
		//操作partnerLinkMan表
				//第一步先删除
				partnerLinkManService.deleteByPartnerId(partnerId);
				//根据获得的值再同步更新  
				String linkMan[] = request.getParameterValues("linkMan");
				String linkMobile[] = request.getParameterValues("linkMobile");
				String linkTel[] = request.getParameterValues("linkTel");
				String linkJob[] = request.getParameterValues("linkJob");
				
				String linkManItem = "";
				String linkMobileItem = "";
				String linkTelItem = "";
				String linkJobItem = "";
				for (int i = 0; i < linkMobile.length; i++) {
					PartnerLinkMan record = partnerLinkManService.initPartnerLinkMan();
					
					linkManItem = linkMan[i];
					linkMobileItem = linkMobile[i];
					if (StringUtil.isEmpty(linkManItem) || StringUtil.isEmpty(linkMobileItem)) {
						continue;
					}
					
					if (!StringUtil.isEmpty(linkTel[i])) {
						linkTelItem = linkTel[i];
					}
					
					if (!StringUtil.isEmpty(linkJob[i])) {
						linkJobItem = linkJob[i];
					}			
					
					record.setPartnerId(partnerId);
					record.setLinkMan(linkManItem);
					record.setLinkMobile(linkMobileItem);
					record.setLinkTel(linkTelItem);
					record.setLinkJob(linkJobItem);
					partnerLinkManService.insertSelective(record);
				}
		return "redirect:list";
	}    

	
}
