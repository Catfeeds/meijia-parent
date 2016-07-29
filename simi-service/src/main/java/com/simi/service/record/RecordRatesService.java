package com.simi.service.record;

import java.util.HashMap;
import java.util.List;
import com.github.pagehelper.PageInfo;
import com.simi.po.model.record.RecordRates;
import com.simi.vo.record.RecordRateSearchVo;

public interface RecordRatesService {

	int deleteByPrimaryKey(Long id);

	int insertSelective(RecordRates record);
	
	int updateByPrimaryKeySelective(RecordRates record);

	RecordRates selectByPrimarykey(Long id);
	
	RecordRates initRecordRates();

	List<RecordRates> selectBySearchVo(RecordRateSearchVo searchVo);

	PageInfo selectByListPage(RecordRateSearchVo searchVo, int pageNo, int pageSize);

	HashMap totalByLinkId(RecordRateSearchVo searchVo);


	
}
