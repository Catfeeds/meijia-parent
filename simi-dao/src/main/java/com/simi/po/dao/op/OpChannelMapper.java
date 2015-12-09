package com.simi.po.dao.op;

import java.util.List;

import com.simi.po.model.op.OpChannel;

public interface OpChannelMapper {
    int deleteByPrimaryKey(Long channelId);

    int insert(OpChannel record);

    int insertSelective(OpChannel record);

    OpChannel selectByPrimaryKey(Long channelId);

    int updateByPrimaryKeySelective(OpChannel record);

    int updateByPrimaryKey(OpChannel record);

	List<OpChannel> selectByListPage();

	List<OpChannel> selectByAll();
}