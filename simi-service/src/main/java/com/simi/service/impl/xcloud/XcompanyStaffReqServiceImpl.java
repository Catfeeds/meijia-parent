package com.simi.service.impl.xcloud;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyStaffReqMapper;
import com.simi.po.model.order.OrderExtClean;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyStaffReq;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyStaffReqService;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.simi.vo.xcloud.XcompanyStaffReqVo;


@Service
public class XcompanyStaffReqServiceImpl implements XcompanyStaffReqService {

	@Autowired
	private XcompanyStaffReqMapper xCompanyStaffReqMapper;
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private XCompanyService xCompanyService;

	
	@Override
	public int updateByPrimaryKeySelective(XcompanyStaffReq userFriendReq) {
		return xCompanyStaffReqMapper.updateByPrimaryKeySelective(userFriendReq);
	}
	
	@Override
	public XcompanyStaffReq initXcompanyStaffReq() {
		XcompanyStaffReq u = new XcompanyStaffReq();

		u.setId(0L);
		u.setUserId(0L);
		u.setCompanyId(0L);
		u.setRemarks("");
	    u.setStatus((short) 0);
		u.setAddTime(TimeStampUtil.getNow() / 1000);
		u.setUpdateTime(TimeStampUtil.getNow() / 1000);
		return u;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		
		return xCompanyStaffReqMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(XcompanyStaffReq record) {
		
		return xCompanyStaffReqMapper.insert(record);
	}

	@Override
	public int insertSelective(XcompanyStaffReq record) {

		return xCompanyStaffReqMapper.insertSelective(record);
	}

	@Override
	public XcompanyStaffReq selectByPrimaryKey(Long id) {
		
		return xCompanyStaffReqMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(XcompanyStaffReq record) {
		
		return xCompanyStaffReqMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<XcompanyStaffReq> selectByUserId(Long userId) {
		
		return xCompanyStaffReqMapper.selectByUserId(userId);
	}
	
	@Override
	public List<XcompanyStaffReq> selectByCompanyId(Long companyId) {
		
		return xCompanyStaffReqMapper.selectByCompanyId(companyId);
	}
	
	@Override
	public List<XcompanyStaffReq> selectByUserIdOrCompanyId(Long userId, Long companyId) {
		return xCompanyStaffReqMapper.selectByUserIdOrCompanyId(userId, companyId);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(UserCompanySearchVo searchVo, int pageNo, int pageSize) {

		 PageHelper.startPage(pageNo, pageSize);
         List<XcompanyStaffReq> list = xCompanyStaffReqMapper.selectByListPage(searchVo);
                  
         PageInfo result = new PageInfo(list);
         
        return result;
    }
	
	@Override
	public List<XcompanyStaffReqVo> getVos(List<XcompanyStaffReq> list, Long userId) {
		List<XcompanyStaffReqVo> result = new ArrayList<XcompanyStaffReqVo>();
		
		if (list.isEmpty()) return result;
		
		
		List<Long> userIds = new ArrayList<Long>();
		List<Long> companyIds = new ArrayList<Long>();
		
		for (XcompanyStaffReq item : list) {
			if (!userIds.contains(item.getUserId())) userIds.add(item.getUserId());
			
			if (!companyIds.contains(item.getCompanyId())) {
				companyIds.add(item.getCompanyId());
			}
		}
		
		List<Users> users = new ArrayList<Users>();
		
		if (!userIds.isEmpty()) {
			users = userService.selectByUserIds(userIds);
		}
		
		List<Xcompany> xcompanys = new ArrayList<Xcompany>();
		
		if (!companyIds.isEmpty()) {
			xcompanys = xCompanyService.selectByIds(companyIds);
		}
		
		for (XcompanyStaffReq item : list) {
			XcompanyStaffReqVo vo = new XcompanyStaffReqVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			vo.setName("");
			vo.setHeadImg("");
			for (Users user : users) {
				if (user.getId().equals(vo.getUserId())) {
					vo.setName(user.getName());
					vo.setHeadImg(user.getHeadImg());
					break;
				}
			}
			
			vo.setCompanyName("");
			for (Xcompany xcompany : xcompanys) {
				if (xcompany.getCompanyId().equals(vo.getCompanyId())) {
					vo.setCompanyName(xcompany.getCompanyName());
					break;
				}
			}
			
			vo.setReqType((short) 0);
			if (!userId.equals(vo.getUserId())) vo.setReqType((short) 1);
			
			result.add(vo);
		}
		
		return result;
	}

}
