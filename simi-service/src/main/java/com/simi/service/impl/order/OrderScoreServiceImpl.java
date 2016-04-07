package com.simi.service.impl.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrderScoreService;
import com.simi.service.sec.SecService;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UsersService;
import com.simi.vo.order.OrderViewVo;
import com.simi.po.dao.order.OrderPricesMapper;
import com.simi.po.dao.order.OrderScoreMapper;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderScore;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderScoreServiceImpl implements OrderScoreService {

	@Autowired
	private OrderScoreMapper orderScoreMapper;

	@Autowired
	UsersService usersService;	
		
	@Autowired
	UserDetailScoreService userDetailScoreService;
		
	@Override
	public int deleteByPrimaryKey(Long id) {
		return orderScoreMapper.deleteByPrimaryKey(id);
	}

	@Override
	public OrderScore initOrderScore() {
		OrderScore record = new OrderScore();
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setUserId(0L);
		record.setMobile("");
		record.setOrderStatus((short) 0);		
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		record.setCredits(0);
		record.setAppkey("");
		record.setTimestamp("");
		record.setDescription("");
		record.setOrdernum("");
		record.setType("");
		record.setFaceprice("");
		record.setActualprice("");
		record.setIp("");
		record.setWaitaudit("");
		record.setParams("");
		record.setSign("");

		
		return record;
	}

	@Override
	public Long insert(OrderScore record) {
		return orderScoreMapper.insert(record);
	}

	@Override
	public Long insertSelective(OrderScore record) {
		return orderScoreMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKey(OrderScore record) {
		return orderScoreMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderScore record) {
		return orderScoreMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<OrderScore> selectByUserId(Long userId) {
		return orderScoreMapper.selectByUserId(userId);
	}


	@Override
	public OrderScore selectByOrderNo(String orderNo) {
		return orderScoreMapper.selectByOrderNo(orderNo);
	}
	
	@Override
	public OrderScore selectByPrimaryKey(Long orderId) {
		return orderScoreMapper.selectByPrimaryKey(orderId);
	}
	
	@Override
	public OrderScore selectByOrderId(Long orderId) {
		return orderScoreMapper.selectByOrderId(orderId);
	}
	
	@Override
	public OrderScore selectByOrderNum(String orderNum) {
		return orderScoreMapper.selectByOrderNum(orderNum);
	}
}