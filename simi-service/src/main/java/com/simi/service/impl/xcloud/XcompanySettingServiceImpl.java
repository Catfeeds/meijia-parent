package com.simi.service.impl.xcloud;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.dict.DictService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.xcloud.CompanySettingVo;
import com.simi.vo.xcloud.json.SettingJsonSettingValue;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.po.model.xcloud.XcompanySetting;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.common.Constants;
import com.simi.po.dao.xcloud.XcompanySettingMapper;

@Service
public class XcompanySettingServiceImpl implements XCompanySettingService {
	
	@Autowired
	XcompanySettingMapper xcompanySettingMapper;
	
	@Autowired
	private DictService dictService;
	
	
	@Override
	public XcompanySetting initXcompanySetting() {
		XcompanySetting record = new XcompanySetting();
		
		record.setId(0L);
		record.setCompanyId(0L);
		record.setUserId(0L);
		record.setName("社保公积金基数");
		record.setSettingJson("");
		//社保公积金 setting_type   =   insurance
		record.setSettingType(Constants.SETTING_TYPE_INSURANCE);
		record.setIsEnable((short)1);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		
		//初始化一个json 字段
		record.setSettingValue(initJsonSettingValue());
		
		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return xcompanySettingMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int insert(XcompanySetting record) {
		return xcompanySettingMapper.insert(record);
	}
	
	@Override
	public int insertSelective(XcompanySetting record) {
		return xcompanySettingMapper.insertSelective(record);
	}

	@Override
	public XcompanySetting selectByPrimaryKey(Long id) {
		return xcompanySettingMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKey(XcompanySetting record) {
		return xcompanySettingMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanySetting record) {
		return xcompanySettingMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<XcompanySetting> selectBySearchVo(CompanySettingSearchVo searchVo) {
		return xcompanySettingMapper.selectBySearchVo(searchVo);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public PageInfo selectByListPage(CompanySettingSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<XcompanySetting> list = xcompanySettingMapper.selectByListPage(searchVo);
		PageInfo result = new PageInfo(list);
		
		return result;
	}

	@Override
	public CompanySettingVo getCompantSettingVo(XcompanySetting item) {
		
		CompanySettingVo vo = new CompanySettingVo();
		//添加时间返回‘yyyy-mm-dd’
		Long addTime = item.getAddTime()*1000;
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime, "yyyy-MM-dd"));

		vo.setName(item.getName());
		vo.setSettingJson(item.getSettingJson());
		vo.setSettingId(item.getId());
		vo.setId(item.getId());
		
		return vo;
	}

	@Override
	public SettingJsonSettingValue initJsonSettingValue() {
		
		SettingJsonSettingValue settingValue = new SettingJsonSettingValue();
		
		settingValue.setCityId("");
		settingValue.setRegionId("");
		
		settingValue.setPensionP("");
		settingValue.setPensionC("");
		
		settingValue.setMedicalP("");
		settingValue.setMedicalC("");
		
		settingValue.setUnemploymentP("");
		settingValue.setUnemploymentC("");
		
		settingValue.setInjuryP("");
		settingValue.setInjuryC("");
		
		settingValue.setBirthP("");
		settingValue.setBirthC("");
		
		settingValue.setFundP("");
		settingValue.setFundC("");
		
		return settingValue;
	}

	@Override
	public CompanySettingVo initSettingVo() {
		
		/*
		 *  这个 初始化方法，仅仅包含了   五险一金 json 的字段，不够通用
		 * 
		 */
		CompanySettingVo settingVo = new CompanySettingVo();
		
		XcompanySetting setting = initXcompanySetting();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(setting, settingVo);
		
		
		settingVo.setSettingId(0L);
		settingVo.setAddTimeStr(DateUtil.getNow());
		
		return settingVo;
	}

	@Override
	public CompanySettingVo transToInstanceVo(XcompanySetting setting) {
		
		CompanySettingVo settingVo = initSettingVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(setting, settingVo);
		//默认一个 json对象
		SettingJsonSettingValue value = initJsonSettingValue();
		
		JSONObject setValue = (JSONObject) setting.getSettingValue();
		
		
		if(setValue != null){
			
			//统一使用 阿里  fastJson 处理
			value = JSON.toJavaObject(setValue, SettingJsonSettingValue.class);
			
			BeanUtilsExp.copyPropertiesIgnoreNull(value, settingVo);
			
			String cityId = value.getCityId();
			String regionId = value.getRegionId();
			
			/*
			 *  由于 json 字段 结构的不同，需要进行 判空处理
			 * 
			 */
			if(!StringUtil.isEmpty(cityId)){
				settingVo.setCityId(cityId);
				settingVo.setCityName(dictService.getCityName(Long.valueOf(cityId)));
			}
			
			if(!StringUtil.isEmpty(regionId)){
				settingVo.setRegionId(regionId);
				settingVo.setRegionName(dictService.getRegionName(Long.valueOf(regionId)));
			}
			
		}
		
		return settingVo;
	}
	
}