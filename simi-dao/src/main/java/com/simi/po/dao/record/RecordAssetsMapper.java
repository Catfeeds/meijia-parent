package com.simi.po.dao.record;

import java.util.List;

import com.simi.po.model.record.RecordAssets;
import com.simi.vo.AssetSearchVo;

public interface RecordAssetsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordAssets record);

    int insertSelective(RecordAssets record);

    RecordAssets selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordAssets record);

    int updateByPrimaryKey(RecordAssets record);
    
    List<RecordAssets> selectBySearchVo(AssetSearchVo searchVo);

	List<RecordAssets> selectByListPage(AssetSearchVo searchVo);
}