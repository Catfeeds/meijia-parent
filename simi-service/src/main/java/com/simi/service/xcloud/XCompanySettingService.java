package com.simi.service.xcloud;

import com.simi.po.model.xcloud.XcompanySetting;



public interface XCompanySettingService {

	int deleteByPrimaryKey(Long id);

	int insert(XcompanySetting record);

	int insertSelective(XcompanySetting record);

	XcompanySetting selectByPrimaryKey(Long id);

	int updateByPrimaryKey(XcompanySetting record);

	int updateByPrimaryKeySelective(XcompanySetting record);

	XcompanySetting initXcompanySetting();

}
