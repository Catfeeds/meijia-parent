package com.simi.service.impl.partners;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.partners.PartnerLinkManMapper;
import com.simi.po.model.partners.PartnerLinkMan;
import com.simi.service.partners.PartnerLinkManService;

/**
 * @description：
 * @author： kerryg
 * @date:2015年8月6日 
 */
@Service
public class PartnerLinkManServiceImpl implements PartnerLinkManService {

	@Autowired 
	private PartnerLinkManMapper partnerLinkManMapper;
	@Override
	public int deleteByPrimaryKey(Long id) {
		return partnerLinkManMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(PartnerLinkMan record) {
		return partnerLinkManMapper.insert(record);
	}

	@Override
	public int insertSelective(PartnerLinkMan record) {
		return partnerLinkManMapper.insertSelective(record);
	}

	@Override
	public PartnerLinkMan selectByPrimaryKey(Long id) {
		return partnerLinkManMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(PartnerLinkMan record) {
		return partnerLinkManMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PartnerLinkMan record) {
		return partnerLinkManMapper.updateByPrimaryKey(record);
	}

	@Override
	public PartnerLinkMan initPartnerLinkMan() {
		PartnerLinkMan vo = new PartnerLinkMan();
		vo.setId(0L);
		vo.setLinkMan("");
		vo.setLinkMobile("");
		vo.setLinkTel("");
		vo.setLinkJob("");
		vo.setAddTime(TimeStampUtil.getNow()/1000);
		return vo;
	}

	@Override
	public List<PartnerLinkMan> selectByPartnerId(Long partnerId) {
		return  partnerLinkManMapper.selectByPartnerId(partnerId);
	}

	@Override
	public int deleteByPartnerId(Long partnerId) {
		return partnerLinkManMapper.deleteByPartnerId(partnerId);
	}
	
	
	
	
}
