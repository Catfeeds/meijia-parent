package com.simi.service.impl.partners;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.partners.PartnerRefRegionMapper;
import com.simi.po.model.partners.PartnerRefRegion;
import com.simi.service.partners.PartnerRefRegionService;
@Service
public class PartnerRefRegionServiceImpl implements PartnerRefRegionService {

	@Autowired
	private PartnerRefRegionMapper partnerRefRegionMapper;
	@Override
	public int insert(PartnerRefRegion record) {
		return partnerRefRegionMapper.insert(record);
	}

	@Override
	public int insertSelective(PartnerRefRegion record) {
		return partnerRefRegionMapper.insertSelective(record);
	}

	@Override
	public List<PartnerRefRegion> selectByPartnerId(Long partnerId) {
		return partnerRefRegionMapper.selectByPartnerId(partnerId);
	}

	@Override
	public int deleteByPartnerId(Long partnerId) {
		return partnerRefRegionMapper.deleteByPartnerId(partnerId);
	}

	@Override
	public PartnerRefRegion initPartnerRefRegion() {
		PartnerRefRegion partnerRefRegion = new PartnerRefRegion();
		partnerRefRegion.setAddTime(TimeStampUtil.getNowSecond());
		partnerRefRegion.setPartnerId(0L);
		partnerRefRegion.setRegionId(0L);
		return partnerRefRegion;
	}
}
