package com.simi.action.job;

import java.text.SimpleDateFormat;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.po.model.user.UserReminds;
import com.simi.service.user.UserRemindService;
import com.simi.service.user.UsersService;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class SendMessageJob implements Job {

	@Autowired
	private UserRemindService userRemindService;
	@Autowired
	private UsersService usersService;
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try{
		UserReminds userReminds = (UserReminds)context.getMergedJobDataMap().get("scheduleJob");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String name = usersService.getUserByMobile(userReminds.getMobile()).getName();
		String date = sdf1.format(userReminds.getStartDate());
		String time = userReminds.getStartTime();
		String[] content = new String[] {name,date+" "+time ,userReminds.getRemindTitle(),""+" "+"" };
		SmsUtil.SendSms(userReminds.getRemindToMobile(),Constants.GET_CODE_REMIND_ID, content);
		userReminds.setIsExecuted(Short.valueOf("1"));
		userReminds.setLastExecuted(TimeStampUtil.getNow()/1000);
		userRemindService.updateByPrimaryKey(userReminds);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
