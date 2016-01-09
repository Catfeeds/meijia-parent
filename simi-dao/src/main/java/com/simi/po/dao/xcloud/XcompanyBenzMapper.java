package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyBenz;

public interface XcompanyBenzMapper {
    int deleteByPrimaryKey(Long benzId);

    Long insert(XcompanyBenz record);

    Long insertSelective(XcompanyBenz record);

    XcompanyBenz selectByPrimaryKey(Long benzId);

    int updateByPrimaryKeySelective(XcompanyBenz record);

    int updateByPrimaryKey(XcompanyBenz record);

	List<XcompanyBenz> selectByXcompanyId(Long xcompanyId);
}