package com.simi.service.partners;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.vo.partners.PartnerUserSearchVo;


public interface PartnerUserService {

	PartnerUsers iniPartnerUsers();

	int deleteByPrimaryKey(Long id);

	int insert(PartnerUsers record);

	int insertSelective(PartnerUsers record);

	int updateByPrimaryKeySelective(PartnerUsers record);

	int updateByPrimaryKey(PartnerUsers record);

	PartnerUsers selectByPrimaryKey(Long id);

	PageInfo searchVoListPage(PartnerUserSearchVo partnersSearchVo, int pageNo, int pageSize);
	

	
}
