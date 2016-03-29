package com.simi.service.dict;

import java.util.List;

import com.simi.po.model.dict.DictExpress;


public interface ExpressService {

	DictExpress selectByPrimaryKey(Long id);

	List<DictExpress> selectAll();

	List<DictExpress> selectByT(Long t);

}
