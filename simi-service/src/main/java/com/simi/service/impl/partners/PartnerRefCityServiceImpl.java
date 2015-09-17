package com.simi.service.impl.partners;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.partners.PartnerRefCityMapper;
import com.simi.po.model.partners.PartnerRefCity;
import com.simi.service.partners.PartnerRefCityService;
@Service
public class PartnerRefCityServiceImpl implements PartnerRefCityService {

	
	@Autowired
	private PartnerRefCityMapper partnerRefCityMapper;
	@Override
	public int deleteByPrimaryKey(Long id) {
		return partnerRefCityMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(PartnerRefCity record) {
		return partnerRefCityMapper.insert(record);
	}

	@Override
	public int insertSelective(PartnerRefCity record) {
		return partnerRefCityMapper.insertSelective(record);
	}

	@Override
	public PartnerRefCity selectByPrimaryKey(Long id) {
		return partnerRefCityMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(PartnerRefCity record) {
		return partnerRefCityMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PartnerRefCity record) {
		return partnerRefCityMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByPartnerId(Long partnerId) {
		return partnerRefCityMapper.deleteByPartnerId(partnerId);
	}

	@Override
	public PartnerRefCity initPartnerRefCity() {
		PartnerRefCity partnerRefCity = new PartnerRefCity();
		partnerRefCity.setAddTime(TimeStampUtil.getNowSecond());
		partnerRefCity.setPartnerId(0L);
		partnerRefCity.setCityId(0L);
		return partnerRefCity;
	}

	@Override
	public List<PartnerRefCity> selectByPartnerId(Long partnerId) {
		return partnerRefCityMapper.selectByPartnerId(partnerId);
	}
	
	
}
