package com.simi.action.partners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.MobileUtil;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.BaseController;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServicePrice;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.record.RecordRates;
import com.simi.po.model.total.TotalHit;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.Users;
import com.simi.service.ImgService;
import com.simi.service.partners.PartnerRefCityService;
import com.simi.service.partners.PartnerRefRegionService;
import com.simi.service.partners.PartnerRefServiceTypeService;
import com.simi.service.partners.PartnerServicePriceService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.record.RecordRatesService;
import com.simi.service.total.TotalHitService;
import com.simi.service.user.TagsService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UsersService;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerServicePriceSearchVo;
import com.simi.vo.partners.PartnerUserVo;
import com.simi.vo.partners.PartnerUserSearchVo;
import com.simi.vo.partners.PartnersSearchVo;
import com.simi.vo.record.RecordRateSearchVo;
import com.simi.vo.record.RecordRateVo;
import com.simi.vo.total.TotalHitSearchVo;

/**
 * @description：
 * @author： kerryg
 * @date:2015年8月11日
 */
@Controller
@RequestMapping(value = "/partners")
public class PartnerUsersController extends BaseController {

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
	private PartnerRefServiceTypeService partnerRefServiceTypeService;

	@Autowired
	private PartnerServicePriceService partnerServicePriceService;

	@Autowired
	private RecordRatesService recordRatesService;
	
	@Autowired
	private ImgService imgService;
	
	@Autowired
	private TotalHitService totalHitService;

