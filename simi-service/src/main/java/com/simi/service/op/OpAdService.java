package com.simi.service.op;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.op.OpAd;
import com.simi.vo.po.AdSearchVo;

public interface OpAdService {

	OpAd initAd();

	OpAd selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OpAd opAd);

	int insertSelective(OpAd opAd);

	List<OpAd> selectByAdType(String adType);

	PageInfo searchVoListPage(AdSearchVo searchVo, int pageNo, int pageSize);



}
