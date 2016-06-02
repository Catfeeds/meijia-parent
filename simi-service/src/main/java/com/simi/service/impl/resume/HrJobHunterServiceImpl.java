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
import com.simi.po.dao.resume.HrJobHunterMapper;
import com.simi.po.model.resume.HrJobHunter;
import com.simi.po.model.user.Users;
import com.simi.service.resume.HrJobHunterService;
import com.simi.service.user.UsersService;
import com.simi.vo.resume.JobHunterVo;
import com.simi.vo.resume.ResumeChangeSearchVo;

/**
 *
 * @author :hulj
 * @Date : 2016年4月29日下午3:31:26
 * @Description: 
 *
 */
@Service
public class HrJobHunterServiceImpl implements HrJobHunterService {
	
	@Autowired
	private HrJobHunterMapper jobMapper;
	
	@Autowired
	private UsersService userService;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return jobMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(HrJobHunter record) {
		return jobMapper.insert(record);
	}

	@Override
	public HrJobHunter selectByPrimaryKey(Long id) {
		return jobMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(HrJobHunter record) {
		return jobMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<HrJobHunter> selectBySearchVo(ResumeChangeSearchVo searchVo) {
		return jobMapper.selectBySearchVo(searchVo);
	}

	@Override
	public PageInfo selectByListPage(ResumeChangeSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<HrJobHunter> list = jobMapper.selectByListPage(searchVo);
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}
	
	@Override
	public JobHunterVo transToHunterVo(HrJobHunter hunter) {
		
		JobHunterVo hunterVo = initHunterVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(hunter, hunterVo);
		
		if(hunter.getUserId() != 0L){
		
			Users users = userService.selectByPrimaryKey(hunter.getUserId());
			
			hunterVo.setUserName(users.getName());
		}
		
		// 截止日期 = 添加时间+有效天数的毫秒时间戳
		Long addTime = hunter.getAddTime()*1000;
		Short limitDay = hunter.getLimitDay();
		Long limitTimeSta = (long)limitDay*3600*24*1000+ addTime;
		
		String dateStr = TimeStampUtil.timeStampToChineseDateStr(limitTimeSta, "yyyy年MM月dd日");
		
		String limitDayStr = "";
		
		if(hunter.getLimitDay() == 0){
			dateStr = "长期有效";
			limitDayStr = "长期有效";
		}else{
			limitDayStr = hunter.getLimitDay()+"天";
		}
		//结束时间
		hunterVo.setEndTimeStr(dateStr);
		
		//详情页 有效天数
		hunterVo.setLimitDayStr(limitDayStr);
		
		//是否到截止日期
		Long now = TimeStampUtil.getNow();
		
		String flag = "enable";
		
		if(now > limitTimeSta && hunter.getLimitDay() != 0){
			flag = "disable";
		}
		
		hunterVo.setEndTimeFlag(flag);
		
		
		return hunterVo;
	}
	
	
	@Override
	public HrJobHunter initHrJobHunter() {
		
		HrJobHunter hunter = new HrJobHunter();
		
		hunter.setId(0L);
	    hunter.setUserId(0L);
	    hunter.setName("");
	    hunter.setCityName("");
	    hunter.setLimitDay((short)0);
	    hunter.setTitle("");
	    hunter.setJobRes("");
	    hunter.setJobReq("");
	    hunter.setRemarks("");
	    hunter.setAddTime(TimeStampUtil.getNowSecond());
	    hunter.setUpdateTime(TimeStampUtil.getNowSecond());
		
	    hunter.setReward("");
	    
		return hunter;
	}
	
	@Override
	public JobHunterVo initHunterVo() {
		
		JobHunterVo vo = new JobHunterVo();
		
		HrJobHunter hunter = initHrJobHunter();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(hunter, vo);
		
		vo.setUserName("");
		
		
		//下拉时间选择
		Map<Long, String> limitDayMap = new LinkedHashMap<Long, String>();
		
		limitDayMap.put(0L, "长期有效");
		limitDayMap.put(30L, "30天有效");
		limitDayMap.put(180L, "6个月有效");
		
		vo.setTimeMap(limitDayMap);
		
		
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
		
		vo.setCitySelectMap(citySelectMap);
		
		vo.setLimitDayStr("");
		vo.setEndTimeStr("");
		vo.setEndTimeFlag("");
		
		//app字段
		vo.setCityName("北京市");
		
		return vo;
	}
}
