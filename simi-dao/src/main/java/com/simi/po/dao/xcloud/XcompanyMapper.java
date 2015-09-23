package com.simi.po.dao.xcloud;

import com.simi.po.model.xcloud.Xcompany;

public interface XcompanyMapper {
    int deleteByPrimaryKey(Long companyId);

    Long insert(Xcompany record);

    int insertSelective(Xcompany record);

    Xcompany selectByPrimaryKey(Long companyId);

    int updateByPrimaryKeySelective(Xcompany record);

    int updateByPrimaryKey(Xcompany record);

	Xcompany selectByUserName(String userName);

	Xcompany selectByUserNameAndPass(String userName, String passMd5);
	
	Xcompany selectByCompanyName(String companyName);
}