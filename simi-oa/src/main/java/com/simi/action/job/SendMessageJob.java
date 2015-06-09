package com.simi.action.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.po.model.user.UserReminds;
import com.simi.service.user.UserRemindService;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class SendMessageJob implements Job {

	@Autowired
	private UserRemindService userRemindService;
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try{
		UserReminds userReminds = (UserReminds)context.getMergedJobDataMap().get("scheduleJob");
		String code = RandomUtil.randomNumber();
		String[] content = new String[] { code, Constants.GET_CODE_MAX_VALID };
		SmsUtil.SendSms(userReminds.getRemindToMobile(),Constants.GET_CODE_TEMPLE_ID, content);
		userReminds.setIsExecuted(Short.valueOf("1"));
		userReminds.setLastExecuted(TimeStampUtil.getNow()/1000);
		userRemindService.updateByPrimaryKey(userReminds);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
