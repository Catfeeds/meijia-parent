package com.simi.service.impl.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.dict.CardTypeService;
import com.simi.po.dao.dict.DictCardTypeMapper;
import com.simi.po.model.dict.DictCardType;

@Service
public class CardTypeServiceImpl implements CardTypeService {

	@Autowired
	private DictCardTypeMapper cardTypeMapper;

	/*
	 * 获取表dict_card_types的数据
	 * @param
	 * @return  List<DictCardType>
	 */
	@Override
	public List<DictCardType> getCardTypes() {
		return cardTypeMapper.selectAll();
	}

	@Autowired
	private DictCardTypeMapper dictCardTypeMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return dictCardTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(DictCardType record) {
		return dictCardTypeMapper.insert(record);
	}

	@Override
	public int insertSelective(DictCardType record) {
		return dictCardTypeMapper.insertSelective(record);
	}

	@Override
	public List<DictCardType> selectAll() {
		return dictCardTypeMapper.selectAll();
	}

	@Override
	public DictCardType selectByPrimaryKey(Long id) {
		return dictCardTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(DictCardType record) {
		return dictCardTypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(DictCardType record) {
		return dictCardTypeMapper.updateByPrimaryKeySelective(record);
	}

}
