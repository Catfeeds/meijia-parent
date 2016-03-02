package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderExtGreenService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.vo.order.OrderExtGreenListVo;
import com.simi.po.dao.order.OrderExtGreenMapper;
import com.simi.po.model.order.OrderExtGreen;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.alibaba.druid.util.Utils;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.mysql.jdbc.Util;

@Service
public class OrderExtGreenServiceImpl implements OrderExtGreenService{
    @Autowired
    private OrderExtGreenMapper orderExtGreenMapper;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private UserAddrsService userAddrsService;
    
    @Autowired
    private OrdersService ordersService;
    
    @Autowired
    private PartnerServiceTypeService partnerServiceTypeService;
    
	@Override
	public int deleteByPrimaryKey(Long id) {
		return orderExtGreenMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderExtGreen record) {
		return orderExtGreenMapper.insert(record);
	}

	@Override
	public int insertSelective(OrderExtGreen record) {
		return orderExtGreenMapper.insertSelective(record);
	}

	@Override
	public OrderExtGreen selectByPrimaryKey(Long id) {
		return orderExtGreenMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(OrderExtGreen record) {
		return orderExtGreenMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderExtGreen record) {
		return orderExtGreenMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderExtGreen initOrderExtGreen() {
		
		OrderExtGreen record = new OrderExtGreen();
		record.setId(0L);
		record.setUserId(0L);
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setMobile("");
		record.setTotalNum(0L);
		record.setTotalBudget(new BigDecimal(0));
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public List<OrderExtGreen> selectByUserId(Long userId) {
		
		return orderExtGreenMapper.selectByUserId(userId);
	}

	@Override
	public OrderExtGreenListVo getOrderExtGreenListVo(OrderExtGreen item) {
		
		OrderExtGreenListVo vo = new OrderExtGreenListVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
		
		Users users = usersService.selectByPrimaryKey(item.getUserId());
		vo.setName(users.getName());
		
		//vo.setServiceTypeName("绿植设计租摆");
		Orders order = ordersService.selectByOrderNo(item.getOrderNo());
		PartnerServiceType  serviceType = partnerServiceTypeService.selectByPrimaryKey(order.getServiceTypeId());
		vo.setServiceTypeName(serviceType.getName());
		//用户地址
		vo.setAddrName("");
		if (order.getAddrId() > 0L) {
			UserAddrs userAddr = userAddrsService.selectByPrimaryKey(order.getAddrId());
			vo.setAddrName(userAddr.getName() + userAddr.getAddr());
		}
		//订单状态
		//0 = 已关闭 1 = 待支付 2 = 已支付 3 = 处理中 7 = 待评价 9 = 已完成
		if (order.getOrderStatus() == 0) {
				vo.setOrderStatusName("已关闭");
		}
		if (order.getOrderStatus() == 1) {
				vo.setOrderStatusName("待支付");
		}
		if (order.getOrderStatus() == 2) {
				vo.setOrderStatusName("已支付");
		}
		if (order.getOrderStatus() == 3) {
				vo.setOrderStatusName("处理中");
		}
		if (order.getOrderStatus() == 7) {
				vo.setOrderStatusName("待评价");
		}
		if (order.getOrderStatus() == 9) {
				vo.setOrderStatusName("已完成");
		}
		
		Long addTime = order.getAddTime()*1000;	
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime));
		
		return vo;
	}

	@Override
	public OrderExtGreen selectByOrderId(Long orderId) {
		
		return orderExtGreenMapper.selectByOrderId(orderId);
	}

}