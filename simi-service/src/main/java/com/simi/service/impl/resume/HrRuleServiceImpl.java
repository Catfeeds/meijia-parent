package com.simi.service.impl.resume;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.resume.po.dao.rule.HrRulesMapper;
import com.resume.po.model.rule.HrRules;
import com.simi.service.resume.HrRuleService;
import com.simi.service.user.UsersService;
import com.simi.vo.resume.ResumeRuleSearchVo;


@Service
public class HrRuleServiceImpl implements HrRuleService {
	
	@Autowired
	private HrRulesMapper hrRuleMapper;
	
	@Autowired
	private UsersService userService;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return hrRuleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(HrRules record) {
		return hrRuleMapper.insert(record);
	}

	@Override
	public HrRules selectByPrimaryKey(Long id) {
		return hrRuleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(HrRules record) {
		return hrRuleMapper.updateByPrimaryKey(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(HrRules record) {
		return hrRuleMapper.updateByPrimaryKeySelective(record);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(ResumeRuleSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<HrRules> list = hrRuleMapper.selectByListPage(searchVo);
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}
	
	@Override
	public List<HrRules> selectBySearchVo(ResumeRuleSearchVo searchVo) {
		return hrRuleMapper.selectBySearchVo(searchVo);
	}

	@Override
	public HrRules initHrDictFrom() {
		HrRules record = new HrRules();	
		record.setId(0L);
		record.setFromId(0L);
		record.setFileType("");
		record.setMatchType("");
		record.setMatchPatten("");
		record.setSampleSrc("");
		record.setSamplePath("");
		record.setAdminId(0L);
		record.setTotalMatch(0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}
}
