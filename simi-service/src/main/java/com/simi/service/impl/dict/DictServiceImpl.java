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
import com.simi.service.user.UserAddrsService;
import com.simi.po.dao.dict.DictCityMapper;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictProvince;
import com.simi.po.model.dict.DictRegion;

@Service
public class DictServiceImpl implements DictService {

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
	public List<DictProvince> LoadProvinceData() {
		// 城市信息
		List<DictProvince> listProvince = memDictMap.get("listProvince");
		if (listProvince == null || listProvince.isEmpty()) {
			listProvince = provinceService.selectAll();
			memDictMap.put("listProvince", listProvince);
		}

		return listProvince;
	}
	
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

	@Override
	public List<DictCity> getCityByProvinceId(Long provinceId) {
		List<DictCity> listCity = LoadCityData();
		List<DictCity> listCityName = new ArrayList<DictCity>();
		if(0L==provinceId){
			return listCity;
		}else{
			for (Iterator iterator = listCity.iterator(); iterator.hasNext();) {
				DictCity dictCity = (DictCity) iterator.next();
				if (dictCity.getProvinceId() == provinceId) {
					listCityName.add(dictCity);
				}
			}
			return listCityName;
		}
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
}
