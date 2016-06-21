package com.simi.service.impl.resume;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.resume.po.dao.dict.HrFromMapper;
import com.resume.po.model.dict.HrFrom;
import com.simi.service.resume.HrFromService;
import com.simi.service.user.UsersService;
import com.simi.vo.resume.ResumeRuleSearchVo;


@Service
public class HrFromServiceImpl implements HrFromService {
	
	@Autowired
	private HrFromMapper hrFromMapper;
	
	@Autowired
	private UsersService userService;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return hrFromMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(HrFrom record) {
		return hrFromMapper.insert(record);
	}

	@Override
	public HrFrom selectByPrimaryKey(Long id) {
		return hrFromMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(HrFrom record) {
		return hrFromMapper.updateByPrimaryKey(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(HrFrom record) {
		return hrFromMapper.updateByPrimaryKeySelective(record);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(ResumeRuleSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<HrFrom> list = hrFromMapper.selectByListPage(searchVo);
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}
	
	@Override
	public List<HrFrom> selectBySearchVo(ResumeRuleSearchVo searchVo) {
		return hrFromMapper.selectBySearchVo(searchVo);
	}
	
	@Override
	public List<HrFrom> selectByAll() {
		return hrFromMapper.selectByAll();
	}

	@Override
	public HrFrom initHrDictFrom() {
		HrFrom record = new HrFrom();	
		record.setFromId(0L);
		record.setName("");
		record.setWebUrl("");
		record.setMatchLink("");
		record.setMatchPage("");
		record.setSearchUrl("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

}
