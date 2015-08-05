package com.simi.service.impl.partners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.StringUtil;
import com.simi.po.dao.partners.PartnersMapper;
import com.simi.po.model.partners.Partners;
import com.simi.service.partners.PartnersService;
import com.simi.vo.partners.PartnersSearchVo;

@Service
public class PartnersServiceImpl implements PartnersService {

	@Autowired
	private PartnersMapper partnersMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return partnersMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Partners record) {
		return partnersMapper.insert(record);
	}

	@Override
	public int insertSelective(Partners record) {
		return partnersMapper.insertSelective(record);
	}

	@Override
	public Partners selectByPrimaryKey(Long id) {
		return partnersMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Partners record) {
		return partnersMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Partners record) {
		return partnersMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageInfo searchVoListPage(PartnersSearchVo partnersSearchVo, int pageNo, int pageSize) {
		Map<String,Object> conditions = new HashMap<String, Object>();
		String shortName = partnersSearchVo.getShortName();
		String companyName = partnersSearchVo.getCompanyName();
		
		if(!StringUtil.isEmpty(shortName)){
			conditions.put("shortName", shortName);
		}
		if(!StringUtil.isEmpty(companyName)){
			conditions.put("companyName",companyName);
		}
		List<Partners> list = partnersMapper.selectByListPage(conditions);;
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
		
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Partners record) {
		return partnersMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	
	
}
