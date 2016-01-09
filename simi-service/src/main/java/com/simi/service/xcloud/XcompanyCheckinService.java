package com.simi.service.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;

public interface XcompanyCheckinService {

	XcompanyCheckin initXcompanyCheckin();

	int deleteByPrimaryKey(Long id);

	int insertSelective(XcompanyCheckin record);
	
	int updateByPrimaryKeySelective(XcompanyCheckin record);

	XcompanyCheckin selectByPrimarykey(Long id);
	
	List<XcompanyCheckin> selectBySearchVo(CompanyCheckinSearchVo searchVo);

}
