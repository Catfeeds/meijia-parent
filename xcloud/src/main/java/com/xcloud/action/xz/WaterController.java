package com.xcloud.action.xz;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.simi.po.model.order.OrderExtWater;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderExtWaterService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderWaterComVo;
import com.simi.vo.order.OrdersWaterAddOaVo;
import com.simi.vo.partners.PartnerUserSearchVo;
import com.simi.vo.user.UserAddrVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

@Controller
@RequestMapping(value = "/xz/water/")
public class WaterController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private OrderExtWaterService orderExtWaterService;

	@Autowired
	private PartnerUserService partnerUserService;

	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private UserAddrsService userAddrsService;
	
	@Autowired
	private OrderQueryService orderQueryService;

	// 一键送水
	@AuthPassport
	@RequestMapping(value = "water-form", method = RequestMethod.GET)
	public String waterForm(Model model, HttpServletRequest request) {

		Long serviceTypeId = 239L;
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long userId = accountAuth.getUserId();
		model.addAttribute("userId", userId);
		
		Users users = usersService.selectByPrimaryKey(userId);
		
		OrdersWaterAddOaVo vo = new OrdersWaterAddOaVo();
		OrderExtWater water = orderExtWaterService.initOrderExtWater();
		BeanUtilsExp.copyPropertiesIgnoreNull(water, vo);
		vo.setRemarks("");
		vo.setMobile("");
		vo.setLinkMan(users.getName());
		vo.setLinkTel(users.getMobile());
		vo.setServiceNum(1);
		// 获得商品列表的选择下拉列表
		PartnerUserSearchVo searchVo = new PartnerUserSearchVo();
		searchVo.setServiceTypeId(serviceTypeId);
		searchVo.setWeightType((short) 1);

		List<PartnerUsers> list = partnerUserService.selectBySearchVo(searchVo);

		PartnerUsers partnerUser = list.get(0);

		Long parnterUserId = partnerUser.getUserId();

		List<PartnerServicePriceDetail> servicePriceDetails = partnerServicePriceDetailService
				.selectByUserId(parnterUserId);

		List<Long> servicePriceIds = new ArrayList<Long>();
		for (PartnerServicePriceDetail item : servicePriceDetails) {
			if (!servicePriceIds.contains(item.getServicePriceId())) {
				servicePriceIds.add(item.getServicePriceId());
			}
		}
		List<PartnerServiceType> serviceTypes = partnerServiceTypeService
				.selectByIds(servicePriceIds);
		List<OrderWaterComVo> waterComVos = new ArrayList<OrderWaterComVo>();
		PartnerServiceType serviceType = null;
		for (PartnerServiceType item : serviceTypes) {
			serviceType = item;
			PartnerServicePriceDetail detail = partnerServicePriceDetailService
					.selectByServicePriceId(serviceType.getId());
			OrderWaterComVo waterComVo = new OrderWaterComVo();
			waterComVo.setPrice(detail.getPrice());
			waterComVo.setDisprice(detail.getDisPrice());
			waterComVo.setImgUrl(detail.getImgUrl());
			waterComVo.setServicePriceId(serviceType.getId());
			waterComVo.setName(serviceType.getName());
			waterComVo.setNamePrice(serviceType.getName() + "(原价:"
					+ detail.getPrice().toString() + "元,折扣价："
					+ detail.getDisPrice().toString() + "元)");
			waterComVos.add(waterComVo);

		}
		model.addAttribute("waterComVos", waterComVos);

		// 商品名称
		Long servicePriceId = water.getServicePriceId();
		PartnerServiceType servicePrice = partnerServiceTypeService
				.selectByPrimaryKey(servicePriceId);
		PartnerServicePriceDetail servicePriceDetail = partnerServicePriceDetailService
				.selectByServicePriceId(servicePriceId);
		model.addAttribute("servicePrice", servicePrice);
		model.addAttribute("servicePriceDetail", servicePriceDetail);

		// 用户地址列表
		List<UserAddrs> userAddrsList = userAddrsService.selectByUserId(userId);
		List<UserAddrVo> voList = new ArrayList<UserAddrVo>();
		for (int i = 0; i < userAddrsList.size(); i++) {
			UserAddrs addrs = userAddrsList.get(i);
			UserAddrVo vos = new UserAddrVo();
			vos.setAddrId(addrs.getId());
			vos.setAddrName(addrs.getAddress() + addrs.getAddr());
			voList.add(vos);
		}
		model.addAttribute("userAddrVo", voList);
		
		model.addAttribute("contentModel", vo);
		return "xz/water-form";
	}

	// 查询与登记
	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model,OrderSearchVo searchVo
		) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();
		
		searchVo.setUserId(userId);
	
		PageInfo result = orderExtWaterService.selectByPage(searchVo, pageNo,pageSize);
		
		model.addAttribute("contentModel", result);
		
		return "xz/water-list";
	}

	// 快递服务商
	@AuthPassport
	@RequestMapping(value = "service", method = RequestMethod.GET)
	public String doSetting(HttpServletRequest request, Model model) {

		return "xz/water-service";
	}
}
