package com.simi.service.impl.user;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.MobileUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.huanxin.EasemobIMUsers;
import com.simi.common.Constants;
import com.simi.po.dao.user.UserRef3rdMapper;
import com.simi.po.dao.user.UserRefSecMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.dict.DictCoupons;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.Users;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.card.CardService;
import com.simi.service.dict.CouponService;
import com.simi.service.order.OrderSeniorService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UsersService;
import com.simi.vo.UserFriendSearchVo;
import com.simi.vo.UserSearchVo;
import com.simi.vo.UsersSearchVo;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.user.UserIndexVo;
import com.simi.vo.user.UserViewVo;


@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private UserCouponService userCouponService;

	@Autowired
	private OrderSeniorService orderSeniorService;

	@Autowired
	private UserRef3rdMapper userRef3rdMapper;

	@Autowired
	private UserRef3rdService userRef3rdService;	
	
	@Autowired
	private UserRefSecService userRefSecService;

	@Autowired
	private AdminAccountService adminAccountService;

	@Autowired
	private CouponService couponService;
	
	@Autowired
	private UserRefSecMapper userRefSecMapper;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private UserFriendService userFriendService;	
	
	/**
	 * 新用户注册流程
	 * 1. 注册用户
	 * 2. 赠送金额
	 */
	@Override
	public Users genUser(String mobile, String name, Short addFrom) {
		Users u = selectByMobile(mobile);
		if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
			u = this.initUsers();
			u.setMobile(mobile);
			u.setAddFrom(addFrom);
			String provinceName = "";
			try {
				provinceName = MobileUtil.calcMobileCity(mobile);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			u.setName(name);
			u.setProvinceName(provinceName);
			this.insertSelective(u);


			//先看看是否已经有赠送
			UserCoupons userCoupon = userCouponService.selectByMobileCardPwd(mobile, Constants.NEW_USER_COUPON_CARD_PASSWORD);

			if (userCoupon == null) {
				//新用户注册赠送相应的优惠劵.
				DictCoupons coupon = couponService.selectByCardPasswd(Constants.NEW_USER_COUPON_CARD_PASSWORD);

				userCoupon = userCouponService.initUserCoupon();
				userCoupon.setUserId(u.getId());
				userCoupon.setMobile(u.getMobile());
				userCoupon.setCouponId(coupon.getId());
				userCoupon.setCardPasswd(coupon.getCardPasswd());
				userCoupon.setValue(coupon.getValue());
				userCoupon.setExpTime(coupon.getExpTime());

				userCouponService.insert(userCoupon);
			}
			
			
			//发送给13810002890 ，做一个提醒
//			String code = mobile;
//			String[] content = new String[] { code, Constants.GET_CODE_MAX_VALID };
//			HashMap<String, String> sendSmsResult = SmsUtil.SendSms("13810002890",
//					Constants.GET_CODE_TEMPLE_ID, content);
			
		}
		return u;
	}

	@Override
	public List<Users> selectByAll() {
		return usersMapper.selectByAll();
	}

	@Override
	public UserViewVo getUserViewByUserId(Long userId) {
		Users user  = usersMapper.selectByPrimaryKey(userId);

		UserViewVo userInfo = new UserViewVo();
		if (user == null) {
			return userInfo;
		}
		
		BeanUtilsExp.copyPropertiesIgnoreNull(user, userInfo);
		
		String seniorRange = "";
		HashMap<String, Date> seniorRangeResult = orderSeniorService.getSeniorRangeDate(userId);

		if (!seniorRangeResult.isEmpty()) {
			Date startDate = seniorRangeResult.get("startDate");
			Date endDate = seniorRangeResult.get("endDate");
			seniorRange = "有效期:" + DateUtil.formatDate(startDate) + "至" + DateUtil.formatDate(endDate);
		}
		userInfo.setSeniorRange(seniorRange);


		return userInfo;
	}
	
	@Override
	public UserIndexVo getUserIndexVoByUserId(Users user, Users viewUser) {

		UserIndexVo vo = new UserIndexVo();
		
		vo.setId(viewUser.getId());
		vo.setSex(viewUser.getSex());
		vo.setHeadImg(viewUser.getHeadImg());
		vo.setProvinceName(viewUser.getProvinceName());
		vo.setUserType(viewUser.getUserType());
		vo.setName(viewUser.getName());		
		vo.setRestMoney(new BigDecimal(0));
		vo.setMobile(viewUser.getMobile());
		if (user.getId().equals(viewUser.getId())) {
			vo.setRestMoney(viewUser.getRestMoney());
		}
		
		
		UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(viewUser.getId());
		
		if (userRef3rd != null) {
			vo.setImUserName(userRef3rd.getUsername());
		}
		vo.setPoiDistance("");
		
		//计算卡片的个数
		vo.setTotalCard(0);
		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setCardFrom((short) 0);
		searchVo.setUserId(viewUser.getId());
		
		PageInfo  pageInfo = cardService.selectByListPage(searchVo, 1, Constants.PAGE_MAX_NUMBER);
		if (pageInfo != null) {
			Long totalCard = pageInfo.getTotal();
			vo.setTotalCard(totalCard.intValue());
		}

		//计算优惠劵个数
		vo.setTotalCoupon(0);
		List<UserCoupons> list = userCouponService.selectByUserId(viewUser.getId());
		if (!list.isEmpty()) vo.setTotalCoupon(list.size());
		
		//计算好友个数
		vo.setTotalFriends(0);
		UserFriendSearchVo searchVo1 = new UserFriendSearchVo();
		searchVo1.setUserId(viewUser.getId());
		PageInfo userFriendPage = userFriendService.selectByListPage(searchVo1, 1, Constants.PAGE_MAX_NUMBER);
		if (userFriendPage != null) {
			Long totalFriends = userFriendPage.getTotal();
			vo.setTotalFriends(totalFriends.intValue());
		}
		return vo;
	}	

	@Override
	public int updateByPrimaryKeySelective(Users user) {
		return usersMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public Users initUserForm() {
		Users u =  new Users();
		u.setId(0L);
		u.setMobile("");
		u.setProvinceName("");
		u.setThirdType(" ");
		u.setOpenid(" ");
		u.setName(" ");
		u.setRealName("");
		u.setBirthDay(new Date());
		u.setIdCard("");
		u.setDegreeId((short) 0);
		u.setMajor("");
		u.setSex(" ");
		u.setHeadImg(" ");
		u.setRestMoney(new BigDecimal(0));
		u.setUserType((short) 1);
		u.setIsApproval((short) 0);
		u.setAddFrom((short) 0);
		u.setScore(0);
		u.setAddTime(TimeStampUtil.getNow()/1000);
		u.setUpdateTime(TimeStampUtil.getNow()/1000);
		return u;
	}

	@Override
	public PageInfo searchVoListPage(UserSearchVo searchVo, int pageNo,
			int pageSize) {

		HashMap<String,Object> conditions = new HashMap<String,Object>();
		 String mobile = searchVo.getMobile();
		 Long secId = searchVo.getSecId();
		 List<Long> userIdList = new ArrayList<Long>();

		if(mobile !=null && !mobile.isEmpty()){
			conditions.put("mobile",mobile.trim());
		}
		if(secId!=null){
			List<UserRefSec> list =  userRefSecService.selectBySecId(secId);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UserRefSec userRefSec = (UserRefSec) iterator.next();
				userIdList.add(userRefSec.getUserId());
			}
		}
		if(userIdList!=null  && userIdList.size()>0){
			conditions.put("userIdList",userIdList);
		}
		
		if(searchVo.getUserType() != null) {
			conditions.put("userType",searchVo.getUserType());
		}

		PageHelper.startPage(pageNo, pageSize);
		List<Users> list = usersMapper.selectByListPage(conditions);
		PageInfo result = new PageInfo(list);
		return result;
	}
	
	/**
	 * 获取用户账号详情接口
	 */
	@Override
	public UserViewVo getUserInfo(Long userId) {
		UserViewVo vo = new UserViewVo();
		Users u = usersMapper.selectByPrimaryKey(userId);;
		
		if (u == null) {
			return vo;
		}
		
		BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);
		vo.setUserId(u.getId());

		
		if (StringUtil.isEmpty(vo.getName())) {
			vo.setName(vo.getMobile());
		}
		
		UserRefSec userRefSec = userRefSecMapper.selectByUserId(userId);
		
		
		//获取用户与绑定的秘书的环信IM账号
		Map imRobot = this.getImRobot(u);
		vo.setImRobotUsername(imRobot.get("username").toString());
		vo.setImRobotNickname(imRobot.get("nickname").toString());
		vo.setSecId(0L);
		if(userRefSec!=null){

			Users secUser = usersMapper.selectByPrimaryKey(userRefSec.getSecId());
			UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(userRefSec.getSecId());
			
			if (userRef3rd == null) {
				
			}
			
			if (userRef3rd != null) {
				vo.setImSecUsername(userRef3rd.getUsername());
				vo.setImSecNickname(secUser.getName());
				vo.setSecId(userRefSec.getSecId());
			}
		}else{
			vo.setImSecUsername("");
			vo.setImSecNickname("");
		}
		
		vo.setIsSenior((short) 0);
		String seniorRange = "";
		
		HashMap<String, Date> seniorRangeResult = orderSeniorService.getSeniorRangeDate(userId);

		if (!seniorRangeResult.isEmpty()) {
			Date startDate = seniorRangeResult.get("startDate");
			Date endDate = seniorRangeResult.get("endDate");
			String endDateStr = DateUtil.formatDate(endDate);
			String nowStr = DateUtil.getToday();
			if(DateUtil.compareDateStr(nowStr, endDateStr) >= 0) {
				vo.setIsSenior((short) 1);
				seniorRange = "有效期:" + DateUtil.formatDate(startDate) + "至" + DateUtil.formatDate(endDate);
			} else {
				seniorRange = "已过期";
			}
		}

		vo.setSeniorRange(seniorRange);

		//用户环信IM信息
		UserRef3rd userRef3rd = this.genImUser(u);
		if (userRef3rd.getUsername().length() > 0) {
			vo.setImUsername(userRef3rd.getUsername());
			vo.setImPassword(userRef3rd.getPassword());
		}

		return vo;
	}
	
	
	/**
	 * 获取用户账号详情接口
	 */
	@Override
	public List<UserViewVo> getUserInfos(List<Long> userIds, Users secUser, UserRef3rd userRef3rd) {
		List<UserViewVo> result = new ArrayList<UserViewVo>();
		List<Users> userList = usersMapper.selectByUserIds(userIds);
		
		List<UserRef3rd> userRef3rds = userRef3rdMapper.selectByUserIds(userIds);
		
		Users u = null;
		for (int i = 0; i < userList.size(); i++) {
			
			UserViewVo vo = new UserViewVo();
			u = userList.get(i);
			
			BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);
			
			vo.setUserId(u.getId());
		
		
			if (StringUtil.isEmpty(vo.getName())) {
				vo.setName(vo.getMobile());
			}
		
		
			//获取用户与绑定的秘书的环信IM账号
			Map imRobot = this.getImRobot(u);
			vo.setImRobotUsername(imRobot.get("username").toString());
			vo.setImRobotNickname(imRobot.get("nickname").toString());

		
			vo.setImSecUsername(userRef3rd.getUsername());
			vo.setImSecNickname(secUser.getName());
			vo.setSecId(secUser.getId());

		
			vo.setIsSenior((short) 0);
			String seniorRange = "";
		
			HashMap<String, Date> seniorRangeResult = orderSeniorService.getSeniorRangeDate(u.getId());

			if (!seniorRangeResult.isEmpty()) {
				Date startDate = seniorRangeResult.get("startDate");
				Date endDate = seniorRangeResult.get("endDate");
				String endDateStr = DateUtil.formatDate(endDate);
				String nowStr = DateUtil.getToday();
				if(DateUtil.compareDateStr(nowStr, endDateStr) >= 0) {
					vo.setIsSenior((short) 1);
				}
	
				seniorRange = "有效期:" + DateUtil.formatDate(startDate) + "至" + DateUtil.formatDate(endDate);
	
	
			}
			
			//去掉已经到期的用户
			if (vo.getIsSenior().equals((short)0)) continue;
			
			vo.setSeniorRange(seniorRange);
			
			
			for (UserRef3rd item : userRef3rds) {
				if (item.getUserId().equals(u.getId())) {
					vo.setImUsername(item.getUsername());
					vo.setImPassword(item.getPassword());
				}
			}
			result.add(vo);
			
		}

		return result;
	}	

	/**
	 * 查询用户与管家绑定的环信账号
	 * 1. 如果用户没有购买过管家卡，则为默认Constans.YGGJ_AMEI
	 * 2. 如果用户购买过管家卡，则为真人管家绑定的环信IM账号
	 */
	@Override
	public Map<String, String> getSeniorImUsername(Users user) {

		Map<String, String> map = new HashMap<String, String>();
		if (user == null) {
			return map;
		}
		//先找出真人管家的admin_id
		Long userId = user.getId();

		UserRefSec userRefSec = userRefSecMapper.selectByUserId(userId);
		if (userRefSec == null) {
			return map;
		}

		Long adminId = userRefSec.getSecId();
		AdminAccount adminAccount = adminAccountService.selectByPrimaryKey(adminId);

		String seniorImUsername = adminAccount.getImUsername();
		String seniorImNickname = adminAccount.getNickname();
		map.put("seniorImUsername", seniorImUsername);
		map.put("seniorImNickname", seniorImNickname);
		return map;
	}

	/**
	 * 获得环信机器人账号
	 * 后续需要根据用户的性别来进行判断，目前都统一为女性.
	 */
	@Override
	public Map<String, String> getImRobot(Users user) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("username", Constants.ROBOT_FEMALE_USERNAME);
		map.put("nickname", Constants.ROBOT_FEMALE_NICKNAME);
		return map;
	}

	@Override
	public List<Users> selectUsersHaveOrdered(List<String> mobiles) {
		return usersMapper.selectByMobiles(mobiles);
	}

	@Override
	public List<Users> selectUsersNoOrdered(List<String> mobiles) {
		return usersMapper.selectNotInMobiles(mobiles);
	}

	@Override
	public Users selectByOpenidAndThirdType(String openid, String thirdType) {
		Map<String,Object> conditions = new HashMap<String, Object>();
		if(openid!=null && !openid.isEmpty()){
			conditions.put("openId",openid);
		}
		if(thirdType!=null && !thirdType.isEmpty()){
			conditions.put("thirdType",thirdType);
		}
		return usersMapper.selectByOpenidAnd3rdType(conditions);
	}
	/**
	 * 第三方登录，注册绑定环信账号
	 */
	@Override
	public UserRef3rd genImUser(Users user) {
		UserRef3rd record = new UserRef3rd();
		Long userId = user.getId();
		UserRef3rd userRef3rd = userRef3rdMapper.selectByUserIdForIm(userId);
		if (userRef3rd !=null) {
			return userRef3rd;
		}

		//如果不存在则新增.并且存入数据库
		String username = "simi-user-"+ user.getId().toString();
		String defaultPassword = com.meijia.utils.huanxin.comm.Constants.DEFAULT_PASSWORD;
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username", username);
        datanode.put("password", defaultPassword);
        if (user.getName() != null && user.getName().length() > 0) {
        	datanode.put("nickname", user.getName());
        }
        ObjectNode createNewIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode);

        JsonNode statusCode = createNewIMUserSingleNode.get("statusCode");
		if (!statusCode.toString().equals("200"))
			return record;

		JsonNode entity = createNewIMUserSingleNode.get("entities");
		String uuid = entity.get(0).get("uuid").toString();
