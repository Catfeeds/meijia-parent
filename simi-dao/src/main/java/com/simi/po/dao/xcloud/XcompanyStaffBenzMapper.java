package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyStaffBenz;
import com.simi.vo.xcloud.UserCompanySearchVo;

public interface XcompanyStaffBenzMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XcompanyStaffBenz record);

    int insertSelective(XcompanyStaffBenz record);

    XcompanyStaffBenz selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XcompanyStaffBenz record);

    int updateByPrimaryKey(XcompanyStaffBenz record);

	List<XcompanyStaffBenz> selectBySearchVo(UserCompanySearchVo searchVo);
}