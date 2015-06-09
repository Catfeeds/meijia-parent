package com.simi.action.app.dict;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simi.vo.AppResultData;
import com.simi.vo.dict.ServiceTypeVo;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictAd;
import com.simi.service.dict.AdService;
import com.simi.service.dict.ServiceTypeService;

@Controller
@RequestMapping(value="/app/dict")
public class BaseDataController<T> {

	@Autowired
	private ServiceTypeService serviceTypeService;

	@Autowired
	private AdService adService;

    @SuppressWarnings({"unchecked", "rawtypes" })
	@RequestMapping(value = "get_base_datas", method = RequestMethod.GET)
    public AppResultData<HashMap<String, Object>> getBaseDatas() {

    	//获得广告配置定义列表项
    	List<DictAd> listAd = adService.getAdByServiceType(0L);

    	//获得服务类型配置定义列表项
    	List<ServiceTypeVo> listServiceType = serviceTypeService.getServiceTypeVos();

    	//组装成基础数据返回格式
    	HashMap<String, Object> resultDatas = new HashMap<String, Object>();

    	resultDatas.put("banner_ad", listAd);
    	resultDatas.put("service_call", Constants.SERVICE_CALL);
    	resultDatas.put("guanjia_call", Constants.GUANJIA_CALL);

    	//组装服务类型的接口
    	HashMap<String, Object> serviceTypes = new HashMap<String, Object>();
    	for (int i =0 ; i < listServiceType.size(); i++) {
    		ServiceTypeVo vo = listServiceType.get(i);
    		serviceTypes.put(vo.getKeyword(), vo);
    	}

    	resultDatas.put("service_types", serviceTypes);

    	AppResultData<HashMap<String, Object>> result = null;
    	result = new AppResultData<HashMap<String, Object>>(0, "ok", resultDatas);

    	return result;
    }

}
