package com.simi.service.impl.resume;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.resume.HrResumeChangeMapper;
import com.simi.po.model.resume.HrResumeChange;
import com.simi.po.model.user.Users;
import com.simi.service.resume.HrResumeChangeService;
import com.simi.service.user.UsersService;
import com.simi.vo.resume.ResumeChangeVo;
import com.simi.vo.resume.ResumeSearchVo;

/**
 *
 * @author :hulj
 * @Date : 2016年4月28日下午4:32:55
 * @Description: 
 *
 */
@Service
public class HrResumeChangeServiceImpl implements HrResumeChangeService {
	
	@Autowired
	private HrResumeChangeMapper changeMapper;
	
	@Autowired
	private UsersService userService;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return changeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(HrResumeChange record) {
		return changeMapper.insert(record);
	}

	@Override
	public HrResumeChange selectByPrimaryKey(Long id) {
		return changeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(HrResumeChange record) {
		return changeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<HrResumeChange> selectBySearchVo(ResumeSearchVo searchVo) {
		return changeMapper.selectBySearchVo(searchVo);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageInfo selectByListPage(ResumeSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<HrResumeChange> list = changeMapper.selectByListPage(searchVo);
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}
	
	@Override
	public HrResumeChange initHrResumeChange() {
		
		HrResumeChange change = new HrResumeChange();
		
		change.setId(0L);
		change.setUserId(0L);
		change.setName("");
		change.setCityName("");
	    change.setLimitDay((short)0L);
	    change.setTitle("");
	    change.setContent("");
	    change.setLink("");
	    change.setAddTime(TimeStampUtil.getNowSecond());
		change.setUpdateTime(TimeStampUtil.getNowSecond());
		
		return change;
	}
	
	@Override
	public ResumeChangeVo initResumeChangeVo() {
		
		HrResumeChange change = initHrResumeChange();
		
		ResumeChangeVo changeVo = new ResumeChangeVo();
	
		BeanUtilsExp.copyPropertiesIgnoreNull(change, changeVo);
		
		changeVo.setUserName("");
		
		return changeVo;
	}
	
	
	@Override
	public ResumeChangeVo transToResumeVo(HrResumeChange change) {
		
		ResumeChangeVo changeVo = initResumeChangeVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(change, changeVo);
		
		// 用户姓名
		Long userId = change.getUserId();
		
		Users users = userService.selectByPrimaryKey(userId);
		
		changeVo.setUserName(users.getName());
		
		// 截止日期 = 添加时间+有效天数的毫秒时间戳
		Long addTime = change.getAddTime()*1000;
		Short limitDay = change.getLimitDay();
		Long limitTimeSta = (long)limitDay*3600*24*1000+ addTime;
		
		String dateStr = TimeStampUtil.timeStampToChineseDateStr(limitTimeSta, "yyyy年MM月dd日");
		
		String limitDayStr = "";
		
		if(change.getLimitDay() == 0){
			dateStr = "长期有效";
			limitDayStr = "长期有效";
		}else{
			limitDayStr = change.getLimitDay()+"天";
		}
		//结束时间
		changeVo.setEndTimeStr(dateStr);
		
		//详情页 有效天数
		changeVo.setLimitDayStr(limitDayStr);
		
		return changeVo;
	}
}
