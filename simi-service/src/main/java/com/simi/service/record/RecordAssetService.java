package com.simi.service.record;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.record.RecordAssets;
import com.simi.vo.AssetSearchVo;

public interface RecordAssetService {

	RecordAssets initRecordAssets();

	int deleteByPrimaryKey(Long id);

	Long insertSelective(RecordAssets RecordAssets);
	
	int updateByPrimaryKeySelective(RecordAssets RecordAssets);

	RecordAssets selectByPrimarykey(Long id);

	@SuppressWarnings("rawtypes")
	PageInfo selectByListPage(AssetSearchVo searchVo, int pageNo, int pageSize);
	
	List<RecordAssets> selectBySearchVo(AssetSearchVo searchVo);
	
}
