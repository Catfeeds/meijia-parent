package com.simi.po.dao.op;

import java.util.List;

import com.simi.po.model.op.OpAutoFeed;
import com.simi.vo.op.OpAutoFeedSearchVo;

public interface OpAutoFeedMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpAutoFeed record);

    Long insertSelective(OpAutoFeed record);

    OpAutoFeed selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpAutoFeed record);

    int updateByPrimaryKey(OpAutoFeed record);
    
    List<OpAutoFeed> selectByTotal(OpAutoFeedSearchVo searchVo);

	List<OpAutoFeed> selectByListPage();
}