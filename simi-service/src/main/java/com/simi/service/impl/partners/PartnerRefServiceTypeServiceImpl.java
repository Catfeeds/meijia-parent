package com.simi.service.impl.partners;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.po.dao.partners.PartnerRefServiceTypeMapper;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.service.partners.PartnerRefServiceTypeService;

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

	@Override
	public List<PartnerRefServiceType> selectByServiceTypeId(
			Long serviceTypeId) {
		
		return partnerRefServiceTypeMapper.selectByServiceTypeId(serviceTypeId);
	}
	
	

}
