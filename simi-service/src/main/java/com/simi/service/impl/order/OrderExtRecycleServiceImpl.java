package com.simi.service.impl.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderExtRecycleService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.utils.OrderUtil;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderExtRecycleListVo;
import com.simi.vo.order.OrderExtRecycleXcloudVo;
import com.simi.po.dao.order.OrderExtRecycleMapper;
import com.simi.po.model.order.OrderExtRecycle;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderExtRecycleServiceImpl implements OrderExtRecycleService {
	@Autowired
	private OrderExtRecycleMapper orderExtRecycleMapper;

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
		return orderExtRecycleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderExtRecycle record) {
		return orderExtRecycleMapper.insert(record);
	}

	@Override
	public int insertSelective(OrderExtRecycle record) {
		return orderExtRecycleMapper.insertSelective(record);
	}

	@Override
	public OrderExtRecycle selectByPrimaryKey(Long id) {
		return orderExtRecycleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(OrderExtRecycle record) {
		return orderExtRecycleMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderExtRecycle record) {
		return orderExtRecycleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderExtRecycle initOrderExtRecycle() {

		OrderExtRecycle record = new OrderExtRecycle();
		record.setId(0L);
		record.setUserId(0L);
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setOrderExtStatus((short) 0);
		record.setRecycleType((short) 0);
		record.setMobile("");
		record.setLinkMan("");
		record.setLinkTel("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public List<OrderExtRecycle> selectByUserId(Long userId) {

		return orderExtRecycleMapper.selectByUserId(userId);
	}

	@Override
	public OrderExtRecycleListVo getOrderExtRecycleListVo(OrderExtRecycle item) {

		OrderExtRecycleListVo vo = new OrderExtRecycleListVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);

		Users users = usersService.selectByPrimaryKey(item.getUserId());
		vo.setName(users.getName());

		// vo.setServiceTypeName("绿植设计租摆");
		Orders order = ordersService.selectByOrderNo(item.getOrderNo());
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(order.getServiceTypeId());
		vo.setServiceTypeName(serviceType.getName());
		// 用户地址
		vo.setAddrName("");
		if (order.getAddrId() > 0L) {
			UserAddrs userAddr = userAddrsService.selectByPrimaryKey(order.getAddrId());
			if (userAddr != null) {
				vo.setAddrName(userAddr.getName() + userAddr.getAddr());
			}

		}
		// 订单状态
		vo.setOrderStatusName(OrderUtil.getOrderStausName(order.getOrderStatus()));

		Long addTime = order.getAddTime() * 1000;
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime));

		vo.setRecycleTypeName(OrderUtil.getOrderRecycleTypeName(item.getRecycleType()));

		return vo;
	}

	@Override
	public OrderExtRecycle selectByOrderId(Long orderId) {

		return orderExtRecycleMapper.selectByOrderId(orderId);
	}

	@Override
	public PageInfo selectByPage(OrderSearchVo searchVo, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<OrderExtRecycleXcloudVo> listVo = new ArrayList<OrderExtRecycleXcloudVo>();
		List<OrderExtRecycle> list = orderExtRecycleMapper.selectByListPage(searchVo);
		OrderExtRecycle item = null;
		for (int i = 0; i < list.size(); i++) {
			item = list.get(i);
			OrderExtRecycleXcloudVo vo = this.getXcloudList(item);
			list.set(i, vo);
		}
		PageInfo result = new PageInfo(list);
		return result;
	}

	private OrderExtRecycleXcloudVo getXcloudList(OrderExtRecycle item) {
		OrderExtRecycleXcloudVo vo = new OrderExtRecycleXcloudVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);

		Users users = usersService.selectByPrimaryKey(item.getUserId());
		// vo.setName(users.getName());

		// vo.setServiceTypeName("绿植设计租摆");
		Orders order = ordersService.selectByOrderNo(item.getOrderNo());
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(order.getServiceTypeId());
		vo.setServiceTypeName(serviceType.getName());
		// 用户地址
		vo.setAddrName("");
		if (order.getAddrId() > 0L) {
			UserAddrs userAddr = userAddrsService.selectByPrimaryKey(order.getAddrId());
			vo.setAddrName(userAddr.getName() + userAddr.getAddr());
		}
		// 订单状态
		vo.setOrderStatusName(OrderUtil.getOrderStausNameRecycle(order.getOrderStatus(), vo.getOrderExtStatus()));

		Long addTime = order.getAddTime() * 1000;
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime));

		vo.setOrderExtStatusName("");
		if (item.getOrderExtStatus() == 0) {
			vo.setOrderExtStatusName("运营人员处理中");
		}
		if (item.getOrderExtStatus() == 1) {
			vo.setOrderExtStatusName("已转派服务商");
		}

		vo.setRecycleTypeName(OrderUtil.getOrderRecycleTypeName(item.getRecycleType()));

		return vo;

	}

}