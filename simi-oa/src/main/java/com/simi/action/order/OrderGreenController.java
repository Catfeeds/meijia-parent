package com.simi.action.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.admin.AdminController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.msg.Msg;
import com.simi.po.model.order.OrderExtGreen;
import com.simi.po.model.order.OrderExtPartner;
import com.simi.po.model.order.OrderExtWater;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.user.UserAddrs;
import com.simi.service.order.OrderExtGreenService;
import com.simi.service.order.OrderExtPartnerService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerRefServiceTypeService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.OrdersGreenPartnerVo;
import com.simi.vo.OrdersListVo;
import com.simi.vo.order.OrderViewVo;
import com.simi.vo.order.OrderWaterComVo;
import com.simi.vo.partners.PartnerUserSearchVo;
import com.simi.vo.user.UserAddrVo;
import com.simi.vo.user.UserViewVo;

@Controller
@RequestMapping(value = "/order")
public class OrderGreenController extends AdminController {

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
	private OrderQueryService orderQueryService;

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
	private OrderLogService orderLogService;
	
	@Autowired
	private PartnerRefServiceTypeService partnerRefServiceTypeService;
	
	@Autowired
	private PartnersService partnersService;

	@Autowired
	private OrderExtPartnerService orderExtPartnerService;
	
	@Autowired
	private OrderExtGreenService orderExtGreenService;
	
	@Autowired
	private PartnerUserService partnerUserService;
	
	@Autowired
	private  PartnerServicePriceDetailService partnerServicePriceDetailService;
	 
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	/**
	 * 订单列表
	 * 
	 * @param request
	 * @param model
	 * @param searchVo
	 * @param userId
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/greenList", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model,
			OrderSearchVo searchVo,
			@RequestParam(value = "user_id", required = false) Long userId) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		searchVo.setUserId(userId);
		//绿植服务大类ID
		searchVo.setServiceTypeId(238L);
		
		// 分页
		PageHelper.startPage(pageNo, pageSize);

		List<Orders> orderList = orderQueryService.selectByListPageList(
				searchVo, pageNo, pageSize);

		Orders orders = null;
		for (int i = 0; i < orderList.size(); i++) {
			orders = orderList.get(i);
			OrdersListVo ordersListVo = orderQueryService.completeVo(orders);
			
			OrdersGreenPartnerVo vo = new OrdersGreenPartnerVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(ordersListVo, vo);
			OrderExtGreen green = orderExtGreenService.selectByOrderId(vo.getOrderId());
			vo.setPartnerOrderNo("");
			vo.setPartnerOrderMoney(new BigDecimal(0));
			vo.setPartnerId(0L);
			vo.setOrderExtStatus((short)0);
			vo.setGreenType((short)0);
			
			vo.setTotalNum(green.getTotalNum());
			vo.setTotalBudget(green.getTotalBudget());
			
			orderList.set(i, vo);
		}
		PageInfo result = new PageInfo(orderList);

		model.addAttribute("contentModel", result);
		model.addAttribute("oaOrderSearchVoModel", searchVo);
		return "order/orderGreenList";
	}

	/**
	 * 订单详情
	 * 
	 * @param orderNo
	 * @param model
	 * @return
	 */
	// @AuthPassport

