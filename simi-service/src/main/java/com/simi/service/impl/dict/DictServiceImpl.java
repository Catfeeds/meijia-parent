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
import com.simi.service.dict.ProvinceService;
import com.simi.service.dict.RegionService;
import com.simi.service.dict.ServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.po.dao.dict.DictCityMapper;
import com.simi.po.dao.dict.DictServiceTypesMapper;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictProvince;
import com.simi.po.model.dict.DictRegion;
import com.simi.po.model.dict.DictServiceTypes;

@Service
public class DictServiceImpl implements DictService {

	public static Map<String, List> memDictMap = new HashMap<String, List>();

	@Autowired
	private DictServiceTypesMapper dictServiceTypesMapper;
	
	@Autowired
	private DictCityMapper dictCityMapper;

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private CityService cityService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private ServiceTypeService serviceTypeService;

	@Autowired
	private UserAddrsService userAddrsService;

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

		// 服务类型
		this.LoadServiceTypeData();

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
	public String getServiceTypeName(Long serviceTypeId) {
		String serviceTypeName = "";
		if (serviceTypeId <= 0)
			return serviceTypeName;

		List<DictServiceTypes> listServiceTypes = this.LoadServiceTypeData();

		DictServiceTypes item = null;
		for (int i = 0; i < listServiceTypes.size(); i++) {
			item = listServiceTypes.get(i);
			if (item.getId().equals(serviceTypeId)) {
				serviceTypeName = item.getName();
				break;
			}
		}
		return serviceTypeName;
	}

	private List<DictProvince> LoadProvinceData() {
		// 城市信息
		List<DictProvince> listProvince = memDictMap.get("listProvince");
		if (listProvince == null || listProvince.isEmpty()) {
			listProvince = provinceService.selectAll();
			memDictMap.put("listProvince", listProvince);
		}

		return listProvince;
	}

	private List<DictCity> LoadCityData() {
		// 城市信息
		List<DictCity> listCity = memDictMap.get("listCity");
		if (listCity == null || listCity.isEmpty()) {
			listCity = cityService.selectAll();
			memDictMap.put("listCity", listCity);
		}

		return listCity;
	}

	@Override
	public List<DictRegion> getRegionByCityId(Long cityId){
		List<DictRegion> listRegion = LoadRegionData();
		List<DictRegion> listRegionName = new ArrayList<DictRegion>();
		if(0L==cityId){
			return listRegion;
		}else{
			for (Iterator iterator = listRegion.iterator(); iterator.hasNext();) {
				DictRegion dictRegion = (DictRegion) iterator.next();
				if (dictRegion.getCityId()==cityId) {
					listRegionName.add(dictRegion);
				}
			}
			return listRegionName;
		}
	}

	private List<DictRegion> LoadRegionData() {
		// 城市信息
		List<DictRegion> listRegion = memDictMap.get("listRegion");
		if (listRegion == null || listRegion.isEmpty()) {
			listRegion = regionService.selectAll();
			memDictMap.put("listRegion", listRegion);
		}

		return listRegion;
	}

	private List<DictServiceTypes> LoadServiceTypeData() {
		// 服务类型
		List<DictServiceTypes> listServiceTypes = memDictMap
				.get("listServiceTypes");
		if (listServiceTypes == null || listServiceTypes.isEmpty()) {
			listServiceTypes = serviceTypeService.getServiceTypes();
			memDictMap.put("listServiceTypes", listServiceTypes);
		}
		return listServiceTypes;
	}



}
