package com.simi.service.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyDept;

public interface XcompanyDeptService {

	XcompanyDept initXcompanyDept();

	int deleteByPrimaryKey(Long id);

	int insertSelective(XcompanyDept record);

	XcompanyDept selectByPrimaryKey(Long id);

	int updateByPrimaryKey(XcompanyDept record);

	int updateByPrimaryKeySelective(XcompanyDept record);

	int insert(XcompanyDept record);

	List<XcompanyDept> selectByXcompanyId(Long xcompanyId);

	List<XcompanyDept> selectByParentId(Long parentId);

	List<XcompanyDept> selectByParentIdAndXcompanyId(Long parentId,
			Long xcompanyId);

}
