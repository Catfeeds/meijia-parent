package com.simi.service.impl.user;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserRemindService;
import com.simi.po.dao.user.UserRemindsMapper;
import com.simi.po.model.user.UserReminds;
import com.meijia.utils.DateUtil;
@Service
public class UserRemindServiceImpl implements UserRemindService {

	@Autowired
	private  UserRemindsMapper userRemindsMapper;



	@Override
	public int deleteByPrimaryKey(Long id) {
		return 	userRemindsMapper.deleteByPrimaryKey(id);

	}

	@Override
	public int insert(UserReminds record) {
		return userRemindsMapper.insert(record);

	}

	@Override
	public Long insertSelective(UserReminds record) {
		return userRemindsMapper.insertSelective(record);

	}

	@Override
	public UserReminds selectByPrimaryKey(Long id) {
		return userRemindsMapper.selectByPrimaryKey(id);

	}

	@Override
	public int updateByPrimaryKeySelective(UserReminds record) {
		return userRemindsMapper.updateByPrimaryKeySelective(record);

	}

	@Override
	public int updateByPrimaryKey(UserReminds record) {
		return userRemindsMapper.updateByPrimaryKey(record);

	}

	@Override
	public List<UserReminds> selectByMobile(String mobile) {
		return userRemindsMapper.selectByMobile(mobile);
	}


	@Override
	public List<UserReminds> queryAllReminds() {
		return userRemindsMapper.queryAllReminds();
	}

	@Override
	public List<UserReminds> queryByCycleAndExcute() {
		return userRemindsMapper.queryByCycleAndExcute();
	}

	@Override
	public String getCrond(String startDate, String startTime, int cycleType) {
		String string = startDate+" "+startTime+":00";
		Date cronDate = DateUtil.parse(string, DateUtil.DEFAULT_FULL_PATTERN);
		Calendar cal = Calendar.getInstance();
		cal.setTime(cronDate);
		StringBuffer crond = new StringBuffer("0");
		if(cycleType==0){
			crond.append(" "+cal.get(Calendar.MINUTE)+" "+cal.get(Calendar.HOUR_OF_DAY)+" "+
						cal.get(Calendar.DAY_OF_MONTH)+" "+(cal.get(Calendar.MONTH)+1)+" ? "+cal.get(Calendar.YEAR));
		}else if(cycleType==1){
			//每天
			crond.append(" "+cal.get(Calendar.MINUTE)+" "+cal.get(Calendar.HOUR_OF_DAY)+
					" * * ? *");
		}else if(cycleType==2){
			//每个工作日
			crond.append(" "+cal.get(Calendar.MINUTE)+" "+cal.get(Calendar.HOUR_OF_DAY)+
					" ? * 2-6 *");
		}else if(cycleType==3){
			//每周
			crond.append(" "+cal.get(Calendar.MINUTE)+" "+cal.get(Calendar.HOUR_OF_DAY)+
					" ? * "+cal.get(Calendar.DAY_OF_WEEK)+" "+cal.get(Calendar.YEAR));
		}else if(cycleType==4){
			//每月
			crond.append(" "+cal.get(Calendar.MINUTE)+" "+cal.get(Calendar.HOUR_OF_DAY)+" "+
					cal.get(Calendar.DAY_OF_WEEK)+" * ? *");
		}else{
			//每年
			crond.append(" "+cal.get(Calendar.MINUTE)+" "+cal.get(Calendar.HOUR_OF_DAY)+" "+
					cal.get(Calendar.DAY_OF_WEEK)+" "+(cal.get(Calendar.MONTH)+1)+" ? *");

		}
		return crond.toString();
	}

}
