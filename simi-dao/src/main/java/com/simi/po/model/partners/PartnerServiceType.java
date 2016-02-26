package com.simi.po.model.partners;

public class PartnerServiceType {
	
    private Long id;

    private String name;

    private Long parentId;

    private Short isEnable;
    
    private Short viewType;
    
    private Integer no;
    
    private Long partnerId;
  

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

	public Short getViewType() {
		return viewType;
	}

	public void setViewType(Short viewType) {
		this.viewType = viewType;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Short getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Short isEnable) {
		this.isEnable = isEnable;
	}
}