package com.simi.po.dao.card;

import java.util.HashMap;
import java.util.List;
import com.simi.po.model.card.CardZan;
import com.simi.vo.card.CardSearchVo;

public interface CardZanMapper {
	
    int deleteByPrimaryKey(Long id);

    Long insert(CardZan record);

    int insertSelective(CardZan record);

    CardZan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CardZan record);

    int updateByPrimaryKey(CardZan record);

	CardZan selectBySearchCardVo(CardSearchVo vo);

	int totalByCardId(Long cardId);
	
	List<CardZan> getByTop10(Long cardId);

	List<HashMap> totalByCardIds(List<Long> cardIds);
}