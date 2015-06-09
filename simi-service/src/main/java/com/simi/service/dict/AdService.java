package com.simi.service.dict;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.dict.DictAd;

public interface AdService {

	List<DictAd> getAdByServiceType(Long serviceType);

	DictAd initAd();

    DictAd selectByPrimaryKey(Long id);

    int insertSelective(DictAd record);

    int updateByPrimaryKeySelective(DictAd record);

	PageInfo searchVoListPage(int pageNo, int pageSize);

	int deleteByPrimaryKey(Long id);



}
