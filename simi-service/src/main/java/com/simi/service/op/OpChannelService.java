package com.simi.service.op;


import com.github.pagehelper.PageInfo;
import com.simi.po.model.op.OpChannel;

public interface OpChannelService {

	PageInfo searchVoListPage(int pageNo, int pageSize);

	OpChannel initOpChannel();

	OpChannel selectByPrimaryKey(Long channelId);

	int updateByPrimaryKeySelective(OpChannel record);

	int insertSelective(OpChannel record);

	int deleteByPrimaryKey(Long channelId);


}
