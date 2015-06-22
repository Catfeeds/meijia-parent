package com.simi.po.dao.dict;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictServiceTypes;

public interface DictServiceTypesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DictServiceTypes record);

    int insertSelective(DictServiceTypes record);

    DictServiceTypes selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictServiceTypes record);

    int updateByPrimaryKey(DictServiceTypes record);

    List<DictServiceTypes> selectAll();

    List<DictServiceTypes> selectByListPage();

    DictServiceTypes selectByName(String name);

    DictServiceTypes selectByNameAndOtherId(HashMap map);


}