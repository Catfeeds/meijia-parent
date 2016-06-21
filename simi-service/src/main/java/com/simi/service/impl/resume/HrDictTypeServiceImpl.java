package com.simi.service.impl.resume;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.resume.po.dao.dict.HrDictTypeMapper;
import com.resume.po.model.dict.HrDictType;
import com.simi.service.resume.HrDictTypeService;
import com.simi.service.user.UsersService;


@Service
public class HrDictTypeServiceImpl implements HrDictTypeService {
	
	@Autowired
	private HrDictTypeMapper hrDictTypeMapper;
	
	@Autowired
	private UsersService userService;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return hrDictTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(HrDictType record) {
		return hrDictTypeMapper.insert(record);
	}

	@Override
	public HrDictType selectByPrimaryKey(Long id) {
		return hrDictTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(HrDictType record) {
		return hrDictTypeMapper.updateByPrimaryKey(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(HrDictType record) {
		return hrDictTypeMapper.updateByPrimaryKeySelective(record);
	}

	
	@Override
	public List<HrDictType> selectByAll() {
		return hrDictTypeMapper.selectByAll();
	}

	@Override
	public HrDictType initHrDictFrom() {
		HrDictType record = new HrDictType();	
		record.setId(0L);
		record.setType("");
		record.setTypeName("");
		record.setMulti((short) 0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

}
