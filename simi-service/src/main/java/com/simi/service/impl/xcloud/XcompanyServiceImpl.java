package com.simi.service.impl.xcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.xcloud.XCompanyService;
import com.simi.po.model.xcloud.Xcompany;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyMapper;

@Service
public class XcompanyServiceImpl implements XCompanyService {
	
	@Autowired
	XcompanyMapper xCompanyMapper;
		
	@Override
	public Xcompany initXcompany() {
		Xcompany record = new Xcompany();
		record.setCompanyId(0L);
		record.setCompanyName("");
		record.setCompanyTrade(0L);
		record.setCompanySize((short) 0);
		record.setProvinceId(0L);
		record.setCityId(0L);
		record.setRegionId(0L);
		record.setLongitude(" ");
		record.setLatitude(" ");
		record.setPoiType((short)0);
		record.setAddrName(" ");
		record.setAddr("");
		record.setUserName("");
		record.setPassword("");
		record.setLinkMan("");
		record.setEmail("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return xCompanyMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insert(Xcompany record) {
		return xCompanyMapper.insert(record);
	}
	
	@Override
	public int insertSelective(Xcompany record) {
		return xCompanyMapper.insertSelective(record);
	}

	@Override
	public Xcompany selectByPrimaryKey(Long id) {
		return xCompanyMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKey(Xcompany record) {
		return xCompanyMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Xcompany record) {
		return xCompanyMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public Xcompany selectByCompanyName(String companyName) {
		return xCompanyMapper.selectByCompanyName(companyName);
	}	
	
	@Override
	public Xcompany selectByUserName(String userName) {
		return xCompanyMapper.selectByUserName(userName);
	}
	
	/**
	 * 登陆方式
	 * @param userName
	 * @param password， 必须为md5的值. passMd5 = md5(password+xcloud)
	 * @return
	 */
	@Override
	public Xcompany selectByUserNameAndPass(String userName, String passMd5) {
		return xCompanyMapper.selectByUserNameAndPass(userName, passMd5);
	}	

}