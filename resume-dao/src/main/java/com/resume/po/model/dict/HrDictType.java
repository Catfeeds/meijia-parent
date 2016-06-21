package com.resume.po.model.dict;

public class HrDictType {
    private String type;

    private String typeName;

    private Long addTime;
    
    private Long id;

    private Short multi;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getMulti() {
		return multi;
	}

	public void setMulti(Short multi) {
		this.multi = multi;
	}
}