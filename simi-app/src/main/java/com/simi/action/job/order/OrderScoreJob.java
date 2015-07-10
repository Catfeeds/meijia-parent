package com.simi.action.job.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;

/**
 * 已经完成的订单，用户获得积分
 *
 * 执行频率： 每30分钟执行
 * @author kerry
 *
 */
@Controller
public class OrderScoreJob {

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
    private OrderQueryService orderQueryService;

	@Autowired
	private UserDetailScoreService userDetailScoreService;

	@Autowired
	private UsersService usersService;

	public void orderScoreAdd(){

//		//查询定单状态为 2,3,4,5,6，并且积分返回为0的订单
//		List<Orders> list = orderQueryService.queryOrdersByStateAndScore();
//		for (Orders orders : list) {
//			//查询已经成功完成的单子，返回相应的积分
//			if((orders.getStartTime()+orders.getServiceHours()*3600)<(TimeStampUtil.getNow()/1000)){
//
//				OrderPrices orderPrices = orderPricesService.selectByOrderId(orders.getId());
//				//每五元一个积分
//				int  credits = MathBigDeciamlUtil.div(orderPrices.getOrderPay(), new BigDecimal(5)).intValue();
//				//用户积分明细表做记录
//				UserDetailScore userDetailScore = new UserDetailScore();
//				userDetailScore.setUserId(orders.getUserId());
//				userDetailScore.setMobile(orders.getMobile());
//				userDetailScore.setScore(credits);
//				userDetailScore.setActionId(Constants.ACTION_ORDRE);
//				userDetailScore.setIsConsume(Constants.CONSUME_SCORE_GET);
//				userDetailScore.setAddTime(TimeStampUtil.getNow()/1000);
//				userDetailScoreService.insert(userDetailScore);
//				//对应用户的score进行相应的更新
//				Users users = usersService.getUserById(orders.getUserId());
//				users.setScore(users.getScore()+credits);
//				usersService.updateByPrimaryKeySelective(users);
//				//更新订单表的是否返还积分字段
//				orders.setIsScore(Constants.RETURN_SCORE_YES);
//				ordersService.updateByPrimaryKeySelective(orders);
//			}
//		}
	}
}
