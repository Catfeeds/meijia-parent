package com.simi.po.dao.record;

import java.util.HashMap;
import java.util.List;
import com.simi.po.model.record.RecordRates;
import com.simi.vo.record.RecordRateSearchVo;

public interface RecordRatesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordRates record);

    int insertSelective(RecordRates record);

    RecordRates selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordRates record);

    int updateByPrimaryKey(RecordRates record);
    
    List<RecordRates> selectByListPage(RecordRateSearchVo searchVo);
    
    List<RecordRates> selectBySearchVo(RecordRateSearchVo searchVo);

	HashMap totalByLinkId(RecordRateSearchVo searchVo);
}