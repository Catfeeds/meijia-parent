package com.simi.service.impl.user;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.order.OrderPricesService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserDetailPayService;
import com.simi.vo.UserSearchVo;
import com.simi.vo.order.OrderListVo;
import com.simi.vo.user.UserDetailPayVo;
import com.simi.common.Constants;
import com.simi.po.dao.user.UserDetailPayMapper;
import com.simi.po.dao.user.UserFeedbackMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.order.OrderCards;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.OrderSenior;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserDetailPay;
import com.simi.po.model.user.UserFeedback;
import com.simi.po.model.user.Users;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserDetailPayServiceImpl implements UserDetailPayService {

	@Autowired
	private UserDetailPayMapper userDetailPayMapper;

	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private OrderPricesService orderPricesService;
	
	@Autowired PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private UserFeedbackMapper userFeedbackMapper;
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
	public UserDetailPay initUserDetail() {
		UserDetailPay userDetailPay = new UserDetailPay();
		userDetailPay.setId(0L);
		userDetailPay.setPayAccount("");
		userDetailPay.setUserId(0L);
		userDetailPay.setMobile("");
		userDetailPay.setOrderType((short) 0);
		userDetailPay.setOrderId(0L);
		userDetailPay.setOrderNo("");
		userDetailPay.setOrderMoney(new BigDecimal(0.0));
		userDetailPay.setOrderPay(new BigDecimal(0.0));
		userDetailPay.setRestMoney(new BigDecimal(0.0));
		userDetailPay.setTradeNo("");
		userDetailPay.setTradeStatus("");
		userDetailPay.setPayType((short) 0);
		userDetailPay.setAddTime(TimeStampUtil.getNow() / 1000);
		return userDetailPay;
	}	
	
	/**
	 * 用户明细- 订单支付.
	 */
	@Override
	public UserDetailPay userDetailPayForOrder(
			Users user, 
			Orders order, 
			OrderPrices orderPrice, 
			String tradeStatus,
			String tradeNo, 
			String payAccount) {
		
		UserDetailPay userDetailPay = new UserDetailPay();
		
		userDetailPay.setUserId(user.getId());
		userDetailPay.setMobile(user.getMobile());
		userDetailPay.setOrderId(order.getOrderId());
		userDetailPay.setOrderNo(order.getOrderNo());

		userDetailPay.setOrderType(Constants.ORDER_TYPE_0);
		userDetailPay.setPayType(orderPrice.getPayType());
		userDetailPay.setOrderMoney(orderPrice.getOrderMoney());
		userDetailPay.setOrderPay(orderPrice.getOrderPay());
		userDetailPay.setRestMoney(user.getRestMoney());
		//trade_no
		userDetailPay.setPayAccount(payAccount);
		userDetailPay.setTradeNo(tradeNo);
		userDetailPay.setTradeStatus(tradeStatus);
		
		userDetailPay.setAddTime(TimeStampUtil.getNowSecond());
		
		userDetailPayMapper.insert(userDetailPay);
		return userDetailPay;
	}
	
	
	/**
	 * 用户明细- 私秘卡支付.
	 */
	@Override
	public UserDetailPay userDetailPayForOrderSenior(
			Users user, 
			OrderSenior orderSenior, 
			String tradeStatus,
			String tradeNo, 
			String payAccount) {
		
		UserDetailPay userDetailPay = initUserDetail();
		
		userDetailPay.setUserId(user.getId());
		userDetailPay.setMobile(user.getMobile());
		userDetailPay.setOrderId(orderSenior.getId());
		userDetailPay.setOrderNo(orderSenior.getSeniorOrderNo());

		userDetailPay.setOrderType(Constants.ORDER_TYPE_2);
		userDetailPay.setPayType(orderSenior.getPayType());
		userDetailPay.setOrderMoney(orderSenior.getOrderMoney());
		userDetailPay.setOrderPay(orderSenior.getOrderPay());
		userDetailPay.setRestMoney(user.getRestMoney());
		//trade_no
		userDetailPay.setPayAccount(payAccount);
		userDetailPay.setTradeNo(tradeNo);
		userDetailPay.setTradeStatus(tradeStatus);
		
		userDetailPayMapper.insert(userDetailPay);
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
	@Override
	public PageInfo searchUserFeedbackListPage(int pageNo, int pageSize) {
		
			 PageHelper.startPage(pageNo, pageSize);
	         List<UserFeedback> list = userFeedbackMapper.selectByListPage();
	        PageInfo result = new PageInfo(list);
			return result;
	
	}
	/**
	 * 用户明细- 会员卡充值明细
	 */
	@Override
	public UserDetailPay userDetailPayForOrderCard(
			Users user, 
			OrderCards orderCard, 
			String tradeStatus,
			String tradeNo, 
			String payAccount) {
		
		UserDetailPay userDetailPay = new UserDetailPay();
		
		userDetailPay.setUserId(user.getId());
		userDetailPay.setMobile(user.getMobile());
		userDetailPay.setOrderId(orderCard.getId());
		userDetailPay.setOrderNo(orderCard.getCardOrderNo());

		userDetailPay.setOrderType(Constants.ORDER_TYPE_1);
		userDetailPay.setPayType(orderCard.getPayType());
		userDetailPay.setOrderMoney(orderCard.getCardMoney());
		userDetailPay.setOrderPay(orderCard.getCardPay());
		userDetailPay.setRestMoney(user.getRestMoney());
		//trade_no
		userDetailPay.setPayAccount(payAccount);
		userDetailPay.setTradeNo(tradeNo);
		userDetailPay.setTradeStatus(tradeStatus);
		
		userDetailPay.setAddTime(TimeStampUtil.getNowSecond());
		
		userDetailPayMapper.insert(userDetailPay);
		return userDetailPay;
	}

	@Override
	public List<UserDetailPay> selectByUserIdPage(Long userId, int page) {
		int start = 0;
		int end = Constants.PAGE_MAX_NUMBER;
		if (page > 1) {
			start = (page - 1) * Constants.PAGE_MAX_NUMBER;
//			end = page * Constants.PAGE_MAX_NUMBER;
		}
		return userDetailPayMapper.selectByUserIdPage(userId, start, end);
	}

	@Override
	public UserDetailPayVo getUserDetailPayVo(UserDetailPay userDetailPay) {
		
		UserDetailPayVo vo = new UserDetailPayVo();
		BeanUtils.copyProperties(userDetailPay, vo);
		
		//订单类型名称
		if (userDetailPay.getOrderType() == Constants.ORDER_TYPE_0) {
			OrderPrices orderPrices = orderPricesService.selectByOrderId(vo.getOrderId());
			if (orderPrices.getServicePriceId() != null) {
				PartnerServiceType partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(orderPrices.getServicePriceId());
				vo.setOrderTypeName(partnerServiceType.getName());
			}else {
				vo.setOrderTypeName("");
			}
			
		}
		if (userDetailPay.getOrderType() == Constants.ORDER_TYPE_1) {
			vo.setOrderTypeName("购买充值卡");
		}
		if (userDetailPay.getOrderType() == Constants.ORDER_TYPE_2) {
			vo.setOrderTypeName("购买私秘卡");
		}
		if (userDetailPay.getOrderType() == Constants.ORDER_TYPE_3) {
			vo.setOrderTypeName("订单退款");
		}
		
		//日期格式的字符串
		if (userDetailPay.getAddTime() != null) {
			Long addTime = userDetailPay.getAddTime()*1000;
			//vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime));
			vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime, "yyyy-mm-dd"));
		}
		
		return vo;
	}
	

	




}