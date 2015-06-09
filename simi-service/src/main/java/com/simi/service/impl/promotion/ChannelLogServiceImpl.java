package com.simi.service.impl.promotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.promotion.ChannelLogService;
import com.simi.po.dao.promotion.ChannelLogMapper;
import com.simi.po.model.promotion.ChannelLog;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class ChannelLogServiceImpl implements ChannelLogService {

	@Autowired
	private ChannelLogMapper channelLogMapper;
	
	@Override
	public ChannelLog initChannelLog() {
		ChannelLog record = new ChannelLog();
		record.setId(0L);
		record.setChannelType((short) 0);
		record.setName("");
		record.setToken("");
		record.setTotalDownloads(0);
		record.setDownloadDay(DateUtil.getNowOfDate());
		record.setAddTime(TimeStampUtil.getNow()/1000);
		record.setUpdateTime(0L);
		return record;
	}
	
	@Override
	public int insert(ChannelLog record) {
		return channelLogMapper.insert(record);
	}

	@Override
	public int insertSelective(ChannelLog record) {
		return channelLogMapper.insertSelective(record);
	}

	@Override
	public ChannelLog selectByPrimaryKey(Long id) {
		return channelLogMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ChannelLog record) {
		return channelLogMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int updateByPrimaryKey(ChannelLog record) {
		return channelLogMapper.updateByPrimaryKey(record);
	}

}
