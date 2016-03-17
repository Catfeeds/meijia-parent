package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyStaffReq;
import com.simi.vo.xcloud.UserCompanySearchVo;

public interface XcompanyStaffReqMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XcompanyStaffReq record);

    int insertSelective(XcompanyStaffReq record);

    XcompanyStaffReq selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XcompanyStaffReq record);

    int updateByPrimaryKey(XcompanyStaffReq record);
    
    List<XcompanyStaffReq> selectByCompanyId(Long compnayId);
    
    List<XcompanyStaffReq> selectByUserId(Long userId);

	List<XcompanyStaffReq> selectByUserIdOrCompanyId(Long userId, Long companyId);

	List<XcompanyStaffReq> selectByListPage(UserCompanySearchVo searchVo);
}