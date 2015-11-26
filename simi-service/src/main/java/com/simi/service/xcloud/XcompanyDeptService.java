package com.simi.service.xcloud;

import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;

public interface XcompanyDeptService {

	XcompanyDept initXcompanyDept();

	int deleteByPrimaryKey(Long id);

	int insertSelective(XcompanyDept record);

	XcompanyDept selectByPrimaryKey(Long id);

	int updateByPrimaryKey(XcompanyDept record);

	int updateByPrimaryKeySelective(XcompanyDept record);

	int insert(XcompanyDept record);

}
