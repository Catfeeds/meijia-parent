package com.simi.vo.xcloud.company;

/**
 * 
* @author hulj 
* @date 2016年6月7日 下午2:45:09 
* @Description: 
*	
*	通讯录--部门管理 ，searchVo
*
 */
public class DeptSearchVo {
	
	private Long deptId;
	private Long companyId;
	private String name;
	private Long parentId;
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
	
}
