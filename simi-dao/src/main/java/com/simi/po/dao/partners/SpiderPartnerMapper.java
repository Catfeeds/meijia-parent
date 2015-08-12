package com.simi.po.dao.partners;

import java.util.List;
import java.util.Map;

import com.simi.po.model.partners.SpiderPartner;

public interface SpiderPartnerMapper {
    int deleteByPrimaryKey(Long spiderPartnerId);

    int insert(SpiderPartner record);

    int insertSelective(SpiderPartner record);

    SpiderPartner selectByPrimaryKey(Long spiderPartnerId);

    int updateByPrimaryKeySelective(SpiderPartner record);

    int updateByPrimaryKeyWithBLOBs(SpiderPartner record);

    int updateByPrimaryKey(SpiderPartner record);
    
    List<SpiderPartner> selectByListPage(Map<String,Object> conditions);
    
    List<SpiderPartner> selectBySpiderIds(List<Long> spiderPartnerIds);

}