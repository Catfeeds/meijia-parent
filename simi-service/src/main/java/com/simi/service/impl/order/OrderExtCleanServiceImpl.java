package com.simi.service.impl.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderExtCleanService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.utils.OrderUtil;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderExtCleanListVo;
import com.simi.po.dao.order.OrderExtCleanMapper;
import com.simi.po.model.order.OrderExtClean;
import com.simi.po.model.order.OrderExtWater;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserAddrs;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderExtCleanServiceImpl implements OrderExtCleanService{
    @Autowired
    private OrderExtCleanMapper orderExtCleanMapper;
    
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
		return orderExtCleanMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderExtClean record) {
		return orderExtCleanMapper.insert(record);
	}

	@Override
	public int insertSelective(OrderExtClean record) {
		return orderExtCleanMapper.insertSelective(record);
	}

	@Override
	public OrderExtClean selectByPrimaryKey(Long id) {
		return orderExtCleanMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public OrderExtClean selectByOrderId(Long orderId) {
		return orderExtCleanMapper.selectByOrderId(orderId);
	}	

	@Override
	public int updateByPrimaryKey(OrderExtClean record) {
		return orderExtCleanMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderExtClean record) {
		return orderExtCleanMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderExtClean initOrderExtClean() {
		
		OrderExtClean record = new OrderExtClean();
		record.setId(0L);
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setOrderExtStatus((short) 0);
		record.setUserId(0L);
		record.setCompanyName("");
		record.setCleanArea((short) 0);
		record.setLinkMan("");
		record.setLinkTel("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(OrderSearchVo orderSearchVo, int pageNo, int pageSize) {

		 PageHelper.startPage(pageNo, pageSize);
         List<OrderExtClean> list = orderExtCleanMapper.selectByListPage(orderSearchVo);
                  
         PageInfo result = new PageInfo(list);
         
        return result;
    }

	@Override
	public List<OrderExtClean> selectByUserId(Long userId) {
		return orderExtCleanMapper.selectByUserId(userId);
	}
	
	@Override
	public OrderExtCleanListVo getListVo(OrderExtClean item) {
		OrderExtCleanListVo  vo = new OrderExtCleanListVo();
		vo.setOrderId(item.getOrderId());
		vo.setOrderNo(item.getOrderNo());
		vo.setUserId(item.getUserId());
		vo.setCompanyName(item.getCompanyName());
		
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
		
		vo.setOrderStatusName(OrderUtil.getOrderStausName(order.getOrderStatus()));
		vo.setAddTimeStr(TimeStampUtil.fromTodayStr(order.getAddTime() * 1000));
		vo.setOrderStatus(order.getOrderStatus());
		vo.setOrderExtStatus(item.getOrderExtStatus());
		return vo;
	}
	
	@Override
	public List<OrderExtCleanListVo> getListVos(List<OrderExtClean> list) {
		List<OrderExtCleanListVo>  result = new ArrayList<OrderExtCleanListVo>();
		
		List<Long> orderIds = new ArrayList<Long>();
		
		//批量查找userAddrs;
		for (OrderExtClean item : list) {
			if (!orderIds.contains(item.getOrderId())) {
				orderIds.add(item.getOrderId());
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
		
		List<UserAddrs> userAddrs = new ArrayList<UserAddrs>();
		
		if (!userAddrIds.isEmpty()) {
				userAddrsService.selectByIds(userAddrIds);
		}
		for (int i = 0; i < list.size(); i++) {
			OrderExtClean item = list.get(i);
			OrderExtCleanListVo vo = new OrderExtCleanListVo();
			
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
			
			if (order != null) {
				vo.setOrderStatus(order.getOrderStatus());
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
	
			
			vo.setOrderStatusName(OrderUtil.getOrderStausName(order.getOrderStatus()));
			vo.setAddTimeStr(TimeStampUtil.fromTodayStr(order.getAddTime() * 1000));
			vo.setOrderExtStatus(item.getOrderExtStatus());
			vo.setCompanyName(item.getCompanyName());
			vo.setCleanAreaName(OrderUtil.getOrderCleaAreaName(item.getCleanArea()));
			vo.setCleanTypeName(OrderUtil.getOrderCleaTypeName(item.getCleanType()));
			result.add(vo);
		}
		return result;
	}
}