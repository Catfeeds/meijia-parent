package com.simi.po.dao.order;

import java.util.List;

import com.simi.po.model.order.Orders;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.chart.ChartSearchVo;

public interface OrdersMapper {
	
    int deleteByPrimaryKey(Long orderId);

    Long insert(Orders record);

    int insertSelective(Orders record);
    
    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);    
    
    Orders selectByPrimaryKey(Long orderId);
    
    Orders selectByOrderNo(String orderNo);

    List<Orders> selectByListPage(OrderSearchVo orderSearchVo);

	List<Orders> selectByUserId(Long userId);
	
	List<Orders> selectByPartnerUserId(Long partnerUserId);

	List<Orders> selectByOrderStatus();

	List<Orders> selectByOrderStatus7Days();

	List<Orders> selectByorder1Hour();

	int statTotalOrder(ChartSearchVo chartSearchVo);

	List<Orders> selectIdsByListPage(List<Long> partnerUserIdList);

	List<Orders> selectByOrderIds(List<Long> orderIds);

	List<Long> getByPartnerUserId(Long partnerUseId);

	int totalByUserId(Long userId);
	
}