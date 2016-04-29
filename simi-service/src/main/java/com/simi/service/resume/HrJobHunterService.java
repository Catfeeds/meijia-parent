package com.simi.service.resume;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.resume.HrJobHunter;
import com.simi.po.model.resume.HrResumeChange;
import com.simi.vo.resume.JobHunterVo;
import com.simi.vo.resume.ResumeChangeVo;
import com.simi.vo.resume.ResumeSearchVo;

/**
 *
 * @author :hulj
 * @Date : 2016年4月28日下午4:31:28
 * @Description: 
 */
public interface HrJobHunterService {
	
	int deleteByPrimaryKey(Long id);

    int insert(HrJobHunter record);

    HrJobHunter selectByPrimaryKey(Long id);

    int updateByPrimaryKey(HrJobHunter record);
    
    List<HrJobHunter> selectBySearchVo(ResumeSearchVo searchVo);

    PageInfo selectByListPage(ResumeSearchVo searchVo, int pageNo, int pageSize);
	
    HrJobHunter initHrJobHunter();
     
    JobHunterVo initHunterVo();
    
    JobHunterVo  transToHunterVo(HrJobHunter hunter);
}
