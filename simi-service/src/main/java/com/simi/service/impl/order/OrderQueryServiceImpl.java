package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.service.dict.DictCouponsService;
import com.simi.service.dict.DictService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UsersService;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderDetailVo;
import com.simi.vo.order.OrderListVo;
import com.simi.vo.order.OrderViewVo;
import com.simi.common.Constants;
import com.simi.po.dao.order.OrdersMapper;
import com.simi.po.model.dict.DictCoupons;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.OrderSenior;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.Users;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

	@Autowired
	private OrdersMapper ordersMapper;

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
	UsersService usersService;

	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
	OrderLogService orderLogService;

	@Autowired
	DictService dictService;
	
	@Autowired
	PartnersService partnersService;
	
	@Autowired
	PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	PartnerServicePriceDetailService partnerServicePriceDetailService;
	
	@Autowired
	UserCouponService userCouponService;   
	
	@Autowired
	private DictCouponsService couponService;
	/**
	 * 根据订单主键进行查询
	 * @param id  订单id
	 * @return Orders
	 */
	@Override
	public Orders selectByPrimaryKey(Long id) {
		return ordersMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据订单编号进行查询
	 * @param orderNo  订单编号
	 * @return Orders
	 */
	@Override
	public Orders selectByOrderNo(String orderNo){
		return ordersMapper.selectByOrderNo(orderNo);
	}
	
	/**
	 * 根据多个查询条件进行查询，并分页展现
	 * @param orderSearchVo  查询条件，OrderSearchVo
	 * @param pageNo  		 页码
	 * @param pageSize		 每页个数
	 * @return PageInfo  List<OrderViewVo>
	 */	
	@Override
	public PageInfo selectByListPage(OrderSearchVo orderSearchVo, int pageNo, int pageSize) {

		 PageHelper.startPage(pageNo, pageSize);
         List<Orders> list = ordersMapper.selectByListPage(orderSearchVo);
         PageInfo result = new PageInfo(list);
        return result;
    }

	/*
	 *  进行orderViewVo  结合了 orders , order_prices,  两张表的元素
	 */
	@Override
	public List<OrderViewVo> getOrderViewList(List<Orders> list) {

	    // 加载更多订单的信息
		List<Long> userIds = new ArrayList<Long>();
		List<Long> addrIds = new ArrayList<Long>();
        List<Long> orderIds = new ArrayList<Long>();
        List<String> orderNos = new ArrayList<String>();
        Orders item = null;
        for (int i = 0 ; i < list.size(); i ++) {
        	item = list.get(i);
        	if (item.getAddrId() > 0L) {
        		addrIds.add(item.getAddrId());
        	}
        	userIds.add(item.getUserId());
        	orderIds.add(item.getOrderId());
        	orderNos.add(item.getOrderNo());
        }
        
        List<OrderPrices> orderPricesList = orderPricesService.selectByOrderIds(orderIds);
        
        List<Users> userList = usersService.selectByUserIds(userIds);
        
        List<UserAddrs> addrList = new ArrayList<UserAddrs>();
        if (addrIds.size() > 0) {
        	addrList = userAddrsService.selectByIds(addrIds);
        }
        //进行orderViewVo  结合了 orders , order_prices,  order_dispatchs 三张表的元素
        List<OrderViewVo> result = new ArrayList<OrderViewVo>();
        Long orderId = 0L;
        Long addrId = 0L;
        Long userId = 0L;

        for (int i = 0 ; i < list.size(); i ++) {
        	item = list.get(i);
        	orderId = item.getOrderId();
        	addrId = item.getAddrId();
        	userId = item.getUserId();

        	OrderViewVo vo = new OrderViewVo();
        	BeanUtils.copyProperties(item, vo);

        	

        	//查找订单金额信息.
        	OrderPrices  orderPrice = null;
        	for (int k = 0; k < orderPricesList.size(); k++) {
        		orderPrice = orderPricesList.get(k);
        		if (orderPrice.getOrderId().equals(orderId)) {
        			vo.setOrderMoney(orderPrice.getOrderMoney());
        			vo.setOrderPay(orderPrice.getOrderPay());
        			vo.setUserCouponId(orderPrice.getUserCouponId());
        			vo.setPayType(orderPrice.getPayType());
        			break;
        		}
        	}

        	//城市名称
    		vo.setCityName("");
    		if (vo.getCityId() > 0L) {
    			String cityName = dictService.getCityName(vo.getCityId());
    			vo.setCityName(cityName);
    		}
    		
    		//服务类型名称
    		vo.setServiceTypeName("");
    		if (vo.getServiceTypeId() > 0L) {
    			PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(vo.getServiceTypeId());
    			vo.setServiceTypeName(serviceType.getName());
    		}
   		
    		//用户姓名
    		String name = "";
    		Users u = null;
    		for (int j = 0; j < userList.size(); j++) {
    			u = userList.get(j);
    			if (u.getId().equals(userId)) {
    				name = u.getName();
    				break;
    			}
    		}
    		vo.setName("");
    		
        	
        	//用户地址
        	String addrName = "";
        	UserAddrs addr = null;
        	for(int n = 0; n < addrList.size(); n++) {
        		addr = addrList.get(n);
        		if (addr.getId().equals(addrId)) {
        			addrName = addr.getName() + addr.getAddr();
        			break;
        		}
        	}

        	vo.setServiceAddr(addrName);

        	result.add(vo);
        }

        return result;
	}
		
	/*
	 *  进行orderViewVo  结合了 orders , order_prices,  两张表的元素
	 */
	@Override
	public OrderViewVo getOrderView(Orders order) {

	    // 加载更多订单的信息
		OrderViewVo vo = new OrderViewVo();
		BeanUtils.copyProperties(order, vo);
		
		//订单价格信息
		OrderPrices orderPrice = orderPricesService.selectByPrimaryKey(order.getOrderId());
		
		vo.setPayType(orderPrice.getPayType());
		vo.setOrderMoney(orderPrice.getOrderMoney());
		vo.setOrderPay(orderPrice.getOrderPay());
		vo.setUserCouponId(orderPrice.getUserCouponId());
		
		//城市名称
		vo.setCityName("");
		if (vo.getCityId() > 0L) {
			String cityName = dictService.getCityName(vo.getCityId());
			vo.setCityName(cityName);
		}
		
		//服务类型名称
		vo.setServiceTypeName("");
		if (vo.getServiceTypeId() > 0L) {
			PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(vo.getServiceTypeId());
			vo.setServiceTypeName(serviceType.getName());
		}
//		
		//用户称呼
		vo.setName("");
		if (vo.getUserId() > 0L) {
			Users user = usersService.selectByPrimaryKey(vo.getUserId());
			vo.setName(user.getName());
		}
		
        //用户地址
		vo.setServiceAddr("");
		if (vo.getAddrId() > 0L) {
			UserAddrs userAddr = userAddrsService.selectByPrimaryKey(vo.getAddrId());
			vo.setServiceAddr(userAddr.getName() + userAddr.getAddr());
		}
		
		//订单状态
		String orderStatusName = MeijiaUtil.getOrderStausName(vo.getOrderStatus());
		vo.setOrderStatusName(orderStatusName);
		
        return vo;
	}
	
	@Override
	public OrderListVo getOrderListVo(Orders order) {

	    // 加载更多订单的信息
		OrderListVo vo = new OrderListVo();
		BeanUtils.copyProperties(order, vo);
		
		
		Users partnerUser = usersService.selectByPrimaryKey(order.getPartnerUserId());
		
		vo.setPartnerUserId(partnerUser.getId());
		vo.setPartnerUserName(partnerUser.getName());
		vo.setPartnerUserHeadImg(partnerUser.getHeadImg());
		
		Users user = usersService.selectByPrimaryKey(order.getUserId());
		vo.setName(user.getName());
		
		//服务类型名称
		vo.setServiceTypeName("");
		if (order.getServiceTypeId() > 0L) {
			PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(order.getServiceTypeId());
			vo.setServiceTypeName(serviceType.getName());
		}		
		
		//用户地址
		vo.setAddrName("");
		if (order.getAddrId() > 0L) {
			UserAddrs userAddr = userAddrsService.selectByPrimaryKey(order.getAddrId());
			vo.setAddrName(userAddr.getName() + userAddr.getAddr());
		}
		vo.setOrderStatus(order.getOrderStatus());
		vo.setOrderStatusName(MeijiaUtil.getOrderStausName(order.getOrderStatus()));
		
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(order.getAddTime() * 1000));
		
		//订单价格信息
		OrderPrices orderPrice = orderPricesService.selectByPrimaryKey(order.getOrderId());
		Long servciePriceId = orderPrice.getServicePriceId();
		
		PartnerServicePriceDetail  partnerServicePriceDetail = partnerServicePriceDetailService.selectByPrimaryKey(servciePriceId);
		
		
		vo.setOrderMoney(orderPrice.getOrderMoney());
		vo.setOrderPay(orderPrice.getOrderPay());
		
		//设置订单的是否需要地址.
		vo.setIsAddr(partnerServicePriceDetail.getIsAddr());
		
        return vo;
	}	
	
	@Override
	public OrderDetailVo getOrderDetailVo(Orders order, OrderListVo listVo) {

	    // 加载更多订单的信息
		OrderDetailVo vo = new OrderDetailVo();
		BeanUtils.copyProperties(listVo, vo);
		
		//订单价格信息
		OrderPrices orderPrice = orderPricesService.selectByPrimaryKey(order.getOrderId());
		
		//城市名称
		vo.setCityName("");
		if (order.getCityId() > 0L) {
			String cityName = dictService.getCityName(order.getCityId());
			vo.setCityName(cityName);
		}		
		
		vo.setPayTypeName(MeijiaUtil.getPayTypeName(orderPrice.getPayType()));
		vo.setRemarks(order.getRemarks());
		vo.setServiceContent(order.getServiceContent());
		
		vo.setUserCouponId(orderPrice.getUserCouponId());
		vo.setUserCouponName("");
		vo.setUserCouponValue(new BigDecimal(0));
		if (orderPrice.getUserCouponId() > 0L) {
			UserCoupons userCoupon = userCouponService.selectByPrimaryKey(orderPrice.getUserCouponId());
			
			DictCoupons coupon = couponService.selectByPrimaryKey(userCoupon.getCouponId());
			vo.setUserCouponName(coupon.getIntroduction());
			vo.setUserCouponValue(coupon.getValue());
		}
		
        return vo;
	}		
	
	
	/*
	 * 根据订单获得秘书的有效期限
	 */
	@Override
	public Date getSeniorRangeDate(Long userId) {

		Date endDate = null;
		OrderSearchVo searchVo = new OrderSearchVo();
		searchVo.setUserId(userId);
		searchVo.setOrderStatus((short) 2);
		searchVo.setServiceTypeId((long) 75);
		List<Orders> list = ordersMapper.selectByListPage(searchVo);
		
		if (list.isEmpty()) {
			return endDate;
		}
		
		
		Orders order = list.get(0);
		
		Short orderDuration = order.getOrderDuration();
		Long addTime = order.getAddTime();
		/**
		 *  1 = 一天
			2 = 一周
			3 = 一个月
			4 = 三个月
			5 = 六个月
			6 = 九个月
			7 = 一年
		 */
		Date startDate = TimeStampUtil.timeStampToDate(addTime * 1000);
		String endDateStr = "";
		
		switch (orderDuration) {
			case 0:
				endDateStr = DateUtil.addDay(startDate, 0, Calendar.DATE, "yyyy-MM-dd");
				break;
			case 1:
				endDateStr = DateUtil.addDay(startDate, 1, Calendar.DATE, "yyyy-MM-dd");
				break;
			case 2:
				endDateStr = DateUtil.addDay(startDate, 7, Calendar.DATE, "yyyy-MM-dd");
				break;	
			case 3:
				endDateStr = DateUtil.addDay(startDate, 1, Calendar.MONTH, "yyyy-MM-dd");
				break;	
			case 4:
				endDateStr = DateUtil.addDay(startDate, 3, Calendar.MONTH, "yyyy-MM-dd");
				break;	
			case 5:
				endDateStr = DateUtil.addDay(startDate, 6, Calendar.MONTH, "yyyy-MM-dd");
				break;	
			case 6:
				endDateStr = DateUtil.addDay(startDate, 9, Calendar.MONTH, "yyyy-MM-dd");
				break;
			case 7:
				endDateStr = DateUtil.addDay(startDate, 1, Calendar.YEAR, "yyyy-MM-dd");
				break;
		}
		
		endDate = DateUtil.parse(endDateStr);
		
		return endDate;
	}	

}