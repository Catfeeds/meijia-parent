package com.simi.po.dao.record;

import java.util.List;

import com.simi.po.model.record.RecordBarcode;
import com.simi.vo.xcloud.CompanySearchVo;

public interface RecordBarcodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordBarcode record);

    int insertSelective(RecordBarcode record);

    RecordBarcode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordBarcode record);

    int updateByPrimaryKey(RecordBarcode record);
    
    RecordBarcode selectByBarcode(String barcode);

}