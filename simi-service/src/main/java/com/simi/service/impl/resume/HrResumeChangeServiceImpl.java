package com.simi.service.impl.resume;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.simi.vo.resume.ResumeChangeSearchVo;

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
	public List<HrResumeChange> selectBySearchVo(ResumeChangeSearchVo searchVo) {
		return changeMapper.selectBySearchVo(searchVo);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageInfo selectByListPage(ResumeChangeSearchVo searchVo, int pageNo, int pageSize) {
		
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
		
		
		//下拉时间选择
		Map<Long, String> limitDayMap = new LinkedHashMap<Long, String>();
		
		limitDayMap.put(0L, "长期有效");
		limitDayMap.put(30L, "30天有效");
		limitDayMap.put(180L, "6个月有效");
		
		changeVo.setTimeMap(limitDayMap);
		
		
		//下拉选择城市
		//北京   上海  杭州 广州 深圳 成都 武汉 南京
		Map<String, String> citySelectMap = new LinkedHashMap<String, String>();
		
		
		citySelectMap.put("北京市", "北京");
		citySelectMap.put("上海市", "上海");
		citySelectMap.put("杭州市", "杭州");
		citySelectMap.put("广州市", "广州");
		citySelectMap.put("深圳市", "深圳");
		citySelectMap.put("成都市", "成都");
		citySelectMap.put("武汉市", "武汉");
		citySelectMap.put("南京市", "南京");
		
		changeVo.setCitySelectMap(citySelectMap);
		
		changeVo.setLimitDayStr("");
		changeVo.setEndTimeStr("");
		changeVo.setEndTimeFlag("");
		
		//app字段
		changeVo.setCityName("北京市");
		
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
