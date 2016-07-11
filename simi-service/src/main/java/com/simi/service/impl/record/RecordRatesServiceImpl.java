package com.simi.service.impl.record;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.record.RecordRatesMapper;
import com.simi.po.model.record.RecordRates;
import com.simi.service.record.RecordRatesService;
import com.simi.vo.record.RecordRateSearchVo;

@Service
public class RecordRatesServiceImpl implements RecordRatesService {

	@Autowired
	RecordRatesMapper recordRatesMapper;

	@Override
	public RecordRates initRecordRates() {

		RecordRates record = new RecordRates();
		record.setId(0L);
		record.setRateType((short) 0);
		record.setLinkId(0L);
		record.setUserId(0L);
		record.setName("");
		record.setRate((short) 0);
		record.setRateContent("");
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return recordRatesMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(RecordRates record) {
		return recordRatesMapper.insertSelective(record);
	}

	@Override
	public RecordRates selectByPrimarykey(Long id) {
		return recordRatesMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RecordRates record) {
		return recordRatesMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<RecordRates> selectBySearchVo(RecordRateSearchVo searchVo) {
		return recordRatesMapper.selectBySearchVo(searchVo);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(RecordRateSearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<RecordRates> list = recordRatesMapper.selectByListPage(searchVo);

		PageInfo result = new PageInfo(list);
		return result;
	}
	
	@Override
	public HashMap  totalByLinkId(RecordRateSearchVo searchVo) {
		return recordRatesMapper.totalByLinkId(searchVo);
	}
}
