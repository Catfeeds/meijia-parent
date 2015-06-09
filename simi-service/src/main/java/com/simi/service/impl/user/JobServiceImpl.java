package com.simi.service.impl.user;

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
import org.springframework.stereotype.Service;

import com.simi.service.user.JobService;
import com.simi.service.user.UserRemindSendService;
import com.simi.po.model.user.UserRemindSend;
import com.simi.po.model.user.UserReminds;

/**添加和删除提醒任务的service实现
 * @author kerry
 *
 */
@Service
public class JobServiceImpl implements JobService {


	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private UserRemindSendService userRemindSendService;
	@SuppressWarnings("unchecked")
	@Override
	public void addJob(UserReminds userReminds) {
			String nameStr = null;
			String groupStr = null;
			if(userReminds.getUserId()==0 ){
				if(userReminds.getCycleType()==0){
					 nameStr = "task-sys-once-["+ userReminds.getId()+"]";
				}else{
					 nameStr = "task-sys-cycle-["+ userReminds.getId()+"]";
				}
				 groupStr = "sys";
			}else {
				if(userReminds.getCycleType()==0){
				 nameStr = "task-user-once-["+ userReminds.getId()+"]";
				}else{
				 nameStr = "task-user-cycle-["+ userReminds.getId()+"]";
				}
				 groupStr = "user";
			}
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			UserRemindSend userRemindSend = userRemindSendService.queryByRemindId(userReminds.getId()).get(0);
			try{
				TriggerKey triggerKey = TriggerKey.triggerKey(nameStr, groupStr);
				CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
				// 不存在，创建一个
				if (null == trigger) {
					JobDetail jobDetail = JobBuilder
							.newJob((Class<? extends Job>) Class.forName(userRemindSend.getJobName()))
							.withIdentity(nameStr,groupStr).build();

					jobDetail.getJobDataMap().put("scheduleJob", userReminds);
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(userReminds.getCrond());
					trigger = TriggerBuilder
							.newTrigger()
							.withIdentity(nameStr,groupStr)
							.startAt(userReminds.getStartDate())
							.withSchedule(scheduleBuilder).build();
					scheduler.scheduleJob(jobDetail, trigger);
				} else {
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
							.cronSchedule(userReminds.getCrond());
					trigger = trigger.getTriggerBuilder()
							.withIdentity(triggerKey)
							.startAt(userReminds.getStartDate())
							.withSchedule(scheduleBuilder)
							.build();
					scheduler.rescheduleJob(triggerKey, trigger);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
}

	@Override
	public void deleteJob(UserReminds userReminds) {
			String nameStr = null;
			String groupStr = null;
			if(userReminds.getUserId()==0 ){
				if(userReminds.getCycleType()==0){
					 nameStr = "task-sys-once-["+ userReminds.getId()+"]";
				}else{
					 nameStr = "task-sys-cycle-["+ userReminds.getId()+"]";
				}
				 groupStr = "sys";
			}else {
				if(userReminds.getCycleType()==0){
				 nameStr = "task-user-once-["+ userReminds.getId()+"]";
				}else{
				 nameStr = "task-user-cycle-["+ userReminds.getId()+"]";
				}
				 groupStr = "user";
			}
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			try {
				JobKey jobKey = new JobKey(nameStr,groupStr);
				    scheduler.deleteJob(jobKey);
				} catch (Exception e) {
					e.printStackTrace();
			}
		}

}
