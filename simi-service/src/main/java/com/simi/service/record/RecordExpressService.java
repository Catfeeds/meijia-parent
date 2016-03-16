package com.simi.service.record;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.record.RecordExpress;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.record.RecordExpressSearchVo;
import com.simi.vo.record.RecordExpressVo;



public interface RecordExpressService {

	RecordExpress initRecordExpress();

	int deleteByPrimaryKey(Long id);

	Long insert(RecordExpress record);

	Long insertSelective(RecordExpress record);

	RecordExpress selectByPrimaryKey(Long id);

	int updateByPrimaryKey(RecordExpress record);

	int updateByPrimaryKeySelective(RecordExpress record);

	List<RecordExpress> selectBySearchVo(RecordExpressSearchVo searchVo);

	PageInfo selectByListPage(RecordExpressSearchVo searchVo, int pageNo, int pageSize);

	RecordExpress selectByExpressNo(String expressNo);

	List<RecordExpressVo> getListVos(List<RecordExpress> list);

	RecordExpressVo getListVo(RecordExpress item);

	PageInfo selectByPage(RecordExpressSearchVo searchVo, int pageNo, int pageSize);

	
}
