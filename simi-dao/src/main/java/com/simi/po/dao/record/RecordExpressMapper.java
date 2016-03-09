package com.simi.po.dao.record;

import java.util.List;

import com.simi.po.model.record.RecordExpress;
import com.simi.vo.record.RecordExpressSearchVo;

public interface RecordExpressMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(RecordExpress record);

    Long insertSelective(RecordExpress record);

    RecordExpress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordExpress record);

    int updateByPrimaryKey(RecordExpress record);

	List<RecordExpress> selectBySearchVo(RecordExpressSearchVo searchVo);

	List<RecordExpress> selectByListPage(RecordExpressSearchVo searchVo);

	RecordExpress selectByExpressNo(String expressNo);
}