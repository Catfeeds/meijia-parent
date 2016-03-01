package com.simi.service.impl.order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderExtTeamService;
import com.simi.po.dao.order.OrderExtTeamMapper;
import com.simi.po.model.order.OrderExtTeam;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderExtTeamServiceImpl implements OrderExtTeamService{
    @Autowired
    private OrderExtTeamMapper orderExtTeamMapper;
    
	@Override
	public int deleteByPrimaryKey(Long id) {
		return orderExtTeamMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderExtTeam record) {
		return orderExtTeamMapper.insert(record);
	}

	@Override
	public int insertSelective(OrderExtTeam record) {
		return orderExtTeamMapper.insertSelective(record);
	}

	@Override
	public OrderExtTeam selectByPrimaryKey(Long id) {
		return orderExtTeamMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(OrderExtTeam record) {
		return orderExtTeamMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderExtTeam record) {
		return orderExtTeamMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderExtTeam initOrderExtTeam() {
		
		OrderExtTeam record = new OrderExtTeam();
	    
		record.setId(0L);
		record.setCityId(0L);
		record.setUserId(0L);
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setMobile("");
		record.setServiceDays((short)0);
		record.setTeamType((short)0);
		record.setAttendNum(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

}