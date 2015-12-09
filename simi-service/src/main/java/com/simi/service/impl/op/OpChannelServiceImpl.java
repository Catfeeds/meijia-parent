package com.simi.service.impl.op;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.op.OpChannelService;
import com.simi.vo.op.OpAdVo;
import com.simi.po.dao.op.OpChannelMapper;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.op.OpChannel;
import com.meijia.utils.TimeStampUtil;

@Service
public class OpChannelServiceImpl implements OpChannelService {

	@Autowired
	private OpChannelMapper opChannelMapper;

	@Override
	public PageInfo searchVoListPage(int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<OpChannel> list = opChannelMapper.selectByListPage();
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}

	@Override
	public OpChannel initOpChannel() {

		  OpChannel record = new OpChannel();
		    
			record.setChannelId(0L);
			record.setName("");
			record.setEnable((short)0);
			record.setAddTime(TimeStampUtil.getNow()/1000);

			return record;
		}
	@Override
	public OpChannel selectByPrimaryKey(Long channelId) {
		return opChannelMapper.selectByPrimaryKey(channelId);
	}
	
	@Override
	public List<OpChannel> selectByAll() {
		return opChannelMapper.selectByAll();
	}	

	@Override
	public int updateByPrimaryKeySelective(OpChannel record) {
		return opChannelMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(OpChannel record) {
		
		return opChannelMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		
		return opChannelMapper.deleteByPrimaryKey(id);
	}
}
