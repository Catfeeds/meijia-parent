package com.simi.vo.admin;

import java.util.ArrayList;
import java.util.List;

import com.simi.po.model.admin.AdminAuthority;
import com.simi.po.model.admin.AdminRole;
import com.meijia.utils.common.extension.ArrayHelper;

public class AdminRoleVo extends AdminRole{

	private  List<AdminAuthority> childList = new ArrayList<AdminAuthority>() ;

	private  Long[]  authorityIds;

	public List<AdminAuthority> getChildList() {
		return childList;
	}

	public void setChildList(List<AdminAuthority> childList) {
		this.childList = childList;
	}

	public Long[] getAuthorityIds() {
		return authorityIds;
	}

	public void setAuthorityIds(Long[] authorityIds) {
		this.authorityIds = authorityIds;
	}

	public String getAuthorityIdsString(){
		return ArrayHelper.LongtoString(authorityIds, ",");
	}





}
