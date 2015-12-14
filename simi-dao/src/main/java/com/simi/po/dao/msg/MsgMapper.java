package com.simi.po.dao.msg;

import java.util.List;

import com.simi.po.model.msg.Msg;
import com.simi.vo.MsgSearchVo;

public interface MsgMapper {
    int deleteByPrimaryKey(Long msgId);

    int insert(Msg record);

    int insertSelective(Msg record);

    Msg selectByPrimaryKey(Long msgId);

    int updateByPrimaryKeySelective(Msg record);

    int updateByPrimaryKeyWithBLOBs(Msg record);

    int updateByPrimaryKey(Msg record);

	List<Msg> selectUserListByUserType(MsgSearchVo searchVo);
}