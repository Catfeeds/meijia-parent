package com.simi.po.dao.record;

import java.util.List;

import com.simi.po.model.record.RecordAssetUse;
import com.simi.vo.AssetSearchVo;

public interface RecordAssetUseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordAssetUse record);

    Long insertSelective(RecordAssetUse record);

    RecordAssetUse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordAssetUse record);

    int updateByPrimaryKey(RecordAssetUse record);
    
    List<RecordAssetUse> selectBySearchVo(AssetSearchVo searchVo);

	List<RecordAssetUse> selectByListPage(AssetSearchVo searchVo);
}