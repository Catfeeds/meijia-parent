package com.simi.po.dao.promotion;

import java.util.List;
import java.util.Map;

import com.simi.po.model.promotion.Msg;

public interface MsgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Msg record);

    int insertSelective(Msg record);

    Msg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Msg record);

    int updateByPrimaryKey(Msg record);

    List<Msg> selectAll();

    List<Msg> selectByIds(List<Integer> ids);
    
    List<Msg> selectByListPage(Map<String, Object> conditions);

	Msg selectByMobile(String mobile);
}