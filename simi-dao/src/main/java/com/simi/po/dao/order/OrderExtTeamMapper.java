package com.simi.po.dao.order;

import java.util.List;

import com.simi.po.model.order.OrderExtTeam;
import com.simi.vo.OrderSearchVo;

public interface OrderExtTeamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderExtTeam record);

    int insertSelective(OrderExtTeam record);

    OrderExtTeam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderExtTeam record);

    int updateByPrimaryKey(OrderExtTeam record);

	List<OrderExtTeam> selectByListPage(OrderSearchVo orderSearchVo);

	OrderExtTeam selectByOrderId(Long orderId);
}