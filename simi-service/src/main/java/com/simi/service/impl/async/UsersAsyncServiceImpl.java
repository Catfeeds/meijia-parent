package com.simi.service.impl.async;

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
import com.meijia.utils.RandomUtil;
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
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.Users;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.card.CardService;
import com.simi.service.dict.CouponService;
import com.simi.service.order.OrderSeniorService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UserPushBindService;
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
public class UsersAsyncServiceImpl implements UsersAsyncService {

	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private UserRef3rdService userRef3rdService;

	@Autowired
	private UserFriendService userFriendService;

	@Autowired
	private UserPushBindService userPushBindService;
	
}
