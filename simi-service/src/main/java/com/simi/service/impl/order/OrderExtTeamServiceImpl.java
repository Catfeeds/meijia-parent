package com.simi.service.impl.order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderExtTeamService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UsersService;
import com.simi.utils.OrderUtil;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderExtWaterListVo;
import com.simi.vo.order.OrdersExtTeamListVo;
import com.simi.po.dao.dict.DictCityMapper;
import com.simi.po.dao.order.OrderExtTeamMapper;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.order.OrderExtTeam;
import com.simi.po.model.order.OrderExtWater;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.sun.tools.classfile.StackMapTable_attribute.chop_frame;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderExtTeamServiceImpl implements OrderExtTeamService{
    @Autowired
    private OrderExtTeamMapper orderExtTeamMapper;
    
    @Autowired
    private OrdersService ordersService;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private DictCityMapper dictCityMapper;
     
    
	@Override
	public int deleteByPrimaryKey(Long id) {
		return orderExtTeamMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderExtTeam record) {
		return orderExtTeamMapper.insert(record);
	}

	@Override
	public int insertSelective(OrderExtTeam record) {
		return orderExtTeamMapper.insertSelective(record);
	}

	@Override
	public OrderExtTeam selectByPrimaryKey(Long id) {
		return orderExtTeamMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(OrderExtTeam record) {
		return orderExtTeamMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderExtTeam record) {
		return orderExtTeamMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderExtTeam initOrderExtTeam() {
		
		OrderExtTeam record = new OrderExtTeam();
	    
		record.setId(0L);
		record.setCityId(0L);
		record.setUserId(0L);
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setOrderExtStatus((short)0);
		record.setMobile("");
		record.setServiceDays((short)0);
		record.setTeamType((short)0);
		record.setAttendNum(0L);
		record.setLinkMan("");
		record.setLinkTel("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(OrderSearchVo orderSearchVo, int pageNo, int pageSize) {

		 PageHelper.startPage(pageNo, pageSize);
         List<OrderExtTeam> list = orderExtTeamMapper.selectByListPage(orderSearchVo);
         PageInfo result = new PageInfo(list);
        return result;
    }

	@Override
	public List<OrdersExtTeamListVo> getListVos(List<OrderExtTeam> list) {
  
		List<OrdersExtTeamListVo>  result = new ArrayList<OrdersExtTeamListVo>();
		
		List<Long> orderIds = new ArrayList<Long>();
		for (OrderExtTeam item : list) {
			if (!orderIds.contains(item.getOrderId())) {
				orderIds.add(item.getOrderId());
			}
		}
		if (orderIds.isEmpty()) return result;
		List<Orders> orders = ordersService.selectByOrderIds(orderIds);
		
		for (int i = 0; i < list.size(); i++) {
			OrderExtTeam item = list.get(i);
			OrdersExtTeamListVo vo = new OrdersExtTeamListVo();
			
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			Orders order = null;
			for (Orders tmpOrder : orders) {
				if (tmpOrder.getOrderId().equals(item.getOrderId())) {
					order = tmpOrder;
					break;
				}
			}
			Users users = usersService.selectByPrimaryKey(item.getUserId());
			vo.setName(users.getName());
			vo.setOrderStatusName(OrderUtil.getOrderStausName(order.getOrderStatus()));
			vo.setAddTimeStr(TimeStampUtil.fromTodayStr(order.getAddTime() * 1000));
			DictCity dictCity = dictCityMapper.selectByPrimaryKey(item.getCityId());
			
			vo.setCityName("");
			if (dictCity != null) {
			vo.setCityName(dictCity.getName());
			
			vo.setTeamTypeName(OrderUtil.getOrderTeamTypeName(item.getTeamType()));
			
			
			}
			
			result.add(vo);
		}
		return result;
	}

	@Override
	public OrderExtTeam selectByOrderId(Long orderId) {

		return orderExtTeamMapper.selectByOrderId(orderId);
	}

	@Override
	public OrdersExtTeamListVo getListVo(OrderExtTeam orderExtTeam) {
		OrdersExtTeamListVo vo = new OrdersExtTeamListVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(orderExtTeam, vo);
		Orders order = ordersService.selectByOrderNo(orderExtTeam.getOrderNo());
		
		Users users = usersService.selectByPrimaryKey(orderExtTeam.getUserId());
		vo.setName(users.getName());
		
		vo.setOrderStatusName(OrderUtil.getOrderStausName(order.getOrderStatus()));
		vo.setAddTimeStr(TimeStampUtil.fromTodayStr(order.getAddTime() * 1000));
		DictCity dictCity = dictCityMapper.selectByPrimaryKey(orderExtTeam.getCityId());
		
		vo.setCityName("");
		if (dictCity != null) {
		vo.setCityName(dictCity.getName());
		}
		
		vo.setTeamTypeName(OrderUtil.getOrderTeamTypeName(orderExtTeam.getTeamType()));
		return vo;
	}

}