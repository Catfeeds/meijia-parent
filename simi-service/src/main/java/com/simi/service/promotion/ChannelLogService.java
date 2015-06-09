package com.simi.service.promotion;

import com.simi.po.model.promotion.ChannelLog;

public interface ChannelLogService {

	ChannelLog initChannelLog();

	int insert(ChannelLog record);

	int insertSelective(ChannelLog record);

	ChannelLog selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ChannelLog record);

	int updateByPrimaryKey(ChannelLog record);

}
