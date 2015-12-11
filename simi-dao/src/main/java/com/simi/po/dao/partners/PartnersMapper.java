package com.simi.po.dao.partners;

import java.util.List;
import java.util.Map;

import com.simi.po.model.partners.Partners;

public interface PartnersMapper {
    int deleteByPrimaryKey(Long partnerId);

    int insert(Partners record);

    int insertSelective(Partners record);

    Partners selectByPrimaryKey(Long partnerId);

    Partners selectBySpiderPartnerId(Long spiderPartnerId);

    int updateByPrimaryKeySelective(Partners record);

    int updateByPrimaryKeyWithBLOBs(Partners record);

    int updateByPrimaryKey(Partners record);
    
    List<Partners> selectByListPage(Map<String,Object> conditions);

    List<Partners> selectByCompanyName(String companyName);

	List<Partners> selectBySpiderIds(List<Long> partnerIds);

	Partners selectByCompanyNames(String companyName);
}