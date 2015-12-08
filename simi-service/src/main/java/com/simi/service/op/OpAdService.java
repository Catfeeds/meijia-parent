package com.simi.service.op;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.dict.DictAd;
import com.simi.po.model.op.OpAd;

public interface OpAdService {

	PageInfo searchVoListPage(int pageNo, int pageSize);

	OpAd initAd();

	OpAd selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OpAd opAd);

	int insertSelective(OpAd opAd);



}
