package com.simi.action.job;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gexin.rp.sdk.http.utils.Constant;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserCoupons;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserCouponService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/job/order")
public class JobOrderController extends BaseController {

	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private UserCouponService userCouponService;

	/**
	 *  订单超过1个小时未支付,则关闭订单,
	 *  就是订单的状态变成 9 ,并且把相应的优惠劵的信息置为空
	 */

	@RequestMapping(value = "check_order_more_60min", method = RequestMethod.GET)
	public AppResultData<Object> OrderMore60min(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// 1.获得秘书一小时内未支付的订单列表
	
		List<Orders> list = ordersService.selectByOrderStatus();

		for (int i = 0; i < list.size(); i++) {

			//订单状态设置为0 已关闭
			Orders record = list.get(i);
			//获得下单时间
			Long orderTime = record.getAddTime();
			//获得当前时间
			Long nowTime = TimeStampUtil.getNowSecond();
			
			//获得下单时间和但钱是捡的时间差
			Long addTime = orderTime + 3600 - nowTime;
			
			//若时间差< 0 则说明订单已经超过一小时未支付
			if (addTime < 0) {
			
			record.setOrderStatus(Constants.ORDER_STATUS_0_CLOSE);
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			ordersService.updateByPrimaryKeySelective(record);
			//相应的优惠券信息置为空
			
			if (record.getOrderNo() != ""&& record.getOrderNo() != null) {
				
			UserCoupons userCoupons = userCouponService.selectByOrderNo(record.getOrderNo());
			userCoupons.setOrderNo("0");
			userCoupons.setUpdateTime(TimeStampUtil.getNowSecond());
			userCouponService.updateByPrimaryKeySelective(userCoupons);
			}
			}
		}
		return result;
	}
	
	/**
	 * 订单如果超过7天未评价，则默认给此订单默认5星的评价
	 * ，评价内容写的是  ： 订单超时未评价，系统默认给5星好评
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "check_order_more_7Day", method = RequestMethod.GET)
	public AppResultData<Object> OrderMore7Day(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		//找出未评价的订单
		List<Orders> list = ordersService.selectByOrderStatus7Days();
		
		
		for (int i = 0; i < list.size(); i++) {
		
		Orders record = list.get(i);
		//获得最后一次更新时间
		Long updateTime = record.getUpdateTime();
		
		//获得当前时间
		Long nowTime = TimeStampUtil.getNowSecond();
		
		//获得最后一次更新时间和当前时间差
		//604800===>七天的秒数
		Long addTime = updateTime + 604800 - nowTime;
		
		//若时间差< 0 则说明订单已经超过七天未支付
		if (addTime < 0) {
		
			record.setOrderRate((short)5);
			record.setOrderRateContent("订单超时未评价，系统默认给5星好评");
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			ordersService.updateByPrimaryKeySelective(record);
		
		}
		}
		return result;
		}
	
	}
