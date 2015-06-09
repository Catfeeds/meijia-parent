package com.simi.action.app.user;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;

import com.simi.action.app.BaseController;
import com.simi.po.model.user.UserRemindSend;
import com.simi.po.model.user.UserReminds;
import com.simi.service.user.UserRemindSendService;
import com.simi.service.user.UserRemindService;

/**
 * 当web容器启动时
 * 加载所有在数据库中的用户提醒并开始执行
 * @author kerry
 *
 */
@Controller
public class UserRemindsQuartzController extends BaseController {

	@Autowired
	private UserRemindService userRemindService;
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private UserRemindSendService userRemindSendService;
	@SuppressWarnings("unchecked")
	public void initTask(){
		try {
			//获得调度器
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			//获得所有的用户提醒
			List<UserReminds> userReminds = userRemindService.queryByCycleAndExcute();
			for (UserReminds uReminds : userReminds) {
				TriggerKey triggerKey = null;
				//UserRemind的userId=0表示系统提醒，userId表示用户提醒
				if(uReminds.getUserId()==0 ){
						if(uReminds.getCycleType()==0){
							triggerKey = TriggerKey.triggerKey("task-sys-once-["+ uReminds.getId()+"]", "sys");
						}else{
							triggerKey = TriggerKey.triggerKey("task-sys-cycle-["+ uReminds.getId()+"]", "sys");
						}
				}else {
					if(uReminds.getCycleType()==0){
						 triggerKey = TriggerKey.triggerKey("task-user-once-["+uReminds.getId()+"]","user");
					}else{
						 triggerKey = TriggerKey.triggerKey("task-user-cycle-["+uReminds.getId()+"]","user");
					}
				}
				List<UserRemindSend> list = userRemindSendService.queryByRemindId(uReminds.getId());
				//UserRemindSend userRemindSend = userRemindSendService.queryByRemindId(uReminds.getId()).get(0);
		//System.out.println(list.size());
				if(list!=null && list.size()!=0){
				//当userRemindSend不为空时，才会执行获得userRemindSend
				UserRemindSend userRemindSend = list.get(0);
				String nameStr = triggerKey.getName();
				String groupStr = triggerKey.getGroup();
				CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
					JobDetail jobDetail = JobBuilder
							.newJob((Class<? extends Job>) Class.forName(userRemindSend.getJobName()))
							.withIdentity(nameStr,groupStr)
							.build();
					jobDetail.getJobDataMap().put("scheduleJob", uReminds);
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
							.cronSchedule(uReminds.getCrond());
					trigger = TriggerBuilder
							.newTrigger()
							.withIdentity(nameStr,groupStr)
							.startAt(uReminds.getStartDate())
							.withSchedule(scheduleBuilder).build();
					scheduler.scheduleJob(jobDetail, trigger);
				JobKey jobKey = JobKey.jobKey(nameStr,groupStr);
				scheduler.startDelayed(60);
				scheduler.triggerJob(jobKey);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
