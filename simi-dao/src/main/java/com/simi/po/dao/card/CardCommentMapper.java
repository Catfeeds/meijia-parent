package com.simi.po.dao.card;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.card.CardComment;
import com.simi.vo.card.CardSearchVo;

public interface CardCommentMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(CardComment record);

    int insertSelective(CardComment record);

    CardComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CardComment record);

    int updateByPrimaryKey(CardComment record);

	CardComment selectBySearchCardVo(CardSearchVo vo);

	int totalByCardId(Long cardId);

	List<HashMap> totalByCardIds(List<Long> cardIds);

	List<CardComment> selectByListPage(CardSearchVo vo);
}