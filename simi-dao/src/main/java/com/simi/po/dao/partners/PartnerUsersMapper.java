package com.simi.po.dao.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerUsers;
import com.simi.vo.partners.PartnerUserSearchVo;

public interface PartnerUsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PartnerUsers record);

    int insertSelective(PartnerUsers record);

    PartnerUsers selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PartnerUsers record);

    int updateByPrimaryKey(PartnerUsers record);

	List<PartnerUsers> selectByListPage(PartnerUserSearchVo partnersSearchVo);

	PartnerUsers selectByUserId(Long userId);

	PartnerUsers selectByServiceTypeIdAndPartnerId(Long serviceTypeId,
			Long partnerId);
}