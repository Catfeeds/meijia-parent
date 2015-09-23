package com.simi.service.xcloud;

import com.simi.po.model.xcloud.Xcompany;



public interface XCompanyService {

	int deleteByPrimaryKey(Long id);

	Long insert(Xcompany record);

	int insertSelective(Xcompany record);

	Xcompany selectByPrimaryKey(Long id);

	int updateByPrimaryKey(Xcompany record);

	int updateByPrimaryKeySelective(Xcompany record);

	Xcompany initXcompany();

	Xcompany selectByUserName(String userName);

	Xcompany selectByUserNameAndPass(String userName, String passMd5);

	Xcompany selectByCompanyName(String companyName);

}
