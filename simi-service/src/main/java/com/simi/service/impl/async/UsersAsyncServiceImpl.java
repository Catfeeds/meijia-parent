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
import com.meijia.utils.GsonUtil;
import com.meijia.utils.MobileUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.huanxin.EasemobIMUsers;
import com.meijia.utils.push.PushUtil;
import com.simi.common.Constants;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.stat.StatUser;
import com.simi.po.model.user.UserLogined;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.card.CardService;
import com.simi.service.feed.FeedService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.stat.StatUserService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.feed.FeedSearchVo;

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
	
	@Autowired
	public StatUserService statUserService;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private FeedService feedService;
	
	@Autowired
	private XcompanyStaffService xcompanyStaffService;
	
	@Autowired
	private OrderQueryService orderQueryService;

	/**
	 * 第三方登录，注册绑定环信账号,异步操作
	 */
	@Async
	@Override
	public Future<Boolean> genImUser(Long userId) {

		Users user = usersService.selectByPrimaryKey(userId);

		UserRef3rd record = new UserRef3rd();

		if (user == null)
			return new AsyncResult<Boolean>(true);

		UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(userId);
		if (userRef3rd != null) {
			return new AsyncResult<Boolean>(true);
		}

		String uuid = "";
		// 如果不存在则新增.并且存入数据库
		String username = "simi-user-" + user.getId().toString();
		String defaultPassword = com.meijia.utils.huanxin.comm.Constants.DEFAULT_PASSWORD;

		// 1. 先去环信查找是否有用户:
		ObjectNode getIMUsersByPrimaryKeyNode = EasemobIMUsers.getIMUsersByPrimaryKey(username);

		JsonNode statusCode = getIMUsersByPrimaryKeyNode.get("statusCode");
		if (statusCode.toString().equals("404")) {
			ObjectNode datanode = JsonNodeFactory.instance.objectNode();
			datanode.put("username", username);
			datanode.put("password", defaultPassword);
			if (user.getName() != null && user.getName().length() > 0) {
				datanode.put("nickname", user.getName());
			}
			ObjectNode createNewIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode);

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

		if (u == null)
			return new AsyncResult<Boolean>(true);

		UserLogined record = userLoginedService.initUserLogined();

		record.setUserId(u.getId());
		record.setMobile(u.getMobile());
		record.setLoginFrom(loginFrom);
		record.setLoginIp(ip);
		userLoginedService.insert(record);

		return new AsyncResult<Boolean>(true);
	}

	/**
	 * 用户手机号所在地
	 */
	@Async
	@Override
	public Future<Boolean> userMobileCity(Long userId) {
		Users u = usersService.selectByPrimaryKey(userId);

		if (u == null)
			return new AsyncResult<Boolean>(true);

		String mobile = u.getMobile();
		if (StringUtil.isEmpty(mobile))
			return new AsyncResult<Boolean>(true);
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
	 * 新用户注册通知运营人员.
	 */
	@Async
	@Override
	public Future<Boolean> newUserNotice(Long userId) {

		Users u = usersService.selectByPrimaryKey(userId);

		if (u == null)
			return new AsyncResult<Boolean>(true);

		String name = u.getName();

		if (StringUtil.isEmpty(name))
			name = u.getMobile();
		// 新用户注册通知运营人员
		long addTime = u.getAddTime();
		String addTimeStr = TimeStampUtil.timeStampToDateStr(addTime * 1000, "MM-dd HH:mm");

		// List<AdminAccount> adminAccounts = adminAccountService.selectByAll();
		// 查出所有运营部的人员（roleId=3）
		Long roleId = 3L;
		List<AdminAccount> adminAccounts = adminAccountService.selectByRoleId(roleId);
		List<String> mobileList = new ArrayList<String>();

		HashMap<String, String> tranParams = new HashMap<String, String>();

		tranParams.put("is", "true");
		tranParams.put("ac", "m");
		tranParams.put("ci", "0");
		tranParams.put("ct", "0");
		tranParams.put("st", "0");
		tranParams.put("re", "0");
		tranParams.put("rt", "新用户注册");
		tranParams.put("rc", "新用户:" + name + "在" + addTimeStr + "注册成功");

		String jsonParams = GsonUtil.GsonString(tranParams);

		HashMap<String, String> params = new HashMap<String, String>();
		for (AdminAccount item : adminAccounts) {
			if (!StringUtil.isEmpty(item.getMobile())) {
				mobileList.add(item.getMobile());
			}

			Users uu = usersService.selectByMobile(item.getMobile());

			if (uu == null)
				continue;

			UserPushBind p = userPushBindService.selectByUserId(uu.getId());

			if (p == null)
				continue;
			
			params.put("transmissionContent", jsonParams);
			params.put("cid", p.getClientId());
			
			if (p.getDeviceType().equals("ios")) {
				try {
					PushUtil.IOSPushToSingle(params, "notification");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (p.getDeviceType().equals("android")) {
				try {
					PushUtil.AndroidPushToSingle(params);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// String[] content = new String[] { name,addTimeStr };
		// for (int i = 0; i < mobileList.size(); i++) {
		// HashMap<String, String> sendSmsResult =
		// SmsUtil.SendSms(mobileList.get(i),
		// Constants.SEC_REGISTER_ID, content);
		// System.out.println(sendSmsResult + "00000000000000");
		// }

		return new AsyncResult<Boolean>(true);
	}

	/**
	 * 用户相互加为好友
	 */
	@Async
	@Override
	public Future<Boolean> addFriends(Users u, Users friendUser) {

		userFriendService.addFriends(u, friendUser);

		return new AsyncResult<Boolean>(true);
	}

	/**
	 * 默认加固定账号为好友.
	 */
	@Async
	@Override
	public Future<Boolean> addDefaultFriends(Long userId) {

		Users u = usersService.selectByPrimaryKey(userId);
		if (u != null) {
			Users friendUser = usersService.selectByMobile("18888888888");
			userFriendService.addFriends(u, friendUser);
		}
		return new AsyncResult<Boolean>(true);
	}

	
	/**
	 * 初始化统计数据
	 */
	@Async
	@Override
	public Future<Boolean> statUserInit(Long userId) {
		
		if (userId.equals(0L)) return new AsyncResult<Boolean>(true);
		
		StatUser record = statUserService.selectByPrimaryKey(userId);
		
		if (record != null) return new AsyncResult<Boolean>(true);
		
		record = statUserService.initStatUser();
		
		record.setUserId(userId);
		
		statUserService.insert(record);
		
		
		
		return new AsyncResult<Boolean>(true);
	}
	
	/**
	 * 初始化统计数据
	 * userId   用户ID
	 * statType
	 *          totalCards = 总卡片数
	 *          totalFeeds = 总问答数
	 *          totalCompanys = 总公司数
	 *          totalOrders  = 总订单数
	 */
	@Async
	@Override
	public Future<Boolean> statUser(Long userId, String statType) {
		
		if (userId.equals(0L)) return new AsyncResult<Boolean>(true);
		
		Boolean isNew = false;
		StatUser record = statUserService.selectByPrimaryKey(userId);
		if (record == null) {
			record = statUserService.initStatUser();
			record.setUserId(userId);
			isNew = true;
		}
		
		
		int total = 0;
		Long tmpUserId = 0L;
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(userId);
		
		//总卡片数
		if (statType.equals("totalCards")) {
			CardSearchVo cSearchVo = new CardSearchVo();
			
			cSearchVo.setUserIds(userIds);
			List<HashMap> totalCards = cardService.totalByUserIds(cSearchVo);
			
			
			for (HashMap totalCard : totalCards) {
				tmpUserId = Long.valueOf(totalCard.get("user_id").toString());
				if (userId.equals(tmpUserId)) {
					total = Integer.valueOf(totalCard.get("total").toString());
					record.setTotalCards(total);
					break;
				}
			}
		}
		
		//总问答数
		if (statType.equals("totalFeeds")) {
			FeedSearchVo fSearchVo = new FeedSearchVo();
			fSearchVo.setUserIds(userIds);
			List<HashMap> totalFeeds = feedService.totalByUserIds(fSearchVo);
			

			for (HashMap totalFeed : totalFeeds) {
				tmpUserId = Long.valueOf(totalFeed.get("user_id").toString());
				if (userId.equals(tmpUserId)) {
					total = Integer.valueOf(totalFeed.get("total").toString());
					record.setTotalFeeds(total);
					break;
				}
			}
		}
		
		//总公司数
		if (statType.equals("totalCompanys")) {
			total = xcompanyStaffService.totalByUserId(userId);
			record.setTotalCompanys(total);
		}
		
		//总订单数
		if (statType.equals("totalOrders")) {
			total = orderQueryService.totalByUserId(userId);
			record.setTotalOrders(total);
		}
		
		
		
		if (isNew) {
			statUserService.insert(record);
		} else {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			statUserService.updateByPrimaryKeySelective(record);
		}
		
		
		
		
		return new AsyncResult<Boolean>(true);
	}
}
