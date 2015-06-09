package com.simi.po.dao.promotion;

import com.simi.po.model.promotion.ChannelLog;

public interface ChannelLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ChannelLog record);

    int insertSelective(ChannelLog record);

    ChannelLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChannelLog record);

    int updateByPrimaryKey(ChannelLog record);
}