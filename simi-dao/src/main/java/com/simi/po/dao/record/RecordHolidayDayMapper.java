package com.simi.po.dao.record;

import java.util.List;

import com.simi.po.model.record.RecordHolidayDay;

public interface RecordHolidayDayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordHolidayDay record);

    int insertSelective(RecordHolidayDay record);

    RecordHolidayDay selectByPrimaryKey(Long id);
    
    RecordHolidayDay selectByDay(String cday);
    
    List<RecordHolidayDay> selectByYear(int year);

    int updateByPrimaryKeySelective(RecordHolidayDay record);

    int updateByPrimaryKey(RecordHolidayDay record);
}