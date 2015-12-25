package com.simi.service.impl.xcloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.po.dao.xcloud.XcompanyDeptMapper;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.service.xcloud.XcompanyDeptService;

@Service
public class XcompanyDeptServiceImpl implements XcompanyDeptService {
	
	@Autowired
	XcompanyDeptMapper xCompanyDeptMapper;		
	
	
	@Override
	public XcompanyDept initXcompanyDept() {
		XcompanyDept record = new XcompanyDept();
		record.setDeptId(0L);
		record.setCompanyId(0L);
		record.setName("");
		record.setParentId(0L);

		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return xCompanyDeptMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insert(XcompanyDept record) {
		return xCompanyDeptMapper.insert(record);
	}
	
	@Override
	public int insertSelective(XcompanyDept record) {
		return xCompanyDeptMapper.insertSelective(record);
	}

	@Override
	public XcompanyDept selectByPrimaryKey(Long id) {
		return xCompanyDeptMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKey(XcompanyDept record) {
		return xCompanyDeptMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyDept record) {
		return xCompanyDeptMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<XcompanyDept> selectByXcompanyId(Long xcompanyId) {
		
		return xCompanyDeptMapper.selectByXcompanyId(xcompanyId);
	}

	@Override
	public List<XcompanyDept> selectByParentId(Long parentId) {

		return xCompanyDeptMapper.selectByParentId(parentId);
	}

	@Override
	public List<XcompanyDept> selectByParentIdAndXcompanyId(Long parentId,Long xcompanyId) {

		return xCompanyDeptMapper.selectByParentIdAndXcompanyId(parentId,xcompanyId);
	}
	
	@Override
	public XcompanyDept selectByXcompanyIdAndDeptName(Long companyId,String name) {

		return xCompanyDeptMapper.selectByXcompanyIdAndDeptName(companyId,name);
	}
	
	@Override
	public XcompanyDept selectByXcompanyIdAndDeptId(Long companyId, Long deptId) {

		return xCompanyDeptMapper.selectByXcompanyIdAndDeptId(companyId,deptId);
	}
	
}

