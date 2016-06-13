package com.simi.service.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyJob;
import com.simi.vo.xcloud.CompanyJobSearchVo;
import com.simi.vo.xcloud.XcompanyJobVo;

public interface XcompanyJobService {
	
	int deleteByPrimaryKey(Long kjobId);

    int insert(XcompanyJob record);

    XcompanyJob selectByPrimaryKey(Long kjobId);

    int updateByPrimaryKey(XcompanyJob record);
    
    List<XcompanyJob> selectByListPage(CompanyJobSearchVo searchVo,int pageNo, int pageSize);
    
    List<XcompanyJob> selectBySearchVo(CompanyJobSearchVo searchVo);
    
    XcompanyJob initJob();
    
    XcompanyJobVo initVo();
    
    XcompanyJobVo transToVO(XcompanyJob job);
}
