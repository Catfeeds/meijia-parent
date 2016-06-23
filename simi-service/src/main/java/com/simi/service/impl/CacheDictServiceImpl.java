package com.simi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.po.model.dict.HrDictType;
import com.resume.po.model.dict.HrDicts;
import com.resume.po.model.dict.HrFrom;
import com.simi.service.CacheDictService;
import com.simi.service.resume.HrDictTypeService;
import com.simi.service.resume.HrDictsService;
import com.simi.service.resume.HrFromService;
import com.simi.vo.resume.HrDictSearchVo;

@Service
public class CacheDictServiceImpl implements CacheDictService {

	@SuppressWarnings("rawtypes")
	public static Map<String, List> cahceDictMap = new HashMap<String, List>();
		
	@Autowired
	private HrDictTypeService hrDictTypeService;
	
	@Autowired
	private HrFromService hrFromService;
	
	@Autowired
	private HrDictsService hrDictService;

	/**
	 * Spring 容器初始化时加载
	 */
	@Override
	public void loadData() {
		
		//简历字段类型
		this.loadHrDictType(false);
		
		//简历来源库
		this.loadHrFrom(false);
		
		// 简历解析库的字典
		this.loadHrDictRules(false);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HrDictType> loadHrDictType(Boolean rebuild) {
		// 城市信息
		List<HrDictType> list = cahceDictMap.get("hrDictType");
		if (rebuild.equals(true) || list == null || list.isEmpty()) {
			list = hrDictTypeService.selectByAll();
			cahceDictMap.put("hrDictType", list);
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HrFrom> loadHrFrom(Boolean rebuild) {
		// 城市信息
		List<HrFrom> list = cahceDictMap.get("hrFrom");
		if (rebuild.equals(true) || list == null || list.isEmpty()) {
			list = hrFromService.selectByAll();
			cahceDictMap.put("hrFrom", list);
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HrDicts> loadHrDictRules(Boolean rebuild) {
		// 城市信息
		List<HrDicts> list = cahceDictMap.get("hrDictRules");
		if (rebuild.equals(true) || list == null || list.isEmpty()) {
			HrDictSearchVo searchVo = new HrDictSearchVo();
			searchVo.setType("parse_rule");
			list = hrDictService.selectBySearchVo(searchVo);
			cahceDictMap.put("hrDictRules", list);
		}

		return list;
	}
}
