package com.simi.service.xcloud;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.vo.xcloud.CheckinNetVo;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;
import com.simi.vo.xcloud.company.DeptSearchVo;

public interface XcompanyCheckinService {

	XcompanyCheckin initXcompanyCheckin();

	int deleteByPrimaryKey(Long id);

	Long insertSelective(XcompanyCheckin record);
	
	int updateByPrimaryKeySelective(XcompanyCheckin record);

	XcompanyCheckin selectByPrimarykey(Long id);
	
	List<XcompanyCheckin> selectBySearchVo(CompanyCheckinSearchVo searchVo);

	Map<String, Object> getCheckinStatus(Long benzTimeId, Long checkinTime, Short checkinType);

	Map<String, Object> getCheckOutStatus(Long benzTimeId, Long checkinTime, Short checkinType);

	XcompanyCheckin getTodayCheckIn(Long companyId, Long userId, Long benzTimeId);

	XcompanyCheckin getTodayCheckOut(Long companyId, Long userId, Long benzTimeId);

	PageInfo selectByListPage(CompanyCheckinSearchVo searchVo, int pageNo, int pageSize);

	CheckinNetVo initCheckinNetVo();

}
