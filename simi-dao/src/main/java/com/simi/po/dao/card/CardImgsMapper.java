package com.simi.po.dao.card;

import java.util.List;

import com.simi.po.model.card.CardImgs;

public interface CardImgsMapper {
    int deleteByPrimaryKey(Long imgId);

    int insert(CardImgs record);

    int insertSelective(CardImgs record);

    CardImgs selectByPrimaryKey(Long imgId);

    int updateByPrimaryKeySelective(CardImgs record);

    int updateByPrimaryKey(CardImgs record);

	List<CardImgs> selectByCardId(Long cardId);
}