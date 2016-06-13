package com.simi.service.xcloud;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.StaffJsonInfo;
import com.simi.vo.xcloud.StaffListVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

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

	List<XcompanyStaff> selectByListPage(UserCompanySearchVo searchVo);

	Xcompany getDefaultCompanyByUserId(Long userId);
	
	
	// 对 xcompanyStaff中的 json 格式字段  jsonInfo 的处理
	StaffJsonInfo initJsonInfo();
	
	
	//云平台  xcompanyStaff 对应的Vo    staffListVo 初始化
	StaffListVo initStaffListVO();
	
}
