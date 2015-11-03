package com.simi.service.impl.async;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meijia.utils.MobileUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.huanxin.EasemobIMUsers;
import com.simi.common.Constants;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.user.UserLogined;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UsersService;

@Service
public class UsersAsyncServiceImpl implements UsersAsyncService {

	@Autowired
	public UsersService usersService;

	@Autowired
	public UserRef3rdService userRef3rdService;

	@Autowired
	public UserFriendService userFriendService;

	@Autowired
	public UserPushBindService userPushBindService;
	
	@Autowired
	public UserLoginedService userLoginedService;
	
	@Autowired
	public AdminAccountService adminAccountService;
	
	/**
	 * 第三方登录，注册绑定环信账号,异步操作
	 */
	@Async
	@Override
	public Future<Boolean> genImUser(Long userId) {
		
		
		Users user = usersService.selectByPrimaryKey(userId);
		
		UserRef3rd record = new UserRef3rd();
		
		if (user == null) return new AsyncResult<Boolean>(true);
		
		UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(userId);
		if (userRef3rd != null) {
			return new AsyncResult<Boolean>(true);
		}
		
		String uuid = "";
		// 如果不存在则新增.并且存入数据库
		String username = "simi-user-" + user.getId().toString();
		String defaultPassword = com.meijia.utils.huanxin.comm.Constants.DEFAULT_PASSWORD;
		
		//1. 先去环信查找是否有用户:
	    ObjectNode getIMUsersByPrimaryKeyNode = EasemobIMUsers.getIMUsersByPrimaryKey(username);
		
	    JsonNode statusCode = getIMUsersByPrimaryKeyNode.get("statusCode");
	    if (statusCode.toString().equals("404")) {
			ObjectNode datanode = JsonNodeFactory.instance.objectNode();
			datanode.put("username", username);
			datanode.put("password", defaultPassword);
			if (user.getName() != null && user.getName().length() > 0) {
				datanode.put("nickname", user.getName());
			}
			ObjectNode createNewIMUserSingleNode = EasemobIMUsers
					.createNewIMUserSingle(datanode);
			
			JsonNode entity = createNewIMUserSingleNode.get("entities");
			uuid = entity.get(0).get("uuid").toString();
	    } else {
	    	JsonNode entity = getIMUsersByPrimaryKeyNode.get("entities");
	    	uuid = entity.get(0).get("uuid").toString();
	    }

		// username = entity.get(0).get("username").toString();

		record.setId(0L);
		record.setUserId(userId);
		record.setRefType(Constants.IM_PROVIDE);
		record.setMobile(user.getMobile());
		record.setUsername(username);
		record.setPassword(defaultPassword);
		record.setRefPrimaryKey(uuid);
		record.setAddTime(TimeStampUtil.getNowSecond());
		userRef3rdService.insert(record);
		return new AsyncResult<Boolean>(true);
	}	
	
	/**
	 * 记录用户登录状态
	 */
	@Async
	@Override
	public Future<Boolean> userLogined(Long userId, Short loginFrom, Long ip) {
		
		
		Users u = usersService.selectByPrimaryKey(userId);
		
		if (u == null) return new AsyncResult<Boolean>(true);
		
		UserLogined record = userLoginedService.initUserLogined();
		
		record.setUserId(userId);
		record.setMobile(u.getMobile());
		record.setLoginFrom(loginFrom);
		record.setLoginIp(ip);
		userLoginedService.insert(record);
		
		return new AsyncResult<Boolean>(true);
	}
	
	/**
	 *  用户手机号所在地
	 */
	@Async
	@Override
	public Future<Boolean> userMobileCity(Long userId) {	
		Users u = usersService.selectByPrimaryKey(userId);
		
		if (u == null) return new AsyncResult<Boolean>(true);
		
		String mobile = u.getMobile();
		if (StringUtil.isEmpty(mobile)) return new AsyncResult<Boolean>(true);
		String provinceName = "";
		try {
			provinceName = MobileUtil.calcMobileCity(mobile);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		u.setProvinceName(provinceName);
		usersService.updateByPrimaryKeySelective(u);
		return new AsyncResult<Boolean>(true);
	}
	
	/**
	 *  新用户注册通知运营人员.
	 */
	@Async
	@Override
	public Future<Boolean> newUserNotice(Long userId) {
		
		Users u = usersService.selectByPrimaryKey(userId);
		
		if (u == null) return new AsyncResult<Boolean>(true);
		
		String name = u.getName();
		
		if (StringUtil.isEmpty(name)) name = u.getMobile();
		//新用户注册通知运营人员
		long addTime = u.getAddTime();
		String addTimeStr = TimeStampUtil.timeStampToDateStr(addTime*1000);
		
		List<AdminAccount> adminAccounts = adminAccountService.selectByAll();
		List<String> mobileList = new ArrayList<String>();
		for (AdminAccount item: adminAccounts) {
			mobileList.add(item.getMobile());
		}
		String[] content = new String[] { name,addTimeStr };
		for (int i = 0; i < mobileList.size(); i++) {
			HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobileList.get(i),
					Constants.SEC_REGISTER_ID, content);
			System.out.println(sendSmsResult + "00000000000000");
		}		
		
		return new AsyncResult<Boolean>(true);
	}
	
}
