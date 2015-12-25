package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.vo.UserCompanySearchVo;

public interface XcompanyStaffMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XcompanyStaff record);

    int insertSelective(XcompanyStaff record);

    XcompanyStaff selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XcompanyStaff record);

    int updateByPrimaryKey(XcompanyStaff record);

	List<XcompanyStaff> selectBySearchVo(Long companyId, Long deptId);

	List<XcompanyStaff> selectByCompanyIdAndDeptId(Long companyId, Long deptId);

	List<XcompanyStaff> selectByUserId(Long userId);

	List<XcompanyStaff> selectByCompanyId(Long companyId);

	XcompanyStaff selectByCompanyIdAndUserId(Long companyId, Long userId);

	List<XcompanyStaff> selectByListPage(UserCompanySearchVo searchVo);

	String getMaxJobNumber(Long companyId);
}