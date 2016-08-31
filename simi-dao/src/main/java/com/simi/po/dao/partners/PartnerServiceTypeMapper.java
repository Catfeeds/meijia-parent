package com.simi.po.dao.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerServicePrice;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;

public interface PartnerServiceTypeMapper {
    int deleteByPrimaryKey(Long serviceTypeId);

    int insert(PartnerServiceType record);

    int insertSelective(PartnerServiceType record);

    PartnerServiceType selectByPrimaryKey(Long serviceTypeId);

    int updateByPrimaryKeySelective(PartnerServiceType record);

    int updateByPrimaryKey(PartnerServiceType record);
    
	List<PartnerServiceType> selectByIds(List<Long> ids);

	List<PartnerServiceType> selectBySearchVo(PartnerServiceTypeSearchVo searchVo);

	List<PartnerServiceType> selectByParentId(Long parentId);

	List<PartnerServiceType> selectByPartnerIdIn(Long partnerId);

	List<PartnerServiceType> selectByListPage(PartnerServiceTypeSearchVo searchVo);

}
