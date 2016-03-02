package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderExtGreenService;
import com.simi.service.order.OrderExtWaterService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.vo.order.OrderExtGreenListVo;
import com.simi.vo.order.OrderExtWaterListVo;
import com.simi.po.dao.order.OrderExtGreenMapper;
import com.simi.po.dao.order.OrderExtWaterMapper;
import com.simi.po.model.order.OrderExtGreen;
import com.simi.po.model.order.OrderExtWater;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.alibaba.druid.util.Utils;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.TimeStampUtil;
import com.mysql.jdbc.Util;

@Service
public class OrderExtWaterServiceImpl implements OrderExtWaterService{
    @Autowired
    private OrderExtWaterMapper orderExtWaterMapper;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private UserAddrsService userAddrsService;
    
    @Autowired
    private OrdersService ordersService;
    
    @Autowired
    private PartnerServiceTypeService partnerServiceTypeService;
    
    @Autowired
    private PartnerServicePriceDetailService partnerServicePriceDetailService;
    
	@Override
	public int deleteByPrimaryKey(Long id) {
		return orderExtWaterMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderExtWater record) {
		return orderExtWaterMapper.insert(record);
	}

	@Override
	public int insertSelective(OrderExtWater record) {
		return orderExtWaterMapper.insertSelective(record);
	}

