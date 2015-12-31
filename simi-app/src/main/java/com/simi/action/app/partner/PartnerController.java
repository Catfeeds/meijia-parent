package com.simi.action.app.partner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.StringUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.user.UserImgs;
import com.simi.po.model.user.Users;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.user.UserImgService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.partners.PartnerServicePriceListVo;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerUserDetailVo;
import com.simi.vo.partners.PartnerUserSearchVo;
import com.simi.vo.partners.PartnerUserVo;
import com.simi.vo.user.UserImgVo;


@Controller
@RequestMapping(value = "/app/partner")
public class PartnerController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserImgService userImgService;	
	
	@Autowired
	private PartnerUserService partnerUserService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;

	/**
	 * 获取可用的服务商人员列表
	 * @param userId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "get_user_list", method = RequestMethod.GET)
	public AppResultData<Object> partnerUserList(
			@RequestParam("user_id") Long userId,
			@RequestParam("service_type_ids") String serviceTypeIdAry,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "city_id", required = false, defaultValue = "0") Long cityId,
			@RequestParam(value = "region_id", required = false, defaultValue = "0") Long regionId) {
			
			AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");

			Users u = userService.selectByPrimaryKey(userId);

			// 判断是否为注册用户，非注册用户返回 999
			if (u == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
				return result;
			}
			
			String[] serviceTypeArray = StringUtil.convertStrToArray(serviceTypeIdAry);
			List<Long> serviceTypeIds = new ArrayList<Long>();
			for (int i = 0; i < serviceTypeArray.length; i++) {
				if (!StringUtil.isEmpty(serviceTypeArray[i])) {
					Long serviceTypeId = Long.valueOf(serviceTypeArray[i]);
					serviceTypeIds.add(serviceTypeId);
				}
			}
			PartnerUserSearchVo searchVo = new PartnerUserSearchVo();
			searchVo.setServiceTypeIds(serviceTypeIds);
			PageInfo pageList = partnerUserService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
			
			List<PartnerUserVo> list = pageList.getList();
			result.setData(list);
			return result;
	}
	
	/**
	 * 获取服务人员详细信息
	 * @param userId
	 * @param page
	 * @return
	 */
	 @RequestMapping(value = "get_user_detail", method = RequestMethod.GET)
	 public AppResultData<Object> getUserDetail(
			 @RequestParam("partner_user_id") Long partnerUserId,
			 @RequestParam("service_type_id") Long serviceTypeId) {
		 AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");
		 
		Users u = userService.selectByPrimaryKey(partnerUserId);
		
		PartnerUsers parnterUser = partnerUserService.selectByUserId(partnerUserId);
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null || parnterUser == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		PartnerUserVo vo  = partnerUserService.changeToVo(parnterUser);
		PartnerUserDetailVo detailVo = new PartnerUserDetailVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(vo, detailVo);
		
		Long partnerId = parnterUser.getPartnerId();
		//服务价格
		List<Long> partnerIds = new ArrayList<Long>();
		partnerIds.add(0L);
		partnerIds.add(partnerId);
		PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
		searchVo.setParentId(serviceTypeId);
		searchVo.setViewType((short) 1);
		searchVo.setPartnerIds(partnerIds);
		
		List<PartnerServiceType> servicePrices = partnerServiceTypeService.selectBySearchVo(searchVo);
		
		List<PartnerServicePriceListVo> servicePriceVos = new ArrayList<PartnerServicePriceListVo>();
		
		for (PartnerServiceType item : servicePrices) {
			PartnerServicePriceListVo servicePriceVo = new PartnerServicePriceListVo();
			PartnerServicePriceDetail servicePriceDetail = partnerServicePriceDetailService.selectByServicePriceId(item.getId());
			PartnerServiceType partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(item.getId());
			//排除掉不是此大类默认的报价和不是此服务人员自己添加的报价
			//排除掉已经下架的商品
			if (!servicePriceDetail.getUserId().equals(0L))  {
				if(!servicePriceDetail.getUserId().equals(partnerUserId)&&!partnerServiceType.getIsEnable().equals(1L)) continue;
			}
			
			/*	if (!servicePriceDetail.getUserId().equals(0L))  {
				if(!servicePriceDetail.getUserId().equals(partnerUserId)||partnerServiceType.getIsEnable().equals(0L)) continue;
			}*/
			
			BeanUtilsExp.copyPropertiesIgnoreNull(servicePriceDetail, servicePriceVo);
			servicePriceVo.setName(item.getName());
			
			String detailUrl = "http://123.57.173.36/simi-h5/discover/service-detail.html?service_type_id=" + item.getId();
			
			if (servicePriceDetail.getContentDesc().equals("") &&
				servicePriceDetail.getContentFlow().equals("") &&
				servicePriceDetail.getContentDesc().equals("")) {
				detailUrl = "";
			}
			
			servicePriceVo.setDetailUrl(detailUrl);
			servicePriceVos.add(servicePriceVo);
			
		}
		
		detailVo.setServicePrices(servicePriceVos);
		
		
		//用户图片
		List<UserImgs> userImgs = userImgService.selectByUserId(partnerUserId);
		
		if (userImgs == null) {
			userImgs = new ArrayList<UserImgs>();
		}
		
		List<UserImgVo> userImgVos = new ArrayList<UserImgVo>();
		
		for (UserImgs item : userImgs) {
			UserImgVo imgVo = new UserImgVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, imgVo);
			
			imgVo.setImgTrumb(ImgServerUtil.getImgSize(item.getImgUrl(), "400", "400"));
			imgVo.setImg(item.getImgUrl());
			userImgVos.add(imgVo);
		}
		
		detailVo.setUserImgs(userImgVos);
		
		result.setData(detailVo);

		 return result;
	 }
	 
	/**
	 * 获取服务人员详细信息
	 * @param userId
	 * @param page
	 * @return
	 */
	 @RequestMapping(value = "get_hot_keyword", method = RequestMethod.GET)	 
	 public AppResultData<Object> getHotKeyword() {
		 AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");
		 List<String> hotKeywords = new ArrayList<String>();
		 hotKeywords.add("工商注册");
		 hotKeywords.add("财务会计");
		 hotKeywords.add("技术服务");
		 hotKeywords.add("社保公积金");
		 result.setData(hotKeywords);
		 return result;
	 }
	 
	 
	/**
	 * 获取可用的服务商人员列表
	 * @param userId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public AppResultData<Object> searchBykey(
			@RequestParam("user_id") Long userId, 
			@RequestParam("keyword") String keyword,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "city_id", required = false, defaultValue = "0") Long cityId,
			@RequestParam(value = "region_id", required = false, defaultValue = "0") Long regionId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		if (StringUtil.isEmpty(keyword)) return result;
		
		PartnerServiceTypeSearchVo searchVo1 = new PartnerServiceTypeSearchVo();
		searchVo1.setName(keyword);
		searchVo1.setParentId(0L);
		
		
		
		List<PartnerServiceType> serviceTypes =  partnerServiceTypeService.selectBySearchVo(searchVo1);
		if (serviceTypes.isEmpty()) return result;

		List<Long> serviceTypeIds = new ArrayList<Long>();
		for (PartnerServiceType item : serviceTypes) {
			if (!serviceTypeIds.contains(item.getId())) {
				serviceTypeIds.add(item.getId());
			}
		}
		
		if (serviceTypeIds.isEmpty()) return result;
		
		PartnerUserSearchVo searchVo = new PartnerUserSearchVo();
		searchVo.setServiceTypeIds(serviceTypeIds);
		PageInfo pageList = partnerUserService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);

		List<PartnerUserVo> list = pageList.getList();
		result.setData(list);
		return result;
	}
	 
	
}