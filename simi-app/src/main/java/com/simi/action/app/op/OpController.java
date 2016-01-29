package com.simi.action.app.op;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.op.AppHelp;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.op.OpChannel;
import com.simi.service.impl.op.AppHelpServiceImpl;
import com.simi.service.op.AppHelpService;
import com.simi.service.op.OpAdService;
import com.simi.service.op.OpChannelService;
import com.meijia.utils.StringUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/op")
public class OpController extends BaseController {

	@Autowired
	private OpChannelService opChannelService;	

	@Autowired
	private OpAdService opAdService;
	
	@Autowired
	private AppHelpService appHelpService;


	@RequestMapping(value = "get_channels", method = RequestMethod.GET)
	public AppResultData<Object> getChannels(
			@RequestParam(value = "app_type", required = false, defaultValue="xcloud") String appType) {
		List<OpChannel> opChannels = opChannelService.selectByAppType(appType);

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, opChannels);
		return result;
	}
	
	@RequestMapping(value = "get_ads", method = RequestMethod.GET)
	public AppResultData<Object> getAds(
			@RequestParam(value = "channel_id", required = false, defaultValue="1") String channelId
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		if (StringUtil.isEmpty(channelId)) return result;
		
		channelId+= ",";
		List<OpAd> list = opAdService.selectByAdType(channelId);
		
		if (list.isEmpty()) return result;
		
//		for (int i =0 ; i < list.size(); i++) {
//			OpAd item = list.get(i);
//			String imgUrl = item.getImgUrl();
//			imgUrl =  imgUrl + "?w=300&h=125";
//			item.setImgUrl(imgUrl);
//			list.set(i, item);
//		}
		
		
		result.setData(list);
		
		return result;
	}
	//帮助接口
	@RequestMapping(value = "get_appHelp", method = RequestMethod.GET)
	public AppResultData<Object> getAppHelp(
			@RequestParam("action") String action) {
		
		AppHelp appHelp = appHelpService.selectByAction(action);

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, appHelp);
		return result;
	}
}
