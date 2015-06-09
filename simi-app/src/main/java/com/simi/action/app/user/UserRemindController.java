package com.simi.action.app.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserRemindSend;
import com.simi.po.model.user.UserReminds;
import com.simi.po.model.user.Users;
import com.simi.service.user.JobService;
import com.simi.service.user.UserRemindSendService;
import com.simi.service.user.UserRemindService;
import com.simi.service.user.UsersService;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserRemindsTypeVo;

/**
 * @author kerry
 *
 */
@Controller
@RequestMapping("/app/user")
public class UserRemindController extends BaseController {

	@Autowired
	private UserRemindService userRemindService;
	@Autowired
	private UsersService userService;
	@Autowired
	private UserRemindSendService userRemindSendService;
	@Autowired
	private  JobService jobService;



	/**
	 * 9.保存用户提醒记录接口
	 * remind_id 0 表示新增 > 0表示修改
	 * mobile 手机号
	 * remind_title 提醒标题
	 * start_date 提醒日期
	 * start_time 提醒时间
	 * cycle_type 提醒周期 0 = 一次性活动 1 = 每个工作日 2 = 每周 3 = 每月 4 = 每年
	 * remind_to_name 提醒对象名称
	 * remind_to_mobile提醒对象手机号
	 * remarks 备注 alert_type 1,0,0 第一位： app提醒 第二位： 短信提醒 第三位: 电话提醒
	 *
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "post_user_remind", method = RequestMethod.POST)
	public AppResultData<UserRemindsTypeVo> saveUserReminds  (
			@RequestParam("mobile") String mobile,
			@RequestParam("remind_id") Long remind_id,
			@RequestParam("remind_title") String remind_title,
			@RequestParam("start_date") String start_date,
			@RequestParam("start_time") String start_time,
			@RequestParam("cycle_type") int cycle_type,
			@RequestParam("remind_to_name") String remind_to_name,
			@RequestParam("remind_to_mobile") String remind_to_mobile,
			@RequestParam("remarks") String remarks,
			@RequestParam("remind_type") String remind_type)
			throws UnsupportedEncodingException {


		String remarkDecode = URLDecoder.decode(remarks, "utf-8");
		String remindTypeDecode = URLDecoder.decode(remind_type, "utf-8");

		//解析提醒类型
		String str[] = remindTypeDecode.split(",");
		String fromApp = str[0];
		String fromEmail = str[1];
		String fromMobile = str[2];



		String crond = userRemindService.getCrond(start_date, start_time, cycle_type);

		Users user = userService.getUserByMobile(mobile);

		UserRemindSend userRemindSend = new UserRemindSend();
		UserReminds userReminds = null;

		// remind_id==0表示新增的用户提醒记录；大于0表示修改该条用户提醒记录
		if (remind_id == 0) {
			userReminds = new UserReminds();
			userReminds.setAddTime(TimeStampUtil.getNow()/1000);
			userReminds.setUpdateTime(TimeStampUtil.getNow()/1000);
		} else {
			userReminds = userRemindService.selectByPrimaryKey(remind_id);
			userReminds.setUpdateTime(TimeStampUtil.getNow()/1000);
		}
		// 用户提醒记录赋值
		userReminds.setUserId(user.getId());
		userReminds.setMobile(mobile);
		userReminds.setRemindTitle(remind_title);
		userReminds.setStartDate(DateUtil.parse(start_date, "yyyy-MM-dd"));
		userReminds.setStartTime(start_time);
		userReminds.setCrond(crond);
		userReminds.setCycleType((short)cycle_type);
		userReminds.setRemindToName(remind_to_name);
		userReminds.setRemindToMobile(remind_to_mobile);
		userReminds.setRemarks(remarkDecode);
		userReminds.setIsExecuted(Short.valueOf("0"));
		Long userRemindTemp = 0L;
		if (remind_id == 0) {
			userRemindTemp=userRemindService.insertSelective(userReminds);
			userRemindSend.setRemindId(userReminds.getId());
		} else {
			userRemindTemp=(long) userRemindService.updateByPrimaryKeySelective(userReminds);
			userRemindSend.setRemindId(remind_id);
		}
		// 保存提醒发送方式表
		userRemindSend.setUserId(user.getId());
		userRemindSend.setMobile(mobile);

		//判断提醒发起来源（0=app,1=短信，2=电话）
		if (fromApp.equals("1")&& fromEmail.equals("0")
				&& fromMobile.equals("0")) {
			userRemindSend.setRemindType(Short.valueOf("0"));
		} else if (fromApp.equals("0") && fromEmail.equals("1")
				&& fromMobile.equals("0")) {
			userRemindSend.setRemindType(Short.valueOf("1"));
		} else{
			userRemindSend.setRemindType(Short.valueOf("2"));
		}
		userRemindSend.setAddTime(TimeStampUtil.getNow()/1000);
		userRemindSend.setJobName("com.zrj.action.job.SendMessageJob");
		int userRemindSendTemp = userRemindSendService.insertSelective(userRemindSend);

		//保存提醒成功，返回提醒的VO类
		UserRemindsTypeVo  userRemindsTypeVo = new UserRemindsTypeVo(
				mobile, userReminds.getId(),userReminds.getRemindTitle(),
				DateUtil.format(userReminds.getStartDate(),"yyyy-MM-dd"),
				userReminds.getStartTime(),userReminds.getCycleType(),
				userReminds.getCrond(),	userReminds.getRemindToName(),
				userReminds.getRemindToMobile(),
				userReminds.getRemarks(),userReminds.getRemarks());


		if(userRemindTemp>0 && userRemindSendTemp>0 ){

			jobService.addJob(userReminds);
			AppResultData<UserRemindsTypeVo> result = new AppResultData<UserRemindsTypeVo>(
					Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, userRemindsTypeVo);
			return result;
		}else {
			AppResultData<UserRemindsTypeVo> result = new AppResultData<UserRemindsTypeVo>(
					Constants.ERROR_100, ConstantMsg.ERROR_100_MSG, null);
			return result;
		}

	}

	/**
	 * 10、根据手机号查询提醒记录
	 *
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "get_user_remind", method = RequestMethod.GET)
	public AppResultData<List<UserRemindsTypeVo>> queryUserRemindsByMobile(
			@RequestParam("mobile") String mobile) {
		List<UserReminds> urList = userRemindService.selectByMobile(mobile);
		List<UserRemindSend> ursList = userRemindSendService.selectByMobile(mobile);
		List<UserRemindsTypeVo> list = new ArrayList<UserRemindsTypeVo>();

		for (int i = 0; i < urList.size(); i++) {
			UserReminds userReminds = urList.get(i);
			UserRemindsTypeVo  userRemindsTypeVo = new UserRemindsTypeVo(
					mobile, userReminds.getId(),userReminds.getRemindTitle(),
					DateUtil.format(userReminds.getStartDate(),"yyyy-MM-dd"),
					userReminds.getStartTime(),userReminds.getCycleType(),
					userReminds.getCrond(),	userReminds.getRemindToName(),
					userReminds.getRemindToMobile(),
					userReminds.getRemarks(),userReminds.getRemarks());
			for (int j = 0; j < ursList.size(); j++) {
				UserRemindSend userRemindSend = ursList.get(j);
				//根据提醒发送方式赋值提醒列表发送方式
				if (userReminds.getId() == userRemindSend.getRemindId()) {
					if (userRemindSend.getRemindType() == 0) {
						userRemindsTypeVo.setRemindType("1,0,0");// 如何获得
					} else if (userRemindSend.getRemindType() == 1) {
						userRemindsTypeVo.setRemindType("0,1,0");
					} else {
						userRemindsTypeVo.setRemindType("0,0,1");
					}
				}
			}
			list.add(userRemindsTypeVo);
		}
		if(list==null || list.size()==0){
			AppResultData<List<UserRemindsTypeVo>> result = new AppResultData<List<UserRemindsTypeVo>>(
					Constants.ERROR_100, ConstantMsg.ERROR_100_MSG, null);
			return result;
		}else {
			AppResultData<List<UserRemindsTypeVo>> result = new AppResultData<List<UserRemindsTypeVo>>(
					Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, list);
			return result;
		}

	}

	/**
	 *11、 根据remindId删除用户提醒记录
	 *
	 * @param remind_id
	 * @return
	 */
	@RequestMapping(value = "post_user_remind_del", method = RequestMethod.POST)
	public AppResultData<String> deleteByRemindId(
			@RequestParam("mobile") String mobile,
			@RequestParam("remind_id") Long remind_id) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("remind_id", remind_id);
		param.put("mobile", mobile);

		UserReminds userReminds = userRemindService.selectByPrimaryKey(remind_id);
		int flag1 = userRemindSendService.deleteByMap(param);
		int flag2 = userRemindService.deleteByPrimaryKey(remind_id);
		//Scheduler scheduler = schedulerFactoryBean.getScheduler();
		//提醒方式表和提醒列表都删除成功则删除提醒任务，否则返回错误
		if (flag1 <= 0 || flag2 <= 0) {
			AppResultData<String> result = new AppResultData<String>(
					Constants.ERROR_100, ConstantMsg.ERROR_100_MSG, "");
			return result;
		} else {
			jobService.deleteJob(userReminds);
			AppResultData<String> result = new AppResultData<String>(
					Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
			return result;
		}
	}
}
