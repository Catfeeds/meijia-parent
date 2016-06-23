package com.simi.service;

import java.util.List;

import com.resume.po.model.dict.HrDictType;
import com.resume.po.model.dict.HrDicts;
import com.resume.po.model.dict.HrFrom;

public interface CacheDictService {

	void loadData();

	List<HrDicts> loadHrDictRules(Boolean rebuild);

	List<HrFrom> loadHrFrom(Boolean rebuild);

	List<HrDictType> loadHrDictType(Boolean rebuild);



	


}
