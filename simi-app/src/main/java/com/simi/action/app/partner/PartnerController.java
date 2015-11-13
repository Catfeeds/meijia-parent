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
import com.simi.vo.partners.PartnerUserDetailVo;
import com.simi.vo.partners.PartnerUserSearchVo;
import com.simi.vo.partners.PartnerUserVo;


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
			@RequestParam("service_type_id") Long serviceTypeId,
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
			
			PartnerUserSearchVo searchVo = new PartnerUserSearchVo();
			searchVo.setServiceTypeId(serviceTypeId);
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
			 @RequestParam("user_id") Long userId,
			 @RequestParam("service_type_id") Long serviceTypeId) {
		 AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");
		 
		Users u = userService.selectByPrimaryKey(userId);
		
		PartnerUsers parnterUser = partnerUserService.selectByUserId(userId);
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null || parnterUser == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		PartnerUserVo vo  = partnerUserService.changeToVo(parnterUser);
		PartnerUserDetailVo detailVo = new PartnerUserDetailVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(vo, detailVo);
		//服务价格
		List<PartnerServiceType> servicePrices = partnerServiceTypeService.selectByParentId(serviceTypeId, (short) 1);
		
		List<PartnerServicePriceListVo> servicePriceVos = new ArrayList<PartnerServicePriceListVo>();
		
		for (PartnerServiceType item : servicePrices) {
			PartnerServicePriceListVo servicePriceVo = new PartnerServicePriceListVo();
			PartnerServicePriceDetail servicePriceDetail = partnerServicePriceDetailService.selectByServicePriceId(item.getId());
			
			BeanUtilsExp.copyPropertiesIgnoreNull(servicePriceDetail, servicePriceVo);
			servicePriceVo.setName(item.getName());
			
			String detailUrl = "http://123.57.173.36/simi-h5/discover/service-detail.html?service_price_id=" + item.getId();
			
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
		List<UserImgs> userImgs = userImgService.selectByUserId(userId);
		
		if (userImgs == null) {
			userImgs = new ArrayList<UserImgs>();
		}
		detailVo.setUserImgs(userImgs);
		
		result.setData(detailVo);

		 return result;
	 }
	
}