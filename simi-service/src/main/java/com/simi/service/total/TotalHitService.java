package com.simi.service.total;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.total.TotalHit;
import com.simi.vo.total.TotalHitSearchVo;

/**
 *
 * @author :hulj
 * @Date : 2016年5月18日下午5:44:21
 * @Description: 
 *
 */
public interface TotalHitService {
	
	TotalHit initTotalHit();

	TotalHit selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(TotalHit hit);

	int insertSelective(TotalHit hit);

	PageInfo searchVoListPage(TotalHitSearchVo searchVo, int pageNo, int pageSize);
	
	List<TotalHit> selectBySearchVo(TotalHitSearchVo searchVo);
	
}
