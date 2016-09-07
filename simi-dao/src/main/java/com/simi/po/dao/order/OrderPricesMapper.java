package com.simi.po.dao.order;

import java.util.List;

import com.simi.po.model.order.OrderPrices;
import com.simi.vo.OrderSearchVo;

public interface OrderPricesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderPrices record);

    int insertSelective(OrderPrices record);

    OrderPrices selectByOrderId(Long id);

    OrderPrices selectByPrimaryKey(Long id);

    List<OrderPrices> selectByOrderIds(List<Long> orderIds);

    List<OrderPrices> selectByOrderNos(List<String> orderNos);

    int updateByPrimaryKeySelective(OrderPrices record);

    int updateByPrimaryKey(OrderPrices record);

	List<OrderPrices> selectBySearchVo(OrderSearchVo searchVo);
}