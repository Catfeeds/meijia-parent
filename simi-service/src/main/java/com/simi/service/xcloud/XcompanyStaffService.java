package com.simi.service.xcloud;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.vo.AppResultData;
import com.simi.vo.UserCompanySearchVo;
import com.simi.vo.xcloud.StaffListVo;

public interface XcompanyStaffService {

	XcompanyStaff initXcompanyStaff();

	int deleteByPrimaryKey(Long id);

	int insertSelective(XcompanyStaff xcompanyStaff);
	
	int updateByPrimaryKeySelective(XcompanyStaff xcompanyStaff);

	XcompanyStaff selectByPrimarykey(Long id);

	@SuppressWarnings("rawtypes")
	PageInfo selectByListPage(UserCompanySearchVo searchVo, int pageNo, int pageSize);
	
	List<XcompanyStaff> selectBySearchVo(UserCompanySearchVo searchVo);
	
	StaffListVo changeToStaffLisVo(XcompanyStaff item);

	List<StaffListVo> changeToStaffLisVos(Long companyId, List<XcompanyStaff> list);
	
	String getNextJobNumber(Long companyId);

	AppResultData<Object> validateStaffImport(Long companyId, List<Object> excelDatas) throws Exception;

	List<Object> checkDuplication(Long companyId, List<Object> excelDatas) throws Exception;

	AppResultData<Object> staffImport(Long companyId, List<Object> datas) throws Exception;

}
