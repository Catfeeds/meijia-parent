package com.simi.service.impl.xcloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyBenzMapper;
import com.simi.po.model.xcloud.XcompanyBenz;
import com.simi.service.xcloud.XcompanyBenzService;

@Service
public class XcompanyBenzServiceImpl implements XcompanyBenzService {
	
	@Autowired
	XcompanyBenzMapper xCompanyBenzMapper;		
	
	
	@Override
	public XcompanyBenz initXcompanyBenz() {
		XcompanyBenz record = new XcompanyBenz();
		record.setBenzId(0L);
		record.setCompanyId(0L);
		record.setName("");
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return xCompanyBenzMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insert(XcompanyBenz record) {
		return xCompanyBenzMapper.insert(record);
	}
	
	@Override
	public Long insertSelective(XcompanyBenz record) {
		return xCompanyBenzMapper.insertSelective(record);
	}

	@Override
	public XcompanyBenz selectByPrimaryKey(Long id) {
		return xCompanyBenzMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKey(XcompanyBenz record) {
		return xCompanyBenzMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyBenz record) {
		return xCompanyBenzMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<XcompanyBenz> selectByXcompanyId(Long xcompanyId) {
		return xCompanyBenzMapper.selectByXcompanyId(xcompanyId);
	}

}

