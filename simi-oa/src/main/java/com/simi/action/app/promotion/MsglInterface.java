package com.simi.action.app.promotion;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;


import com.simi.po.model.promotion.Msg;
import com.simi.service.promotion.MsgService;
import com.meijia.utils.JspToHtmlFile;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/interface-promotion")
public class MsglInterface extends BaseController {


    @Autowired
    private MsgService msgService;
	/**
	 * 新增消息时预览
	 * @param request
	 * @param model
	 * @param
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "preview-msg", method = RequestMethod.POST)
	public  AppResultData<String> checkToken(
			HttpServletRequest request,
			@RequestParam(value = "title", required = false, defaultValue = "") String title,
    		@RequestParam(value = "content", required = false, defaultValue = "") String content,
    		@RequestParam(value = "summary", required = false, defaultValue = "") String summary
			) {

		AppResultData<String> result = new AppResultData<String>(
		Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		  String sourcePath =  request.getSession().getServletContext().getRealPath("/WEB-INF/upload/html");
		  	String msgUrl = RandomUtil.randomNumber(6);
	        String destPath = sourcePath+File.separator+"temp"+File.separator+msgUrl+".html";
	        String templatePath =sourcePath+File.separator+"news_detail.html";
	        Map<String,Object> map = new HashMap<String, Object>();
	        map.put("title",title);
	        map.put("content",content);
	        String addTime = TimeStampUtil.timeStampToDateStr(TimeStampUtil.getNow(),"yyyy-MM-dd");
	        map.put("addTime",addTime);
	        JspToHtmlFile.JspToHtmlFile(templatePath, destPath, map);
	        String tempUrl = "upload/html/temp/"+msgUrl+".html";
	        result.setData(tempUrl);
	      return result;
	}
	/**
	 * 重新发送消息
	 * @param request
	 * @param model
	 * @param
	 * @return
	 */
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "send_msg_again", method = RequestMethod.POST)
	public  AppResultData<String> sendMsgAgain(HttpServletRequest request,
			@RequestParam(value = "msgId", required = false, defaultValue = "") Long msgId
			) {
		
		AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		Msg msg = msgService.selectByPrimaryKey(Long.valueOf(msgId));
		String message = "";
		if(msg.getSendStatus()==1){
			message = "再次发送成功";
		}else{
			message = "发送成功";
		}
		
		if(((TimeStampUtil.getNow()/1000)-msg.getLastSendTime())/60>15){
			try {
				int count = msgService.pushMsgFromBaidu(Long.valueOf(msgId));
				msg.setSendTotal(count);
				msg.setSendStatus((short)1);
				msg.setLastSendTime(TimeStampUtil.getNow()/1000);
				msgService.updateByPrimaryKeySelective(msg);
				result.setData(message);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			result.setData("15分钟之后才能够再次发送！");
		}
		return result;
	}
}
