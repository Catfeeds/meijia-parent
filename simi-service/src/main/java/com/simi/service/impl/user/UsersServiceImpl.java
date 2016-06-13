package com.simi.service.impl.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.common.Constants;
import com.simi.po.dao.user.UserRef3rdMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.user.UserFriends;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.UserRef;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.async.UserScoreAsyncService;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.card.CardService;
import com.simi.service.dict.DictCouponsService;
import com.simi.service.feed.FeedService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.user.UserBaseVo;
import com.simi.vo.user.UserFriendSearchVo;
import com.simi.vo.user.UserIndexVo;
import com.simi.vo.user.UserRefSearchVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.user.UserViewVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private UserCouponService userCouponService;

	@Autowired
	private OrderQueryService orderQueryService;

	@Autowired
	private UserRef3rdMapper userRef3rdMapper;

	@Autowired
	private UserRef3rdService userRef3rdService;

	@Autowired
	private DictCouponsService couponService;

	@Autowired
	private CardService cardService;

	@Autowired
	private UserFriendService userFriendService;

	@Autowired
	private UserPushBindService userPushBindService;

	@Autowired
	private UsersAsyncService userAsyncService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;	

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private XCompanyService xCompanyService;
	
	@Autowired
	private UserScoreAsyncService userScoreAsyncService;
	
	@Autowired
	private FeedService feedService;
	
	@Autowired
	private XCompanySettingService xCompanySettingService;
	
	@Autowired
	private UserRefService userRefService;
	
	@Override
	public Long insert(Users record) {
		return usersMapper.insert(record);
	}
	
	@Override
	public Long insertSelective(Users u) {
		// TODO Auto-generated method stub
		return usersMapper.insertSelective(u);
	}
	
	@Override
	public int updateByPrimaryKeySelective(Users user) {
		return usersMapper.updateByPrimaryKeySelective(user);
	}	
	
	@Override
	public Users initUsers() {
		Users u = new Users();
		u.setId(0L);
		u.setMobile("");
		u.setProvinceName("");
		u.setThirdType(" ");
		u.setOpenid(" ");
		u.setName("");
		u.setRealName("");
		u.setIdCard("");
		u.setSex("男");
		u.setHeadImg(" ");
		u.setQrCode("");
		u.setIntroduction("");
		u.setLevel((short) 0);
		u.setRestMoney(new BigDecimal(0));
		u.setUserType((short) 0);
		u.setAddFrom((short) 0);
		u.setScore(0);
		u.setExp(0);
		u.setAddTime(TimeStampUtil.getNow() / 1000);
		u.setUpdateTime(TimeStampUtil.getNow() / 1000);
		return u;
	}
	
	@Override
	public Users genUser(String mobile, String name, short addFrom, String introduction) {
		Users u = selectByMobile(mobile);
		if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
			u = this.initUsers();
			u.setMobile(mobile);
			u.setAddFrom(addFrom);
			u.setName(name);
			u.setIntroduction(introduction);
			this.insertSelective(u);

			// 检测用户所在地，异步操作
			userAsyncService.userMobileCity(u.getId());

			// 新用户注册通知运营人员
			userAsyncService.newUserNotice(u.getId());

			// 默认加固定客服用户为好友
			userAsyncService.addDefaultFriends(u.getId());
			
			// 发送默认欢迎消息
			userMsgAsyncService.newUserMsg(u.getId());
			
			//新用户注册赠送积分
			userScoreAsyncService.sendScore(u.getId(), Constants.SCORE_USER_REG, "new_user", u.getId().toString(), "新用户注册");
			
		}
		return u;
	}

	@Override
	public List<Users> selectByAll() {
		return usersMapper.selectByAll();
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageInfo selectByListPage(UserSearchVo searchVo, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<Users> list = usersMapper.selectByListPage(searchVo);
		PageInfo result = new PageInfo(list);
		return result;
	}
	
	@Override
	public List<Users> selectBySearchVo(UserSearchVo searchVo) {
		return usersMapper.selectBySearchVo(searchVo);
	}

	/**
	 * 获取用户账号详情接口
	 */
	@Override
	public UserViewVo getUserInfo(Long userId) {
		UserViewVo vo = new UserViewVo();
		Users u = usersMapper.selectByPrimaryKey(userId);

		if (u == null) {
			return vo;
		}

		BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);
		vo.setJobName("");
		vo.setUserId(u.getId());

		if (StringUtil.isEmpty(vo.getName())) {
			vo.setName(vo.getMobile());
		}
		
		//用户余额
		BigDecimal restMoney = u.getRestMoney();
		String restMoneyStr = MathBigDecimalUtil.round2(restMoney);
		vo.setRestMoney(restMoneyStr);
		
		
		// 获取用户与绑定的秘书的环信IM账号
		UserRefSearchVo refSearchVo = new UserRefSearchVo();
		refSearchVo.setUserId(userId);
		refSearchVo.setRefType("sec");
		List<UserRef> rs = userRefService.selectBySearchVo(refSearchVo);
		
		UserRef userRef = null;
		if (!rs.isEmpty()) userRef = rs.get(0);
		
		vo.setSecId(0L);
		if (userRef != null) {
			Long refId = userRef.getRefId();
			Users secUser = usersMapper.selectByPrimaryKey(refId);
			UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(refId);

			if (userRef3rd != null) {
				vo.setImSecUsername(userRef3rd.getUsername());
				vo.setImSecNickname(secUser.getName());
				vo.setSecId(refId);
			}
		} else {
			vo.setImSecUsername("");
			vo.setImSecNickname("");
		}

		vo.setIsSenior((short) 0);
		String seniorRange = "";

		Date seniorEndDate = orderQueryService.getSeniorRangeDate(userId);

		if (!(seniorEndDate == null)) {

			String endDateStr = DateUtil.formatDate(seniorEndDate);
			String nowStr = DateUtil.getToday();
			if (DateUtil.compareDateStr(nowStr, endDateStr) >= 0) {
				vo.setIsSenior((short) 1);
				seniorRange = "截止" + endDateStr;
			} else {
				seniorRange = "已过期";
			}

		}

		vo.setSeniorRange(seniorRange);

		// 用户环信IM信息
		UserRef3rd userRef3rd = userRef3rdService.genImUser(u);
		if (userRef3rd.getUsername().length() > 0) {
			vo.setImUsername(userRef3rd.getUsername());
			vo.setImPassword(userRef3rd.getPassword());
		}

		// 用户绑定推送设备信息
		vo.setClientId("");
		UserPushBind userPushBind = userPushBindService.selectByUserId(userId);
		if (userPushBind != null)
			vo.setClientId(userPushBind.getClientId());

		// 用户是否为某个团队的职员
		vo.setHasCompany((short) 0);
		vo.setCompanyId(0L);
		vo.setCompanyCount(0);
		vo.setCompanyId(0L);
		vo.setCompanyName("");

		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> companyList = xcompanyStaffService.selectBySearchVo(searchVo);

		if (!companyList.isEmpty()) {
			vo.setHasCompany((short) 1);
			vo.setCompanyCount(companyList.size());
		}
		
		
		
		Xcompany defaultXcompany = xcompanyStaffService.getDefaultCompanyByUserId(userId);
		
		if (defaultXcompany != null) {
			vo.setCompanyId(defaultXcompany.getCompanyId());
			vo.setCompanyName(defaultXcompany.getCompanyName());
		}
		
		return vo;
	}

	/**
	 * 获取用户账号详情接口
	 */
	@Override
	public List<UserViewVo> getUserInfos(List<Long> userIds, Users secUser, UserRef3rd userRef3rd) {
		List<UserViewVo> result = new ArrayList<UserViewVo>();
		
		
		UserSearchVo searchVo = new UserSearchVo();
		searchVo.setUserIds(userIds);
		
		List<Users> userList = usersMapper.selectBySearchVo(searchVo);

		List<UserRef3rd> userRef3rds = userRef3rdMapper.selectByUserIds(userIds);

		Users u = null;
		for (int i = 0; i < userList.size(); i++) {

			UserViewVo vo = new UserViewVo();
			u = userList.get(i);

			BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);
			
			vo.setUserId(u.getId());

			BigDecimal restMoney = u.getRestMoney();
			String restMoneyStr = MathBigDecimalUtil.round2(restMoney);
			vo.setRestMoney(restMoneyStr);
			
			if (StringUtil.isEmpty(vo.getName())) {
				vo.setName(vo.getMobile());
			}
			
			// 获取用户与绑定的秘书的环信IM账号

			vo.setImSecUsername(userRef3rd.getUsername());
			vo.setImSecNickname(secUser.getName());
			vo.setSecId(secUser.getId());

			vo.setIsSenior((short) 0);
			String seniorRange = "";

			Date seniorEndDate = orderQueryService.getSeniorRangeDate(u.getId());

			if (!(seniorEndDate == null)) {

				String endDateStr = DateUtil.formatDate(seniorEndDate);
				String nowStr = DateUtil.getToday();
				if (DateUtil.compareDateStr(nowStr, endDateStr) >= 0) {
					vo.setIsSenior((short) 1);
					seniorRange = "截止" + endDateStr;
				} else {
					seniorRange = "已过期";
				}

			}

			// 去掉已经到期的用户
			if (vo.getIsSenior().equals((short) 0))
				continue;

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

	@Override
	public List<Users> selectNotInMobiles(List<String> mobiles) {
		return usersMapper.selectNotInMobiles(mobiles);
	}

	@Override
	public Users selectByPrimaryKey(Long id) {
		return usersMapper.selectByPrimaryKey(id);
	}

	@Override
	public Users selectByMobile(String mobile) {
		return usersMapper.selectByMobile(mobile);
	}

	@Override
	public UserIndexVo getUserIndexVo(Users user, Users viewUser) {

		UserIndexVo vo = new UserIndexVo();

		vo.setId(viewUser.getId());
		vo.setSex(viewUser.getSex());
		vo.setHeadImg(getHeadImg(viewUser));
		vo.setProvinceName(viewUser.getProvinceName());
		vo.setUserType(viewUser.getUserType());
		vo.setName(viewUser.getName());
		vo.setRestMoney(new BigDecimal(0));
		vo.setMobile(viewUser.getMobile());
		vo.setScore(viewUser.getScore());
		vo.setExp(viewUser.getScore());
		if (user.getId().equals(viewUser.getId())) {
			vo.setRestMoney(viewUser.getRestMoney());
		}

		UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(viewUser.getId());

		if (userRef3rd != null) {
			vo.setImUserName(userRef3rd.getUsername());
		}
		vo.setPoiDistance("");


		// 计算好友个数
		vo.setTotalFriends(0);
		UserFriendSearchVo searchVo1 = new UserFriendSearchVo();
		searchVo1.setUserId(viewUser.getId());
		PageInfo userFriendPage = userFriendService.selectByListPage(searchVo1, 1, Constants.PAGE_MAX_NUMBER);
		if (userFriendPage != null) {
			Long totalFriends = userFriendPage.getTotal();
			vo.setTotalFriends(totalFriends.intValue());
		}
		
		//是否为好友
		vo.setIsFriend((short) 0);
		Long userId = user.getId();
		Long friendId = viewUser.getId();
		
		if (!userId.equals(friendId)) {
			searchVo1 = new UserFriendSearchVo();
			searchVo1.setUserId(userId);
			searchVo1.setFriendId(friendId);
			UserFriends userFriend = userFriendService.selectByIsFirend(searchVo1);
			
			if (userFriend != null) {
				vo.setIsFriend((short) 1);
			}
		}
		
		//用户等级
		String level = "LV1";
		Integer levelMin = 0;
		Integer levelMax = 0;
		String levelBanner = "http://app.51xingzheng.cn/simi-h5/show/images/level-banner-1.jpg";
		
		Integer exp = vo.getExp();
		
		CompanySettingSearchVo s = new CompanySettingSearchVo();
		s.setSettingType("user-level");
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(s);
		XcompanySetting setting = null;
		if (!list.isEmpty()) {
			setting = list.get(0);
			String gsonString = setting.getSettingJson();
			List<Map<String, Object>> levels = GsonUtil.GsonToListMaps(gsonString);
			
			for(int i =0; i < levels.size(); i++) {
				Map<String, Object> item = levels.get(i);
				levelMin = Integer.valueOf(item.get("min").toString());
				levelMax = Integer.valueOf(item.get("max").toString());
				
				if (exp > levelMin && exp < levelMax) {
					level = item.get("name").toString();
					levelBanner = item.get("banner").toString();
					break;
				}
				
			}
			
		}
		
		vo.setLevel(level);
		vo.setLevelMin(levelMin);
		vo.setLevelMax(levelMax);
		vo.setLevelBanner(levelBanner);
		
		
		return vo;
	}
	
	@Override
	public UserBaseVo getUserBaseVo(Users user) {

		UserBaseVo vo = new UserBaseVo();
		vo.setUserId(user.getId());
		vo.setHeadImg(user.getHeadImg());
		vo.setMobile(user.getMobile());
		vo.setName(user.getName());
		vo.setProvinceName(user.getProvinceName());
		vo.setSex(user.getSex());
		
		return vo;
	}	
	
	@Override
	public List<UserBaseVo> getUserBaseVos(List<Users> list) {
		List<UserBaseVo> result = new ArrayList<UserBaseVo>();
		
		for (Users user : list) {
			UserBaseVo vo = new UserBaseVo();
			vo.setUserId(user.getId());
			vo.setHeadImg(user.getHeadImg());
			vo.setMobile(user.getMobile());
			vo.setName(user.getName());
			vo.setProvinceName(user.getProvinceName());
			vo.setSex(user.getSex());
			result.add(vo);
		}
		
		return result;
	}		
	

	/**
	 * 获得用户头像的方法
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getHeadImg(Users u) {
		String headImg = "";

		if (!StringUtil.isEmpty(u.getHeadImg().trim()))
			return u.getHeadImg();

		if (StringUtil.isEmpty(u.getName().trim()))
			return Constants.DEFAULT_HEAD_IMG;

		String name = u.getName();
		name = name.replace(" ", "");
		String headImgWord = "";
		if (StringUtil.isContainChinese(name)) {
			if (name.length() > 2) {
				headImgWord = name.substring(name.length() - 2, name.length());
			} else {
				headImgWord = name;
			}
		} else {
			if (name.length() > 2) {
				headImgWord = name.substring(0, 2);
			} else {
				headImgWord = name;
			}
		}

		if (StringUtil.isEmpty(headImgWord))
			return Constants.DEFAULT_HEAD_IMG;

		byte[] imgUrlBytes = null;
		if (StringUtil.isContainChinese(name)) {
			imgUrlBytes = ImgServerUtil.ImgYin(headImgWord, Constants.DEFAULT_HEAD_IMG_BACK, -82, 22);
		} else {
			imgUrlBytes = ImgServerUtil.ImgYin(headImgWord, Constants.DEFAULT_HEAD_IMG_BACK, -40, 20);
		}

		if (imgUrlBytes == null)
			return Constants.DEFAULT_HEAD_IMG;
		String url = Constants.IMG_SERVER_HOST + "/upload/";

		String sendResult = ImgServerUtil.sendPostBytes(url, imgUrlBytes, "jpg");

		ObjectMapper mapper = new ObjectMapper();

		HashMap<String, Object> o = null;
		try {
			o = mapper.readValue(sendResult, HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(o.toString());
		String ret = o.get("ret").toString();

		HashMap<String, String> info = (HashMap<String, String>) o.get("info");

		String imgUrl = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();
		imgUrl = ImgServerUtil.getImgSize(imgUrl, "100", "100");
		u.setHeadImg(imgUrl);
		headImg = imgUrl;
		updateByPrimaryKeySelective(u);

		return headImg;
	}
	
}
