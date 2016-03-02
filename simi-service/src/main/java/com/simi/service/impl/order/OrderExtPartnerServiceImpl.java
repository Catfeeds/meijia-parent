package com.simi.service.impl.order;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderExtPartnerService;
import com.simi.po.dao.order.OrderExtPartnerMapper;
import com.simi.po.model.order.OrderExtPartner;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderExtPartnerServiceImpl implements OrderExtPartnerService{
    @Autowired
    private OrderExtPartnerMapper orderExtPartnerMapper;
    
	@Override
	public int deleteByPrimaryKey(Long id) {
		return orderExtPartnerMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderExtPartner record) {
		return orderExtPartnerMapper.insert(record);
	}

	@Override
	public int insertSelective(OrderExtPartner record) {
		return orderExtPartnerMapper.insertSelective(record);
	}

	@Override
	public OrderExtPartner selectByPrimaryKey(Long id) {
		return orderExtPartnerMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(OrderExtPartner record) {
		return orderExtPartnerMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderExtPartner record) {
		return orderExtPartnerMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderExtPartner initOrderExtPartner() {
		
		OrderExtPartner record = new OrderExtPartner();
		
		record.setId(0L);
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setPartnerId(0L);
		record.setPartnerOrderNo("");
		record.setPartnerOrderMoney(new BigDecimal(0));
		record.setRemarks("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public OrderExtPartner selectByOrderId(Long orderId) {
		
		return orderExtPartnerMapper.selectByOrderId(orderId);
	}

}