package com.simi.service.record;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.record.RecordAssetUse;
import com.simi.vo.AssetSearchVo;
import com.simi.vo.record.RecordAssetUseVo;

public interface RecordAssetUseService {

	RecordAssetUse initRecordAssetUse();

	int deleteByPrimaryKey(Long id);

	Long insertSelective(RecordAssetUse RecordAssetUse);
	
	int updateByPrimaryKeySelective(RecordAssetUse RecordAssetUse);

	RecordAssetUse selectByPrimarykey(Long id);

	@SuppressWarnings("rawtypes")
	PageInfo selectByListPage(AssetSearchVo searchVo, int pageNo, int pageSize);
	
	List<RecordAssetUse> selectBySearchVo(AssetSearchVo searchVo);
	
	RecordAssetUseVo getUserAssetVo(RecordAssetUse assetUse) throws UnsupportedEncodingException;
	
	
}
