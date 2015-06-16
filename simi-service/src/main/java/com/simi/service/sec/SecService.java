package com.simi.service.sec;
import com.github.pagehelper.PageInfo;
import com.simi.po.model.sec.Sec;
import com.simi.po.model.sec.SecRef3rd;
import com.simi.vo.SecList;


public interface SecService {

	 int insertSelective(Sec record);

	 PageInfo searchVoListPage(int pageNo, int pageSize);
	 
	 int deleteByPrimaryKey(Long id);

	 Sec initSec();
	
	 Sec selectByUserNameAndOtherId(String name, Long id);

	SecList selectByMobile(String mobile);

	SecRef3rd genImSec(Sec sec);


}
