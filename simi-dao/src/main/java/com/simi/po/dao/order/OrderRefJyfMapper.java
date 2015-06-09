package com.simi.po.dao.order;

import com.simi.po.model.order.OrderRefJyf;

public interface OrderRefJyfMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderRefJyf record);

    int insertSelective(OrderRefJyf record);

    OrderRefJyf selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderRefJyf record);

    int updateByPrimaryKey(OrderRefJyf record);
}