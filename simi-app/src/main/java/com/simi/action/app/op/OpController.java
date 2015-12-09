package com.simi.action.app.op;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.op.OpChannel;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.service.dict.CityService;
import com.simi.service.op.OpAdService;
import com.simi.service.op.OpChannelService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/op")
public class OpController extends BaseController {

	@Autowired
	private OpChannelService opChannelService;	

	@Autowired
	private OpAdService opAdService;


	@RequestMapping(value = "get_channels", method = RequestMethod.GET)
	public AppResultData<Object> getChannels() {
		List<OpChannel> opChannels = opChannelService.selectByAll();

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
		
		result.setData(list);
		
		return result;
	}	
}
