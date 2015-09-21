package com.simi.service.dict;

import java.util.List;

import com.simi.service.impl.dict.DictServiceImpl;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictProvince;
import com.simi.po.model.dict.DictRegion;

public class DictUtil {
	
	/**
	 * 获取内存省份数据
	 * @return
	 */
	public static List<DictProvince> getProvinces() {
		List<DictProvince> listProvince = DictServiceImpl.memDictMap.get("listProvince");
		return listProvince;
	}
	
	/**
	 * 获取内存城市数据
	 * @return
	 */
	public static List<DictCity> getCitys() {
		List<DictCity> listCity = DictServiceImpl.memDictMap.get("listCity");
		return listCity;
	}
	
	/**
	 * 获取内存区县数据
	 * @return
	 */
	public static List<DictRegion> getRegions() {
		List<DictRegion> listRegion = DictServiceImpl.memDictMap.get("listRegion");
		return listRegion;
	}
	
	/**
	 * 通过省份id获取省份名称
	 * @return
	 */
	public static String getProvinceName(Long provinceId) {

		String provinceName = "";
		if (provinceId <=0) return provinceName;

		List<DictProvince> listProvince = DictServiceImpl.memDictMap.get("listProvince");

		DictProvince item = null;
		for (int i = 0 ; i < listProvince.size(); i++) {
			item = listProvince.get(i);
			if (item.getProvinceId().equals(provinceId)) {
				provinceName = item.getName();
				break;
			}
		}
		return provinceName;
	}	
	
	/**
	 * 通过城市id获取城市名称
	 * @return
	 */
	public static String getCityName(Long cityId) {

		String cityName = "";
		if (cityId <=0) return cityName;

		List<DictCity> listCity = DictServiceImpl.memDictMap.get("listCity");

		DictCity item = null;
		for (int i = 0 ; i < listCity.size(); i++) {
			item = listCity.get(i);
			if (item.getCityId().equals(cityId)) {
				cityName = item.getName();
				break;
			}
		}
		return cityName;
	}
	
	/**
	 * 通过区县id获取区县名称
	 * @return
	 */
	public static String getRegionName(Long regionId) {

		String regionName = "";
		if (regionId <=0) return regionName;

		List<DictRegion> listRegion = DictServiceImpl.memDictMap.get("listRegion");

		DictRegion item = null;
		for (int i = 0 ; i < listRegion.size(); i++) {
			item = listRegion.get(i);
			if (item.getRegionId().equals(regionId)) {
				regionName = item.getName();
				break;
			}
		}
		return regionName;
	}

//	public static String getServiceTypeName(Long serviceTypeId) {
//		String serviceTypeName = "";
//		if (serviceTypeId <=0) return serviceTypeName;
//
//		List<DictServiceTypes>  listServiceTypes = DictServiceImpl.memDictMap.get("listServiceTypes");
//
//		DictServiceTypes item = null;
//		for (int i = 0 ; i < listServiceTypes.size(); i++) {
//			item = listServiceTypes.get(i);
//			if (item.getId().equals(serviceTypeId)) {
//				serviceTypeName = item.getName();
//				break;
//			}
//		}
//		return serviceTypeName;
//	}
}
