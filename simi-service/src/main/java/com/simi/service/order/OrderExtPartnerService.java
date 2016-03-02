package com.simi.service.order;


import com.simi.po.model.order.OrderExtPartner;

public interface OrderExtPartnerService {

	OrderExtPartner initOrderExtPartner();

	int deleteByPrimaryKey(Long id);

	int insert(OrderExtPartner record);

	int insertSelective(OrderExtPartner record);

	OrderExtPartner selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrderExtPartner record);

	int updateByPrimaryKey(OrderExtPartner record);

	OrderExtPartner selectByOrderId(Long orderId);



}