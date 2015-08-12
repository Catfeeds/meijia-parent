package com.simi.service.card;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.card.CardComment;
import com.simi.vo.card.CardCommentViewVo;
import com.simi.vo.card.CardSearchVo;

public interface CardCommentService {

	CardComment initCardComment();

	CardComment selectByPrimaryKey(Long id);

	CardComment selectBySearchCardVo(CardSearchVo vo);

	int totalByCardId(Long cardId);

	int insertSelective(CardComment record);

	Long insert(CardComment record);

	int updateByPrimaryKey(CardComment record);

	int updateByPrimaryKeySelective(CardComment record);

	int deleteByPrimaryKey(Long id);

	List<HashMap> totalByCardIds(List<Long> cardIds);

	List<CardComment> selectByListPage(CardSearchVo vo, int pageNo, int pageSize);

	List<CardCommentViewVo> changeToCardComments(List<CardComment> cardComments);

}
