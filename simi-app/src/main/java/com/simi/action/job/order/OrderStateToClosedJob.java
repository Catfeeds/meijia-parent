package com.simi.action.job.order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simi.action.job.BaseJob;
import com.simi.common.Constants;
import com.simi.po.model.order.Orders;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.meijia.utils.TimeStampUtil;

/**
 * 7天后未评价的订单，自动好评和订单状态改为已完成
 *
 * 执行频率，每天晚上01:00执行
 * 判断条件：
 * 	2）、Long endTime = start_time+service_hour*3600
 * 		a.服务时间已经结束 endTime<now
 * 		b.订单状态为order_status in(3,4,5)
 * 		c.服务结束时间大于7天
 * 3）、 符合2）条件的数据， set order_status = 6,  order_rate = 0, order_rate_content = “系统自动好评
 *
 * @author kerry
 *
 */
@Controller
public class OrderStateToClosedJob extends BaseJob {
	@Autowired
	private OrdersService ordersService;

	@Autowired
    private OrderQueryService orderQueryService;

	public void changeOrderStateToClosedJob(){

		//获得订单状态为3,4,5的所用订单
//		List<Short> orderStates = new ArrayList<Short>();
//		orderStates.add((short)3);
//		orderStates.add((short)4);
//		orderStates.add((short)5);
//
//		List<Orders> list = orderQueryService.queryOrdersByStates(orderStates);
//		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//			Orders orders = (Orders) iterator.next();
//			Long endTime = orders.getStartTime()+orders.getServiceHours()*3600;
//			Long timeAfter7Days = endTime+604800;
//			Long nowSeconds = TimeStampUtil.getNow()/1000;
//			String orderRateContent = orders.getOrderRateContent();
//			//当订单已经结束,大于七天为评价，则自动好评
//			if(endTime<=nowSeconds && timeAfter7Days <=nowSeconds &&
//					(orderRateContent==null || orderRateContent.equals(""))){
//				orders.setOrderStatus(Constants.ORDER_STATS_6_COMPLETE);
//				orders.setOrderRate(Constants.ORDER_RATE_GOOD);
//				orders.setOrderRateContent("系统自动好评");
//				ordersService.updateByPrimaryKeySelective(orders);
//			}
//		}
	}
}
