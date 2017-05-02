package com.simi.po.model.feed;

public class Feeds {
    private Long fid;

    private Long userId;
    
    private Short feedType;

    private String title;

    private String lat;

    private String lng;

    private String poiName;
    
    private int totalView;
    
    private String feedExtra;
    
    private Short status;
    
    private Short featured;

    private Long addTime;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName == null ? null : poiName.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

	public int getTotalView() {
		return totalView;
	}

	public void setTotalView(int totalView) {
		this.totalView = totalView;
	}

	public String getFeedExtra() {
		return feedExtra;
	}

	public void setFeedExtra(String feedExtra) {
		this.feedExtra = feedExtra;
	}

	public Short getFeedType() {
		return feedType;
	}

	public void setFeedType(Short feedType) {
		this.feedType = feedType;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getFeatured() {
		return featured;
	}

	public void setFeatured(Short featured) {
		this.featured = featured;
	}
}