package com.simi.service.impl.user;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.service.user.UserSmsTokenService;
import com.simi.po.dao.user.UserSmsTokenMapper;
import com.simi.po.model.user.UserSmsToken;
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
	public UserSmsToken selectByMobileAndType(String mobile) {
		return userSmsTokenMapper.selectByMobileAndType(mobile);
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

}