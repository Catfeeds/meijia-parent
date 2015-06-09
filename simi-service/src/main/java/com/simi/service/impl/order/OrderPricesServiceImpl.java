package com.simi.service.impl.order;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.user.UserCouponService;
import com.simi.po.dao.order.OrderPricesMapper;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.Users;
import com.meijia.utils.MathBigDeciamlUtil;

@Service
public class OrderPricesServiceImpl implements OrderPricesService{
    @Autowired
    private OrderPricesMapper orderPricesMapper;
    
	@Autowired
	UserCouponService userCouponService;   
	
	@Autowired
    private OrderQueryService orderQueryService;	

	@Override
	public int deleteByPrimaryKey(Long id) {
		return orderPricesMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderPrices record) {
		return orderPricesMapper.insert(record);
	}

	@Override
	public int insertSelective(OrderPrices record) {
		return orderPricesMapper.insertSelective(record);
	}

	@Override
	public OrderPrices selectByOrderId(Long id) {
		return orderPricesMapper.selectByOrderId(id);
	}

	@Override
	public OrderPrices selectByPrimaryKey(Long id) {
		return orderPricesMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(OrderPrices record) {
		return orderPricesMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderPrices record) {
		return orderPricesMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderPrices initOrderPrices(String mobile, Users u,
			BigDecimal orderPay, BigDecimal orderMoney, String orderNo,
			Long now, OrderPrices orderPrices) {
		orderPrices.setAddTime(now);
		orderPrices.setMobile(mobile);
		orderPrices.setOrderMoney(orderMoney);
		orderPrices.setUserId(u.getId());
		orderPrices.setOrderNo(orderNo);
		orderPrices.setCardPasswd("");
		orderPrices.setOrderPay(orderPay);
		orderPrices.setOrderPayBack(orderPay);
		orderPrices.setOrderPayBackFee(new BigDecimal(Constants.BACK_MONEY_FEE));
		orderPrices.setCleanTools((short) 0);
		orderPrices.setCleanToolsPrice(new BigDecimal(0));
		orderPrices.setPriceHour(Constants.PRICE_HOUR);
		orderPrices.setPriceHourDiscount(Constants.PRICE_HOUR_DIS_COUNT);
		orderPrices.setUsedScore(0l);
		orderPrices.setPayType((short)Constants.PAY_TYPE_0);
		orderPrices.setUpdateTime(now);
		return orderPrices;
	}
	
	/**
	 * 获取服务订单及优惠券等的金额，返回最终的订单支付金额
	 */
	@Override
	public BigDecimal getPayByOrder(String orderNo, String cardPasswd) {
		
		BigDecimal orderPayNow = new BigDecimal(0);
		Orders order = orderQueryService.selectByOrderNo(orderNo);
		if (order == null) {
			return orderPayNow;
		}
		OrderPrices orderPrices = this.selectByOrderId(order.getId());

		BigDecimal orderMoney = orderPrices.getOrderMoney();
		BigDecimal orderPay = orderPrices.getOrderMoney();
		String mobile = order.getMobile();
		Short orderFrom = order.getOrderFrom();
		Short serviceType = order.getServiceType();
		
		if (cardPasswd == null || cardPasswd.equals("0")) {
			cardPasswd = orderPrices.getCardPasswd();
		}
		// 处理优惠券的情况

		UserCoupons userCoupons = null;
		// 验证优惠券是否正确.
		if (cardPasswd!= null && !cardPasswd.equals("") && !cardPasswd.equals("0")) {
			userCoupons = userCouponService.selectByMobileCardPwd(mobile,
					cardPasswd);

			orderPay = orderPrices.getOrderMoney();
			BigDecimal couponValue = new BigDecimal(0);

			couponValue = userCoupons.getValue();
			// 如果优惠券金额大于订单总金额
			if (orderMoney.compareTo(couponValue) == 1) {
				orderPay = orderMoney.subtract(couponValue);
			} else {
				orderPay = new BigDecimal(0);
			}
			orderPrices.setOrderPay(orderPay);
		}

		// 实际支付金额
		BigDecimal p1 = new BigDecimal(100);
		BigDecimal p2 = MathBigDeciamlUtil.mul(orderPay, p1);
		orderPayNow = MathBigDeciamlUtil.round(p2, 0);

		return orderPayNow;
	}	


}