package com.simi.service.partners;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.partners.Partners;
import com.simi.vo.partners.PartnerFormVo;
import com.simi.vo.partners.PartnersSearchVo;
import com.simi.vo.partners.PartnersVo;

public interface PartnersService {
	
	int deleteByPrimaryKey(Long partnerId);

    Long insert(Partners record);

    Long insertSelective(Partners record);

    Partners selectByPrimaryKey(Long partnerId);

    int updateByPrimaryKeySelective(Partners record);

    int updateByPrimaryKeyWithBLOBs(Partners record);

    int updateByPrimaryKey(Partners record);
    
    Partners iniPartners();
    
    PartnerFormVo selectPartnerFormVoByPartnerFormVo(PartnerFormVo partnerFormVo);
    
	public void savePartnerToPartnerType(Long partnerId, Long[] partnerTypeIds);
	
	PageInfo selectByListPage(PartnersSearchVo partnersSearchVo, int pageNo, int pageSize);

	List<PartnersVo> getPartnerVos(List<Partners> list);

	Partners selectBySpiderPartnerId(Long spiderPartnerId);

	List<Partners> selectBySearchVo(PartnersSearchVo searchVo);

}
