package com.simi.service.impl.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.dict.DictSeniorTypeService;
import com.simi.po.dao.dict.DictSeniorTypeMapper;
import com.simi.po.model.dict.DictSeniorType;

@Service
public class DictSeniorTypeServiceImpl implements DictSeniorTypeService{
    @Autowired
    private DictSeniorTypeMapper dictSeniorTypeMapper;


	@Override
	public List<DictSeniorType> getSeniorTypes() {
		return dictSeniorTypeMapper.selectAll();
	}    
    
	@Override
	public int deleteByPrimaryKey(Long id) {
		return dictSeniorTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(DictSeniorType record) {
		return dictSeniorTypeMapper.insert(record);
	}

	@Override
	public int insertSelective(DictSeniorType record) {
		return dictSeniorTypeMapper.insertSelective(record);
	}

	@Override
	public DictSeniorType selectByPrimaryKey(Long id) {
		return dictSeniorTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(DictSeniorType record) {
		return dictSeniorTypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(DictSeniorType record) {
		return dictSeniorTypeMapper.updateByPrimaryKeySelective(record);
	}
    
}