package com.simi.service.xcloud;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.CheckinNetVo;
import com.simi.vo.xcloud.CheckinVo;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;

public interface XcompanyCheckinService {

	XcompanyCheckin initXcompanyCheckin();

	int deleteByPrimaryKey(Long id);

	Long insertSelective(XcompanyCheckin record);
	
	int updateByPrimaryKeySelective(XcompanyCheckin record);

	XcompanyCheckin selectByPrimarykey(Long id);
	
	List<XcompanyCheckin> selectBySearchVo(CompanyCheckinSearchVo searchVo);

	PageInfo selectByListPage(CompanyCheckinSearchVo searchVo, int pageNo, int pageSize);

	CheckinNetVo initCheckinNetVo();

	List<CheckinVo> getVos(List<XcompanyCheckin> list);

	AppResultData<Object> matchCheckinSetting(Long id);

}
