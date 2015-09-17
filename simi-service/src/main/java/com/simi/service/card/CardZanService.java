package com.simi.service.card;

import java.util.HashMap;
import java.util.List;
import com.simi.po.model.card.CardZan;
import com.simi.vo.card.CardZanViewVo;
import com.simi.vo.card.CardSearchVo;

public interface CardZanService {

	int deleteByPrimaryKey(Long id);

	Long insert(CardZan record);

	CardZan initCardZan();

	int insertSelective(CardZan record);

	CardZan selectByPrimaryKey(Long id);

	int updateByPrimaryKey(CardZan record);

	int updateByPrimaryKeySelective(CardZan record);

	CardZan selectBySearchCardVo(CardSearchVo vo);

	int totalByCardId(Long cardId);

	List<CardZanViewVo> getByTop10(Long cardId);

	List<HashMap> totalByCardIds(List<Long> cardIds);

}
