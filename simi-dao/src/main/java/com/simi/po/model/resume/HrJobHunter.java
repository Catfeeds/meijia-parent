package com.simi.po.model.resume;

public class HrJobHunter {
    private Long id;

    private Long userId;

    private String name;
    
    private Long hrDictId;
    
    private String hrDictName;

    private String reward;

    private String cityName;

    private Short limitDay;

    private String title;

    private String jobRes;

    private String jobReq;

    private String remarks;

    private Long addTime;

    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward == null ? null : reward.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Short getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(Short limitDay) {
        this.limitDay = limitDay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getJobRes() {
        return jobRes;
    }

    public void setJobRes(String jobRes) {
        this.jobRes = jobRes == null ? null : jobRes.trim();
    }

    public String getJobReq() {
        return jobReq;
    }

    public void setJobReq(String jobReq) {
        this.jobReq = jobReq == null ? null : jobReq.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

	public Long getHrDictId() {
		return hrDictId;
	}

	public void setHrDictId(Long hrDictId) {
		this.hrDictId = hrDictId;
	}

	public String getHrDictName() {
		return hrDictName;
	}

	public void setHrDictName(String hrDictName) {
		this.hrDictName = hrDictName;
	}
}