package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyAssets;
import com.simi.vo.AssetSearchVo;

public interface XcompanyAssetsMapper {
    int deleteByPrimaryKey(Long assetId);

    Long insert(XcompanyAssets record);

    Long insertSelective(XcompanyAssets record);

    XcompanyAssets selectByPrimaryKey(Long assetId);

    int updateByPrimaryKeySelective(XcompanyAssets record);

    int updateByPrimaryKey(XcompanyAssets record);
    
    List<XcompanyAssets> selectBySearchVo(AssetSearchVo searchVo);

	List<XcompanyAssets> selectByListPage(AssetSearchVo searchVo);
}