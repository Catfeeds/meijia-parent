package com.simi.vo.partners;

public class PartnersSearchVo {
	
	 private String companyName;

	 private String shortName;
	 
	 private Short status;
	 
	 private Short isCooperate;
	 
	 private String serviceType;
	 
	 private Short companySize;
	 

	public Short getCompanySize() {
		return companySize;
	}

	public void setCompanySize(Short companySize) {
		this.companySize = companySize;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Short getIsCooperate() {
		return isCooperate;
	}

	public void setIsCooperate(Short isCooperate) {
		this.isCooperate = isCooperate;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	 
	 
}
