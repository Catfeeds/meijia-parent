package com.simi.action.job;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.service.job.JobImService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/job")
public class SyncImMsgController extends BaseController {
	
	@Autowired
	private JobImService jobImService;
	
	/**
	 * 同步所有的IM信息.
	 * @return
	 */
	@RequestMapping(value = "sync_im_all", method = RequestMethod.GET)
	public AppResultData<Object> syncImAll( HttpServletRequest request ) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, new String());
		
//		System.out.println("Remote Host: " + request.getRemoteHost());
		String reqHost = request.getRemoteHost();
		//限定只有localhost能访问
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			Long beginTime = 1420041600000L;
			jobImService.syncIm(beginTime, "all");
		}
		
		
		
		return result;
	}
	
	/**
	 * 同步昨天所有的IM信息.
	 * @return
	 */
	@RequestMapping(value = "sync_im_yesterday", method = RequestMethod.GET)
	public AppResultData<Object> syncImYesterday( HttpServletRequest request ) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, new String());
		
		System.out.println("Remote Host: " + request.getRemoteHost());
		String reqHost = request.getRemoteHost();
		//限定只有localhost能访问
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			Long beginTime = TimeStampUtil.getBeginOfYesterDay();
			jobImService.syncIm(beginTime * 1000, "yesterday");
		}
		

		
		return result;
	}	
	
	/**
	 * 同步最近5分钟所有的IM信息.
	 * @return
	 */
	@RequestMapping(value = "sync_im_five", method = RequestMethod.GET)
	public AppResultData<Object> syncImFive( HttpServletRequest request ) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, new String());
		
		System.out.println("Remote Host: " + request.getRemoteHost());
		String reqHost = request.getRemoteHost();
		//限定只有localhost能访问
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			Long beginTime = TimeStampUtil.getNowSecond() - 600;
			jobImService.syncIm(beginTime * 1000, "five");
		}

		return result;
	}		
	
}
