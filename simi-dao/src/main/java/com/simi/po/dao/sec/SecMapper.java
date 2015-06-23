package com.simi.po.dao.sec;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.sec.Sec;
import com.simi.vo.SecList;




public interface SecMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(Sec record);

    Long insertSelective(Sec record);

    Sec selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Sec record);

    int updateByPrimaryKey(Sec record);
    
    List<Sec> selectByListPage();

	Sec selectByNameAndOtherId(HashMap map);

	SecList selectByMobile(String mobile);

	Sec selectVoBySecId(Long secId);


	
	
	
	

}