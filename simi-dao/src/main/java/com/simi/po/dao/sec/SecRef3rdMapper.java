package com.simi.po.dao.sec;

import com.simi.po.model.sec.Sec;
import com.simi.po.model.sec.SecRef3rd;
import com.simi.po.model.user.UserRef3rd;



public interface SecRef3rdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecRef3rd record);

    int insertSelective(SecRef3rd record);

    SecRef3rd selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecRef3rd record);

    int updateByPrimaryKey(SecRef3rd record);

	SecRef3rd selectBySecIdForIm(Long secId);

	int updateByPrimaryKeySelective(Sec sec);

}