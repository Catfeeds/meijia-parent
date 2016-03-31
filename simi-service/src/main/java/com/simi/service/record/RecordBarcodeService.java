package com.simi.service.record;

import com.simi.po.model.record.RecordBarcode;

public interface RecordBarcodeService {

	int deleteByPrimaryKey(Long id);

	int insertSelective(RecordBarcode RecordAssets);
	
	int updateByPrimaryKeySelective(RecordBarcode RecordAssets);

	RecordBarcode selectByPrimarykey(Long id);
	
	RecordBarcode initRecordBarcode();

	RecordBarcode selectByBarcode(String barcode);
	
}
