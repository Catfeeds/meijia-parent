package com.simi.po.model.user;

import java.math.BigDecimal;

public class GetUserInfo {
    private Long user_id;

    private String mobile;

    private BigDecimal restMoney;

    private Integer score;

    private String seniorRange;

    private short userType;

    public GetUserInfo() {
    }

	public GetUserInfo(Long user_id, String mobile, BigDecimal restMoney,
			Integer score, String seniorRange, short userType) {
		super();
		this.user_id = user_id;
		this.mobile = mobile;
		this.restMoney = restMoney;
		this.score = score;
		this.seniorRange = seniorRange;
		this.userType = userType;
	}


	public BigDecimal getRestMoney() {
        return restMoney;
    }

    public void setRestMoney(BigDecimal restMoney) {
        this.restMoney = restMoney;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSeniorRange() {
		return seniorRange;
	}

	public void setSeniorRange(String seniorRange) {
		this.seniorRange = seniorRange;
	}

	public short getUserType() {
		return userType;
	}

	public void setUserType(short userType) {
		this.userType = userType;
	}
}