package com.simi.service.dict;

import java.util.List;

import com.resume.po.model.dict.HrDictType;
import com.resume.po.model.dict.HrDicts;
import com.resume.po.model.dict.HrFrom;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictExpress;
import com.simi.po.model.dict.DictProvince;
import com.simi.po.model.dict.DictRegion;

public interface DictService {

	void loadData();

	String getCityName(Long cityId);

	List<DictRegion> getRegionByCityId(Long cityId);
	
	List<DictCity> getCityByProvinceId(Long provinceId);

	List<DictProvince> LoadProvinceData();

	List<DictCity> LoadCityData();

	List<DictExpress> loadExpressData();

	String getRegionName(Long regionId);

	List<HrDicts> loadHrDictRules(Boolean rebuild);

	List<HrFrom> loadHrFrom(Boolean rebuild);

	List<HrDictType> loadHrDictType(Boolean rebuild);



	


}
