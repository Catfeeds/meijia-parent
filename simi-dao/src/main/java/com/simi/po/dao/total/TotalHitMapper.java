package com.simi.po.dao.total;

import java.util.List;

import com.simi.po.model.total.TotalHit;
import com.simi.vo.total.TotalHitSearchVo;

public interface TotalHitMapper {
	int insert(TotalHit record);

	int insertSelective(TotalHit record);

	TotalHit selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(TotalHit record);

	List<TotalHit> selectBySearchVo(TotalHitSearchVo searchVo);

	List<TotalHit> selectByListPage(TotalHitSearchVo searchVo);
}