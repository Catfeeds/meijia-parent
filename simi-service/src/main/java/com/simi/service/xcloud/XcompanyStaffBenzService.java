package com.simi.service.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyStaffBenz;
import com.simi.vo.xcloud.UserCompanySearchVo;

public interface XcompanyStaffBenzService {

	XcompanyStaffBenz initXcompanyStaffBenz();

	int deleteByPrimaryKey(Long id);

	int insertSelective(XcompanyStaffBenz record);
	
	int updateByPrimaryKeySelective(XcompanyStaffBenz record);

	XcompanyStaffBenz selectByPrimarykey(Long id);
	
	List<XcompanyStaffBenz> selectBySearchVo(UserCompanySearchVo searchVo);

}
