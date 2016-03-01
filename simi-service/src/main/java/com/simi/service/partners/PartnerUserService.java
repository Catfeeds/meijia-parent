package com.simi.service.partners;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.vo.partners.PartnerUserSearchVo;
import com.simi.vo.partners.PartnerUserVo;


public interface PartnerUserService {

	PartnerUsers iniPartnerUsers();

	int deleteByPrimaryKey(Long id);

	int insert(PartnerUsers record);

	int insertSelective(PartnerUsers record);

	int updateByPrimaryKeySelective(PartnerUsers record);

	int updateByPrimaryKey(PartnerUsers record);

	PartnerUsers selectByPrimaryKey(Long id);

	PartnerUserVo changeToVo(PartnerUsers partnerUser);

	PageInfo selectByListPage(PartnerUserSearchVo partnersSearchVo, int pageNo, int pageSize);

	PartnerUsers selectByUserId(Long userId);

	PartnerUsers selectByServiceTypeIdAndPartnerId(Long serviceTypeId,
			Long partnerId);

	List<PartnerUsers> selectByPartnerId(Long partnerId);

	List<PartnerUsers> selectBySearchVo(PartnerUserSearchVo partnersSearchVo);
	

	
}
