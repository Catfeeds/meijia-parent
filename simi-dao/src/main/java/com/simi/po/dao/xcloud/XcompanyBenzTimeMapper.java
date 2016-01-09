package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyBenzTime;

public interface XcompanyBenzTimeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XcompanyBenzTime record);

    int insertSelective(XcompanyBenzTime record);

    XcompanyBenzTime selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XcompanyBenzTime record);

    int updateByPrimaryKey(XcompanyBenzTime record);

	List<XcompanyBenzTime> selectByBenzId(Long benzId);
}