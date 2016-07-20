package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.vo.xcloud.UserCompanySearchVo;

public interface XcompanyStaffMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XcompanyStaff record);

    int insertSelective(XcompanyStaff record);

    XcompanyStaff selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XcompanyStaff record);

    int updateByPrimaryKey(XcompanyStaff record);
    
    int updateByPrimaryKeyAndJson(XcompanyStaff record);
    
    List<XcompanyStaff> selectBySearchVo(UserCompanySearchVo searchVo);

  	List<XcompanyStaff> selectByListPage(UserCompanySearchVo searchVo);

  	String getMaxJobNumber(Long companyId);

  	String getNextJobNumber(Long companyId);

	int totalByUserId(Long userId);
}