//		username = entity.get(0).get("username").toString();

		record.setId(0L);
		record.setUserId(userId);
		record.setRefType(Constants.IM_PROVIDE);
		record.setMobile(user.getMobile());
		record.setUsername(username);
		record.setPassword(defaultPassword);
		record.setRefPrimaryKey(uuid);
		record.setAddTime(TimeStampUtil.getNowSecond());
		userRef3rdMapper.insert(record);
        return record;
	}

	
	@Override
	public List<Users> selectByUserIds(List<Long> ids) {
		return usersMapper.selectByUserIds(ids);
	}	
	
	@Override
	public List<Users> selectByUserType(Short userType) {
		return usersMapper.selectByUserType(userType);
	}

	@Override
	public Long insert(Users record) {
		
		return usersMapper.insert(record);
	}

	@Override
	public Users selectUserByIdCard(String idCard) {
		return usersMapper.selectUserByIdCard(idCard);
	}

	@Override
	public PageInfo selectByIsAppRoval(int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<Users> list = usersMapper.selectByIsAppRoval();
		PageInfo result = new PageInfo(list);
		return result;
	}
	@Override
	public PageInfo selectByIsAppRovalYes(int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<Users> list = usersMapper.selectByIsAppRovalYes();
		PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public Users selectByPrimaryKey(Long id) {
		return usersMapper.selectByPrimaryKey(id);
	}

	@Override
	public Long insertSelective(Users u) {
		// TODO Auto-generated method stub
		return usersMapper.insertSelective(u);
	}

	@Override
	public Users selectByMobile(String mobile) {
		return usersMapper.selectByMobile(mobile);
	}

	@Override
	public List<Users> selectByListPage(UsersSearchVo usersSearchVo, int pageNo,
			int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<Users> lists = usersMapper.selectVoByListPage(usersSearchVo);
		return lists;
	}

	@Override
	public Users initUsers() {
		Users u =  new Users();
		u.setId(0L);
		u.setMobile("");
		u.setProvinceName("");
		u.setThirdType(" ");
		u.setOpenid(" ");
		u.setName(" ");
		u.setRealName("");
		u.setBirthDay(new Date());
		u.setIdCard("");
		u.setDegreeId((short) 0);
		u.setMajor("");
		u.setSex(" ");
		u.setHeadImg(" ");
		u.setRestMoney(new BigDecimal(0));
		u.setUserType((short) 0);
		u.setIsApproval((short) 0);
		u.setAddFrom((short) 0);
		u.setScore(0);
		u.setAddTime(TimeStampUtil.getNow()/1000);
		u.setUpdateTime(TimeStampUtil.getNow()/1000);
		return u;
	}

	@Override
	public Users initUsersByMobileAndName(String mobile, String name) {
			Users u =  new Users();
			u.setId(0L);
			u.setMobile(mobile);
			u.setProvinceName("");
			u.setThirdType(" ");
			u.setOpenid(" ");
			u.setName(name);
			u.setRealName("");
			u.setBirthDay(new Date());
			u.setIdCard("");
			u.setDegreeId((short) 0);
			u.setMajor("");
			u.setSex(" ");
			u.setHeadImg(" ");
			u.setRestMoney(new BigDecimal(0));
			u.setUserType((short) 0);
			u.setIsApproval((short) 0);
			u.setAddFrom((short) 0);
			u.setScore(0);
			u.setAddTime(TimeStampUtil.getNow()/1000);
			u.setUpdateTime(TimeStampUtil.getNow()/1000);
			return u;
		}
	}
	
