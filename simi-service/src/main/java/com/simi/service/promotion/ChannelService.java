package com.simi.service.promotion;

import com.github.pagehelper.PageInfo;
import com.simi.vo.chan.ChannelSearchVo;
import com.simi.po.model.promotion.Channel;

public interface ChannelService {

	int deleteByPrimaryKey(Long id);

	int insert(Channel record);

	int insertSelective(Channel record);

	Channel selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Channel record);

	int updateByPrimaryKey(Channel record);

	PageInfo searchVoListPage(ChannelSearchVo searchVo,int pageNo, int pageSize);

	Channel initChannel();

	Channel selectByToken(String token);

	int updateByTotalDownload(String token);





}
