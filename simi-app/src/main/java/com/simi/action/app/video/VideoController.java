package com.simi.action.app.video;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.feed.FeedZan;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePrice;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.total.TotalHit;
import com.simi.po.model.user.UserActionRecord;
import com.simi.service.feed.FeedZanService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServicePriceService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.total.TotalHitService;
import com.simi.service.user.UserActionRecordService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerServicePriceSearchVo;
import com.simi.vo.total.TotalHitSearchVo;
import com.simi.vo.user.UserActionSearchVo;

@Controller
@RequestMapping(value = "/app/video")
public class VideoController extends BaseController {

	@Autowired
	private UsersService userService;

	@Autowired
	private PartnerUserService partnerUserService;

	@Autowired
	private PartnersService partnersService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private PartnerServicePriceService partnerServicePriceService;

	@Autowired
	private TotalHitService totalHitService;

	@Autowired
	private UserActionRecordService userActionRecordService;

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
	private FeedZanService feedZanService;

	public Long serviceTypeId = 306L;

	/**
	 * 视频文章频道列表
	 * 
	 */
	@RequestMapping(value = "channels", method = RequestMethod.GET)
	public AppResultData<Object> channels() {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
		searchVo.setParentId(serviceTypeId);
		searchVo.setIsEnable((short) 1);
		List<PartnerServiceType> list = partnerServiceTypeService.selectBySearchVo(searchVo);

		List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

		for (PartnerServiceType item : list) {
			HashMap<String, Object> vo = new HashMap<String, Object>();
			vo.put("channel_id", item.getId());
			vo.put("name", item.getName());
			datas.add(vo);
		}

		result.setData(datas);

		return result;

	}

