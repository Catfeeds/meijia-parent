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
import com.simi.po.model.user.UserActionRecord;
import com.simi.service.op.AppHelpService;
import com.simi.service.op.OpAdService;
import com.simi.service.op.OpChannelService;
import com.simi.service.user.UserActionRecordService;
import com.meijia.utils.StringUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.po.AdSearchVo;
import com.simi.vo.user.UserActionSearchVo;

@Controller
@RequestMapping(value = "/app/op")
public class OpController extends BaseController {

	@Autowired
	private OpChannelService opChannelService;	

	@Autowired
	private OpAdService opAdService;
	
	@Autowired
	private AppHelpService appHelpService;

	@Autowired
	private UserActionRecordService userActionRecordService;

	@RequestMapping(value = "get_channels", method = RequestMethod.GET)
	public AppResultData<Object> getChannels(
			@RequestParam(value = "app_type", required = false, defaultValue="xcloud") String appType,
			@RequestParam(value = "channel_positon", required = false, defaultValue="discovery") String channelPositon) {
		//List<OpChannel> opChannels = opChannelService.selectByAppType(appType);
		List<OpChannel> opChannels = opChannelService.selectByAppTypeAndPosition(appType,channelPositon);
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, opChannels);
		return result;
	}
	
	@RequestMapping(value = "get_ads", method = RequestMethod.GET)
	public AppResultData<Object> getAds(
			@RequestParam(value = "channel_id", required = false, defaultValue="1") String channelId,
			@RequestParam(value = "t", required = false, defaultValue="0") Long t
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		if (StringUtil.isEmpty(channelId)) return result;
		
		channelId+= ",";
		
		
		AdSearchVo searchVo = new AdSearchVo();
		if (t > 0L) {
			searchVo.setUpdateTime(t);
		}
		
		searchVo.setAdType(channelId);
		List<OpAd> list = opAdService.selectBySearchVo(searchVo);
		
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
			@RequestParam("action") String action,
			@RequestParam("user_id") Long userId) {
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		//判断用户是否已经操作过，如果操作过则直接返回空值.
		UserActionSearchVo searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("app_help");
		searchVo.setParams(action);
		
		List<UserActionRecord> rs = userActionRecordService.selectBySearchVo(searchVo);
		if (!rs.isEmpty()) {
			return result;
		}
		
		
		AppHelp appHelp = appHelpService.selectByAction(action);
		
		if (appHelp == null) {
			return result;
		}
		
		result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, appHelp);
		
		return result;
	}
	
	
	//帮助接口
		@RequestMapping(value = "post_help", method = RequestMethod.POST)
		public AppResultData<Object> postHelp(
				@RequestParam("action") String action,
				@RequestParam("user_id") Long userId) {
			
			AppResultData<Object> result = new AppResultData<Object>(
					Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
			
			AppHelp appHelp = appHelpService.selectByAction(action);
			
			if (appHelp == null) {
				return result;
			}
			
			//判断用户是否已经操作过，如果操作过则直接返回空值.
			UserActionSearchVo searchVo = new UserActionSearchVo();
			searchVo.setUserId(userId);
			searchVo.setActionType("app_help");
			searchVo.setParams(action);
			
			List<UserActionRecord> rs = userActionRecordService.selectBySearchVo(searchVo);
			
			if (!rs.isEmpty()) {
				return result;
			}
			
			UserActionRecord record = userActionRecordService.initUserActionRecord();
			record.setUserId(userId);
			record.setActionType("app_help");
			record.setParams(action);
			
			userActionRecordService.insert(record);
			
			return result;
		}
	
	
}