	@Override
	public OrderExtWater selectByPrimaryKey(Long id) {
		return orderExtWaterMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public OrderExtWater selectByOrderId(Long orderId) {
		return orderExtWaterMapper.selectByOrderId(orderId);
	}	

	@Override
	public int updateByPrimaryKey(OrderExtWater record) {
		return orderExtWaterMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderExtWater record) {
		return orderExtWaterMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderExtWater initOrderExtWater() {
		
		OrderExtWater record = new OrderExtWater();
		record.setId(0L);
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setUserId(0L);
		record.setServicePriceId(0L);
		record.setServiceNum(0);
		record.setLinkMan("");
		record.setLinkTel("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public List<OrderExtWater> selectByUserId(Long userId) {
		return orderExtWaterMapper.selectByUserId(userId);
	}
	
	@Override
	public OrderExtWaterListVo getListVo(OrderExtWater item) {
		OrderExtWaterListVo  vo = new OrderExtWaterListVo();
		vo.setOrderId(item.getOrderId());
		vo.setOrderNo(item.getOrderNo());
		vo.setUserId(item.getUserId());
		
		//服务地址
		vo.setAddrName("");
		Orders order = ordersService.selectByOrderNo(item.getOrderNo());
		Long addrId = order.getAddrId();
		UserAddrs userAddr = userAddrsService.selectByPrimaryKey(addrId);
		if (userAddr != null) {
			vo.setAddrName(userAddr.getName() + " " + userAddr.getAddr());
		}
		
		
		//服务大类名称
		vo.setServiceTypeName("");
		Long serviceTypeId = order.getServiceTypeId();
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
		if (serviceType != null) {
			vo.setServiceTypeName(serviceType.getName());
		}

		//服务价格商品名称
		vo.setServicePriceName("");
		vo.setImgUrl("");
		vo.setDisPrice(new BigDecimal(0));
		
		Long servicePriceId = item.getServicePriceId();
		PartnerServiceType servicePrice = partnerServiceTypeService.selectByPrimaryKey(servicePriceId);
		if (servicePrice != null) {
			vo.setServicePriceName(servicePrice.getName());
		}
		
		
		PartnerServicePriceDetail servicePriceDetail = partnerServicePriceDetailService.selectByServicePriceId(servicePriceId);
		if (servicePriceDetail != null) {
			vo.setImgUrl(servicePriceDetail.getImgUrl());
			vo.setDisPrice(servicePriceDetail.getDisPrice());
		}
		
		vo.setServiceNum(item.getServiceNum());
		
		vo.setOrderStatusName(MeijiaUtil.getOrderStausName(order.getOrderStatus()));
		vo.setAddTimeStr(TimeStampUtil.fromTodayStr(order.getAddTime() * 1000));
		return vo;
	}
	
	@Override
	public List<OrderExtWaterListVo> getListVos(List<OrderExtWater> list) {
		List<OrderExtWaterListVo>  result = new ArrayList<OrderExtWaterListVo>();
		
		List<Long> orderIds = new ArrayList<Long>();
		
		List<Long> serivcePriceIds = new ArrayList<Long>();
		//批量查找userAddrs;
		for (OrderExtWater item : list) {
			if (!orderIds.contains(item.getOrderId())) {
				orderIds.add(item.getOrderId());
			}
			
			if (!serivcePriceIds.contains(item.getServicePriceId())) {
				serivcePriceIds.add(item.getServicePriceId());
			}
		}
		
		if (orderIds.isEmpty()) return result;
		List<Orders> orders = ordersService.selectByOrderIds(orderIds);
		
		List<Long> serviceTypeIds = new ArrayList<Long>();
		List<Long> userAddrIds = new ArrayList<Long>();
		
		for (Orders item : orders) {
			if (!serviceTypeIds.contains(item.getServiceTypeId())) {
				serviceTypeIds.add(item.getServiceTypeId());
			}
			
			if(!userAddrIds.contains(item.getAddrId())) {
				if (item.getAddrId() > 0L) {
					userAddrIds.add(item.getAddrId());
				}
			}
		}
		
		List<PartnerServiceType> serviceTypes = partnerServiceTypeService.selectByIds(serviceTypeIds);
		List<PartnerServiceType> servicePrices = partnerServiceTypeService.selectByIds(serivcePriceIds);
		List<PartnerServicePriceDetail> servicePriceDetails = partnerServicePriceDetailService.selectByServicePriceIds(serivcePriceIds);
		List<UserAddrs> userAddrs = userAddrsService.selectByIds(userAddrIds);
		
		for (int i = 0; i < list.size(); i++) {
			OrderExtWater item = list.get(i);
			OrderExtWaterListVo vo = new OrderExtWaterListVo();
			
			vo.setOrderId(item.getOrderId());
			vo.setOrderNo(item.getOrderNo());
			vo.setUserId(item.getUserId());
			
			Orders order = null;
			for (Orders tmpOrder : orders) {
				if (tmpOrder.getOrderId().equals(item.getOrderId())) {
					order = tmpOrder;
					break;
				}
			}
			
			//服务地址
			vo.setAddrName("");
			Long addrId = order.getAddrId();
			UserAddrs userAddr = null;
			for (UserAddrs tmpUserAddr : userAddrs) {
				if (tmpUserAddr.getId().equals(addrId)) {
					userAddr = tmpUserAddr;
					break;
				}
			}
			
			if (userAddr != null) {
				vo.setAddrName(userAddr.getName() + " " + userAddr.getAddr());
			}
			
			
			//服务大类名称
			vo.setServiceTypeName("");
			Long serviceTypeId = order.getServiceTypeId();
			PartnerServiceType serviceType = null;
			for (PartnerServiceType tmpServiceType : serviceTypes) {
				if (tmpServiceType.getId().equals(serviceTypeId)) {
					serviceType = tmpServiceType;
					break;
				}
			}
			
			if (serviceType != null) {
				vo.setServiceTypeName(serviceType.getName());
			}
	
			//服务价格商品名称
			vo.setServicePriceName("");
			vo.setImgUrl("");
			vo.setDisPrice(new BigDecimal(0));
			
			Long servicePriceId = item.getServicePriceId();
			PartnerServiceType servicePrice = null;
			for (PartnerServiceType tmpServicePrice : servicePrices) {
				if (tmpServicePrice.getId().equals(servicePriceId)) {
					servicePrice = tmpServicePrice;
					break;
				}
			}
			if (servicePrice != null) {
				vo.setServicePriceName(servicePrice.getName());
			}
			
			
			PartnerServicePriceDetail servicePriceDetail = null;
			for (PartnerServicePriceDetail tmpServicePriceDetail : servicePriceDetails) {
				if (tmpServicePriceDetail.getServicePriceId().equals(servicePriceId)) {
					servicePriceDetail = tmpServicePriceDetail;
					break;
				}
			}
			if (servicePriceDetail != null) {
				vo.setImgUrl(servicePriceDetail.getImgUrl());
				vo.setDisPrice(servicePriceDetail.getDisPrice());
			}
			
			vo.setServiceNum(item.getServiceNum());
//			System.out.println("order_id =" + order.getOrderId().toString() + " status = " + order.getOrderStatus().toString());
			vo.setOrderStatusName(MeijiaUtil.getOrderStausName(order.getOrderStatus()));
			vo.setAddTimeStr(TimeStampUtil.fromTodayStr(order.getAddTime() * 1000));
			result.add(vo);
		}
		return result;
	}
}