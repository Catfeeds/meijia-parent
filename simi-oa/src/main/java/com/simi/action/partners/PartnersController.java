package com.simi.action.partners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.common.extension.ArrayHelper;
import com.meijia.utils.common.extension.StringHelper;
import com.simi.action.BaseController;
import com.simi.common.Constants;
import com.simi.models.TreeModel;
import com.simi.models.extention.TreeModelExtension;
import com.simi.oa.auth.AccountAuth;
import com.simi.oa.auth.AuthHelper;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictRegion;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerLinkMan;
import com.simi.po.model.partners.PartnerRefCity;
import com.simi.po.model.partners.PartnerRefRegion;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.partners.Partners;
import com.simi.service.ImgService;
import com.simi.service.dict.CityService;
import com.simi.service.dict.RegionService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.partners.PartnerLinkManService;
import com.simi.service.partners.PartnerRefCityService;
import com.simi.service.partners.PartnerRefRegionService;
import com.simi.service.partners.PartnerRefServiceTypeService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.partners.SpiderPartnerService;
import com.simi.vo.OrdersListVo;
import com.simi.vo.partners.PartnerFormVo;
import com.simi.vo.partners.PartnersSearchVo;

/**
 * @description：
 * @author： kerryg
 * @date:2015年8月11日
 */
@Controller
@RequestMapping(value = "/partners")
public class PartnersController extends BaseController {

	@Autowired
	private PartnersService partnersService;

	@Autowired
	private SpiderPartnerService spiderPartnerService;

	@Autowired
	private PartnerLinkManService partnerLinkManService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private PartnerRefServiceTypeService partnerRefServiceTypeService;

	@Autowired
	private PartnerRefRegionService partnerRefRegionService;

	@Autowired
	private PartnerRefCityService partnerRefCityService;

	@Autowired
	private CityService cityService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private PartnerUserService partnerUserService;

	@Autowired
	private OrderQueryService orderQueryService;
	
	@Autowired
	private ImgService imgService;

	/**
	 * 服务提供商列表
	 * 
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, PartnersSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result = partnersService.selectByListPage(searchVo, pageNo, pageSize);

		model.addAttribute("contentModel", result);
		return "partners/partnersList";
	}

	@AuthPassport
	@RequestMapping(value = "/partnerOrderlist", method = { RequestMethod.GET })
	public String partnerOrderlist(HttpServletRequest request, Model model,
	// ,OrderSearchVo searchVo,
			@RequestParam(value = "partnerId", required = false) Long partnerId) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		// model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		// 分页
		PageHelper.startPage(pageNo, pageSize);

		List<PartnerUsers> puList = partnerUserService.selectByPartnerId(partnerId);
		// 获得userId列表
		List<Long> partnerUserIdList = new ArrayList<Long>();
		for (int i = 0; i < puList.size(); i++) {

			PartnerUsers partnerUsers = puList.get(i);
			partnerUserIdList.add(partnerUsers.getUserId());

		}
		/*
		 * if (partnerUserIdList.size() < 2) {
		 * return "partners/partnerOrderList";
		 * }
		 */
		List<Orders> orderList = orderQueryService.selectByUserIdsListPageList(partnerUserIdList, pageNo, pageSize);

		Orders orders = null;
		for (int i = 0; i < orderList.size(); i++) {
			orders = orderList.get(i);
			OrdersListVo ordersListVo = orderQueryService.completeVo(orders);
			orderList.set(i, ordersListVo);
		}
		PageInfo result = new PageInfo(orderList);

