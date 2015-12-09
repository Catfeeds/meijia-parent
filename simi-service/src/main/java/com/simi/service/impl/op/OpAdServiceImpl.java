package com.simi.service.impl.op;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.op.OpAdService;
import com.simi.po.dao.op.OpAdMapper;
import com.simi.po.model.op.OpAd;
import com.meijia.utils.TimeStampUtil;

@Service
public class OpAdServiceImpl implements OpAdService {

	@Autowired
	private OpAdMapper opAdMapper;

	@Override
	public PageInfo searchVoListPage(int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<OpAd> list = opAdMapper.selectByListPage();

		PageInfo result = new PageInfo(list);
		
		return result;
	}

	@Override
	public OpAd initAd() {

		    OpAd record = new OpAd();
			record.setId(0L);
			record.setNo((short)0);
			record.setImgUrl("");
			record.setGotoUrl("");
			record.setTitle("");
			record.setAdType("");
			record.setServiceTypeIds("");
			record.setAddTime(TimeStampUtil.getNow()/1000);
			record.setUpdateTime(0L);
			record.setEnable((short)1);

			return record;
		}
	@Override
	public OpAd selectByPrimaryKey(Long id) {
		return opAdMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<OpAd> selectByAdType(String adType) {
		return opAdMapper.selectByAdType(adType);
	}	

	@Override
	public int updateByPrimaryKeySelective(OpAd record) {
		return opAdMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(OpAd opAd) {
		// TODO Auto-generated method stub
		return opAdMapper.insertSelective(opAd);
	}
}
