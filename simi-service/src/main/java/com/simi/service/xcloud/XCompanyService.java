package com.simi.service.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.Xcompany;
import com.simi.vo.xcloud.CompanySearchVo;



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

	Xcompany selectByInvitationCode(String invitationCode);

	Xcompany selectByCompanyNameAndUserName(String companyName, String userName);

	List<Xcompany> selectByIds(List<Long> ids);
	
	List<Xcompany> selectByListPage(CompanySearchVo searchVo, int pageNo,
			int pageSize);

}
