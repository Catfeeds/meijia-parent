package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyDept;

public interface XcompanyDeptMapper {
    int deleteByPrimaryKey(Long deptId);

    Long insert(XcompanyDept record);

    int insertSelective(XcompanyDept record);

    XcompanyDept selectByPrimaryKey(Long deptId);

    int updateByPrimaryKeySelective(XcompanyDept record);

    int updateByPrimaryKey(XcompanyDept record);

	List<XcompanyDept> selectByXcompanyId(Long xcompanyId);

	List<XcompanyDept> selectByParentId(Long parentId);

	List<XcompanyDept> selectByParentIdAndXcompanyId(Long parentId,
			Long xcompanyId);
}