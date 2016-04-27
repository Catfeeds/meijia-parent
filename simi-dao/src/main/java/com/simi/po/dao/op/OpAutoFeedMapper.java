package com.simi.po.dao.op;

import java.util.List;

import com.simi.po.model.op.OpAutoFeed;

public interface OpAutoFeedMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpAutoFeed record);

    Long insertSelective(OpAutoFeed record);

    OpAutoFeed selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpAutoFeed record);

    int updateByPrimaryKey(OpAutoFeed record);
    
    List<OpAutoFeed> selectByTotal();

	List<OpAutoFeed> selectByListPage();
}