package com.simi.po.dao.order;

import com.simi.po.model.order.OrderExtPartner;

public interface OrderExtPartnerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderExtPartner record);

    int insertSelective(OrderExtPartner record);

    OrderExtPartner selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderExtPartner record);

    int updateByPrimaryKey(OrderExtPartner record);

	OrderExtPartner selectByOrderId(Long orderId);
}