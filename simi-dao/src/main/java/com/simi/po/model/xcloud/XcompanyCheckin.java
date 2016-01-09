package com.simi.po.model.xcloud;

public class XcompanyCheckin {
    private Long id;

    private Long companyId;

    private Long staffId;

    private Long userId;

    private Short checkinFrom;

    private Short checkinType;

    private String checkinDevice;

    private String checkinSn;

    private String poiName;

    private String poiLat;

    private String poiLng;

    private Integer poiDistance;

    private String remarks;
    
    private Long benzTimeId;

    private Short checkinStatus;
    
    private String checkinRemarks;

    private String checkIn;

    private String checkOut;

    private Integer flexibleMin;

    private Long addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getCheckinFrom() {
        return checkinFrom;
    }

    public void setCheckinFrom(Short checkinFrom) {
        this.checkinFrom = checkinFrom;
    }

    public Short getCheckinType() {
        return checkinType;
    }

    public void setCheckinType(Short checkinType) {
        this.checkinType = checkinType;
    }

    public String getCheckinDevice() {
        return checkinDevice;
    }

    public void setCheckinDevice(String checkinDevice) {
        this.checkinDevice = checkinDevice == null ? null : checkinDevice.trim();
    }

    public String getCheckinSn() {
        return checkinSn;
    }

    public void setCheckinSn(String checkinSn) {
        this.checkinSn = checkinSn == null ? null : checkinSn.trim();
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName == null ? null : poiName.trim();
    }

    public String getPoiLat() {
        return poiLat;
    }

    public void setPoiLat(String poiLat) {
        this.poiLat = poiLat == null ? null : poiLat.trim();
    }

    public String getPoiLng() {
        return poiLng;
    }

    public void setPoiLng(String poiLng) {
        this.poiLng = poiLng == null ? null : poiLng.trim();
    }

    public Integer getPoiDistance() {
        return poiDistance;
    }

    public void setPoiDistance(Integer poiDistance) {
        this.poiDistance = poiDistance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Short getCheckinStatus() {
        return checkinStatus;
    }

    public void setCheckinStatus(Short checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn == null ? null : checkIn.trim();
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut == null ? null : checkOut.trim();
    }

    public Integer getFlexibleMin() {
        return flexibleMin;
    }

    public void setFlexibleMin(Integer flexibleMin) {
        this.flexibleMin = flexibleMin;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

	public Long getBenzTimeId() {
		return benzTimeId;
	}

	public void setBenzTimeId(Long benzTimeId) {
		this.benzTimeId = benzTimeId;
	}

	public String getCheckinRemarks() {
		return checkinRemarks;
	}

	public void setCheckinRemarks(String checkinRemarks) {
		this.checkinRemarks = checkinRemarks;
	}
}