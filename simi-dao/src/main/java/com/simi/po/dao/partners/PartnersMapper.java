package com.simi.po.dao.partners;

import java.util.List;

import com.simi.po.model.partners.Partners;
import com.simi.vo.partners.PartnersSearchVo;

public interface PartnersMapper {
    int deleteByPrimaryKey(Long partnerId);

    Long insert(Partners record);

    Long insertSelective(Partners record);

    Partners selectByPrimaryKey(Long partnerId);

    Partners selectBySpiderPartnerId(Long spiderPartnerId);

    int updateByPrimaryKeySelective(Partners record);

    int updateByPrimaryKeyWithBLOBs(Partners record);

    int updateByPrimaryKey(Partners record);
    
    List<Partners> selectByListPage(PartnersSearchVo partnersSearchVo);

	List<Partners> selectBySearchVo(PartnersSearchVo partnersSearchVo);
}