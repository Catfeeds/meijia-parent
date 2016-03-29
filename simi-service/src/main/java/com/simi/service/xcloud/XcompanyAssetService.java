package com.simi.service.xcloud;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.xcloud.XcompanyAssets;
import com.simi.vo.AssetSearchVo;

public interface XcompanyAssetService {

	XcompanyAssets initXcompanyAssets();

	int deleteByPrimaryKey(Long id);

	Long insertSelective(XcompanyAssets XcompanyAssets);
	
	int updateByPrimaryKeySelective(XcompanyAssets XcompanyAssets);

	XcompanyAssets selectByPrimarykey(Long id);

	@SuppressWarnings("rawtypes")
	PageInfo selectByListPage(AssetSearchVo searchVo, int pageNo, int pageSize);
	
	List<XcompanyAssets> selectBySearchVo(AssetSearchVo searchVo);
	
}
