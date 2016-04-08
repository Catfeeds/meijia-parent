package com.simi.service.xcloud;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.xcloud.XcompanyStaffReq;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.simi.vo.xcloud.XcompanyStaffReqVo;


public interface XcompanyStaffReqService {

	int deleteByPrimaryKey(Long id);

	int insert(XcompanyStaffReq record);

	int insertSelective(XcompanyStaffReq record);

	XcompanyStaffReq selectByPrimaryKey(Long id);

	int updateByPrimaryKey(XcompanyStaffReq record);

	int updateByPrimaryKeySelective(XcompanyStaffReq record);

	XcompanyStaffReq initXcompanyStaffReq();

	List<XcompanyStaffReq> selectByUserId(Long userId);

	List<XcompanyStaffReq> selectByCompanyId(Long companyId);

	List<XcompanyStaffReq> selectByUserIdOrCompanyId(Long userId, Long companyId);

	PageInfo selectByListPage(UserCompanySearchVo searchVo, int pageNo, int pageSize);

	List<XcompanyStaffReqVo> getVos(List<XcompanyStaffReq> list, Long userId);

}