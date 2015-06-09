package com.simi.service.order;

import java.util.HashMap;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.vo.OrdersList;
import com.simi.vo.order.OrderSearchVo;
import com.simi.vo.order.OrderViewVo;
import com.simi.vo.order.OrdersVo;
import com.simi.po.model.order.Orders;

public interface OrderQueryService {

    List<OrdersList> selectByMobile(String mobile, int page);

    Orders selectByOrderNo(String orderNo);

    Orders selectByPrimaryKey(Long id);

    List<Orders> queryOrdersByState(Short order_state);

    List<Orders> queryOrdersByStates(List<Short> ordertates);

    List<Orders> queryOrdersByStateAndScore();

    PageInfo listPage(String mobile, String orderNo, int pageNo, int pageSize);

    PageInfo searchVoListPage(OrderSearchVo searchVo, int pageNo, int pageSize);

    PageInfo<OrdersVo> listPageOrdersVo(String mobile, String orderNo, int pageNo, int pageSize);

    List<Orders> queryOrdersByStateAndStartTime(HashMap conditions);

    List<Orders> queryOrdersCompletedAndUnEvaluated(HashMap conditions);

	List<OrderViewVo> getOrderViewList(List<Orders> list);

	List<OrdersList> selectByAgentMobile(String mobile, int page);

	List<Orders> selectBySameDateTime(String orderNo);

}