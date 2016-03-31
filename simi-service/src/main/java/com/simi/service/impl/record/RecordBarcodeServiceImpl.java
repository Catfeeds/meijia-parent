package com.simi.service.impl.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.record.RecordBarcodeMapper;
import com.simi.po.model.record.RecordBarcode;
import com.simi.service.record.RecordBarcodeService;


@Service
public class RecordBarcodeServiceImpl implements RecordBarcodeService {

	@Autowired
	RecordBarcodeMapper recordBarcodeMapper;

	@Override
	public RecordBarcode initRecordBarcode() {

		RecordBarcode record = new RecordBarcode();
		record.setId(0L);
		record.setBarcode("");
		record.setName("");
		record.setPrice("");
		record.setSpec("");
		record.setBrand("");
		record.setCountry("");
		record.setCompany("");
		record.setPrefix("");
		record.setAddr("");
		record.setRes("");
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return recordBarcodeMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int insertSelective(RecordBarcode record) {
		return recordBarcodeMapper.insertSelective(record);
	}

	@Override
	public RecordBarcode selectByPrimarykey(Long id) {
		return recordBarcodeMapper.selectByPrimaryKey(id);
	}

	@Override
	public RecordBarcode selectByBarcode(String barcode) {
		return recordBarcodeMapper.selectByBarcode(barcode);
	}
	
	@Override
	public int updateByPrimaryKeySelective(RecordBarcode record) {
		return recordBarcodeMapper.updateByPrimaryKeySelective(record);
	}
}
