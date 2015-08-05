package com.simi.service.partners;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.partners.SpiderPartner;
import com.simi.vo.partners.PartnersSearchVo;

public interface SpiderPartnerService {
	
	int deleteByPrimaryKey(Long spiderPartnerId);

    int insert(SpiderPartner record);

    int insertSelective(SpiderPartner record);

    SpiderPartner selectByPrimaryKey(Long spiderPartnerId);

    int updateByPrimaryKeySelective(SpiderPartner record);

    int updateByPrimaryKeyWithBLOBs(SpiderPartner record);

    int updateByPrimaryKey(SpiderPartner record);
    
    PageInfo searchVoListPage(PartnersSearchVo partnersSearchVo,int pageNo,int pageSize);
    
    SpiderPartner initSpiderPartner();


}
