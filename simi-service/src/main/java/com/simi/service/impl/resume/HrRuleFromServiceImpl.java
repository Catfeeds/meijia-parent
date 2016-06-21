package com.simi.service.impl.resume;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.resume.po.dao.rule.HrRuleFromMapper;
import com.resume.po.model.dict.HrFrom;
import com.resume.po.model.rule.HrRuleFrom;
import com.simi.service.resume.HrFromService;
import com.simi.service.resume.HrRuleFromService;
import com.simi.service.user.UsersService;
import com.simi.vo.resume.HrRuleFromVo;
import com.simi.vo.resume.ResumeRuleSearchVo;


@Service
public class HrRuleFromServiceImpl implements HrRuleFromService {
	
	@Autowired
	private HrRuleFromMapper hrRuleFromMapper;
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private HrFromService hrFromService;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return hrRuleFromMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(HrRuleFrom record) {
		return hrRuleFromMapper.insert(record);
	}

	@Override
	public HrRuleFrom selectByPrimaryKey(Long id) {
		return hrRuleFromMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(HrRuleFrom record) {
		return hrRuleFromMapper.updateByPrimaryKey(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(HrRuleFrom record) {
		return hrRuleFromMapper.updateByPrimaryKeySelective(record);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(ResumeRuleSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<HrRuleFrom> list = hrRuleFromMapper.selectByListPage(searchVo);
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}
	
	@Override
	public List<HrRuleFrom> selectBySearchVo(ResumeRuleSearchVo searchVo) {
		return hrRuleFromMapper.selectBySearchVo(searchVo);
	}
	
	@Override
	public List<HrRuleFromVo> getVos(List<HrRuleFrom> list) {
		List<HrRuleFromVo> result = new ArrayList<HrRuleFromVo>();
		
		if (list.isEmpty()) return result;
		
		List<Long> fromIds = new ArrayList<Long>();
		
		for (HrRuleFrom item : list) {
			if (!fromIds.contains(item.getFromId())) {
				fromIds.add(item.getFromId());
			}
		}
		
		ResumeRuleSearchVo searchVo = new ResumeRuleSearchVo();
		searchVo.setIds(fromIds);
		
		List<HrFrom> hrFroms = hrFromService.selectBySearchVo(searchVo);
		
		for (int i = 0 ; i < list.size(); i++) {
			HrRuleFrom item = list.get(i);
			HrRuleFromVo vo = new HrRuleFromVo();
			
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			for (HrFrom h : hrFroms) {
				if (h.getFromId().equals(vo.getFromId())) {
					vo.setFromName(h.getName());
					break;
				}
			}
			
			result.add(vo);
			
		}
		
		return result;
	}

	@Override
	public HrRuleFrom initHrDictFrom() {
		HrRuleFrom record = new HrRuleFrom();	
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
