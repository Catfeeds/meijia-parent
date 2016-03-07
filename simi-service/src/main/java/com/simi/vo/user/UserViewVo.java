package com.simi.vo.user;

import com.simi.po.model.user.Users;

public class UserViewVo extends Users {
	
	private long userId;
	
	/**
	 * 私秘卡有效期
	 */
    private String seniorRange;
    
    /**
     * 是否可以使用真人秘书服务 1=是 0=否
     */
    private Short isSenior;
    
    private Long secId;
    
    /**
     * 秘书IM账号
     */
    private String imSecUsername;
    
    /**
     * 秘书IM昵称
     */
    private String imSecNickname;
    
    /**
     * 用户Im用户名
     */
    private String ImUsername;
    
    /**
     * 用户Im密码
     */
    private String ImPassword;
    
    /**
     * 机器人管家账号
     */
    private String ImRobotUsername;
    
    /**
     * 机器人管家昵称
     */
    private String ImRobotNickname;
    
    /**
     * 绑定推送设备clientID
     */
    private String clientId;
    
    //是否属于某公司的员工
    private Short hasCompany;
    
    //所属公司的ID
    private Long companyId;
    
    private String companyName;
    
    //所属公司个数
    private int companyCount;
        
	public String getImUsername() {
		return ImUsername;
	}

	public void setImUsername(String imUsername) {
		ImUsername = imUsername;
	}

	public String getImPassword() {
		return ImPassword;
	}

	public void setImPassword(String imPassword) {
		ImPassword = imPassword;
	}

	public String getImRobotUsername() {
		return ImRobotUsername;
	}

	public void setImRobotUsername(String imRobotUsername) {
		ImRobotUsername = imRobotUsername;
	}

	public String getImRobotNickname() {
		return ImRobotNickname;
	}

	public void setImRobotNickname(String imRobotNickname) {
		ImRobotNickname = imRobotNickname;
	}

	public UserViewVo() {
		super();
	}

	public String getSeniorRange() {
		return seniorRange;
	}

	public void setSeniorRange(String seniorRange) {
		this.seniorRange = seniorRange;
	}

	public Short getIsSenior() {
		return isSenior;
	}

	public void setIsSenior(Short isSenior) {
		this.isSenior = isSenior;
	}


	public String getImSecUsername() {
		return imSecUsername;
	}

	public void setImSecUsername(String imSecUsername) {
		this.imSecUsername = imSecUsername;
	}

	public String getImSecNickname() {
		return imSecNickname;
	}

	public void setImSecNickname(String imSecNickname) {
		this.imSecNickname = imSecNickname;
	}

	public long getUserId() {
		return userId;
	}

	public Long getSecId() {
		return secId;
	}

	public void setSecId(Long secId) {
		this.secId = secId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Short getHasCompany() {
		return hasCompany;
	}

	public void setHasCompany(Short hasCompany) {
		this.hasCompany = hasCompany;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public int getCompanyCount() {
		return companyCount;
	}

	public void setCompanyCount(int companyCount) {
		this.companyCount = companyCount;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