	/**
	 * 视频文章频道列表
	 * 
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public AppResultData<Object> list(@RequestParam(value = "channel_id", required = false, defaultValue = "0") Long channelId,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (channelId.equals(0L) && StringUtil.isEmpty(keyword)) {
			return result;
		}

		PartnerServicePriceSearchVo searchVo = new PartnerServicePriceSearchVo();
		searchVo.setServiceTypeId(serviceTypeId);
		if (channelId > 0L) {
			searchVo.setExtendId(channelId);
		}

		if (!StringUtil.isEmpty(keyword)) {
			searchVo.setKeyword(keyword);
		}

		PageInfo p = partnerServicePriceService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<PartnerServicePrice> list = p.getList();

		if (list.isEmpty())
			return result;

		// 阅读数
		List<Long> linkIds = new ArrayList<Long>();
		for (PartnerServicePrice item : list) {
			if (!linkIds.contains(item.getServicePriceId())) {
				linkIds.add(item.getServicePriceId());
			}
		}

		TotalHitSearchVo searchVo1 = new TotalHitSearchVo();
		searchVo1.setLinkType(Constants.TOTAL_HIT_LINK_TYPE_SERVICE_PRICE);
		searchVo1.setLinkIds(linkIds);
		List<TotalHit> totalHits = totalHitService.selectBySearchVo(searchVo1);

		List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < list.size(); i++) {
			PartnerServicePrice item = list.get(i);

			HashMap<String, Object> vo = new HashMap<String, Object>();
			vo.put("channel_id", item.getServiceTypeId());
			vo.put("article_id", item.getServicePriceId());
			vo.put("title", item.getName());
			vo.put("img_url", item.getImgUrl());
			vo.put("add_time", TimeStampUtil.timeStampToDateStr(item.getAddTime() * 1000, "MM-dd HH:mm"));
			vo.put("total_view", 0);

			for (TotalHit t : totalHits) {
				if (t.getLinkId().equals(item.getServicePriceId())) {
					vo.put("total_view", t.getTotal());
					break;
				}
			}

			datas.add(vo);

		}

		result.setData(datas);
		return result;
	}

	/**
	 * 视频文章详细内容接口
	 * 
	 * @throws URISyntaxException
	 * 
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public AppResultData<Object> getDetail(@RequestParam(value = "article_id") Long servicePriceId,
			@RequestParam(value = "user_id", required = false, defaultValue = "0") Long userId) throws URISyntaxException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (servicePriceId.equals(0L))
			return result;

		PartnerServicePrice servicePrice = partnerServicePriceService.selectByPrimaryKey(servicePriceId);

		if (servicePrice == null)
			return result;

		// 阅读数加1
		int total = 0;
		TotalHit record = totalHitService.initTotalHit();
		TotalHitSearchVo searchVo1 = new TotalHitSearchVo();
		searchVo1.setLinkType(Constants.TOTAL_HIT_LINK_TYPE_SERVICE_PRICE);
		searchVo1.setLinkId(servicePriceId);
		List<TotalHit> totalHits = totalHitService.selectBySearchVo(searchVo1);
		if (!totalHits.isEmpty()) {
			record = totalHits.get(0);
		}

		record.setLinkType(Constants.TOTAL_HIT_LINK_TYPE_SERVICE_PRICE);
		record.setLinkId(servicePriceId);

		total = record.getTotal() + 1;
		record.setTotal(total);
		record.setAddTime(TimeStampUtil.getNowSecond());
		if (record.getId() > 0L) {
			totalHitService.updateByPrimaryKeySelective(record);
		} else {
			totalHitService.insertSelective(record);
		}

		// 检测是否赞过
		FeedSearchVo searchVo2 = new FeedSearchVo();
		searchVo2.setFid(servicePriceId);
		searchVo2.setUserId(userId);
		searchVo2.setFeedType((short) 1);
		List<FeedZan> feedZans = feedZanService.selectBySearchVo(searchVo2);

		HashMap<String, Object> vo = new HashMap<String, Object>();
		vo.put("channel_id", servicePrice.getServiceTypeId());
		vo.put("article_id", servicePrice.getServicePriceId());
		vo.put("title", servicePrice.getName());
		vo.put("img_url", servicePrice.getImgUrl());
		vo.put("total_view", total);
		vo.put("add_time", TimeStampUtil.timeStampToDateStr(servicePrice.getAddTime() * 1000, "MM-dd HH:mm"));

		vo.put("price", MathBigDecimalUtil.round2(servicePrice.getPrice()));
		vo.put("dis_price", MathBigDecimalUtil.round2(servicePrice.getDisPrice()));
		vo.put("content", servicePrice.getContentStandard());
		vo.put("keywords", servicePrice.getTags());



		vo.put("video_url", "");
		vo.put("vid", "");
		vo.put("cid", "");
		vo.put("partner_user_id", 3669);
		vo.put("service_type_id", serviceTypeId);
		vo.put("service_price_id", servicePrice.getServicePriceId());
		vo.put("is_join", 0);

		vo.put("is_zan", 0);
		if (!feedZans.isEmpty())
			vo.put("is_zan", 1);
		
		//查询是否已经点击过弹窗信息
		vo.put("category", "");
		vo.put("content_desc", "");
		vo.put("goto_url", "");
		if (servicePrice.getCategory().equals("h5")) {
			
			UserActionSearchVo searchVo = new UserActionSearchVo();
			searchVo.setUserId(userId);
			searchVo.setActionType("video-help");
			searchVo.setParams(servicePrice.getServicePriceId().toString());
			
			List<UserActionRecord> rs = userActionRecordService.selectBySearchVo(searchVo);
			
			if (rs.isEmpty()) {
				vo.put("category", servicePrice.getCategory());
				vo.put("content_desc", servicePrice.getContentDesc());
				vo.put("goto_url", servicePrice.getGotoUrl());
			}
		}
		
		// 是否参加过该课程。或者是否购买过该课程.
		BigDecimal z = new BigDecimal(0);
		if (servicePrice.getDisPrice().compareTo(z) == 0) {
			UserActionSearchVo searchVo = new UserActionSearchVo();
			searchVo.setUserId(userId);
			searchVo.setActionType("video");
			searchVo.setParams(servicePriceId.toString());

			List<UserActionRecord> list = userActionRecordService.selectBySearchVo(searchVo);
			if (!list.isEmpty()) {
				vo.put("is_join", 1);
			}

			String vurl = servicePrice.getVideoUrl();
			if (!StringUtil.isEmpty(vurl)) {
				vo.put("video_url", servicePrice.getVideoUrl());

				MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(new URI(vurl)).build().getQueryParams();
				List<String> vid = parameters.get("vid");
				List<String> cid = parameters.get("cid");

				vo.put("vid", vid.get(0));
				vo.put("cid", cid.get(0));
			}
		}

		// 必须要购买
		if (servicePrice.getDisPrice().compareTo(z) == 1) {

			// 查询是否已经购买过;
			OrderSearchVo searchVo = new OrderSearchVo();
			searchVo.setUserId(userId);
			searchVo.setServicePriceId(servicePriceId);

			List<OrderPrices> orderPricesList = orderPricesService.selectBySearchVo(searchVo);

			if (!orderPricesList.isEmpty()) {
				OrderPrices orderPrice = orderPricesList.get(0);
				Long orderId = orderPrice.getOrderId();
				Orders order = ordersService.selectByPrimaryKey(orderId);
				if (order != null && order.getOrderStatus().equals((short) 2)) {
					vo.put("is_join", 1);

					String vurl = servicePrice.getVideoUrl();
					if (!StringUtil.isEmpty(vurl)) {
						vo.put("video_url", servicePrice.getVideoUrl());

						MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(new URI(vurl)).build().getQueryParams();
						List<String> vid = parameters.get("vid");
						List<String> cid = parameters.get("cid");

						vo.put("vid", vid.get(0));
						vo.put("cid", cid.get(0));
					}
				}
			}

		}

		result.setData(vo);

		return result;
	}

	/**
	 * 视频文章详细内容接口
	 * 
	 * @throws URISyntaxException
	 * 
	 */
	@RequestMapping(value = "join", method = RequestMethod.POST)
	public AppResultData<Object> join(@RequestParam(value = "article_id") Long servicePriceId,
			@RequestParam(value = "user_id", required = false, defaultValue = "0") Long userId) throws URISyntaxException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (servicePriceId.equals(0L))
			return result;

		UserActionSearchVo searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("video");
		searchVo.setParams(servicePriceId.toString());

