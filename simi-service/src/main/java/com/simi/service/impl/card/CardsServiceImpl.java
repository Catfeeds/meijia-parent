package com.simi.service.impl.card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardCommentService;
import com.simi.service.card.CardService;
import com.simi.service.card.CardZanService;
import com.simi.service.data.WeatherService;
import com.simi.service.dict.CityService;
import com.simi.service.dict.DictUtil;
import com.simi.service.user.UsersService;
import com.simi.utils.CardUtil;
import com.simi.vo.card.CardListVo;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.card.CardViewVo;
import com.simi.vo.card.CardZanViewVo;
import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.CardImgs;
import com.simi.po.model.card.Cards;
import com.simi.po.model.data.Weathers;
import com.simi.po.model.user.Users;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.baidu.BaiduMapUtil;
import com.meijia.utils.weather.WeatherDataVo;
import com.meijia.utils.weather.WeatherIndexVo;
import com.simi.po.dao.card.CardImgsMapper;
import com.simi.po.dao.card.CardsMapper;

@Service
public class CardsServiceImpl implements CardService {
	@Autowired
	CardsMapper cardsMapper;
	
	@Autowired
	CardImgsMapper cardImgsMapper;
	
	@Autowired
	UsersService usersService;

	@Autowired
	CardAttendService cardAttendService;

	@Autowired
	CardZanService cardZanService;
	
	@Autowired
	CardCommentService cardCommentService;	
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private WeatherService weatherService;	
		
