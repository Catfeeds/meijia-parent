package com.simi.vo.msg;

import com.simi.po.model.msg.Msg;

/**
 *
 * @author :hulj
 * @Date : 2016年5月19日下午5:35:46
 * @Description: 
 *
 */
public class OaMsgVo extends Msg {
	
	private Short sendWay;	// 消息 发送方式  0=测试  1=保存并推送
	
	private String sendTestUser;
	
	private Long selectUserType;	// 下拉选的 userType
	
	public Long getSelectUserType() {
		return selectUserType;
	}

	public void setSelectUserType(Long selectUserType) {
		this.selectUserType = selectUserType;
	}

	public Short getSendWay() {
		return sendWay;
	}

	public void setSendWay(Short sendWay) {
		this.sendWay = sendWay;
	}

	public String getSendTestUser() {
		return sendTestUser;
	}

	public void setSendTestUser(String sendTestUser) {
		this.sendTestUser = sendTestUser;
	}
	
	
}
