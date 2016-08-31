package com.simi.action.app.partner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.StringUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.partners.PartnerServicePrice;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.user.Users;
import com.simi.service.ImgService;
import com.simi.service.partners.PartnerServicePriceService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.partners.PartnerServicePriceListVo;
import com.simi.vo.partners.PartnerServicePriceSearchVo;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerUserSearchVo;

@Controller
@RequestMapping(value = "/app/partner")
public class PartnerServicePriceController extends BaseController {

	@Autowired
	private PartnerServicePriceService partnerServicePriceService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private PartnerUserService partnerUserService;

	@Autowired
	private PartnersService partnersService;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private ImgService imgService;

	/**
	 * 根据用户id判断用户是否为服务商（未完待续）
	 * 
	 * @param userId
	 * @return
	 */
	// 1.普通用户进入我的，点击店铺，系统判断其是否归属某个服务商，且此服务商是否已经通过我方后台审核，即状态是“已认证”。
	@RequestMapping(value = "get_userType_by_user_id", method = RequestMethod.GET)
	public AppResultData<Object> getUserType(@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		// 先判断用户是否有对应的服务商
		PartnerUsers pUsers = partnerUserService.selectByUserId(userId);

		// 若有对应的服务商,判断该服务商是否已验证
		if (pUsers != null) {
			Partners partners = partnersService.selectByPrimaryKey(pUsers.getPartnerId());
			if (partners.getStatus() == 4) {
				// 若服务商验证已通过====》直接进入店铺
				return result;
			} else {
				// 此服务商还未认证通过，则提示
				result.setStatus(Constants.ERROR_100);
				result.setMsg(ConstantMsg.PARTNERS_NOT_THROUGH);
				return result;

			}

		}
		/**
		 * 若没有对应的服务商， 1则查用户是否存在====》若不存在则让她注册 提示本业务只想服务商提供，您还未注册店铺，是否要注册店铺？
		 * 2.若用户存在但是user_type=0,则让它注册
		 */
		// 1.普通用户进入我的，点击店铺，系统判断其是否归属某个服务商，且此服务商是否已经通过我方后台审核，即状态是“已认证”。
		// 2.若不满足以上条件，则跳到店铺注册页面，完成店铺申请注册。
		// 3.注册提交后，且我方后台审核通过后，此用户可以顺利进入店铺首页，从而进行商品管理。
		// 4.在商品管理中发布商品，则在发现中此人的服务商品列表中出现。
		// 5.某人购买了此服务人员刚发布的商品，则订单流程可走完。
		Users users = usersService.selectByPrimaryKey(userId);
		if (users == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.PARTNERS_NOT_EXIST_MG);
			return result;
		}
		if (users != null && users.getUserType() == 0) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USERS_NOT_REGIETER_STORE);
			return result;
		}

		return result;
	}

	/**
	 * 服务报价列表接口(通过服务人员ID查询)
	 * 
	 * @param partnerUserId
	 * @param page
	 * @return
	 */

	@RequestMapping(value = "get_partner_service_price_list", method = RequestMethod.GET)
	public AppResultData<Object> list(@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		PartnerUsers partnerUsers = partnerUserService.selectByUserId(userId);

		Long partnerId = partnerUsers.getPartnerId();
		Long serviceTypeId = partnerUsers.getServiceTypeId();

		// List<PartnerServiceType> list =
		// partnerServiceTypeService.selectByPartnerIdIn(partnerId);
		// 服务价格
		List<Long> partnerIds = new ArrayList<Long>();
		partnerIds.add(0L);
		partnerIds.add(partnerId);
		PartnerServicePriceSearchVo searchVo = new PartnerServicePriceSearchVo();
		searchVo.setServiceTypeId(serviceTypeId);
		searchVo.setPartnerIds(partnerIds);
		List<PartnerServicePrice> list = partnerServicePriceService.selectBySearchVo(searchVo);

		List<PartnerServicePrice> listVo = new ArrayList<PartnerServicePrice>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				PartnerServicePrice partnerServicePrice = list.get(i);
				
				if (partnerServicePrice != null) {
					if (partnerServicePrice.getUserId() != 0 && partnerServicePrice.getUserId().equals(userId)) {
						listVo.add(partnerServicePrice);
					}
				}
			}
		}

		result.setData(listVo);

		return result;

	}

	/**
	 * 通过userId在关联表里面获得partnerId和serviceTypeId
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "get_partnerId_by_user_id", method = RequestMethod.GET)
	public AppResultData<Object> getPartnerId(@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		PartnerUsers partnerUsers = partnerUserService.selectByUserId(userId);

		result.setData(partnerUsers);

		return result;
	}

	/**
	 * 获取服务价格详情接口
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "get_partner_service_price_detail", method = RequestMethod.GET)
	public AppResultData<Object> getPartnerServicePriceDetail(Model model, @RequestParam("service_price_id") Long servicePriceId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (servicePriceId == 0) {
			return result;
		}
		PartnerServicePrice servicePrice = partnerServicePriceService.selectByPrimaryKey(servicePriceId);

		result.setData(servicePrice);
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/post_partner_service_price_add", method = { RequestMethod.POST })
	public AppResultData<Object> partnerAdd(
			HttpServletRequest request,
			// @RequestParam("parent_id") Long parentId,//服务商id
			@RequestParam("partner_id") Long partnerId,
			@RequestParam("service_type_id") Long serviceTypeId,// 服务类别
			@RequestParam("user_id") Long userId,// 用户id

			@RequestParam("is_enable") Short isEnable,
			@RequestParam("name") String name,
			@RequestParam("price") BigDecimal price,
			@RequestParam("dis_price") BigDecimal disPrice,
			@RequestParam("order_type") short orderType,
			@RequestParam(value = "service_price_id", required = false, defaultValue = "0") Long servicePriceId,
			@RequestParam(value = "title", required = false, defaultValue = "") String title,
			@RequestParam(value = "content_standard", required = false, defaultValue = "") String contentStandard,// 服务标准
			@RequestParam(value = "content_desc", required = false, defaultValue = "") String contentDesc,// 服务说明
			@RequestParam(value = "content_flow", required = false, defaultValue = "") String contentFlow,// 服务流程
			@RequestParam(value = "order_duration", required = false, defaultValue = "0") short orderDuration,
			@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
			@RequestParam(value = "is_addr", required = false, defaultValue = "0") short isAddr,
			@RequestParam(value = "imgUrlFile", required = false) MultipartFile file) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		PartnerServicePrice record = partnerServicePriceService.initPartnerServicePrice();

		if (servicePriceId > 0L) {
			record = partnerServicePriceService.selectByPrimaryKey(servicePriceId);
		}

		record.setUserId(userId);
		record.setServicePriceId(servicePriceId);
		record.setServiceTypeId(serviceTypeId);
		record.setName(name);
		record.setPartnerId(partnerId);
		record.setServiceTitle(title);

		record.setPrice(price);
		record.setDisPrice(disPrice);
		record.setOrderType(orderType);
		record.setOrderDuration(orderDuration);
		record.setIsAddr(isAddr);
		record.setContentStandard(contentStandard);
		record.setContentDesc(contentDesc);
		record.setContentFlow(contentFlow);
		record.setIsEnable((short) 1);
		
		if (servicePriceId.equals(0L)) {
			record.setCategory("");
			record.setAction("");
			record.setParams("");
			record.setGotoUrl("");
			record.setVideoUrl("");
			record.setExtendId(0L);
			record.setTags("");
		}
		
		
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
		}
		return result;

	}

	// 判断商品列表是否显示新增按钮
	@RequestMapping(value = "get_partnerStatus_by_user_id", method = RequestMethod.GET)
	public AppResultData<Object> getPartnerStatus(@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		PartnerUsers pUsers = partnerUserService.selectByUserId(userId);
		if (pUsers == null) {

			return result;
		}
		Partners partners = partnersService.selectByPrimaryKey(pUsers.getPartnerId());
		if (partners == null) {

			return result;
		}

		result.setData(partners);
		return result;
	}

	/**
	 * 服务报价列表接口(通过服务大类查询)
	 * 
	 * @param service_type_id
	 *            服务大类ID
	 * @param page
	 * @return
	 */

	@RequestMapping(value = "get_default_service_price_list", method = RequestMethod.GET)
	public AppResultData<Object> getDefaultList(@RequestParam("user_id") Long userId, @RequestParam("service_type_id") Long serviceTypeId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		// 先根据服务大类找到相应的推荐人员.
		PartnerUserSearchVo searchVo = new PartnerUserSearchVo();
		searchVo.setServiceTypeId(serviceTypeId);
		searchVo.setWeightType((short) 1);

		List<PartnerUsers> list = partnerUserService.selectBySearchVo(searchVo);
		if (list.isEmpty())
			return result;

		PartnerUsers partnerUser = list.get(0);

		Long partnerUserId = partnerUser.getUserId();
		
		PartnerServicePriceSearchVo searchVo1 = new PartnerServicePriceSearchVo();
		searchVo1.setUserId(partnerUserId);
		List<PartnerServicePrice> servicePrices = partnerServicePriceService.selectBySearchVo(searchVo1);

		// List<PartnerServiceType> list =
		// partnerServiceTypeService.selectByPartnerIdIn(partnerId);
		// 服务价格
		List<PartnerServicePriceListVo> servicePriceVos = new ArrayList<PartnerServicePriceListVo>();

		for (PartnerServicePrice item : servicePrices) {

			PartnerServicePriceListVo servicePriceVo = new PartnerServicePriceListVo();

			BeanUtilsExp.copyPropertiesIgnoreNull(item, servicePriceVo);
			
			servicePriceVo.setPartnerUserId(partnerUserId);
			// 图片处理成190x140大小
			String imgUrl = servicePriceVo.getImgUrl();
			imgUrl = imgUrl + "?p=0";
			servicePriceVo.setImgUrl(imgUrl);

			String detailUrl = "http://app.bolohr.com/simi-h5/discover/service-detail.html?service_type_id=" + item.getServicePriceId();

			if (item.getContentDesc().equals("") && item.getContentFlow().equals("") && item.getContentDesc().equals("")) {
				detailUrl = "";
			}

			servicePriceVo.setDetailUrl(detailUrl);
			servicePriceVos.add(servicePriceVo);

		}

		result.setData(servicePriceVos);

		return result;

	}

	/**
	 * 服务报价列表接口(通过服务大类和服务商)
	 * 
	 * @param service_type_id
	 *            服务大类ID
	 * @param page
	 * @return
	 */

	@RequestMapping(value = "get_service_price_list", method = RequestMethod.GET)
	public AppResultData<Object> getServicePriceList(@RequestParam("service_type_id") Long serviceTypeId, @RequestParam("partner_id") Long partnerId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		// 先根据服务大类和服务商找到相应的服务人员.
		PartnerUserSearchVo searchVo = new PartnerUserSearchVo();
		searchVo.setServiceTypeId(serviceTypeId);
		searchVo.setPartnerId(partnerId);

		List<PartnerUsers> list = partnerUserService.selectBySearchVo(searchVo);

		if (list.isEmpty())
			return result;

		List<Map> resultList = new ArrayList<Map>();
		
		
		
		for (PartnerUsers partnerUser : list) {
			Long userId = partnerUser.getUserId();
			Users u = usersService.selectByPrimaryKey(userId);
			
			PartnerServicePriceSearchVo searchVo1 = new PartnerServicePriceSearchVo();
			searchVo1.setUserId(userId);
			List<PartnerServicePrice> servicePrices = partnerServicePriceService.selectBySearchVo(searchVo1);
			for (PartnerServicePrice item : servicePrices) {
				Map<String, String> resultMap = new HashMap<String, String>();
				resultMap.put("name", item.getName());
				resultMap.put("servce_price_id", item.getServicePriceId().toString());
				resultMap.put("price", MathBigDecimalUtil.round2(item.getPrice()));
				resultMap.put("dis_price", MathBigDecimalUtil.round2(item.getDisPrice()));
				resultMap.put("img_url", item.getImgUrl());
				resultMap.put("user_name", u.getName());
				resultMap.put("mobile", u.getMobile());

				resultList.add(resultMap);

			}

		}

		result.setData(resultList);

		return result;

	}

}
