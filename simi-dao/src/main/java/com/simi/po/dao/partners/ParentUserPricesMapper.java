package com.simi.po.dao.partners;

import com.simi.po.model.partners.ParentUserPrices;

public interface ParentUserPricesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ParentUserPrices record);

    int insertSelective(ParentUserPrices record);

    ParentUserPrices selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ParentUserPrices record);

    int updateByPrimaryKey(ParentUserPrices record);
}