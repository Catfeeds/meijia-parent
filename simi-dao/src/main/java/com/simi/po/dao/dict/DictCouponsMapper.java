package com.simi.po.dao.dict;

import java.util.List;
import java.util.Map;

import com.simi.po.model.dict.DictCoupons;

public interface DictCouponsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DictCoupons record);

    int insertSelective(DictCoupons record);

    DictCoupons selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictCoupons record);

    int updateByPrimaryKey(DictCoupons record);

	List<DictCoupons> selectAll();

	List<DictCoupons> selectAllByCardNo();

	List<DictCoupons> selectByIds(List<Long> ids);

	DictCoupons selectByCardPasswd(String cardPasswd);

	List<DictCoupons> selectByListPage(Map<String,Object> conditions);
}