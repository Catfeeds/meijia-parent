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

	Boolean checkInStat(Long id);


}
