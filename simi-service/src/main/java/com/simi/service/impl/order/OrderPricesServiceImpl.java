package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.user.UserCouponService;
import com.simi.po.dao.order.OrderPricesMapper;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.user.UserCoupons;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.TimeStampUtil;

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
	public OrderPrices initOrderPrices() {
		
		OrderPrices record = new OrderPrices();
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setServicePriceId(0L);
		record.setServicePriceName("");
		record.setPartnerUserId(0L);
		record.setUserId(0L);
		record.setMobile("");
		record.setPayType((short)Constants.PAY_TYPE_0);
		record.setUserCouponId(0L);
		record.setUsedScore(0l);
		BigDecimal defaultValue = new BigDecimal(0);
		record.setOrderMoney(defaultValue);
		record.setOrderPay(defaultValue);
		record.setOrderPayBack(defaultValue);
		record.setOrderPayBackFee(defaultValue);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		return record;
	}
	
	/**
	 * 获取服务订单及优惠券等的金额，返回最终的订单支付金额
	 */
	@Override
	public BigDecimal getPayByOrder(BigDecimal orderPay, Long userCouponId) {
		
	//	BigDecimal orderPayNow = new BigDecimal(0.0);
		// 处理优惠券的情况
		if (userCouponId > 0L) {
			UserCoupons userCoupons = null;
			userCoupons = userCouponService.selectByPrimaryKey(userCouponId);

			BigDecimal couponValue = new BigDecimal(0);
			couponValue = userCoupons.getValue();
			
			// 如果优惠券金额大于订单总金额
			if (orderPay.compareTo(couponValue) == 1) {
				orderPay = orderPay.subtract(couponValue);
			} else {
				orderPay = new BigDecimal(0);
			}
			
		}
		// 实际支付金额
		/*BigDecimal p1 = new BigDecimal(100);
		BigDecimal p2 = MathBigDeciamlUtil.mul(orderPay, p1);
		orderPayNow = MathBigDeciamlUtil.round(p2, 0);*/
		orderPay = MathBigDecimalUtil.round(orderPay, 2);
		return orderPay;
	}	
	
	@Override
	public List<OrderPrices> selectByOrderIds(List<Long> orderIds) {
		return orderPricesMapper.selectByOrderIds(orderIds);
	}


}