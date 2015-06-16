package com.simi.service.impl.user;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.user.UserDetailPayService;
import com.simi.vo.UserSearchVo;
import com.simi.po.dao.user.UserDetailPayMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.order.OrderCards;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserDetailPay;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserDetailPayServiceImpl implements UserDetailPayService {

	@Autowired
	private UserDetailPayMapper userDetailPayMapper;

	@Autowired
	private UsersMapper usersMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userDetailPayMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserDetailPay record) {
		return userDetailPayMapper.insert(record);
	}

	@Override
	public int insertSelective(UserDetailPay record) {
		return userDetailPayMapper.insertSelective(record);
	}

	@Override
	public UserDetailPay selectByPrimaryKey(Long id) {
		return userDetailPayMapper.selectByPrimaryKey(id);
	}

	@Override
	public UserDetailPay selectByTradeNo(String tradeNo) {
		return userDetailPayMapper.selectByTradeNo(tradeNo);
	}

	@Override
	public int updateByPrimaryKey(UserDetailPay record) {
		return userDetailPayMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserDetailPay record) {
		return userDetailPayMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public UserDetailPay initUserDetailPay(String mobile, String trade_status, OrderCards orderCards,
			Long userId, Long orderId, short payType, OrderPrices orderPrices, String trade_no, String payAccount) {
		return setUserDetailPay(mobile, trade_status, orderCards.getCardOrderNo(), userId, orderId, payType,orderPrices, trade_no, payAccount);
	}

	@Override
	public UserDetailPay initUserDetailPay(String mobile, String trade_status, Orders orders,
			Long userId, Long orderId, short payType, OrderPrices orderPrices, String trade_no, String payAccount) {
		return setUserDetailPay(mobile, trade_status, orders.getOrderNo(), userId, orderId, payType, orderPrices, trade_no, payAccount);
	}

	private UserDetailPay setUserDetailPay(String mobile, String trade_status,
			String orderNo, Long userId, Long orderId, short payType, OrderPrices orderPrices, String tradeNo, String payAccount) {
		UserDetailPay userDetailPay = new UserDetailPay();
		userDetailPay.setAddTime(TimeStampUtil.getNow() / 1000);
		userDetailPay.setMobile(mobile);
		userDetailPay.setOrderNo(orderNo);

		userDetailPay.setOrderType(payType);
		userDetailPay.setOrderMoney(orderPrices.getOrderMoney());
		userDetailPay.setOrderPay(orderPrices.getOrderPay());

		//trade_no
		userDetailPay.setPayAccount(payAccount);
		userDetailPay.setTradeNo(tradeNo);
		userDetailPay.setTradeStatus(trade_status);
		userDetailPay.setUserId(userId);
		userDetailPay.setOrderId(orderId);
		userDetailPay.setPayType(payType);
		return userDetailPay;
	}

	@Override
	public UserDetailPay initUserDetailDefault() {
		UserDetailPay userDetailPay = new UserDetailPay();
		userDetailPay.setId(0L);
		userDetailPay.setUserId(0L);
		userDetailPay.setMobile("");
		userDetailPay.setOrderType((short) 0);
		userDetailPay.setOrderId(0L);
		userDetailPay.setOrderNo("");
		userDetailPay.setOrderMoney(new BigDecimal(0.0));
		userDetailPay.setOrderPay(new BigDecimal(0.0));


		userDetailPay.setTradeNo("");

		userDetailPay.setTradeStatus("");
		userDetailPay.setPayType((short) 0);
		userDetailPay.setAddTime(TimeStampUtil.getNow() / 1000);

		return userDetailPay;
	}

	/*
	 * 更新订单明细信息
	 */
	@Override
	public void updateByPayAccount (String tradeNo, String payAccount) {
		//先查找出账号明细记录
		UserDetailPay record = this.selectByTradeNo(tradeNo);
		if (record.getPayAccount() == null || record.getPayAccount().equals("")) {
			record.setPayAccount(payAccount);
			this.updateByPrimaryKey(record);
		}
	}

	@Override
	public PageInfo searchVoListPage(UserSearchVo searchVo, int pageNo,
			int pageSize) {
		HashMap<String,Object> conditions = new HashMap<String,Object>();
		 String mobile = searchVo.getMobile();

		if(mobile !=null && !mobile.isEmpty()){
			conditions.put("mobile",mobile.trim());
		}

		PageHelper.startPage(pageNo, pageSize);
		List<UserDetailPay> list = userDetailPayMapper.selectByListPage(conditions);
		PageInfo result = new PageInfo(list);
		return result;
	}




}