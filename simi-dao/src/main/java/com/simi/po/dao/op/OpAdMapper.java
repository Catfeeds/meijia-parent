package com.simi.po.dao.op;

import java.util.List;

import com.simi.po.model.op.OpAd;

public interface OpAdMapper {
    int insert(OpAd record);

    int insertSelective(OpAd record);

	List<OpAd> selectByListPage();

	OpAd selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OpAd record);
}