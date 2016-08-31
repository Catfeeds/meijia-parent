package com.simi.service.partners;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerServiceTypeTreeVo;
import com.simi.vo.partners.PartnerServicePriceSearchVo;

public interface PartnerServiceTypeService {

	int deleteByPrimaryKey(Long serviceTypeId);

	int insert(PartnerServiceType record);

	int insertSelective(PartnerServiceType record);

	PartnerServiceType selectByPrimaryKey(Long serviceTypeId);

	int updateByPrimaryKeySelective(PartnerServiceType record);

	int updateByPrimaryKey(PartnerServiceType record);

	PartnerServiceType initPartnerServiceType();

	List<PartnerServiceTypeTreeVo> listChain(List<Long> partnerIds);

	PartnerServiceTypeTreeVo ToTree(Long id, List<Long> partnerIds);

	List<PartnerServiceType> selectBySearchVo(PartnerServiceTypeSearchVo searchVo);

	List<PartnerServiceType> selectByParentId(Long parentId);

	PageInfo selectByListPage(PartnerServiceTypeSearchVo searchVo, int pageNo, int pageSize);

	List<PartnerServiceType> selectByPartnerId(Long parentId);

}