	@Override
	public Cards initCards() {
		Cards record = new Cards();
		record.setCardId(0L);
		record.setCreateUserId(0L);
		record.setUserId(0L);
		record.setCardType((short) 0);
		record.setTitle("");
		record.setServiceTime(0L);
		record.setServiceContent("");
		record.setCardExtra("");
		record.setSetRemind((short) 0);
		record.setSetNowSend((short) 0);
		record.setSetSecDo((short) 0);
		record.setSetSecRemarks("");
		record.setStatus((short) 1);
		record.setSecRemarks("");
		record.setSetFriendView((short) 0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		return record;
	}
	
	@Override
	public CardViewVo initCardView() {
		CardViewVo record = new CardViewVo();
		record.setCardId(0L);
		record.setCreateUserId(0L);
		record.setUserId(0L);
		record.setCardType((short) 0);
		record.setTitle("");
		record.setServiceTime(0L);
		record.setServiceContent("");
		record.setCardExtra("");
		record.setSetRemind((short) 0);
		record.setSetNowSend((short) 0);
		record.setSetSecDo((short) 0);
		record.setSetSecRemarks("");
		record.setStatus((short) 1);
		record.setSecRemarks("");
		record.setSetFriendView((short) 0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		
		record.setName("");
		record.setAttends(new ArrayList<CardAttend>());
		record.setCardImgsList(new ArrayList<CardImgs>());
		record.setCreateUserName("");
		record.setUserName("");
		record.setUserHeadImg("");
		record.setTotalZan(0);
		record.setTotalComment(0);
		record.setZanTop10(new ArrayList<CardZanViewVo>());
		record.setCardTypeName("");
		record.setAddTimeStr("");


		return record;
	}	
	
	@Override
	public CardListVo initCardListVo() {
		CardListVo record = new CardListVo();
		record.setCardId(0L);
		record.setCardType((short) 0);
		record.setServiceTime(0L);
		record.setServiceContent("");
		record.setTotalZan(0);
		record.setTotalComment(0);
		record.setCardTypeName("");
		record.setAddTimeStr("");
		record.setCardExtra("");
		record.setTitle("");
		return record;
	}		
	
	/**
	 * 转换card 对象为 cardViewVo对象
	 * @param card
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Override
	public CardViewVo changeToCardViewVo(Cards card) {
		CardViewVo vo = new CardViewVo();
		if (card == null) return vo;
		Long cardId = card.getCardId();
		
		//进行对象复制.
		BeanUtilsExp.copyPropertiesIgnoreNull(card, vo);
		
		//获取到参会、参与人员列表
		List<CardAttend> attends = cardAttendService.selectByCardId(cardId);
		vo.setAttends(attends);
		
		//获取用户名称
		Users createUser = usersService.getUserInfo(vo.getCreateUserId());
		if (createUser != null) {
			vo.setCreateUserId(createUser.getId());
			vo.setCreateUserName(createUser.getName());
			vo.setHeadImgCreateUser(createUser.getHeadImg());
		}
		
		Users u = usersService.getUserInfo(vo.getUserId());
		if (u != null) {
			vo.setUserName(u.getName());
			vo.setUserHeadImg(usersService.getHeadImg(u));
		}
		
		//统计赞的数量
		int totalZan = cardZanService.totalByCardId(cardId);
		vo.setTotalZan(totalZan);
		
		//统计评论的数量
		int totalComment = cardCommentService.totalByCardId(cardId);
		vo.setTotalComment(totalComment);
		
		//获得点赞前十个用户及头像.
		List<CardZanViewVo> zanTop10 = cardZanService.getByTop10(cardId);
		vo.setZanTop10(zanTop10);
		
		//卡片类型名称
		String cardTypeName = CardUtil.getCardTypeName(vo.getCardType());
		vo.setCardTypeName(cardTypeName);
		
		//卡片添加时间字符串
		Date addTimeDate = TimeStampUtil.timeStampToDateFull(vo.getAddTime() * 1000, null);
		String addTimeStr = DateUtil.fromToday(addTimeDate);
		vo.setAddTimeStr(addTimeStr);
		
		//卡片图片
		List<CardImgs> list = cardImgsMapper.selectByCardId(vo.getCardId());

		if (list != null) {
			vo.setCardImgsList(list);
		}
		
		//卡片log
		vo.setCardLogo(CardUtil.getCardLogo(vo.getCardType()));
		
		return vo;
	}
	
	/**
	 * 批量转换card 对象为 cardViewVo对象
	 * @param List<card>
	 * @return
	 */
	@Override
	public List<CardListVo> changeToCardListVo(List<Cards> cards) {
		List<CardListVo> result = new ArrayList<CardListVo>();
		if (cards.isEmpty()) return result;
		
		List<Long> cardIds = new ArrayList<Long>();
		List<Long> userIds = new ArrayList<Long>();
		for (Cards item : cards) {
			cardIds.add(item.getCardId());
			if (!userIds.contains(item.getCreateUserId())) {
				userIds.add(item.getCreateUserId());
			}
		}

		List<CardAttend> cardAttends = new ArrayList<CardAttend>();
		List<HashMap> totalCardZans = new ArrayList<HashMap>();
		List<HashMap> totalCardComments = new ArrayList<HashMap>();
		List<Users> createUsers = new ArrayList<Users>();
		
		if (!cardIds.isEmpty()) {
			cardAttends = cardAttendService.selectByCardIds(cardIds);
			totalCardZans = cardZanService.totalByCardIds(cardIds);
			totalCardComments = cardCommentService.totalByCardIds(cardIds);
		}
		
		if (!userIds.isEmpty()) {
			createUsers = usersService.selectByUserIds(userIds);
		}
				
		Cards item = null;
		for (int i = 0; i < cards.size(); i++) {
			item = cards.get(i);
			CardListVo vo = this.initCardListVo();
			
			vo.setCardId(item.getCardId());
			vo.setCardType(item.getCardType());
			
			for (Users us : createUsers) {
				if (us.getId().equals(item.getCreateUserId())) {
					vo.setCreateUserId(item.getCreateUserId());
					vo.setHeadImgCreateUser(us.getHeadImg());
					break;
				}
			}
			
			//卡片类型名称
			String cardTypeName = CardUtil.getCardTypeName(vo.getCardType());
			vo.setCardTypeName(cardTypeName);
			
			vo.setServiceTime(item.getServiceTime());
			
			if (!StringUtil.isEmpty(item.getServiceContent())) {
				if (item.getServiceContent().length() > 200) {
					vo.setServiceContent(item.getServiceContent().substring(0, 200));
				} else {
					vo.setServiceContent(item.getServiceContent());
				}
			}
			
			vo.setServiceAddr(item.getServiceAddr());
			
			//服务时间字符串
			Date addTimeDate = TimeStampUtil.timeStampToDateFull(item.getAddTime() * 1000, null);
			String addTimeStr = DateUtil.fromToday(addTimeDate);
			vo.setAddTimeStr(addTimeStr);			
			
			//统计赞的数量
			vo.setTotalZan(0);
			for (HashMap totalCardZan : totalCardZans) {
				Long tmpCardId = Long.valueOf(totalCardZan.get("card_id").toString());
				if (tmpCardId.equals(vo.getCardId())) {
					vo.setTotalZan(Integer.parseInt(totalCardZan.get("total").toString()));
				}
			}
			
			//统计评论的数量
			vo.setTotalComment(0);
			for (HashMap totalCardComment : totalCardComments) {
				Long tmpCardId = Long.valueOf(totalCardComment.get("card_id").toString());
				if (tmpCardId.equals(vo.getCardId())) {
					vo.setTotalComment(Integer.parseInt(totalCardComment.get("total").toString()));
				}
			}
			
			//获取到参会、参与人员列表
			List<CardAttend> cardAttendItems = new ArrayList<CardAttend>();
			for (CardAttend cardAttend : cardAttends) {
				if (cardAttend.getCardId().equals(vo.getCardId())) {
					cardAttendItems.add(cardAttend);
				}
			}
			vo.setAttends(cardAttendItems);
			
			vo.setStatus(item.getStatus());
			
			vo.setCardExtra(item.getCardExtra());

			result.add(vo);
		}
		
		return result;
	}	
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return cardsMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insert(Cards record) {
		return cardsMapper.insert(record);
	}
	
	@Override
	public int insertSelective(Cards record) {
		return cardsMapper.insertSelective(record);
	}

	@Override
	public Cards selectByPrimaryKey(Long id) {
		return cardsMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<Cards> selectBySearchVo(CardSearchVo vo) {
		
		List<Cards> list = new ArrayList<Cards>();
		
		Short cardFrom = vo.getCardFrom();
		
		//某个用户所有的卡片
		if (cardFrom.equals((short)0)) {
			list = cardsMapper.selectBySearchVo(vo);
		}
		
		//某个用户发布的卡片
		if (cardFrom.equals((short)1)) {
			list = cardsMapper.selectMineBySearchVo(vo);
		}
		
		//某个用户参与的卡片
		if (cardFrom.equals((short)2)) {
			list = cardsMapper.selectAttendBySearchVo(vo);
		}
		
		return list;
	}	
	
	@Override
	public PageInfo selectByListPage(CardSearchVo vo, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		
		List<Cards> list = new ArrayList<Cards>();
		
		Short cardFrom = vo.getCardFrom();
		
		//某个用户所有的卡片
		if (cardFrom.equals((short)0)) {
			list = cardsMapper.selectByListPage(vo);
		}
		
		//某个用户发布的卡片
		if (cardFrom.equals((short)1)) {
			list = cardsMapper.selectMineByListPage(vo);
		}
		
		//某个用户参与的卡片
		if (cardFrom.equals((short)2)) {
			list = cardsMapper.selectAttendByListPage(vo);
		}
		
		PageInfo result = new PageInfo(list);
		return result;
	}		

	@Override
	public int updateByPrimaryKey(Cards record) {
		return cardsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Cards record) {
		return cardsMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<HashMap> totalByMonth(CardSearchVo vo) {
		return cardsMapper.totalByMonth(vo);
	}
	
	@Override
	public List<Cards> selectByReminds(CardSearchVo vo) {
		
		List<Cards> result = new ArrayList<Cards>();
		result = cardsMapper.selectByReminds(vo);
		return result;
	}
	
	@Override
	public List<Cards> selectByRemindAll(CardSearchVo vo) {
		
		List<Cards> result = new ArrayList<Cards>();
		result = cardsMapper.selectByRemindAll(vo);
		return result;
	}	
	
	/**
	 * 查找服务时间超过当前时间的卡片,并更新状态 = 3
	 * @return
	 */
	@Override
	public boolean updateFinishByOvertime() {
		cardsMapper.updateFinishByOvertime();
		return true;
	}
	
	@Override
	public List<Cards> selectListByAddtimeTwo() {
		
		return cardsMapper.selectListByAddtimeTwo();
	}

	@Override
	public List<Cards> selectListByAddtimeThirty() {
		
		return cardsMapper.selectListByAddtimeThirty();
	}
		
	@Override
	public CardListVo getWeatherCard(String serviceDate, String lat, String lng) {
		
		Cards card = initCards();

		CardListVo result = this.initCardListVo();

		BeanUtilsExp.copyPropertiesIgnoreNull(card, result);
		//如果没有地理位置信息，默认则为北京市
		String cityName = "北京市";
		if (StringUtil.isEmpty(lat) || StringUtil.isEmpty(lng)) {
			cityName = "北京市";
		} else {
			cityName = BaiduMapUtil.getCityByPoi(lat, lng);
		}
		
		if (StringUtil.isEmpty(cityName)) cityName = "北京市";
		
		Long cityId = 0L;
		cityId = DictUtil.getCityId(cityName);
		
		if (cityId.equals(0L)) cityId = 2L;
		
		Date weatherDate = DateUtil.parse(serviceDate);
		Weathers weatherInfo = weatherService.selectByCityIdAndDate(cityId, weatherDate);
		
		
		if (weatherInfo == null) return result;
		
		List<WeatherDataVo> weatherDatas =GsonUtil.GsonToList(weatherInfo.getWeatherData(), WeatherDataVo.class);
		
		String nowDateStr = DateUtil.getToday();
		
		WeatherDataVo curItem = weatherDatas.get(0);
		String realTemp = curItem.getDate();
		
		realTemp = realTemp.substring(realTemp.indexOf("(") + 1, realTemp.indexOf(")"));
		realTemp = realTemp.substring(realTemp.indexOf("：") + 1);

		WeatherIndexVo weatherIndex = null;
		List<WeatherIndexVo> weatherIndexs =GsonUtil.GsonToList(weatherInfo.getWeatherIndex(), WeatherIndexVo.class);
		for (WeatherIndexVo item : weatherIndexs) {
			System.out.println(item.getTitle());
			
			if (item.getTitle().equals("穿衣")) {
				weatherIndex = item;
				break;
			}
			
		}
		
		Short cardType = 99;
		result.setCardTypeName(CardUtil.getCardTypeName(cardType));
		result.setCardType(cardType);
		
		String lastTime = weatherInfo.getLastTime();
		result.setAddTimeStr(lastTime);
		
		Map<String, Object> cardExtraMap = new HashMap<String, Object>();
		cardExtraMap.put("cityName", cityName);
		cardExtraMap.put("weatherDatas",weatherDatas);
		cardExtraMap.put("weatherIndex", weatherIndex);
		cardExtraMap.put("real_temp", realTemp);
		
		String cardExtra = GsonUtil.GsonString(cardExtraMap);
		
		result.setCardExtra(cardExtra);
		
//		result.setServiceContent(serviceContent);
		
		return result;
	}

}