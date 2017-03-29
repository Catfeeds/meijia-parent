package com.simi.service.order;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.order.OrderScore;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderScoreVo;

public interface OrderScoreService {

	int deleteByPrimaryKey(Long id);

	Long insert(OrderScore record);

	Long insertSelective(OrderScore record);

	int updateByPrimaryKey(OrderScore record);

	int updateByPrimaryKeySelective(OrderScore record);

	List<OrderScore> selectByUserId(Long userId);

	OrderScore selectByOrderNo(String orderNo);

	OrderScore selectByPrimaryKey(Long orderId);

	OrderScore selectByOrderId(Long orderId);

	OrderScore selectByOrderNum(String orderNum);

	OrderScore initOrderScore();

	PageInfo selectByListPage(OrderSearchVo searchVo, int pageNo, int pageSize);

	List<OrderScore> selectBySearchVo(OrderSearchVo searchVo);

	OrderScoreVo getVos(OrderScore item);

}
