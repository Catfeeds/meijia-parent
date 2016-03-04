package com.simi.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.StringUtil;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UserTrailRealService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.po.dao.user.UserPushBindMapper;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.UserTrailReal;
import com.simi.po.model.user.Users;

@Service
public class ValidateServiceImpl implements ValidateService {

	@Autowired
	private UsersService userService;

	@Autowired
	private UserTrailRealService userTrailRealService;

	@Autowired
	private UserAddrsService userAddrsService;

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
				if (!u.getProvinceName().equals("北京市")) {
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

}
