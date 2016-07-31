package com.simi.service.record;

import java.util.List;

import com.simi.po.model.record.RecordHolidayDay;

public interface RecordHolidayDayService {

	int deleteByPrimaryKey(Long id);

	int insertSelective(RecordHolidayDay record);
	
	int updateByPrimaryKeySelective(RecordHolidayDay record);

	RecordHolidayDay selectByPrimarykey(Long id);
	
	RecordHolidayDay initRecordHolidayDay();

	RecordHolidayDay selectByDay(String cday);

	List<RecordHolidayDay> selectByYear(int year);

	Boolean getHolidayByApi(int year);	
}
