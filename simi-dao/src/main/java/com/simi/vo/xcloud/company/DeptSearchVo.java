package com.simi.vo.xcloud.company;

/**
 * 
* @author hulj 
* @date 2016��6��7�� ����2:45:09 
* @Description: 
*	
*	ͨѶ¼--���Ź��� ��searchVo
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
