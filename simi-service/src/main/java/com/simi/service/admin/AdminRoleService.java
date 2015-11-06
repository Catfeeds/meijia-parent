package com.simi.service.admin;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.simi.vo.admin.AdminRoleSearchVo;
import com.simi.po.model.admin.AdminRole;


public interface AdminRoleService {

	AdminRole selectByPrimaryKey(Long id);

	AdminRole selectByName(String name);
	
	AdminRole initAdminRole();

	int insert(AdminRole record);

    Long insertSelective(AdminRole record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);

	PageInfo searchVoListPage(AdminRoleSearchVo searchVo,int pageNo,int pageSize);

	AdminRole selectAdminRoleVoByPrimaryKey(Long id);

	public void saveRoleToAuthorize(Long roleId, Long[] authorityIds);

	Map<Long,String> getSelectSource();








}