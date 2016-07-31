package com.simi.service.record;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.record.RecordHoliday;
import com.simi.vo.record.RecordHolidaySearchVo;

public interface RecordHolidayService {

	int deleteByPrimaryKey(Long id);

	int insertSelective(RecordHoliday record);
	
	int updateByPrimaryKeySelective(RecordHoliday record);

	RecordHoliday selectByPrimarykey(Long id);
	
	RecordHoliday initRecordHoliday();

	List<RecordHoliday> selectBySearchVo(RecordHolidaySearchVo searchVo);

	PageInfo selectByListPage(RecordHolidaySearchVo searchVo, int pageNo, int pageSize);

	Boolean getHolidayByApi(int year);

	
}
