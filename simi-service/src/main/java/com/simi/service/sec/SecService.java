package com.simi.service.sec;
import com.github.pagehelper.PageInfo;
import com.simi.po.model.sec.Sec;


public interface SecService {

	 int insertSelective(Sec record);

	 PageInfo searchVoListPage(int pageNo, int pageSize);
	 
	 int deleteByPrimaryKey(Long id);

	 Sec initSec();
	
	 Sec selectByUserNameAndOtherId(String username, Long id);
}
