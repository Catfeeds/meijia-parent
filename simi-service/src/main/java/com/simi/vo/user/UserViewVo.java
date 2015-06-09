package com.simi.vo.user;

import com.simi.po.model.user.Users;

public class UserViewVo extends Users {
	
	private long user_id;
	
	/**
	 * 管家卡有效期
	 */
    private String seniorRange;
    
    /**
     * 是否可以使用真人管家服务 1=是 0=否
     */
    private Short isSenior;
    /**
     * 真人管家IM账号
     */
    private String imSeniorUsername;
    
    /**
     * 真人管家IM昵称
     */
    private String imSeniorNickname;
    
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

	public String getImSeniorUsername() {
		return imSeniorUsername;
	}

	public void setImSeniorUsername(String imSeniorUsername) {
		this.imSeniorUsername = imSeniorUsername;
	}

	public String getImSeniorNickname() {
		return imSeniorNickname;
	}

	public void setImSeniorNickname(String imSeniorNickname) {
		this.imSeniorNickname = imSeniorNickname;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

}
