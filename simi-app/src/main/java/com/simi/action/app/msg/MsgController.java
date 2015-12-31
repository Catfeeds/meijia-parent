package com.simi.action.app.msg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.vo.AppResultData;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.msg.Msg;
import com.simi.po.model.msg.MsgReaded;
import com.simi.service.msg.MsgReadedService;
import com.simi.service.msg.MsgService;
import com.simi.vo.MsgSearchVo;
import com.simi.vo.msg.MsgVo;

@Controller
@RequestMapping(value = "/app/msg")
public class MsgController extends BaseController {

	@Autowired
	private MsgService msgService;

	@Autowired
	private MsgReadedService msgReadedService;
	
	/**
	 * 获取消息列表
	 * @param userId
	 * @param userType
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "get_list", method = RequestMethod.GET)
	public AppResultData<Object> MsgList(
			@RequestParam("user_id") Long userId,
			@RequestParam("user_type") short userType,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		
		MsgSearchVo searchVo = new MsgSearchVo();
		List<Msg> list = msgService.selectMsgListBySearchVo(searchVo,
				page, Constants.PAGE_MAX_NUMBER);

		if (list == null) {
			return result;
		}

		List<MsgVo> msgVo = new ArrayList<MsgVo>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			
			Msg msg = (Msg) iterator.next();
			MsgVo vo = msgService.getMsgList(msg);
			msgVo.add(vo);
		}
		result.setData(msgVo);
		return result;
	}
	/**
	 * 消息置为已读接口
	 * @param userId
	 * @param userType
	 * @param msgId
	 * @return
	 */
	@RequestMapping(value = "post_read", method = RequestMethod.POST)
	public AppResultData<Object> MsgPostRead(
			@RequestParam("user_id") Long userId,
			@RequestParam("user_type") short userType,
			@RequestParam("msg_id") Long msgId) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		//msg_readed 插入记录，注意保持 user_id 和 msg_id 唯一性
		MsgReaded msgReaded = msgReadedService.selectByUserIdAndMsgId(userId,msgId);
		
		if (msgReaded != null) {
			
			return result;
		}
		msgReaded = new MsgReaded();
		msgReaded.setUserId(userId);
		msgReaded.setUserType(userType);
		msgReaded.setMsgId(msgId);
		msgReaded.setAddTime(TimeStampUtil.getNow()/1000);
		
		msgReadedService.insert(msgReaded);
		
		return result;
	}
}
