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
import com.meijia.utils.StringUtil;
import com.simi.po.model.op.AppTools;
import com.simi.po.model.order.OrderExtWater;
import com.simi.po.model.partners.PartnerServicePrice;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.service.op.AppToolsService;
import com.simi.service.order.OrderExtWaterService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.partners.PartnerServicePriceService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.ApptoolsSearchVo;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderWaterComVo;
import com.simi.vo.order.OrdersWaterAddOaVo;
import com.simi.vo.partners.PartnerServicePriceSearchVo;
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
	private PartnerServicePriceService partnerServicePriceService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
	private OrderQueryService orderQueryService;

	@Autowired
	private AppToolsService appToolsService;	

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
		
		PartnerServicePriceSearchVo searchVo1 = new PartnerServicePriceSearchVo();
		searchVo1.setUserId(parnterUserId);
		List<PartnerServicePrice> servicePrices = partnerServicePriceService.selectBySearchVo(searchVo1);

		
		List<OrderWaterComVo> waterComVos = new ArrayList<OrderWaterComVo>();

		for (PartnerServicePrice item : servicePrices) {

			OrderWaterComVo waterComVo = new OrderWaterComVo();
			waterComVo.setPrice(item.getPrice());
			waterComVo.setDisprice(item.getDisPrice());
			waterComVo.setImgUrl(item.getImgUrl());
			waterComVo.setServicePriceId(item.getServiceTypeId());
			waterComVo.setName(item.getName());
			waterComVos.add(waterComVo);

		}
		model.addAttribute("waterComVos", waterComVos);

		// 商品名称
		Long servicePriceId = water.getServicePriceId();
		PartnerServicePrice servicePrice = partnerServicePriceService.selectByPrimaryKey(servicePriceId);
		model.addAttribute("servicePrice", servicePrice);
		model.addAttribute("servicePrice", servicePrice);

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
		
		//二维码
		String qrCode = "";
		ApptoolsSearchVo searchVo2 = new ApptoolsSearchVo();
		searchVo2.setAction("water");
		List<AppTools> apptools = appToolsService.selectBySearchVo(searchVo2);
		if (!apptools.isEmpty()) {
			AppTools a = apptools.get(0);
			qrCode = a.getQrCode();
		}
		
		if (StringUtil.isEmpty(qrCode)) qrCode = "/assets/img/erweima.png";
		model.addAttribute("qrCode", qrCode);
		
		
		return "xz/water-form";
	}

	// 查询与登记
	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model, OrderSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		
		Long serviceTypeId = 239L;
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();
		
		if (searchVo == null) searchVo = new OrderSearchVo();
		
		searchVo.setUserId(userId);
		
		List<Integer> orderStatusIn = new ArrayList<Integer>();
		orderStatusIn.add(1);
		orderStatusIn.add(2);
		orderStatusIn.add(3);
		orderStatusIn.add(7);
		orderStatusIn.add(8);
		searchVo.setOrderStatusIn(orderStatusIn);
		
		searchVo.setServiceTypeId(serviceTypeId);
		

		PageInfo result = orderExtWaterService.selectByPage(searchVo, pageNo, pageSize);

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
