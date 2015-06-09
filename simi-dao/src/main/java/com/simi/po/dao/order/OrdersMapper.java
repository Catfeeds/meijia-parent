package com.simi.po.dao.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simi.po.model.order.Orders;
import com.simi.vo.OrdersList;

public interface OrdersMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByOrderNo(String orderNo);

    Orders selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    List<Orders> queryOrdersByState(Short order_state);

    List<Orders> queryOrdersByStates(List<Short> orderStates);

    List<Orders> queryOrdersByStateAndScore();

    List<Orders> selectByListPage(HashMap conditions);

    List<Orders> queryOrdersByStateAndStartTime(HashMap conditions);

    List<Orders> queryOrdersCompletedAndUnEvaluated(HashMap conditions);

    List<OrdersList> selectByMobile(String mobile, int start, int end);

    List<OrdersList> selectByAgentMobile(String mobile, int start, int end);

    List<Orders> selectBySameDateTime(HashMap conditions);


	List<Map<String, Object>> totalCountByStartTime(Map<String, Object> conditions);

	List<String> selectDistinctMobileLists();




}