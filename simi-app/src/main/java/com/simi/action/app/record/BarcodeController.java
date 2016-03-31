package com.simi.action.app.record;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BarcodeUtil;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.common.Imgs;
import com.simi.po.model.record.RecordAssets;
import com.simi.po.model.record.RecordBarcode;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.TotalCompany;
import com.simi.po.model.xcloud.XcompanyAssets;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.ImgService;
import com.simi.service.ValidateService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.record.RecordAssetService;
import com.simi.service.record.RecordBarcodeService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.TotalCompanyService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyAssetService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.AssetSearchVo;
import com.simi.vo.ImgSearchVo;
import com.simi.vo.record.RecordAssetVo;
import com.simi.vo.xcloud.CompanySearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Controller
@RequestMapping(value = "/app/record")
public class BarcodeController extends BaseController {

	@Autowired
	private ValidateService validateService;

	@Autowired
	private RecordBarcodeService recordBarcodeService;
	
	@Autowired
	private TotalCompanyService totalCompanyService;

	//条形码详细信息
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/barcode", method = { RequestMethod.GET })
	public AppResultData<Object> getAssetList(
			@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId,
			@RequestParam("barcode") String barcode
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		AppResultData<Object> v = validateService.validateIsCompanyStaff(userId, companyId);

		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		Map<String, String> data = new HashMap<String, String>();
		//查询barcode 是否有记录
		RecordBarcode record = recordBarcodeService.selectByBarcode(barcode);
		
		if (record != null) {
			data.put("barcode", barcode);
			data.put("name", record.getName());
			data.put("unit", record.getSpec());
			data.put("price", record.getPrice());
			result.setData(data);
			return result;
		}
		
		//查询当前公司是否超过每日访问50次数.

		CompanySearchVo searchVo = new CompanySearchVo();
		searchVo.setCompanyId(companyId);
		
		Long startTime = TimeStampUtil.getBeginOfToday();
		Long endTime = TimeStampUtil.getEndOfToday();
		
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		
		List<TotalCompany>  list = totalCompanyService.selectBySearchVo(searchVo);
		TotalCompany totalCompany = null;
		if (!list.isEmpty()) {
			totalCompany = list.get(0);
			if (totalCompany.getTotalBarcode() > 50) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("超过每日访问上限");
				return result;
			}
		}
		
		//访问条形码接口信息.
		Map<String, String> barcodeData = BarcodeUtil.baiduBarCode(barcode);
		record = recordBarcodeService.initRecordBarcode();

		record.setBarcode(barcode);
		record.setName(barcodeData.get("name").toString());
		record.setPrice(String.valueOf(barcodeData.get("price")));
		record.setSpec(barcodeData.get("spec").toString());
		record.setBrand(barcodeData.get("brand").toString());
		record.setCountry(barcodeData.get("country").toString());
		record.setCompany(barcodeData.get("company").toString());
		record.setPrefix(barcodeData.get("prefix").toString());
		record.setAddr(barcodeData.get("addr").toString());
		record.setRes(barcodeData.get("res").toString());
		recordBarcodeService.insertSelective(record);
		
		if (totalCompany == null) totalCompany = totalCompanyService.initTotalCompany();
		Integer totalBarcode = totalCompany.getTotalBarcode();
		totalBarcode = totalBarcode + 1;
		totalCompany.setCompanyId(companyId);
		totalCompany.setTotalBarcode(totalBarcode);
		
		if (totalCompany.getId() > 0L) {
			totalCompany.setUpdateTime(TimeStampUtil.getNowSecond());
			totalCompanyService.updateByPrimaryKeySelective(totalCompany);
		} else {
			totalCompanyService.insertSelective(totalCompany);
		}
		
		data.put("barcode", barcode);
		data.put("name", record.getName());
		data.put("unit", record.getSpec());
		data.put("price", record.getPrice());
		result.setData(data);
		
		return result;
	}
}
