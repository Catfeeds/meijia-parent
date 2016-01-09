package com.simi.service.impl.xcloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyBenzTimeMapper;
import com.simi.po.model.xcloud.XcompanyBenzTime;
import com.simi.service.xcloud.XcompanyBenzTimeService;

@Service
public class XcompanyBenzTimeServiceImpl implements XcompanyBenzTimeService {
	
	@Autowired
	XcompanyBenzTimeMapper xCompanyBenzTimeMapper;		
	
	
	@Override
	public XcompanyBenzTime initXcompanyBenzTime() {
		XcompanyBenzTime record = new XcompanyBenzTime();
		record.setId(0L);
		record.setBenzId(0L);
		record.setCompanyId(0L);
		record.setCheckIn("");
		record.setCheckOut("");
		record.setFlexibleMin(0);
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return xCompanyBenzTimeMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int insert(XcompanyBenzTime record) {
		return xCompanyBenzTimeMapper.insert(record);
	}
	
	@Override
	public int insertSelective(XcompanyBenzTime record) {
		return xCompanyBenzTimeMapper.insertSelective(record);
	}

	@Override
	public XcompanyBenzTime selectByPrimaryKey(Long id) {
		return xCompanyBenzTimeMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKey(XcompanyBenzTime record) {
		return xCompanyBenzTimeMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyBenzTime record) {
		return xCompanyBenzTimeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<XcompanyBenzTime> selectByBenzId(Long benzId) {
		return xCompanyBenzTimeMapper.selectByBenzId(benzId);
	}

}

