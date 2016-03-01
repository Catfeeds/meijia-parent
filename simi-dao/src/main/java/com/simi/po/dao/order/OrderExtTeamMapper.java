package com.simi.po.dao.order;

import com.simi.po.model.order.OrderExtTeam;

public interface OrderExtTeamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderExtTeam record);

    int insertSelective(OrderExtTeam record);

    OrderExtTeam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderExtTeam record);

    int updateByPrimaryKey(OrderExtTeam record);
}