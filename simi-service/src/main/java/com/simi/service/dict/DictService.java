package com.simi.service.dict;

import java.util.List;

import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictRegion;
import com.simi.po.model.dict.DictServiceTypes;

public interface DictService {

	void loadData();

	String getCityName(Long cityId);

	String getServiceTypeName(Long serviceTypeId);

	List<DictRegion> getRegionByCityId(Long cityId);


	


}
