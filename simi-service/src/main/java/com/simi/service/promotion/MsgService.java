package com.simi.service.promotion;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.github.pagehelper.PageInfo;
import com.simi.vo.promotion.MsgSearchVo;
import com.simi.po.model.promotion.Msg;

public interface MsgService {

	int deleteByPrimaryKey(Long id);

    int insert(Msg record);

    int insertSelective(Msg record);

    Msg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Msg record);

    int updateByPrimaryKey(Msg record);

    PageInfo searchListPage(MsgSearchVo msgSearchVo,int pageNo, int pageSize);

    Msg initMsg();

	Msg selectByMobile(String mobile);

	int pushMsgFromBaidu(Long msgId) throws PushClientException,
			PushServerException;


}
