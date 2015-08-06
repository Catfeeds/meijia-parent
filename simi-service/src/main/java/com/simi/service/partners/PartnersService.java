package com.simi.service.partners;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.partners.Partners;
import com.simi.vo.partners.PartnerFormVo;
import com.simi.vo.partners.PartnersSearchVo;

public interface PartnersService {
	
	int deleteByPrimaryKey(Long partnerId);

    int insert(Partners record);

    int insertSelective(Partners record);

    Partners selectByPrimaryKey(Long partnerId);

    Partners selectBySpiderPartnerId(Long spiderPartnerId);

    int updateByPrimaryKeySelective(Partners record);

    int updateByPrimaryKeyWithBLOBs(Partners record);

    int updateByPrimaryKey(Partners record);
    
    PageInfo searchVoListPage(PartnersSearchVo partnersSearchVo,int pageNo,int pageSize);
    
    Partners iniPartners();
    
    PartnerFormVo selectPartnerFormVoByPartnerFormVo(PartnerFormVo partnerFormVo);
    
	public void savePartnerToPartnerType(Long partnerId, Long[] partnerTypeIds);

}