		List<UserActionRecord> list = userActionRecordService.selectBySearchVo(searchVo);

		UserActionRecord record = userActionRecordService.initUserActionRecord();
		if (!list.isEmpty()) {
			record = list.get(0);
		}

		record.setUserId(userId);
		record.setActionType("video");
		record.setParams(servicePriceId.toString());

		if (record.getId() > 0L) {
			userActionRecordService.updateByPrimaryKey(record);
		} else {
			userActionRecordService.insert(record);
		}

		PartnerServicePrice servicePrice = partnerServicePriceService.selectByPrimaryKey(servicePriceId);

		if (servicePrice == null)
			return result;

		HashMap<String, Object> vo = new HashMap<String, Object>();

		BigDecimal z = new BigDecimal(0);
		if (servicePrice.getDisPrice().compareTo(z) == 0) {
			String vurl = servicePrice.getVideoUrl();
			if (!StringUtil.isEmpty(vurl)) {
				vo.put("video_url", servicePrice.getVideoUrl());

				MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(new URI(vurl)).build().getQueryParams();
				List<String> vid = parameters.get("vid");
				List<String> cid = parameters.get("cid");

				vo.put("vid", vid.get(0));
				vo.put("cid", cid.get(0));
			}
		}

		// 必须要购买
		if (servicePrice.getDisPrice().compareTo(z) == 1) {

			// 查询是否已经购买过;
			OrderSearchVo searchVo1 = new OrderSearchVo();
			searchVo1.setUserId(userId);
			searchVo1.setServicePriceId(servicePriceId);

			List<OrderPrices> orderPricesList = orderPricesService.selectBySearchVo(searchVo1);

			if (!orderPricesList.isEmpty()) {
				OrderPrices orderPrice = orderPricesList.get(0);
				Long orderId = orderPrice.getOrderId();
				Orders order = ordersService.selectByPrimaryKey(orderId);
				if (order != null && order.getOrderStatus().equals((short) 2)) {
					vo.put("is_join", 1);

					String vurl = servicePrice.getVideoUrl();
					if (!StringUtil.isEmpty(vurl)) {
						vo.put("video_url", servicePrice.getVideoUrl());

						MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(new URI(vurl)).build().getQueryParams();
						List<String> vid = parameters.get("vid");
						List<String> cid = parameters.get("cid");

						vo.put("vid", vid.get(0));
						vo.put("cid", cid.get(0));
					}
				}
			}

		}
		
		result.setData(vo);

		return result;
	}

	/**
	 * 视频文章详细内容接口
	 * 
	 */
	@RequestMapping(value = "relate", method = RequestMethod.GET)
	public AppResultData<Object> relate(@RequestParam(value = "article_id") Long servicePriceId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (servicePriceId.equals(0L))
			return result;

		PartnerServicePrice servicePrice = partnerServicePriceService.selectByPrimaryKey(servicePriceId);

		if (servicePrice == null)
			return result;

		String tags = servicePrice.getTags();

		if (StringUtil.isEmpty(tags))
			return result;

		PartnerServicePriceSearchVo searchVo = new PartnerServicePriceSearchVo();
		searchVo.setServiceTypeId(serviceTypeId);

		searchVo.setTags(tags);

		List<PartnerServicePrice> list = partnerServicePriceService.selectBySearchVo(searchVo);

		List<PartnerServicePrice> top10 = new ArrayList<PartnerServicePrice>();

		int maxLength = list.size();
		if (maxLength > 10)
			maxLength = 10;

		for (int i = 0; i < maxLength; i++) {
			PartnerServicePrice item = list.get(i);

			if (item.getServicePriceId().equals(servicePriceId))
				continue;

			top10.add(item);
		}

		if (top10.isEmpty())
			return result;

		// 阅读数
		List<Long> linkIds = new ArrayList<Long>();
		for (PartnerServicePrice item : top10) {
			if (!linkIds.contains(item.getServicePriceId())) {
				linkIds.add(item.getServicePriceId());
			}
		}

		TotalHitSearchVo searchVo1 = new TotalHitSearchVo();
		searchVo1.setLinkType(Constants.TOTAL_HIT_LINK_TYPE_SERVICE_PRICE);
		searchVo1.setLinkIds(linkIds);
		List<TotalHit> totalHits = totalHitService.selectBySearchVo(searchVo1);

		List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < top10.size(); i++) {
			PartnerServicePrice item = top10.get(i);

			HashMap<String, Object> vo = new HashMap<String, Object>();
			vo.put("channel_id", item.getServiceTypeId());
			vo.put("article_id", item.getServicePriceId());
			vo.put("title", item.getName());
			vo.put("img_url", item.getImgUrl());
			vo.put("add_time", TimeStampUtil.timeStampToDateStr(item.getAddTime() * 1000, "MM-dd HH:mm"));
			vo.put("total_view", 0);

			for (TotalHit t : totalHits) {
				if (t.getLinkId().equals(item.getServicePriceId())) {
					vo.put("total_view", t.getTotal());
					break;
				}
			}

			datas.add(vo);
		}

		result.setData(datas);

		return result;
	}

}