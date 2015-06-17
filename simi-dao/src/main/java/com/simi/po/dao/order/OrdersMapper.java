package com.simi.po.dao.order;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.order.Orders;
import com.simi.vo.OrderSearchVo;

public interface OrdersMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(Orders record);

    int insertSelective(Orders record);
    
    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);    

    Orders selectByOrderNo(String orderNo);

    Orders selectByPrimaryKey(Long id);

    List<Orders> selectByListPage(OrderSearchVo orderSearchVo);

    List<Orders> selectByStatus(Short orderStatus);

    List<Orders> selectByStatuses(List<Short> orderStatus);

	List<String> selectByDistinctMobileLists();

}