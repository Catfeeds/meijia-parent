package com.simi.service.impl.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.service.user.UserSmsTokenService;
import com.simi.vo.AppResultData;
import com.simi.po.dao.user.UserSmsTokenMapper;
import com.simi.po.model.user.UserSmsToken;
import com.github.pagehelper.PageHelper;
import com.simi.vo.user.UsersSmsTokenVo;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserSmsTokenServiceImpl implements UserSmsTokenService{

	@Autowired
	private UserSmsTokenMapper userSmsTokenMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userSmsTokenMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserSmsToken record) {
		return userSmsTokenMapper.insert(record);
	}

	@Override
	public UserSmsToken selectByMobile(String mobile) {
		return userSmsTokenMapper.selectByMobile(mobile);
	}
	@Override
	public UserSmsToken selectByMobileAndType(String mobile, Short smsType) {
		return userSmsTokenMapper.selectByMobileAndType(mobile, smsType);
	}

	@Override
	public UserSmsToken selectByPrimaryKey(Long id) {
		return userSmsTokenMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UserSmsToken record) {
		return userSmsTokenMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserSmsToken record) {
		return userSmsTokenMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public UserSmsToken initUserSmsToken(String mobile, int sms_type,
			String code, HashMap<String, String> sendSmsResult) {

		UserSmsToken record = new UserSmsToken();
		record.setSmsToken(String.valueOf(code));
		record.setMobile(mobile);
		record.setAddTime(TimeStampUtil.getNow()/1000);
		record.setUpdateTime(TimeStampUtil.getNow()/1000);
		record.setUserId(0L);
		record.setLoginFrom(new Short((short)sms_type));
		short isSuceess = (sendSmsResult.get(Constants.SMS_SUCCESS_CODE) != null && sendSmsResult
				.get(Constants.SMS_SUCCESS_CODE).equals(
						Constants.SMS_SUCCESS_CODE)) ? Constants.SMS_SUCCESS
				: Constants.SMS_FAIL;
		record.setIsSuceess(isSuceess);
		record.setSmsReturn(sendSmsResult.get(Constants.SMS_STATUS_CODE));
		return record;
	}
	
	
	@Override
	public AppResultData<Object> validateSmsToken(String mobile, String token, Short smsType) {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		//根据手机号查询出普通用户的验证码sms_type = 0
		UserSmsToken smsToken = selectByMobileAndType(mobile, smsType);// 1、根据mobile
		// 从表user_sms_token找出最新一条记录
		if (smsToken == null || smsToken.getAddTime() == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ERROR_999_MSG_2);
			return result;
		}

		// 2、判断是否表记录字段add_time 是否超过30分钟.
		long expTime = TimeStampUtil.compareTimeStr(smsToken.getAddTime(), System.currentTimeMillis() / 1000);
		if (expTime > 1800) {// 超时
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ERROR_999_MSG_4);
			return result;
		} 
		
		if (!smsToken.getSmsToken().equals(token)) {// 验证码错误
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ERROR_999_MSG_2);
			return result;
		}
		return result;
	}
	
	@Override
	public List<UserSmsToken> selectByListPage(UsersSmsTokenVo usersSmsTokenVo, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<UserSmsToken> lists = userSmsTokenMapper.selectUserSmsTokenByMobile(usersSmsTokenVo);
		return lists;
	}
}