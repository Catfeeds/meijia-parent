package com.simi.service.impl.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.dict.CityService;
import com.simi.service.dict.DictService;
import com.simi.service.dict.ExpressService;
import com.simi.service.dict.ProvinceService;
import com.simi.service.dict.RegionService;
import com.simi.service.user.UserAddrsService;
import com.simi.vo.ExpressSearchVo;
import com.simi.po.dao.dict.DictCityMapper;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictExpress;
import com.simi.po.model.dict.DictProvince;
import com.simi.po.model.dict.DictRegion;

@Service
public class DictServiceImpl implements DictService {

	@SuppressWarnings("rawtypes")
	public static Map<String, List> memDictMap = new HashMap<String, List>();
	
	@Autowired
	private DictCityMapper dictCityMapper;

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private CityService cityService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private UserAddrsService userAddrsService;
	
	@Autowired
	private ExpressService expressService;

	/**
	 * Spring 容器初始化时加载
	 */
	@Override
	public void loadData() {

		// 省份信息
		this.LoadProvinceData();

		// 城市信息
		this.LoadCityData();

		// 区县信息
		this.LoadRegionData();
		
		// 快递服务商信息
		this.loadExpressData();

	}

	@Override
	public String getCityName(Long cityId) {
		String cityName = "";
		if (cityId <= 0)
			return cityName;

		List<DictCity> listCity = this.LoadCityData();

		DictCity item = null;
		for (int i = 0; i < listCity.size(); i++) {
			item = listCity.get(i);
			if (item.getCityId().equals(cityId)) {
				cityName = item.getName();
				break;
			}
		}
		return cityName;
	}
	
	@Override
	public String getRegionName(Long regionId) {
		String regionName = "";
		if (regionId <= 0)
			return regionName;

		List<DictRegion> listRegion = this.LoadRegionData();

		DictRegion item = null;
		for (int i = 0; i < listRegion.size(); i++) {
			item = listRegion.get(i);
			if (item.getRegionId().equals(regionId)) {
				regionName = item.getName();
				break;
			}
		}
		return regionName;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DictProvince> LoadProvinceData() {
		// 城市信息
		List<DictProvince> listProvince = memDictMap.get("listProvince");
		if (listProvince == null || listProvince.isEmpty()) {
			listProvince = provinceService.selectAll();
			memDictMap.put("listProvince", listProvince);
		}

		return listProvince;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DictCity> LoadCityData() {
		// 城市信息
		List<DictCity> listCity = memDictMap.get("listCity");
		if (listCity == null || listCity.isEmpty()) {
			listCity = cityService.selectAll();
			memDictMap.put("listCity", listCity);
		}

		return listCity;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DictCity> getCityByProvinceId(Long provinceId) {
		List<DictCity> listCity = LoadCityData();
		List<DictCity> listCityName = new ArrayList<DictCity>();
		if(0L==provinceId){
			return listCity;
		}else{
			for (Iterator iterator = listCity.iterator(); iterator.hasNext();) {
				DictCity dictCity = (DictCity) iterator.next();
				if (dictCity.getProvinceId().equals(provinceId)) {
					listCityName.add(dictCity);
				}
			}
			return listCityName;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DictRegion> getRegionByCityId(Long cityId){
		List<DictRegion> listRegion = LoadRegionData();
		List<DictRegion> listRegionName = new ArrayList<DictRegion>();
		if(0L==cityId){
			return listRegion;
		}else{
			for (DictRegion dictRegion : listRegion) {
				/*
				 *  对于基本数据类型的 包装类的 相等性 比较
				 * 
				 *  由于是  '对象实例'的比较
				 * 
				 * 	Long中有个小小的陷阱，就是在-128至127范围内，
				 * 	Long.valueOf(long l)返回的Long的实例是相同的，
				 * 	而在此范围之外每次使用valueOf(long l)时，返回的实例都是不同的
				 * 
				 *  ==> 从而 解释了  
				 *  
				 *  	在 写成  dictRegion.getCityId() == cityId
				 *  
				 *  	北京 cityId = 2, 可以获得正确数据
				 * 		广州 cityId = 198, 不能进入 if		
				 */
				
				//这里 cityId, 根据业务场景,只涉及 实例 值的 比较。。用equals即可 
				if(dictRegion.getCityId().equals(cityId)){
					listRegionName.add(dictRegion);
				}
			}
			
			return listRegionName;
		}
	}

	@SuppressWarnings("unchecked")
	private List<DictRegion> LoadRegionData() {
		// 城市信息
		List<DictRegion> listRegion = memDictMap.get("listRegion");
		if (listRegion == null || listRegion.isEmpty()) {
			listRegion = regionService.selectAll();
			memDictMap.put("listRegion", listRegion);
		}

		return listRegion;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DictExpress> loadExpressData() {
		// 城市信息
		List<DictExpress> listExpress = memDictMap.get("listExpress");
		if (listExpress == null || listExpress.isEmpty()) {
			ExpressSearchVo searchVo = new ExpressSearchVo();
			listExpress = expressService.selectBySearchVo(searchVo);
			memDictMap.put("listExpress", listExpress);
		}

		return listExpress;
	}
}
