package com.simi.service.op;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.op.OpAutoFeed;
import com.simi.vo.po.AdSearchVo;

public interface OpAutoFeedService {

	PageInfo selectByListPage(int pageNo, int pageSize);

	OpAutoFeed initOpAutoFeed();

	OpAutoFeed selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OpAutoFeed record);

	Long insertSelective(OpAutoFeed opAd);

	List<OpAutoFeed> selectByTotal();

}
