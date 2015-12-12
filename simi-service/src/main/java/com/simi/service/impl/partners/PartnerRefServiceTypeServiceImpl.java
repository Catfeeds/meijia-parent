package com.simi.service.impl.partners;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.po.dao.partners.PartnerRefServiceTypeMapper;
import com.simi.po.dao.partners.PartnerServiceTypeMapper;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.partners.PartnerRefServiceTypeService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerServiceTypeVo;

@Service
public class PartnerRefServiceTypeServiceImpl implements PartnerRefServiceTypeService {

	@Autowired
	private PartnerRefServiceTypeMapper partnerRefServiceTypeMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return partnerRefServiceTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(PartnerRefServiceType record) {
		return partnerRefServiceTypeMapper.insert(record);
	}

	@Override
	public int insertSelective(PartnerRefServiceType record) {
		return partnerRefServiceTypeMapper.insertSelective(record);
	}

	@Override
	public PartnerRefServiceType selectByPrimaryKey(Long id) {
		return partnerRefServiceTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(PartnerRefServiceType record) {
		return partnerRefServiceTypeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PartnerRefServiceType record) {
		return partnerRefServiceTypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public PartnerRefServiceType initPartnerRefServiceType() {
		
		PartnerRefServiceType partnerRefServiceType = new PartnerRefServiceType();

		partnerRefServiceType.setId(0L);
		partnerRefServiceType.setServiceTypeId(0L);
		partnerRefServiceType.setParentId(0L);
		partnerRefServiceType.setName("");
		partnerRefServiceType.setPrice(new BigDecimal(0L));
		partnerRefServiceType.setPartnerId(0L);
		
		return partnerRefServiceType;
		
	}
	@Override
	public int deleteByPartnerId(Long partnerId) {
		
		return partnerRefServiceTypeMapper.deleteByPartnerId(partnerId);
	}
	
	

}
