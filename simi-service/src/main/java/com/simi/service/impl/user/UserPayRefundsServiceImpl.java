package com.simi.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserPayRefundsService;
import com.simi.po.dao.user.UserPayRefundsMapper;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserPayRefunds;
import com.meijia.utils.TimeStampUtil;
@Service
public class UserPayRefundsServiceImpl implements UserPayRefundsService {
	@Autowired
	private UserPayRefundsMapper userPayRefundsMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userPayRefundsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserPayRefunds record) {
		return userPayRefundsMapper.insert(record);
	}

	@Override
	public int insertSelective(UserPayRefunds record) {
		return userPayRefundsMapper.insertSelective(record);
	}

	@Override
	public UserPayRefunds selectByPrimaryKey(Long id) {
		return userPayRefundsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserPayRefunds record) {
		return userPayRefundsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserPayRefunds record) {
		return 0;
	}
	@Override
	public UserPayRefunds initUserPayRefunds(Orders orders, String mobile, String trade_status,
			Long orderId, String trade_no,Short orderType, UserPayRefunds userPayRefunds) {
		Long addTime = TimeStampUtil.getNow()/1000;
		userPayRefunds.setAddTime(addTime);
		userPayRefunds.setMobile(mobile);
		userPayRefunds.setUserId(orders.getUserId());
		userPayRefunds.setRefundStatus((short) 0);
		userPayRefunds.setOrderId(orderId);
		userPayRefunds.setOrderNo(orders.getOrderNo());
		userPayRefunds.setOrderType(orderType);
		userPayRefunds.setPostParams("");
		userPayRefunds.setTradeId(trade_no);
		userPayRefunds.setTradeStatus(trade_status);
		return userPayRefunds;
	}

}