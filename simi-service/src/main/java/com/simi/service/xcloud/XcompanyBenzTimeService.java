package com.simi.service.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyBenzTime;

public interface XcompanyBenzTimeService {

	XcompanyBenzTime initXcompanyBenzTime();

	int deleteByPrimaryKey(Long id);

	int insertSelective(XcompanyBenzTime record);

	XcompanyBenzTime selectByPrimaryKey(Long id);

	int updateByPrimaryKey(XcompanyBenzTime record);

	int updateByPrimaryKeySelective(XcompanyBenzTime record);

	int insert(XcompanyBenzTime record);

	List<XcompanyBenzTime> selectByBenzId(Long benzId);

}
