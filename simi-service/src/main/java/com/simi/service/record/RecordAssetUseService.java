package com.simi.service.record;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.record.RecordAssetUse;
import com.simi.vo.AssetSearchVo;

public interface RecordAssetUseService {

	RecordAssetUse initRecordAssetUse();

	int deleteByPrimaryKey(Long id);

	int insertSelective(RecordAssetUse RecordAssetUse);
	
	int updateByPrimaryKeySelective(RecordAssetUse RecordAssetUse);

	RecordAssetUse selectByPrimarykey(Long id);

	@SuppressWarnings("rawtypes")
	PageInfo selectByListPage(AssetSearchVo searchVo, int pageNo, int pageSize);
	
	List<RecordAssetUse> selectBySearchVo(AssetSearchVo searchVo);
	
}
