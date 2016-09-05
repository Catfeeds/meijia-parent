package com.simi.action.app.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.partners.PartnerServicePrice;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.total.TotalHit;
import com.simi.service.partners.PartnerServicePriceService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.total.TotalHitService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerServicePriceSearchVo;
import com.simi.vo.total.TotalHitSearchVo;

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
			vo.put("channelId", item.getId());
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
	public AppResultData<Object> list(
			@RequestParam(value = "channel_id", required = false, defaultValue = "0") Long channelId,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
			) {

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
		
		List<PartnerServicePrice> list = partnerServicePriceService.selectBySearchVo(searchVo);
		
		//阅读数
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
		
		for (int i = 0 ; i < list.size(); i++) {
			PartnerServicePrice item = list.get(i);
			
			HashMap<String, Object> vo = new HashMap<String, Object>();
			vo.put("channelId", item.getServiceTypeId());
			vo.put("articleId", item.getServicePriceId());
			vo.put("title", item.getName());
			vo.put("imgUrl", item.getImgUrl());		
			vo.put("addTime", TimeStampUtil.timeStampToDateFull(item.getAddTime() * 1000, "MM-dd HH:mm"));
			vo.put("totalView", 0);
			
			for (TotalHit t : totalHits) {
				if (t.getLinkId().equals(item.getServicePriceId())) {
					vo.put("totalView", t.getTotal());
					break;
				}
			}

		}
		
		result.setData(datas);
		return result;
	}
	
	
	/**
	 * 视频文章详细内容接口
	 * 
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public AppResultData<Object> getDetail(
			@RequestParam(value = "article_id") Long servicePriceId,
			@RequestParam(value = "user_id", required = false, defaultValue = "0") Long userId
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (servicePriceId.equals(0L)) return result;
		
		PartnerServicePrice servicePrice = partnerServicePriceService.selectByPrimaryKey(servicePriceId);
		
		if (servicePrice == null) return result;
		

		
		//阅读数加1
		int total = 0;
		TotalHit record = totalHitService.initTotalHit();
		TotalHitSearchVo searchVo1 = new TotalHitSearchVo();
		searchVo1.setLinkType(Constants.TOTAL_HIT_LINK_TYPE_SERVICE_PRICE);
		searchVo1.setLinkId(serviceTypeId);
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
		

		HashMap<String, Object> vo = new HashMap<String, Object>();
		vo.put("channelId", servicePrice.getServiceTypeId());
		vo.put("articleId", servicePrice.getServicePriceId());
		vo.put("title", servicePrice.getName());
		vo.put("imgUrl", servicePrice.getImgUrl());
		vo.put("totalView", total);
		vo.put("addTime", TimeStampUtil.timeStampToDateFull(servicePrice.getAddTime() * 1000, "MM-dd HH:mm"));
		
		vo.put("price", MathBigDecimalUtil.round2(servicePrice.getPrice()));
		vo.put("disPrice", MathBigDecimalUtil.round2(servicePrice.getDisPrice()));
		vo.put("content", servicePrice.getContentDesc());
		vo.put("keywords", servicePrice.getContentFlow());
		vo.put("videoUrl", servicePrice.getVideoUrl());
		vo.put("category", servicePrice.getCategory());
		vo.put("contentDesc", servicePrice.getContentDesc());
		vo.put("gotoUrl", servicePrice.getGotoUrl());
		
		
		
		result.setData(vo);		
		
		
		return result;
	}
	

}