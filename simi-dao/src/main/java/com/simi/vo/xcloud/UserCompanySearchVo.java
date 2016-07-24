package com.simi.vo.xcloud;

import java.util.List;

public class UserCompanySearchVo{
	
	private String keyword;
	
	private Long companyId;
	
	private List<Long> companyIds;
	
	private Long deptId;
	
	private Long userId;
	
	private String mobile;
	
	private String name;
	
	private Long staffId;
	
	private Long benzId;
	
	private Short status;
	
	private String jobNumber;
	
	private Short isDefault;
	
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Long getBenzId() {
		return benzId;
	}

	public void setBenzId(Long benzId) {
		this.benzId = benzId;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Long> getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(List<Long> companyIds) {
		this.companyIds = companyIds;
	}
}
