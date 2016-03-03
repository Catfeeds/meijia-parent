package com.simi.vo.user;

import java.util.List;

public class UserLeaveDetailVo extends UserLeaveListVo {
	
	private List<String> imgs;
	
	private List<UserLeavePassVo> passUsers;



	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}

	public List<UserLeavePassVo> getPassUsers() {
		return passUsers;
	}

	public void setPassUsers(List<UserLeavePassVo> passUsers) {
		this.passUsers = passUsers;
	}

}
