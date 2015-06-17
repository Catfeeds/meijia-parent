package com.simi.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserAddrsService;
import com.simi.po.dao.user.UserAddrsMapper;
import com.simi.po.model.user.UserAddrs;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserAddrsServiceImpl implements UserAddrsService {
	@Autowired
	private UserAddrsMapper userAddrsMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userAddrsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(UserAddrs record) {
		return userAddrsMapper.insert(record);
	}

	@Override
	public int insertSelective(UserAddrs record) {
		return userAddrsMapper.insertSelective(record);
	}

	@Override
	public UserAddrs selectByPrimaryKey(Long id) {
		return userAddrsMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UserAddrs> selectByMobile(String mobile) {
		return userAddrsMapper.selectByMobile(mobile);
	}
	@Override
	public int updataDefaultByMobile(String mobile){
		return userAddrsMapper.updateDefaultByMobile(mobile);
	}

	@Override
	public int updateByPrimaryKey(UserAddrs record) {
		return userAddrsMapper.updateByPrimaryKey(record);
	}



	@Override
	public int updateByPrimaryKey(UserAddrs record, boolean updateOther) {
		if(updateOther) {//取消其他默认地址，修改当前为默认地址
			if(userAddrsMapper.updateByMobile(record.getMobile())>0) {
				return userAddrsMapper.updateByPrimaryKey(record);
			}
			return 0;
		}
		return userAddrsMapper.updateByPrimaryKey(record);
	}


	@Override
	public int updateByPrimaryKeySelective(UserAddrs record) {
		return userAddrsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<UserAddrs> getAll(){
		return userAddrsMapper.selectAll();
	}

	@Override
	public UserAddrs initUserAddrs(Long user_id, String mobile, Long cell_id, String addr,
			String longitude, String latitude, int poi_type,String name,String address,
			String city,String uid,String phone,String post_code,Short is_default,UserAddrs userAddrs) {

		userAddrs.setUserId(user_id);
		userAddrs.setAddr(addr);
		userAddrs.setLongitude("");
		userAddrs.setLatitude("");
		userAddrs.setPoiType((short)poi_type);
		userAddrs.setName(name);
		userAddrs.setAddress(address);
		userAddrs.setCity(city);
		userAddrs.setUid(uid);
		userAddrs.setPhone(phone);
		userAddrs.setPostCode(post_code);
		userAddrs.setCellId(cell_id);

		userAddrs.setMobile(mobile);
		userAddrs.setIsDefault(is_default);
		userAddrs.setAddTime(TimeStampUtil.getNow() / 1000);
		userAddrs.setUpdateTime(TimeStampUtil.getNow() / 1000);

		return userAddrs;
	}

	@Override
	public List<UserAddrs> selectByUserId(Long userId) {
		return userAddrsMapper.selectByUserId(userId);
	}







}