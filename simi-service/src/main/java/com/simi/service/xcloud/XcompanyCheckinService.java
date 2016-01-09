package com.simi.service.xcloud;

import java.util.List;
import java.util.Map;

import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;

public interface XcompanyCheckinService {

	XcompanyCheckin initXcompanyCheckin();

	int deleteByPrimaryKey(Long id);

	int insertSelective(XcompanyCheckin record);
	
	int updateByPrimaryKeySelective(XcompanyCheckin record);

	XcompanyCheckin selectByPrimarykey(Long id);
	
	List<XcompanyCheckin> selectBySearchVo(CompanyCheckinSearchVo searchVo);

	Map<String, Object> getCheckinStatus(Long benzTimeId, Long checkinTime, Short checkinType);

	Map<String, Object> getCheckOutStatus(Long benzTimeId, Long checkinTime, Short checkinType);

}
