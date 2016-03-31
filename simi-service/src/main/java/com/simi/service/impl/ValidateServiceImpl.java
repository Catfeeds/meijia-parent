package com.simi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.StringUtil;
import com.simi.service.ValidateService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UserTrailRealService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserFriendSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.UserFriends;
import com.simi.po.model.user.UserTrailReal;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyStaff;

@Service
public class ValidateServiceImpl implements ValidateService {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserFriendService userFriendService;

	@Autowired
	private UserTrailRealService userTrailRealService;

	@Autowired
	private UserAddrsService userAddrsService;
	
	@Autowired
	private XcompanyStaffService xCompanyStaffService;
	
	// 验证用户是否存在
	@Override
	public AppResultData<Object> validateUser(Long userId) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		return result;
	}

	// 验证订单服务范围
	@Override
	public AppResultData<Object> validateOrderCity(Long userId) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		UserTrailReal userTrailReal = userTrailRealService.selectByUserId(userId);

		if (userTrailReal != null) {
			if (!userTrailReal.getCity().equals("北京市")) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("目前仅支持北京市区服务范围，敬请谅解！");
				return result;
			}
		} else {
			Users u = userService.selectByPrimaryKey(userId);
			if (!StringUtil.isEmpty(u.getProvinceName())) {
				if (!u.getProvinceName().equals("北京")) {
					result.setStatus(Constants.ERROR_999);
					result.setMsg("目前仅支持北京市区服务范围，敬请谅解！");
					return result;
				}
			}
		}

		return result;
	}

	// 验证地址服务范围
	@Override
	public AppResultData<Object> validateUserAddr(Long userId, Long addrId) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		// 如果城市不是北京市，则提示无法服务
		UserAddrs userAddr = userAddrsService.selectByPrimaryKey(addrId);
		if (userAddr == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("目前仅支持北京市区服务范围，敬请谅解！");
			return result;
		}

		String cityName = userAddr.getCity();
		if (!cityName.equals("北京市")) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("目前仅支持北京市区服务范围，敬请谅解！");
			return result;
		}

		return result;
	}
	
	//验证是不是好友
	@Override
	public AppResultData<Object> validateFriend(Long userId, Long friendId) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		UserFriendSearchVo searchVo = new UserFriendSearchVo();
		searchVo.setUserId(userId);
		searchVo.setFriendId(friendId);
		UserFriends userFriend = userFriendService.selectByIsFirend(searchVo);	
		
		if (userFriend == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("不是好友");
			return result;
		}
		return result;
	}
	
	//验证是不是同一个团队/组织
	@Override
	public AppResultData<Object> validateSameCompany(Long userId, Long friendId) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> company1 = xCompanyStaffService.selectBySearchVo(searchVo);
		
		if (company1.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("不在同一家团队");
			return result;
		}
		
		searchVo = new UserCompanySearchVo();
		searchVo.setUserId(friendId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> company2 = xCompanyStaffService.selectBySearchVo(searchVo);
		
		if (company2.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("不在同一家团队");
			return result;
		}
		
		Boolean isSame = false;
		for (XcompanyStaff item1 : company1) {
			for (XcompanyStaff item2 : company2) {
				if (item1.getCompanyId().equals(item2.getCompanyId())) {
					isSame = true;
					break;
				}
			}
		}
		
		if (isSame == false) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("不在同一家团队");
			return result;
		}
		
		return result;
	}	
	
	//验证是否为公司员工
	@Override
	public AppResultData<Object> validateIsCompanyStaff(Long userId, Long companyId) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		if (companyId.equals(0L)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("团队不存在");
			return result;
		}

		// 判断员工是否为团队一员
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xCompanyStaffService.selectBySearchVo(searchVo);

		if (staffList.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("您不是团队中一员,请查验.");
			return result;
		}

		return result;
	}

}
