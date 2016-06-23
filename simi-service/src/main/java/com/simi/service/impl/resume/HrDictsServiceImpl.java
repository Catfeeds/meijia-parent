package com.simi.service.impl.resume;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.resume.po.dao.dict.HrDictsMapper;


import com.resume.po.model.dict.HrDicts;
import com.simi.service.resume.HrDictsService;
import com.simi.service.user.UsersService;
import com.simi.vo.resume.HrDictSearchVo;
import com.simi.vo.resume.ResumeRuleSearchVo;


@Service
public class HrDictsServiceImpl implements HrDictsService {
	
	@Autowired
	private HrDictsMapper hrDictMapper;
	
	@Autowired
	private UsersService userService;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return hrDictMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(HrDicts record) {
		return hrDictMapper.insert(record);
	}

	@Override
	public HrDicts selectByPrimaryKey(Long id) {
		return hrDictMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(HrDicts record) {
		return hrDictMapper.updateByPrimaryKey(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(HrDicts record) {
		return hrDictMapper.updateByPrimaryKeySelective(record);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(HrDictSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<HrDicts> list = hrDictMapper.selectByListPage(searchVo);
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}
	
	@Override
	public List<HrDicts> selectBySearchVo(HrDictSearchVo searchVo) {
		return hrDictMapper.selectBySearchVo(searchVo);
	}

	@Override
	public HrDicts initHrDicts() {
		HrDicts record = new HrDicts();	
		record.setId(0L);
		record.setFromId(0L);
		record.setType("");
		record.setName("");
		record.setCode("");
		record.setEnName("");
		record.setFromMin(0L);
		record.setToMax(0L);
		record.setPid("");
		record.setLevel((short) 0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		return record;
	}

}