		model.addAttribute("contentModel", result);
		// model.addAttribute("oaOrderSearchVoModel", searchVo);
		return "partners/partnerOrderList";
	}

	/**
	 * 跳转到新增或修改服务提供商的页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/partnerAddNewForm", method = { RequestMethod.GET })
	public String spiderPartnerForm(Model model, @RequestParam("partnerId") Long partnerId, HttpServletRequest request, HttpServletRequest response) {
		// Long partnerId = Long.valueOf(request.getParameter("partnerId"));
		Partners partners = partnersService.iniPartners();
		PartnerFormVo partnerFormVo = new PartnerFormVo();
		if (partnerId != null && partnerId > 0) {
			partners = partnersService.selectByPrimaryKey(partnerId);
		}
		try {
			// BeanUtils.copyProperties(partnerFormVo, partners);
			BeanUtilsExp.copyPropertiesIgnoreNull(partners, partnerFormVo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 获得服务商的联系人
		List<PartnerLinkMan> linkMan = new ArrayList<PartnerLinkMan>();
		linkMan = partnerLinkManService.selectByPartnerId(partnerId);
		// 保证至少有一个，默认为空的列表
		if (linkMan == null || linkMan.size() == 0) {
			PartnerLinkMan linkManVo = partnerLinkManService.initPartnerLinkMan();
			linkMan.add(linkManVo);
		}
		partnerFormVo.setLinkMan(linkMan);
		/**
		 * 包装partner为Vo
		 */
		PartnerFormVo partnerFormVoItem = partnersService.selectPartnerFormVoByPartnerFormVo(partnerFormVo);

		/**
		 * 获得提供商所关联的服务类型
		 */
		List<Long> checkedPartnerTypeIds = new ArrayList<Long>();
		List<Integer> checkedPartnerTypeIntegers = new ArrayList<Integer>();
		if (partnerFormVoItem.getChildList() != null) {
			List<PartnerServiceType> list = partnerFormVoItem.getChildList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				PartnerServiceType partnerServiceType = (PartnerServiceType) iterator.next();
				if (partnerServiceType != null) {
					checkedPartnerTypeIds.add(partnerServiceType.getId());
					checkedPartnerTypeIntegers.add(partnerServiceType.getId().intValue());
				}
			}
		}
		
		
		Long[] checkedAuthorityIdsArray = new Long[checkedPartnerTypeIds.size()];
		checkedPartnerTypeIds.toArray(checkedAuthorityIdsArray);
		partnerFormVo.setPartnerTypeIds(checkedAuthorityIdsArray);
		
		if (!model.containsAttribute("partners")) {
			
			model.addAttribute("partners", partnerFormVo);
		}
		String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);

		List<TreeModel> children = TreeModelExtension.ToTreeModels(partnerServiceTypeService.listChain(new ArrayList<Long>()), null,
				checkedPartnerTypeIntegers, StringHelper.toIntegerList(expanded, ","));
		List<TreeModel> treeModels = new ArrayList<TreeModel>(Arrays.asList(new TreeModel(null, null, "根节点", false, false, false, children)));
		model.addAttribute("treeDataSource", JSONArray.fromObject(treeModels, new JsonConfig()).toString());

		/**
		 * 获得服务商服务类别大类
		 */
		PartnersSearchVo searchVo = new PartnersSearchVo();
		searchVo.setPartnerId(partnerFormVo.getPartnerId());
		searchVo.setParentId(0L);
		List<PartnerRefServiceType> listBig = partnerRefServiceTypeService.selectBySearchVo(searchVo);
		List<String> bigServiceTypeName = new ArrayList<String>();
		for (Iterator iterator = listBig.iterator(); iterator.hasNext();) {
			PartnerRefServiceType partnerRefServiceType = (PartnerRefServiceType) iterator.next();
			bigServiceTypeName.add(partnerRefServiceType.getName());
		}

		/**
		 * 获得服务商服务类别小类
		 */
		List<String> subServiceTypeName = new ArrayList<String>();
		List<PartnerRefServiceType> listSub = partnerRefServiceTypeService.selectBySearchVo(searchVo);
		for (Iterator iterator = listSub.iterator(); iterator.hasNext();) {
			PartnerRefServiceType partnerRefServiceType = (PartnerRefServiceType) iterator.next();
			subServiceTypeName.add(partnerRefServiceType.getName());

		}

		/**
		 * 获取提供商对应的城市
		 */
		String cityId = "";
		List<PartnerRefCity> partnerRefCity = partnerRefCityService.selectByPartnerId(partnerFormVo.getPartnerId());
		if (partnerRefCity != null && partnerRefCity.size() > 0) {
			for (int i = 0; i < partnerRefCity.size(); i++) {
				cityId += partnerRefCity.get(i).getCityId().toString();
				if (i != (partnerRefCity.size() - 1)) {
					cityId += ",";
				}
			}
		}
		partnerFormVo.setPartnerCityId(cityId);
		;

		/**
		 * 获取北,上,广,深等城市和地区字典信息
		 */
		List<Long> cityIds = new ArrayList<Long>();
		cityIds.add(2L);
		cityIds.add(3L);
		cityIds.add(74L);
		cityIds.add(200L);
		List<DictCity> dictCityList = cityService.selectByCityIds(cityIds);
		List<DictRegion> dictReigionList = regionService.selectByCityIds(cityIds);

		model.addAttribute("bigServiceTypeName", bigServiceTypeName);
		// model.addAttribute("subServiceTypeName", subServiceTypeName);

		model.addAttribute("dictCityList", dictCityList);
		model.addAttribute("dictReigionList", dictReigionList);
		model.addAttribute("partners", partnerFormVo);
		return "partners/partnerAddNewForm";
	}

	/**
	 * 新增或修改服务提供商
	 *
	 * @param request
	 * @param model
	 * @param partners
	 * @param result
	 * @return
	 * @throws IOException
	 */
	@AuthPassport
	@RequestMapping(value = "/savePartnerAddNewForm", method = { RequestMethod.POST })
	public String doPartnerForm(HttpServletRequest request, Model model, @ModelAttribute("partners") PartnerFormVo partners, BindingResult result, HttpServletRequest response)
			throws IOException {
		// String registerTime = request.getParameter("registerTime");
		String registerTime = request.getParameter("registerTime");

		Long partnerId = partners.getPartnerId();
				  
		Map<String, String> fileMaps = imgService.multiFileUpLoad(request);
		if (fileMaps.get("companyDescImg") != null) {
			String imgUrl = fileMaps.get("companyDescImg");
			
			if (!StringUtil.isEmpty(imgUrl)) {
				imgUrl = ImgServerUtil.getImgSize(imgUrl, "100", "100");
	
				partners.setCompanyDescImg(imgUrl);
			}
		}

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Partners partnersItem = partnersService.iniPartners();
		if (partnerId > 0L) {
			
			partnersItem = partnersService.selectByPrimaryKey(partnerId);
			
			BeanUtilsExp.copyPropertiesIgnoreNull(partners, partnersItem);
			
			partnersItem.setPartnerId(partners.getPartnerId());
			// 注册时间
			partnersItem.setRegisterTime(DateUtil.parse(registerTime));
			partnersItem.setUpdateTime(TimeStampUtil.getNow() / 1000);

			partnersService.updateByPrimaryKeySelective(partnersItem);
			// partnersService.insertSelective(partnersItem);
		} else {
			try {
				BeanUtilsExp.copyPropertiesIgnoreNull(partners, partnersItem);
				// BeanUtils.copyProperties(partnersItem, partners);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//需要检测库里是否已经有相同的名称，如果有则不能更新
			PartnersSearchVo searchVo = new PartnersSearchVo();
			searchVo.setCompanyName(partners.getCompanyName());
			List<Partners> listExist = partnersService.selectBySearchVo(searchVo);
			if (!listExist.isEmpty()) {
				result.addError(new FieldError("partners", "companyName", "公司名称已存在."));
				return spiderPartnerForm(model, partnerId, request, response);
			}
			
			partnersItem.setServiceArea("");
			partnersItem.setServiceType("");
			partnersItem.setAdminId(accountAuth.getId());
			partnersItem.setCompanyLogo("");
			partnersItem.setRemark("");
			if (!StringUtil.isEmpty(registerTime)) {
				partnersItem.setRegisterTime(DateUtil.parse(registerTime));
			}
			// partnersItem.setRegisterTime(DateUtil.parse(registerTime));
			partnersItem.setStatusRemark("");
			partnersItem.setAddTime(TimeStampUtil.getNow() / 1000);
			partnersItem.setUpdateTime(TimeStampUtil.getNow() / 1000);

			partnersService.insertSelective(partnersItem);
		}

		/**
		 * 保存服务商选中的服务类型
		 */
		partnersService.savePartnerToPartnerType(partnersItem.getPartnerId(), ArrayHelper.removeArrayLongItem(partners.getPartnerTypeIds(), new Integer(0)));

		/**
		 * 操作partner_ref_region表更新
		 */
		// 1、先删除原来的数据
		if (partnerId > 0L) {
			partnerRefRegionService.deleteByPartnerId(partnersItem.getPartnerId());
		}
		String tempRegionId = request.getParameter("regionIdStr");
		if (!StringUtil.isEmpty(tempRegionId)) {
			Long regionIdLong = 0L;
			String regionId[] = tempRegionId.split(",");
			// 循环批量插入
			if (regionId != null) {
				for (int i = 0; i < regionId.length; i++) {
					if (!regionId[i].equals(",")) {
						regionIdLong = Long.valueOf(regionId[i]);
						PartnerRefRegion partnerRefRegion = partnerRefRegionService.initPartnerRefRegion();
						partnerRefRegion.setPartnerId(partnersItem.getPartnerId());
						partnerRefRegion.setRegionId(regionIdLong);
						partnerRefRegionService.insertSelective(partnerRefRegion);
					}
				}
			}
		}
		/**
		 * 操作partner_ref_city表更新
		 */
		// 1、先删除原来的数据
		if (partnerId > 0L) {
			partnerRefCityService.deleteByPartnerId(partnersItem.getPartnerId());
		}
		String tempCityId = request.getParameter("cityIdStr");
		if (!StringUtil.isEmpty(tempCityId)) {
			String cityId[] = tempCityId.split(",");
			Long cityIdLong = 0L;
			// 循环批量插入
			if (cityId != null) {
				for (int i = 0; i < cityId.length; i++) {
					if (!cityId[i].equals(",")) {
						cityIdLong = Long.valueOf(cityId[i]);
						PartnerRefCity partnerRefCity = partnerRefCityService.initPartnerRefCity();
						partnerRefCity.setPartnerId(partnersItem.getPartnerId());
						partnerRefCity.setCityId(cityIdLong);
						partnerRefCityService.insertSelective(partnerRefCity);
					}
				}
			}
		}
		/**
		 * 操作partnerLinkMan表
		 */
		// 第一步先删除
		if (partnerId > 0L) {
			partnerLinkManService.deleteByPartnerId(partnersItem.getPartnerId());
		}
		String linkMan[] = request.getParameterValues("linkMan");
		String linkMobile[] = request.getParameterValues("linkMobile");
		String linkTel[] = request.getParameterValues("linkTel");
		String linkJob[] = request.getParameterValues("linkJob");

		String linkManItem = "";
		String linkMobileItem = "";
		String linkTelItem = "";
		String linkJobItem = "";
		if (linkMobile != null) {
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
				record.setPartnerId(partnersItem.getPartnerId());
				record.setLinkMan(linkManItem);
				record.setLinkMobile(linkMobileItem);
				record.setLinkTel(linkTelItem);
				record.setLinkJob(linkJobItem);
				partnerLinkManService.insertSelective(record);
			}
		}
		return "redirect:list";
	}

}
