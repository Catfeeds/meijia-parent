package com.simi.po.dao.xcloud;

import com.simi.po.model.xcloud.XcompanySetting;

public interface XcompanySettingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XcompanySetting record);

    int insertSelective(XcompanySetting record);

    XcompanySetting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XcompanySetting record);

    int updateByPrimaryKey(XcompanySetting record);
}