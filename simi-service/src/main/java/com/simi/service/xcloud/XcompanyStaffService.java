package com.simi.service.xcloud;

import java.util.List;

import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.vo.UserCompanySearchVo;
import com.simi.vo.xcloud.UserCompanyFormVo;

public interface XcompanyStaffService {

	XcompanyStaff initXcompanyStaff();

	List<XcompanyStaff> selectBySearchVo(Long companyId, Long deptId);

	List<XcompanyStaff> selectByCompanyIdAndDeptId(Long companyId, Long deptId);
	
	int deleteByPrimaryKey(Long id);

	List<XcompanyStaff> selectByCompanyId(Long companyId);

	int insertSelective(XcompanyStaff xcompanyStaff);

	XcompanyStaff selectByPrimarykey(Long id);

	List<XcompanyStaff> selectByUserId(Long userId);

	int updateByPrimaryKeySelective(XcompanyStaff xcompanyStaff);

	XcompanyStaff selectByCompanyIdAndUserId(Long companyId, Long userId);

	UserCompanyFormVo getUserCompany(Long compnayId, Users users);


	


}
