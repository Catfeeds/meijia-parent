package com.simi.service.xcloud;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.xcloud.XcompanyCheckinStat;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;

public interface XcompanyCheckinStatService {

	XcompanyCheckinStat initXcompanyCheckinStat();

	int deleteByPrimaryKey(Long id);

	int insertSelective(XcompanyCheckinStat record);
	
	int updateByPrimaryKeySelective(XcompanyCheckinStat record);

	XcompanyCheckinStat selectByPrimarykey(Long id);
	
	List<XcompanyCheckinStat> selectBySearchVo(CompanyCheckinSearchVo searchVo);

	List<HashMap> totalBySearchVo(CompanyCheckinSearchVo searchVo);

	Boolean checkInStatLate(Long companyId, Long userId, Long deptId);

	Boolean checkInStatEarly(Long companyId, Long userId, Long deptId);

	Boolean setCheckinFirst(Long companyId, Long userId);

	Boolean setCheckinLast(Long companyId, Long userId);

	List<HashMap<String, Object>> getStaffCheckin(CompanyCheckinSearchVo searchVo);


}
