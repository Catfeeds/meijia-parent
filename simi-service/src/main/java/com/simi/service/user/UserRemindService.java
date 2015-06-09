package com.simi.service.user;


import java.util.List;

import com.simi.po.model.user.UserReminds;

public interface UserRemindService {

	    int deleteByPrimaryKey(Long id);

	    int insert(UserReminds record);

	    Long insertSelective(UserReminds record);

	    UserReminds selectByPrimaryKey(Long id);

	    int updateByPrimaryKeySelective(UserReminds record);

	    int updateByPrimaryKey(UserReminds record);

	    List<UserReminds> selectByMobile(String mobile);

	    List<UserReminds> queryAllReminds();

	    List<UserReminds> queryByCycleAndExcute();

	    String getCrond(String startDate,String time,int cycleType);


}
