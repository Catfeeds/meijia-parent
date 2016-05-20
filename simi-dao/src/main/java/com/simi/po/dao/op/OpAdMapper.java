package com.simi.po.dao.op;

import java.util.List;

import com.simi.po.model.op.OpAd;
import com.simi.vo.po.AdSearchVo;

public interface OpAdMapper {
	int insert(OpAd record);

	int insertSelective(OpAd record);

	OpAd selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OpAd record);
	
	int updateByPrimaryKey(OpAd record);
	
	int deleteByPrimaryKey(Long id);

	List<OpAd> selectByAdType(String adType);

	List<OpAd> selectBySearchVo(AdSearchVo searchVo);

	List<OpAd> selectByListPage(AdSearchVo searchVo);
}