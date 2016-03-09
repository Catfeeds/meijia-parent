package com.simi.po.dao.common;

import java.util.List;

import com.simi.po.model.common.Imgs;
import com.simi.vo.ImgSearchVo;

public interface ImgsMapper {
    int deleteByPrimaryKey(Long imgId);

    Long insert(Imgs record);

    int insertSelective(Imgs record);

    Imgs selectByPrimaryKey(Long imgId);

    int updateByPrimaryKeySelective(Imgs record);

    Long updateByPrimaryKey(Imgs record);

	List<Imgs> selectBySearchVo(ImgSearchVo searchVo);
}