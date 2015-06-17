package com.simi.action.app.user;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.promotion.Msg;
import com.simi.po.model.user.UserMsg;
import com.simi.service.promotion.MsgService;
import com.simi.service.user.UserMsgService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserMsgVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserMsgController extends BaseController {

	@Autowired
	private UserMsgService userMsgService;

	@Autowired
	private MsgService msgService;

	@RequestMapping(value = "post_msg_read", method = RequestMethod.POST)
	public AppResultData<String> SaveMsg(

	@RequestParam("user_id") Long userId, @RequestParam("msg_id") Long msgId,
			HttpServletRequest request) {

		UserMsg userMsg = userMsgService.getUserByUserId(userId, msgId);

		AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (userMsg == null) {
			return result;
		}
		userMsg.setIsReaded((short) 1);
		userMsgService.updateByPrimaryKeySelective(userMsg);

		return result;

	}

	@RequestMapping(value = "get_msg", method = RequestMethod.GET)
	public AppResultData<Object> getMsg(

	@RequestParam("user_id") Long  userId, @RequestParam("page") int page,
			HttpServletRequest request, Model model) throws IllegalAccessException, InvocationTargetException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new ArrayList());

		// 分页
		int pageNo = page;
		int pageSize = 10;
		PageInfo results = userMsgService.searchVoListPage(pageNo, pageSize,userId);

		List<UserMsgVo> resultData = new ArrayList<UserMsgVo>();
		List<UserMsg> resultList = results.getList();
		//初始化
		UserMsg userMsg = null;
		Msg msg = null;
		//循环UserMsg
		for (int i = 0; i < resultList.size(); i++) {
			//获得UserMsg中的数据
			userMsg = resultList.get(i);

            //获得UserMsg的msgId值

			Long msgId = userMsg.getMsgId();


			//根据UserMsg的msgId值到msg表中查询数据
			msg = msgService.selectByPrimaryKey(msgId);


			//new新的Vo
			UserMsgVo vo = new UserMsgVo();

			//把userMsg里面的数据复制到新的vo里面
			BeanUtils.copyProperties(vo, userMsg);



			//将新的值设置到新的vo中
			vo.setTitle(msg.getTitle());
			vo.setSummary(msg.getSummary());
			vo.setHtmlUrl(msg.getHtmlUrl());

			//把vo里面的值放到resultData集合中
			resultData.add(vo);
		}

		//返回resultData里面的参数
		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, resultData);
		return result;
	}
	/**
	 * 用户未读消息接口
	 * 根据用户Id查询用户消息未读消息的个数
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "get_new_msg", method = RequestMethod.POST)
	public AppResultData<Long> getNewMsg(
				@RequestParam("user_id") Long userId) {

		Long counts = userMsgService.countNewMsgByUserId(userId);

		AppResultData<Long> result = new AppResultData<Long>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG,counts);
		return result;

	}
}
