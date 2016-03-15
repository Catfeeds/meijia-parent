package com.simi.service.order;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.order.OrderExtTeam;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderExtTeamXcloudVo;
import com.simi.vo.order.OrdersExtTeamListVo;

public interface OrderExtTeamService {

	OrderExtTeam initOrderExtTeam();

	int deleteByPrimaryKey(Long id);

	int insert(OrderExtTeam record);

	int insertSelective(OrderExtTeam record);

	OrderExtTeam selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrderExtTeam record);

	int updateByPrimaryKey(OrderExtTeam record);

	PageInfo selectByListPage(OrderSearchVo searchVo, int pageNo, int pageSize);

	List<OrdersExtTeamListVo> getListVos(List<OrderExtTeam> list);

	OrderExtTeam selectByOrderId(Long orderId);

	OrdersExtTeamListVo getListVo(OrderExtTeam orderExtTeam);

	PageInfo selectByPage(OrderSearchVo searchVo, int pageNo, int pageSize);

	OrderExtTeamXcloudVo getXcloudListVo(OrderExtTeam orderExtTeam);



}