package com.simi.po.dao.record;

import java.util.List;

import com.simi.po.model.record.RecordHoliday;
import com.simi.vo.record.RecordHolidaySearchVo;

public interface RecordHolidayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordHoliday record);

    int insertSelective(RecordHoliday record);

    RecordHoliday selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordHoliday record);

    int updateByPrimaryKey(RecordHoliday record);
    
    List<RecordHoliday> selectByListPage(RecordHolidaySearchVo searchVo);
    
    List<RecordHoliday> selectBySearchVo(RecordHolidaySearchVo searchVo);
}