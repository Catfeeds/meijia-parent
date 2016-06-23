package com.simi.service.dict;

import java.util.List;

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

}
