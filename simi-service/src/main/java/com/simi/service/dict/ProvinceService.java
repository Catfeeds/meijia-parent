package com.simi.service.dict;

import java.util.List;

import com.simi.po.model.dict.DictProvince;

public interface ProvinceService {

	DictProvince getProvinceById(Long id);

	List<DictProvince> selectAll();

}
