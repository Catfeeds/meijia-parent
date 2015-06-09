package com.simi.po.dao.promotion;

import java.util.List;
import java.util.Map;

import com.simi.po.model.promotion.Channel;

public interface ChannelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Channel record);
    int inserts(Channel record);

    int insertSelective(Channel record);

    Channel selectByPrimaryKey(Long id);

    Channel selectByToken(String token);

    int updateByPrimaryKeySelective(Channel record);

    int updateByPrimaryKey(Channel record);

    int updateByTotalDownload(String token);

    List<Channel> selectByListPage(Map<String,Object>  conditions);
}