package com.simi.action.app.xcloud;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.record.RecordAssets;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyAssets;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.record.RecordAssetService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyAssetService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.AssetSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Controller
@RequestMapping(value = "/app/company")
public class CompanyAssetController extends BaseController {

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyAssetService xcompanyAssetService;
	
	@Autowired
	private RecordAssetService recordAssetService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private XcompanyStaffService xCompanyStaffService;
		
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;

	@RequestMapping(value = "/post_asset", method = { RequestMethod.POST })
	public AppResultData<Object> postAsset(
			@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId, 
			@RequestParam("asset_type_id") Long assetTypeId, 
			@RequestParam("name") String name,
			@RequestParam("total") Integer total,
			@RequestParam("price") BigDecimal price,
			@RequestParam(value = "unit", required = false, defaultValue = "") String unit, 
			@RequestParam(value = "place", required = false, defaultValue = "") String place,
			@RequestParam(value = "seq", required = false, defaultValue = "") String seq) 
			{

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		AppResultData<Object> v = validateApi(userId, companyId);
		
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		name = name.trim();
		name = name.toLowerCase();
		
		//查询公司资产表中是否有此资产名称
		AssetSearchVo asearchVo = new AssetSearchVo();
		asearchVo.setCompanyId(companyId);
		asearchVo.setAssetTypeId(assetTypeId);
		asearchVo.setName(name);
		
		List<XcompanyAssets> rs = xcompanyAssetService.selectBySearchVo(asearchVo);
		XcompanyAssets xcompanyAsset = xcompanyAssetService.initXcompanyAssets();
		
		if (!rs.isEmpty()) {
			xcompanyAsset = rs.get(0);
		}
		
		//存储公司资产表
		xcompanyAsset.setCompanyId(companyId);
		xcompanyAsset.setAssetTypeId(assetTypeId);
		xcompanyAsset.setName(name);
		xcompanyAsset.setPrice(price);
		xcompanyAsset.setPlace(place);
		xcompanyAsset.setSeq(seq);
		xcompanyAsset.setUnit(unit);
		
		BigDecimal btotal = new BigDecimal(total.toString());
		BigDecimal totalPrice = MathBigDecimalUtil.mul(price, btotal);
		xcompanyAsset.setTotalPrice(totalPrice);
		
		Integer stock = xcompanyAsset.getStock();
		stock = stock + total;
		xcompanyAsset.setStock(stock);
		
		if (xcompanyAsset.getAssetId() > 0L) {
			xcompanyAsset.setUpdateTime(TimeStampUtil.getNowSecond());
			xcompanyAssetService.updateByPrimaryKeySelective(xcompanyAsset);
		} else {
			xcompanyAssetService.insertSelective(xcompanyAsset);
		}
		
		Long assetId = xcompanyAsset.getAssetId();
		
		//存储公司资产登记表
		RecordAssets record = recordAssetService.initRecordAssets();

		record.setCompanyId(companyId);
		record.setAssetId(assetId);
		record.setUserId(userId);
		record.setAssetTypeId(assetTypeId);
		record.setName(name);
		record.setTotal(total);
		record.setUnit(unit);
		record.setPrice(price);
		record.setTotalPrice(totalPrice);
		record.setPlace(place);
		record.setSeq(seq);
		
		recordAssetService.insertSelective(record);
		
		return result;

	}
	
	@RequestMapping(value = "/get_asset_list", method = { RequestMethod.GET })
	public AppResultData<Object> getList(
			@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page
			)  {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		AppResultData<Object> v = validateApi(userId, companyId);
		
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		AssetSearchVo searchVo = new AssetSearchVo();
		searchVo.setUserId(userId);
		searchVo.setCompanyId(companyId);
		
		
		PageInfo pageInfo = recordAssetService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<RecordAssets> list = pageInfo.getList();
		result.setData(list);
		return result;
	}
	
	@RequestMapping(value = "/get_asset_detail", method = { RequestMethod.GET })
	public AppResultData<Object> getDetail(
			@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId,
			@RequestParam("id") Long id
			)  {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		AppResultData<Object> v = validateApi(userId, companyId);
		
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		RecordAssets record = recordAssetService.selectByPrimarykey(id);
		result.setData(record);
		
		return result;
	}
	
	
	private AppResultData<Object> validateApi(Long userId, Long companyId) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		if (companyId.equals(0L)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("团队不存在");
			return result;
		}
		
		//判断员工是否为团队一员
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xCompanyStaffService.selectBySearchVo(searchVo);
		
		if (staffList.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("数据有错误：团队与员工未绑定");
			return result;
		}
		
		return result;
	}
}
