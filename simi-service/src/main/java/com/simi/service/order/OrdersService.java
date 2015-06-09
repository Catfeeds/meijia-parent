package com.simi.service.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;

public interface OrdersService {

	int cancelOrderAbout(Orders orders, OrderPrices orderPrices);

	void orderPaySuccess(String mobile, Orders orders);

    int deleteByPrimaryKey(Long id);

	public Orders initOrders(String mobile, int city_id, int service_type,
			Long service_date, Long start_time, int service_hour, Users u,
			String orderNo, Long now, Orders orders) ;

    Long insert(Orders record);

    int insertSelective(Orders record);

    int updateByPrimaryKeySelective(Orders record);

    int upderOrdeRateAbout(Orders record, UserDetailScore userDetailScore);

    int updateByPrimaryKey(Orders record);

	void orderPaySuccessSendToAdmin(String mobile, Orders orders);

	List<Orders> selectBySameDateTime(HashMap conditions);

	List<Map<String, Object>> selectOrdersCountByYearAndMonth(String start,String end);

	void orderPaySuccessSendToSenior(Orders orders);

	List<String> selectDistinctMobileLists();

	int insert(Orders orders, OrderPrices orderPrices);

}