	@RequestMapping(value = "/orderGreenView", method = RequestMethod.GET)
	public String orderDetail(String orderNo, Model model) {

		Orders orders = ordersService.selectByOrderNo(orderNo);
		
		
		OrdersListVo listvo = orderQueryService.completeVo(orders);
		OrdersGreenPartnerVo vo = new OrdersGreenPartnerVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(listvo, vo);
		OrderExtGreen green = orderExtGreenService.selectByOrderId(vo.getOrderId());
		OrderExtPartner orderExtPartner = orderExtPartnerService
				.selectByOrderId(orders.getOrderId());
		vo.setPartnerOrderNo(orderExtPartner.getPartnerOrderNo());
		vo.setPartnerOrderMoney(orderExtPartner.getPartnerOrderMoney());
		vo.setPartnerId(orderExtPartner.getPartnerId());
		
		vo.setOrderExtStatus(green.getGreenType());
		vo.setGreenType(green.getGreenType());
		vo.setTotalNum(green.getTotalNum());
		vo.setTotalBudget(green.getTotalBudget());
		model.addAttribute("contentModel", vo);
	    //服务商列表
		Long serviceTypeId = 238L;
		List<PartnerRefServiceType> partnerRefServiceType = 
				partnerRefServiceTypeService.selectByServiceTypeId(serviceTypeId);
		List<Partners> partnerList = new ArrayList<Partners>();
		for (int i = 0; i < partnerRefServiceType.size(); i++) {
			PartnerRefServiceType serviceType = partnerRefServiceType.get(i);
			Partners partners = partnersService.selectByPrimaryKey(serviceType.getPartnerId());
			partnerList.add(partners);
		}
		model.addAttribute("partnerList", partnerList);
		//用户地址列表
		 List<UserAddrs> userAddrsList = userAddrsService.selectByUserId(orders.getUserId());
		    List<UserAddrVo> voList = new ArrayList<UserAddrVo>();
		   for (int i = 0; i < userAddrsList.size(); i++) {
			    UserAddrs addrs = userAddrsList.get(i);
			    UserAddrVo vos = new UserAddrVo();
				vos.setAddrId(addrs.getId());
				vos.setAddrName(addrs.getAddress() + addrs.getAddr());
			    voList.add(vos);
		}
	   model.addAttribute("userAddrVo", voList);
	   
		
		return "order/orderGreenViewForm";
	}
    /**
     * 订单保存修改
     * @param model
     * @param orders
     * @param result
     * @param request
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/saveOrderGreen", method = { RequestMethod.POST})
	public String adForm(Model model,
			/*@RequestParam(value = "id") Long id,
			@RequestParam(value = "isApproval") short isApproval,*/
			@ModelAttribute("contentModel") OrdersGreenPartnerVo vo,
			BindingResult result,HttpServletRequest request)throws IOException {
    	
    	String orderNo = vo.getOrderNo();
    	Orders orders = ordersService.selectByOrderNo(orderNo);
    	Long orderId = orders.getOrderId();
    	Orders record = ordersService.initOrders();

    //	BeanUtilsExp.copyPropertiesIgnoreNull(msg, record);
    
    	if (orderId > 0L ) {
    		record = orders;
    		record.setAddrId(vo.getAddrId());
    		record.setOrderStatus(vo.getOrderStatus());
    		ordersService.updateByPrimaryKeySelective(record);
    		
			// 像订单服务商信息扩展表中插记录
						OrderExtPartner orderExtPartner = orderExtPartnerService
								.selectByOrderId(orderId);
						OrderExtPartner partner = orderExtPartnerService
								.initOrderExtPartner();
						if (orderExtPartner == null) {
							orderExtPartner = partner;
							orderExtPartner.setOrderId(record.getOrderId());
							orderExtPartner.setOrderNo(record.getOrderNo());
							orderExtPartner.setPartnerId(vo.getPartnerId());
							orderExtPartner.setPartnerOrderNo(vo.getPartnerOrderNo());
							orderExtPartner.setPartnerOrderMoney(vo.getPartnerOrderMoney());
							orderExtPartner.setRemarks(vo.getRemarks());
							orderExtPartnerService.insert(orderExtPartner);
						} else {
							orderExtPartner.setOrderId(record.getOrderId());
							orderExtPartner.setOrderNo(record.getOrderNo());
							orderExtPartner.setPartnerId(vo.getPartnerId());
							orderExtPartner.setPartnerOrderNo(vo.getPartnerOrderNo());
							orderExtPartner.setPartnerOrderMoney(vo.getPartnerOrderMoney());
							orderExtPartner.setRemarks(vo.getRemarks());
							orderExtPartner.setAddTime(TimeStampUtil.getNowSecond());
							orderExtPartnerService
									.updateByPrimaryKeySelective(orderExtPartner);
						}
			//更新绿植表
			OrderExtGreen green = orderExtGreenService.selectByOrderId(orderId);
			
			green.setOrderExtStatus(vo.getOrderExtStatus());
			green.setGreenType(vo.getGreenType());
			green.setTotalNum(vo.getTotalNum());
			green.setTotalBudget(vo.getTotalBudget());
			green.setAddTime(TimeStampUtil.getNowSecond());
			orderExtGreenService.updateByPrimaryKeySelective(green);
			
		}else {
			ordersService.insertSelective(record);
		}
    	
    	return "redirect:/order/greenList";
    }

}
