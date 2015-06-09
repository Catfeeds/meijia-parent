package com.simi.action.d;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simi.action.BaseController;
import com.simi.po.model.promotion.Channel;
import com.simi.po.model.promotion.ChannelLog;
import com.simi.service.promotion.ChannelLogService;
import com.simi.service.promotion.ChannelService;
import com.meijia.utils.DateUtil;

@Controller
@RequestMapping(value = "/d")
public class DController extends BaseController {

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ChannelLogService channelLogService;
	/**
	 * 访问来源计数器
	 * @param request
	 * @param token  推广活动唯一标示
	 * @return
	 */

	@RequestMapping(value = "/{token}", method = { RequestMethod.GET })
	public String totalDownload(HttpServletRequest request, Model model,
			@PathVariable(value = "token") String token) {
		
		//更新总的下载量
		Channel record = channelService.selectByToken(token.trim());
		if (record != null) {
			channelService.updateByTotalDownload(token.trim());
			
			//新增下载量明细
			ChannelLog log = channelLogService.initChannelLog();
			log.setChannelId(record.getId());
			log.setChannelType(record.getChannelType());
			log.setName(record.getName());
			log.setToken(record.getToken());
			log.setTotalDownloads(1);
			log.setDownloadDay(DateUtil.getNowOfDate());
			log.setUpdateTime(0L);
			channelLogService.insert(log);
		}
		

		return "d/index";
	}

}
