package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderExtGreenService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.utils.OrderUtil;
import com.simi.vo.order.OrderExtGreenListVo;
import com.simi.po.dao.order.OrderExtGreenMapper;
import com.simi.po.model.order.OrderExtRecycle;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.TimeStampUtil;

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
	public int insert(OrderExtRecycle record) {
		return orderExtGreenMapper.insert(record);
	}

	@Override
	public int insertSelective(OrderExtRecycle record) {
		return orderExtGreenMapper.insertSelective(record);
	}

	@Override
	public OrderExtRecycle selectByPrimaryKey(Long id) {
		return orderExtGreenMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(OrderExtRecycle record) {
		return orderExtGreenMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderExtRecycle record) {
		return orderExtGreenMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderExtRecycle initOrderExtGreen() {
		
		OrderExtRecycle record = new OrderExtRecycle();
		record.setId(0L);
		record.setUserId(0L);
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setOrderExtStatus((short)0);
		record.setRecycleType((short)0);
		record.setMobile("");
		record.setLinkMan("");
		record.setLinkTel("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public List<OrderExtRecycle> selectByUserId(Long userId) {
		
		return orderExtGreenMapper.selectByUserId(userId);
	}

	@Override
	public OrderExtGreenListVo getOrderExtGreenListVo(OrderExtRecycle item) {
		
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
		vo.setOrderStatusName(OrderUtil.getOrderStausName(order.getOrderStatus()));
		
		Long addTime = order.getAddTime()*1000;	
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime));
		
		return vo;
	}

	@Override
	public OrderExtRecycle selectByOrderId(Long orderId) {
		
		return orderExtGreenMapper.selectByOrderId(orderId);
	}

}