package com.simi.service.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyBenz;

public interface XcompanyBenzService {

	XcompanyBenz initXcompanyBenz();

	int deleteByPrimaryKey(Long id);

	Long insertSelective(XcompanyBenz record);

	XcompanyBenz selectByPrimaryKey(Long id);

	int updateByPrimaryKey(XcompanyBenz record);

	int updateByPrimaryKeySelective(XcompanyBenz record);

	Long insert(XcompanyBenz record);

	List<XcompanyBenz> selectByXcompanyId(Long xcompanyId);

	Long setDefaultBenz(Long companyId);

}
