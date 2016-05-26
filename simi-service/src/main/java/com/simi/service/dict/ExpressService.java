package com.simi.service.dict;

import java.util.List;

import com.simi.po.model.dict.DictExpress;
import com.simi.vo.ExpressSearchVo;


public interface ExpressService {

	DictExpress selectByPrimaryKey(Long id);

	List<DictExpress> selectBySearchVo(ExpressSearchVo searchVo);

}
