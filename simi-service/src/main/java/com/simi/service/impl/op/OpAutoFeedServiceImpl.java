package com.simi.service.impl.op;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.op.OpAutoFeedService;
import com.simi.service.op.OpChannelService;
import com.simi.vo.po.AdSearchVo;
import com.simi.po.dao.op.OpAutoFeedMapper;
import com.simi.po.model.op.OpAutoFeed;
import com.simi.po.model.op.OpChannel;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class OpAutoFeedServiceImpl implements OpAutoFeedService {

	@Autowired
	private OpAutoFeedMapper opAutoFeedMapper;
	
	@Autowired
	private OpChannelService opChannelService;		

	@Override
	public PageInfo selectByListPage(int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<OpAutoFeed> list = opAutoFeedMapper.selectByListPage();
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}

	@Override
	public OpAutoFeed initOpAutoFeed() {

		    OpAutoFeed record = new OpAutoFeed();
			record.setId(0L);
			record.setAutoType((short) 0);
			record.setTitle("");
			record.setContent("");
			record.setStatus((short) 1);
			record.setAddTime(TimeStampUtil.getNow()/1000);

			return record;
		}
	@Override
	public OpAutoFeed selectByPrimaryKey(Long id) {
		return opAutoFeedMapper.selectByPrimaryKey(id);
	}


	@Override
	public int updateByPrimaryKeySelective(OpAutoFeed record) {
		return opAutoFeedMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Long insertSelective(OpAutoFeed opAd) {
		// TODO Auto-generated method stub
		return opAutoFeedMapper.insertSelective(opAd);
	}
	
	@Override
	public List<OpAutoFeed> selectByTotal() {
		return opAutoFeedMapper.selectByTotal();
	}
}
