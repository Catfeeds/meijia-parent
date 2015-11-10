package com.simi.service.impl.partners;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.dict.DictCityMapper;
import com.simi.po.dao.dict.DictRegionMapper;
import com.simi.po.dao.partners.PartnerRefRegionMapper;
import com.simi.po.dao.partners.PartnerRefServiceTypeMapper;
import com.simi.po.dao.partners.PartnerServiceTypeMapper;
import com.simi.po.dao.partners.PartnerUsersMapper;
import com.simi.po.dao.partners.SpiderPartnerMapper;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictRegion;
import com.simi.po.model.partners.PartnerRefRegion;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.partners.SpiderPartner;
import com.simi.service.partners.PartnerUserService;
import com.simi.vo.partners.PartnerUserSearchVo;



@Service
public class PartnerUserServiceImpl implements PartnerUserService {

	@Autowired
	private PartnerUsersMapper partnerUsersMapper;
	
	@Override
	public PartnerUsers iniPartnerUsers() {
		PartnerUsers vo = new PartnerUsers();
		vo.setId(0L);
		vo.setPartnerId(0L);
		vo.setUserId(0L);
		vo.setServiceTypeId(0L);
		vo.setAddTime(TimeStampUtil.getNow()/1000);
		return vo;
	}	
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return partnerUsersMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(PartnerUsers record) {
		return partnerUsersMapper.insert(record);
	}

	@Override
	public int insertSelective(PartnerUsers record) {
		return partnerUsersMapper.insertSelective(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(PartnerUsers record) {
		return partnerUsersMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PartnerUsers record) {
		return partnerUsersMapper.updateByPrimaryKey(record);
	}	

	@Override
	public PartnerUsers selectByPrimaryKey(Long id) {
		return partnerUsersMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public PageInfo searchVoListPage(PartnerUserSearchVo partnersSearchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<PartnerUsers> list = new ArrayList<PartnerUsers>();
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
		
	}
	
}