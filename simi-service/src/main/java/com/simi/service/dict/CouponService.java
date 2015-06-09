package com.simi.service.dict;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.vo.dict.CouponSearchVo;
import com.simi.po.model.dict.DictCoupons;

public interface CouponService {

	DictCoupons selectByPrimaryKey(Long id);

	int insert(DictCoupons record);

    int insertSelective(DictCoupons record);

	int updateByPrimaryKeySelective(DictCoupons record);

	int updateByPrimaryKey(DictCoupons record);

	DictCoupons initCoupon(String cardNo, String cardPasswd);

	List<DictCoupons> selectAll();

	List<DictCoupons> selectAllByCardNo();


	DictCoupons selectByCardPasswd(String cardPasswd);

	List<DictCoupons> selectByIds(List<Long> ids);

	PageInfo searchVoListPage(CouponSearchVo searchVo,int pageNo,int pageSize);


}
