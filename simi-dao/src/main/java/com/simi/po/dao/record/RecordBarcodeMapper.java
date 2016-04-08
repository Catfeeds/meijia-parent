package com.simi.po.dao.record;

import com.simi.po.model.record.RecordBarcode;

public interface RecordBarcodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordBarcode record);

    int insertSelective(RecordBarcode record);

    RecordBarcode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordBarcode record);

    int updateByPrimaryKey(RecordBarcode record);
    
    RecordBarcode selectByBarcode(String barcode);

}