	/**
	 * 服务人员列表
	 * 
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/user_list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, @RequestParam("partnerId") Long partnerId, PartnerUserSearchVo searchVo) {
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

		PageInfo result = partnerUserService.selectByListPage(searchVo, pageNo, pageSize);

		model.addAttribute("contentModel", result);
		return "partners/partnerUserList";
	}

	/**
	 * 跳转到编辑服务人员的页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws IOException
	 */
	@AuthPassport
	@RequestMapping(value = "/user_form", method = { RequestMethod.GET })
	public String userForm(Model model, HttpServletRequest request, @RequestParam("id") Long id, @RequestParam("partnerId") Long partnerId,
			HttpServletRequest response) throws JsonParseException, JsonMappingException, IOException {

		if (id == null) {
			id = 0L;
		}

		PartnerUsers partnerUser = partnerUserService.iniPartnerUsers();
		Users u = userService.initUsers();
		Long userId = u.getId();

		List<Tags> tags = tagsService.selectByTagType((short) 2);
		List<Tags> userTags = new ArrayList<Tags>();
		String tagIds = "";
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

		PartnerUserVo vo = new PartnerUserVo();
		vo.setId(id);
		vo.setPartnerId(partnerId);
		vo.setCompanyName(partner.getCompanyName());
		vo.setUserId(u.getId());
		vo.setIntroduction(u.getIntroduction());
		vo.setHeadImg(userService.getHeadImg(u));
		vo.setName(u.getName());
		vo.setMobile(u.getMobile());
		vo.setResponseTime(partnerUser.getResponseTime());
		vo.setServiceTypeId(partnerUser.getServiceTypeId());
		vo.setUserTags(userTags);
		vo.setProvinceId(partnerUser.getProvinceId());
		vo.setCityId(partnerUser.getCityId());
		vo.setRegionId(partnerUser.getRegionId());
		vo.setWeightType(partnerUser.getWeightType());
		vo.setWeightNo(partnerUser.getWeightNo());
		vo.setTotalOrder(partnerUser.getTotalOrder());
		vo.setTotalRate(partnerUser.getTotalRate());
		vo.setTotalRateResponse(partnerUser.getTotalRateResponse());
		vo.setTotalRateAttitude(partnerUser.getTotalRateAttitude());
		vo.setTotalRateMajor(partnerUser.getTotalRateMajor());

		model.addAttribute("contentModel", vo);
		model.addAttribute("tags", tags);
		model.addAttribute("tagIds", tagIds);

		// 服务大类，该团队的服务大类
		List<PartnerServiceType> serviceTypes = new ArrayList<PartnerServiceType>();

		PartnersSearchVo searchVo = new PartnersSearchVo();
		searchVo.setPartnerId(partnerId);
		searchVo.setParentId(0L);
		List<PartnerRefServiceType> partnerRefServiceType = partnerRefServiceTypeService.selectBySearchVo(searchVo);

		if (!partnerRefServiceType.isEmpty()) {
			List<Long> serviceTypeIds = new ArrayList<Long>();

			for (PartnerRefServiceType item : partnerRefServiceType) {
				if (!serviceTypeIds.contains(item.getServiceTypeId())) {
					serviceTypeIds.add(item.getServiceTypeId());
				}
			}
			
			PartnerServiceTypeSearchVo searchVo1 = new PartnerServiceTypeSearchVo();
			searchVo1.setServiceTypeIds(serviceTypeIds);
			serviceTypes  = partnerServiceTypeService.selectBySearchVo(searchVo1);
		}

		model.addAttribute("partnerServiceType", serviceTypes);

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
	 * @throws IOException
	 */
	@AuthPassport
	@RequestMapping(value = "/user_form", method = { RequestMethod.POST })
	public String doPartnerForm(HttpServletRequest request, Model model, @ModelAttribute("contentModel") PartnerUserVo partnerUserVo,
			@RequestParam("imgUrlFile") MultipartFile file, BindingResult result) throws IOException {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		Long id = partnerUserVo.getId();
		Long userId = partnerUserVo.getUserId();
		Long partnerId = partnerUserVo.getPartnerId();
		Short responseTime = partnerUserVo.getResponseTime();
		Long serviceTypeId = partnerUserVo.getServiceTypeId();
		String mobile = partnerUserVo.getMobile();
		String name = partnerUserVo.getName();

		Users u = userService.selectByMobile(mobile);

		// 创建新用户
		if (u == null) {
			u = userService.genUser(mobile, name, "", (short) 2, Constants.USER_TYPE_2, partnerUserVo.getIntroduction());
			userId = u.getId();
		} else {
			if (!u.getName().equals(name)) {
				u.setName(name);
			}
			userId = u.getId();
		}

		u.setIntroduction(partnerUserVo.getIntroduction());

		u.setUserType((short) 2);
		if (serviceTypeId.equals(75L)) {
			u.setUserType((short) 1);
		}

		// u.setIsApproval((short) 2);
		// 更新头像
		
		// 处理 多文件 上传
		Map<String, String> fileMaps = imgService.multiFileUpLoad(request);
		if (fileMaps.get("imgUrlFile") != null) {
			String imgUrl = fileMaps.get("imgUrlFile");
			
			if (!StringUtil.isEmpty(imgUrl)) u.setHeadImg(imgUrl);
		}

		userService.updateByPrimaryKeySelective(u);

		// 更新服务商用户表
		PartnerUsers partnerUser = partnerUserService.iniPartnerUsers();
		if (id > 0L) {
			partnerUser = partnerUserService.selectByPrimaryKey(id);
		}

		partnerUser.setPartnerId(partnerId);
		partnerUser.setUserId(userId);
		partnerUser.setResponseTime(responseTime);
		partnerUser.setServiceTypeId(serviceTypeId);
		partnerUser.setPartnerId(partnerId);
		partnerUser.setProvinceId(partnerUserVo.getProvinceId());
		partnerUser.setCityId(partnerUserVo.getCityId());
		partnerUser.setRegionId(partnerUserVo.getRegionId());
		partnerUser.setWeightNo(partnerUserVo.getWeightNo());
		partnerUser.setWeightType(partnerUserVo.getWeightType());
		if (id > 0L) {
			partnerUserService.updateByPrimaryKey(partnerUser);
		} else {
			partnerUser.setId(0L);
			partnerUserService.insert(partnerUser);
		}

		// 处理标签
		String tagIds = request.getParameter("tagIds");
		if (!StringUtil.isEmpty(tagIds)) {
			tagsUsersService.deleteByUserId(userId);
			String[] tagList = StringUtil.convertStrToArray(tagIds);

			for (int i = 0; i < tagList.length; i++) {
				if (StringUtil.isEmpty(tagList[i]))
					continue;

				TagUsers record = new TagUsers();
				record.setId(0L);
				record.setUserId(userId);
				record.setTagId(Long.valueOf(tagList[i]));
				record.setAddTime(TimeStampUtil.getNowSecond());
				tagsUsersService.insert(record);
			}
		}

		return "redirect:user_list?partnerId=" + partnerId;
	}

	// todo商品列表
	@AuthPassport
	@RequestMapping(value = "/partner_service_price_list", method = { RequestMethod.GET })
	public String partnerServicelist(HttpServletRequest request, Model model, 
			@RequestParam("partner_id") Long partnerId,
			@RequestParam("service_type_id") Long serviceTypeId, 
			@RequestParam("user_id") Long userId, PartnerServicePriceSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		if (searchVo == null) {
			searchVo = new PartnerServicePriceSearchVo();
		}

		searchVo.setPartnerId(partnerId);
		searchVo.setUserId(userId);
		searchVo.setServiceTypeId(serviceTypeId);

		model.addAttribute("searchModel", searchVo);

		model.addAttribute("partnerId", partnerId);

		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result = partnerServicePriceService.selectByListPage(searchVo, pageNo, pageSize);
		model.addAttribute("user_id", userId);
		model.addAttribute("partner_id", partnerId);
		model.addAttribute("service_type_id", serviceTypeId);
		model.addAttribute("contentModel", result);
		return "partners/partnerStorePriceList";
	}

	// todo
	// 商品添加
	@AuthPassport
	@RequestMapping(value = "/partner_service_price_form", method = { RequestMethod.GET })
	public String partnerPrice(Model model, HttpServletRequest request, 
			@RequestParam("service_type_id") Long serviceTypeId,
			@RequestParam("service_price_id") Long servicePriceId, 
			@RequestParam("partner_id") Long partnerId, 
			@RequestParam("user_id") Long userId,
			HttpServletRequest response) throws JsonParseException, JsonMappingException, IOException {


		
		PartnerServicePrice servicePrice = partnerServicePriceService.initPartnerServicePrice();
		if (!servicePriceId.equals(0L)) {
			servicePrice = partnerServicePriceService.selectByPrimaryKey(servicePriceId);
		}
		
		servicePrice.setUserId(userId);
		servicePrice.setServiceTypeId(serviceTypeId);
		servicePrice.setPartnerId(partnerId);
		servicePrice.setServicePriceId(servicePriceId);
		model.addAttribute("contentModel", servicePrice);

		// 视频播放文章内容表单 品类 = 306
		if (serviceTypeId.equals(306L)) {
			
			//读取二级品类，当做频道来使用.
			PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
			searchVo.setParentId(serviceTypeId);
			List<PartnerServiceType> channelList = partnerServiceTypeService.selectBySearchVo(searchVo);
			
			model.addAttribute("channelList", channelList);
			return "partners/partnerStoreVideoForm";
		} else {
			return "partners/partnerStorePriceForm";
		}
	}

	@AuthPassport
	@RequestMapping(value = "/partner_service_price_form", method = { RequestMethod.POST })
	public String partnerPriceAdd(Model model, HttpServletRequest request, @ModelAttribute("contentModel") PartnerServicePrice vo,
			@RequestParam(value = "imgUrlFile", required = false) MultipartFile file,  BindingResult result) throws IOException {
		
		Long partnerId = vo.getPartnerId();
		Long serviceTypeId = vo.getServiceTypeId();
		Long servicePriceId = vo.getServicePriceId();
	
		PartnerServicePrice record = partnerServicePriceService.initPartnerServicePrice();
		
		if (servicePriceId > 0L) {
			record = partnerServicePriceService.selectByPrimaryKey(servicePriceId);
		}

		
		record.setServicePriceId(servicePriceId);
		record.setServiceTypeId(serviceTypeId);
		record.setPartnerId(partnerId);
		record.setServiceTitle(vo.getServiceTitle());
		record.setUserId(vo.getUserId());
		record.setName(vo.getName());
		record.setPrice(vo.getPrice());
		record.setDisPrice(vo.getDisPrice());
		record.setOrderType(vo.getOrderType());
		record.setOrderDuration(vo.getOrderDuration());
		record.setIsAddr(vo.getIsAddr());
		record.setContentStandard(vo.getContentStandard());
		record.setContentDesc(vo.getContentDesc());
		record.setContentFlow(vo.getContentFlow());
		record.setVideoUrl(vo.getVideoUrl());
		
		record.setCategory(vo.getCategory());
		record.setAction(vo.getAction());
		record.setParams(vo.getParams());
		record.setGotoUrl(vo.getGotoUrl());
		record.setTags(vo.getTags());
		
		
		record.setExtendId(vo.getExtendId());
		
		
		// 处理 多文件 上传
		Map<String, String> fileMaps = imgService.multiFileUpLoad(request);
		if (fileMaps.get("imgUrlFile") != null) {
			String imgUrl = fileMaps.get("imgUrlFile");
			
			if (!StringUtil.isEmpty(imgUrl)) record.setImgUrl(imgUrl);
		}
		
		if (servicePriceId > 0L) {
			partnerServicePriceService.updateByPrimaryKeySelective(record);
		} else {
			partnerServicePriceService.insert(record);
			
			//对于视频大类，新增的情况，需要增加阅读数起步数量为100以上的随机值.
			servicePriceId = record.getServicePriceId();
			
			int total = 0;
			TotalHit totalHit = totalHitService.initTotalHit();
			TotalHitSearchVo searchVo1 = new TotalHitSearchVo();
			searchVo1.setLinkType(Constants.TOTAL_HIT_LINK_TYPE_SERVICE_PRICE);
			searchVo1.setLinkId(servicePriceId);
			List<TotalHit> totalHits = totalHitService.selectBySearchVo(searchVo1);
			if (!totalHits.isEmpty()) {
				totalHit = totalHits.get(0);
			}

			totalHit.setLinkType(Constants.TOTAL_HIT_LINK_TYPE_SERVICE_PRICE);
			totalHit.setLinkId(servicePriceId);

//			total = record.getTotal() + 1;
			total = 130 + Integer.parseInt(RandomUtil.randomNumber(1));
			totalHit.setTotal(total);
			totalHit.setAddTime(TimeStampUtil.getNowSecond());
			if (totalHit.getId() > 0L) {
				totalHitService.updateByPrimaryKeySelective(totalHit);
			} else {
				totalHitService.insertSelective(totalHit);
			}
			
		}

		return "redirect:/partners/partner_service_price_list?partner_id=" + partnerId + "&user_id=" + vo.getUserId() + "&service_type_id=" + serviceTypeId;

	}

	/**
	 * 跳转到编辑服务人员的页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws IOException
	 */
	@AuthPassport
	@RequestMapping(value = "/rateform", method = { RequestMethod.GET })
	public String rateForm(Model model, HttpServletRequest request, @RequestParam("id") Long id, @RequestParam("partnerId") Long partnerId,
			HttpServletRequest response) throws JsonParseException, JsonMappingException, IOException {

		PartnerUsers partnerUser = partnerUserService.selectByPrimaryKey(id);
		Long userId = partnerUser.getUserId();
		Users u = userService.selectByPrimaryKey(userId);

		List<Tags> tags = tagsService.selectByTagType((short) 2);
		List<Tags> userTags = new ArrayList<Tags>();
		String tagIds = "";

		List<TagUsers> tagUsers = tagsUsersService.selectByUserId(userId);

		for (TagUsers item : tagUsers) {
			tagIds += item.getTagId() + ",";
		}

		Partners partner = partnersService.selectByPrimaryKey(partnerId);

		PartnerUserVo vo = new PartnerUserVo();

		BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);
		BeanUtilsExp.copyPropertiesIgnoreNull(partner, vo);

		vo.setId(id);
		vo.setPartnerId(partnerId);
		vo.setCompanyName(partner.getCompanyName());
		vo.setUserId(userId);
		vo.setUserTags(userTags);
		vo.setTotalRate(partnerUser.getTotalRate());

		model.addAttribute("contentModel", vo);
		model.addAttribute("tags", tags);
		model.addAttribute("tagIds", tagIds);

		// 服务大类，该团队的服务大类
		List<PartnerServiceType> partnerServiceTypes= new ArrayList<PartnerServiceType>();

		PartnersSearchVo searchVo = new PartnersSearchVo();
		searchVo.setPartnerId(partnerId);
		searchVo.setParentId(0L);
		List<PartnerRefServiceType> partnerRefServiceType = partnerRefServiceTypeService.selectBySearchVo(searchVo);

		if (!partnerRefServiceType.isEmpty()) {
			List<Long> serviceTypeIds = new ArrayList<Long>();

			for (PartnerRefServiceType item : partnerRefServiceType) {
				if (!serviceTypeIds.contains(item.getServiceTypeId())) {
					serviceTypeIds.add(item.getServiceTypeId());
				}
			}
			PartnerServiceTypeSearchVo searchVo1 = new PartnerServiceTypeSearchVo();
			searchVo1.setServiceTypeIds(serviceTypeIds);
			partnerServiceTypes = partnerServiceTypeService.selectBySearchVo(searchVo1);
		}

		model.addAttribute("partnerServiceTypes", partnerServiceTypes);

		// 评价列表
		RecordRateSearchVo searchVo1 = new RecordRateSearchVo();
		searchVo1.setRateType((short) 1);
		searchVo1.setLinkId(userId);
		List<RecordRates> list = recordRatesService.selectBySearchVo(searchVo1);

		List<RecordRateVo> vos = new ArrayList<RecordRateVo>();
		for (int i = 0; i < list.size(); i++) {
			RecordRates item = list.get(i);

			RecordRateVo rvo = new RecordRateVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, rvo);

			Long uid = item.getUserId();
			String name = item.getName();

			Users us = null;

			if (uid > 0L)
				us = userService.selectByPrimaryKey(uid);

			if (StringUtil.isEmpty(name) && u != null) {
				name = us.getName();
				if (StringUtil.isEmpty(name))
					name = MobileUtil.getMobileStar(us.getMobile());
			}

			String headImg = Constants.DEFAULT_HEAD_IMG;

			if (uid > 0L) {
				headImg = us.getHeadImg();
			}

			rvo.setName(name);
			rvo.setHeadImg(headImg);

			rvo.setAddTimeStr(TimeStampUtil.fromTodayStr(rvo.getAddTime() * 1000));
			vos.add(rvo);
		}

		model.addAttribute("rates", vos);

		return "partners/partnerUserRateForm";
	}

}
