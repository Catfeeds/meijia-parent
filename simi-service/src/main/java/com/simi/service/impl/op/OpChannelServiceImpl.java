package com.simi.service.impl.op;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.op.OpChannelService;
import com.simi.po.dao.op.OpChannelMapper;
import com.simi.po.model.op.OpChannel;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class OpChannelServiceImpl implements OpChannelService {

	@Autowired
	private OpChannelMapper opChannelMapper;

	@Override
	public PageInfo searchVoListPage(int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<OpChannel> list = opChannelMapper.selectByListPage();
		
		for (int i = 0 ; i < list.size(); i++) {
			OpChannel item = list.get(i);
			//应用类型名称
			String appType = item.getAppType();
			appType = MeijiaUtil.getAppTypeName(appType);
			item.setAppType(appType);
			//频道位置名称
			String channelPosition = item.getChannelPosition();
			channelPosition = MeijiaUtil.getChannelPositionName(channelPosition);
			item.setChannelPosition(channelPosition);
			list.set(i, item);
		}
		PageInfo result = new PageInfo(list);
		
		return result;
	}

	@Override
	public OpChannel initOpChannel() {

		  OpChannel record = new OpChannel();
		    
			record.setChannelId(0L);
			record.setName("");
			record.setAppType("");
			record.setChannelPosition("");
			record.setEnable((short)0);
			record.setAddTime(TimeStampUtil.getNow()/1000);

			return record;
		}
	@Override
	public OpChannel selectByPrimaryKey(Long channelId) {
		return opChannelMapper.selectByPrimaryKey(channelId);
	}
	
	@Override
	public List<OpChannel> selectByAppType(String appType) {
		return opChannelMapper.selectByAppType(appType);
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

	@Override
	public List<OpChannel> selectByAppTypeAndPosition(String appType,
			String channelPositon) {

		return opChannelMapper.selectByAppTypeAndPosition(appType,channelPositon);
	}
}
