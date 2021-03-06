package com.simi.action.job;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.msg.Msg;
import com.simi.po.model.user.GroupUser;
import com.simi.po.model.user.Users;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.msg.MsgService;
import com.simi.service.user.GroupUserService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.MsgSearchVo;
import com.simi.vo.user.UserSearchVo;

/**
 *
 * @author :hulj
 * @Date : 2016年5月21日上午10:37:39
 * @Description: 消息推送
 */
@Controller
@RequestMapping(value = "/app/job/msg")
public class JobMsgController extends BaseController {

	@Autowired
	private MsgService msgService;

	@Autowired
	private NoticeAppAsyncService noticeAsyncService;

	@Autowired
	private AdminAccountService accountService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private UserLoginedService userLoginedService;

	@Autowired
	private GroupUserService groupUserService;

	/*
	 * 定时 推送消息
	 */
	@RequestMapping(value = "push_msg_at_reqular_time", method = RequestMethod.GET)
	public AppResultData<Object> genUserProvince(HttpServletRequest request) throws ParseException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		/*
		 * 定时发送的消息。。
		 * 
		 * 1. is_send = 0 2. 消息 可用 isEnable = 1
		 */

		MsgSearchVo msgSearchVo = new MsgSearchVo();

		// 标志位
		msgSearchVo.setSendAtOnce((short) 1);

		msgSearchVo.setIsSend((short) 0);

		List<Msg> list = msgService.selectMsgBySearchVo(msgSearchVo);

		// 发送时间 精确到 分钟的 时间戳 == 当前分钟时间的 时间戳（ 但都是 11位的 秒值时间戳）
		Long minute = TimeStampUtil.getNowMin();

		for (Msg msg : list) {

			Long sendTime = msg.getSendTime();

			String title = msg.getTitle();

			String content = msg.getContent();

			Long userType = msg.getUserType();

			// 定时时间 精确到分钟
			if (sendTime == minute) {

				// 如果选择的 测试用户
				if (userType == 4) {

					// 如果是 测试 发送, 发消息 给 运营部人员

					List<GroupUser> groupAdmins = groupUserService.selectByGropuId(3L);

					for (GroupUser gu : groupAdmins) {
						// 异步推送 给 测试 人员（运营部），消息
						noticeAsyncService.pushMsgToDevice(gu.getUserId(), title, content, msg.getCategory(), msg.getAction(), msg.getParams(),
								msg.getGotoUrl());
					}

				} else {

					List<Users> userList = new ArrayList<Users>();

					// 最近一个月 登录过的用户。
					List<Long> lastMonthUser = userLoginedService.selectUserIdsLastMonth();

					UserSearchVo searchVo = new UserSearchVo();

					if (userType == 0) {
						// 选择 普通用户（一个月的）
						searchVo.setUserType(Constants.OA_PUSH_USER_TYPE_0);
						searchVo.setUserIds(lastMonthUser);

						userList = usersService.selectBySearchVo(searchVo);

					} else {
						Long groupId = msg.getUserType();
						List<GroupUser> groupUsers = groupUserService.selectByGropuId(groupId);
						List<Long> userIds = new ArrayList<Long>();
						for (GroupUser gu : groupUsers) {
							if (!userIds.contains(gu.getUserId()))
								userIds.add(gu.getUserId());
						}
						searchVo = new UserSearchVo();
						searchVo.setUserIds(userIds);
						userList = usersService.selectBySearchVo(searchVo);
					}

					// 异步推送
					for (Users users : userList) {
						noticeAsyncService
								.pushMsgToDevice(users.getId(), title, content, msg.getCategory(), msg.getAction(), msg.getParams(), msg.getGotoUrl());
					}

				}

				if (msg.getIsSend() == 0) {
					// 设置为已发送
					msg.setIsSend((short) 1);
					msgService.updateByPrimaryKey(msg);
				}
			}
		}

		return result;
	}

	/*
	 * 定时 推送消息
	 */
	@RequestMapping(value = "push_msg_day", method = RequestMethod.GET)
	public AppResultData<Object> pushMsgDay(HttpServletRequest request) throws ParseException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		List<GroupUser> groupAdmins = groupUserService.selectByGropuId(4L);

		for (GroupUser gu : groupAdmins) {
			// 异步推送 给 测试 人员（运营部），消息
			noticeAsyncService.pushMsgToDevice(gu.getUserId(), "每日推送", "每日热点推送", "h5", "", "", "http://bolohr.com");
		}

		return result;
	}
}
