package com.simi.po.dao.sec;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.sec.Sec;


public interface SecMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Sec record);

    int insertSelective(Sec record);

    Sec selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Sec record);

    int updateByPrimaryKey(Sec record);
    
    List<Sec> selectByListPage();

	Sec selectByUserNameAndOtherId(HashMap map);
}