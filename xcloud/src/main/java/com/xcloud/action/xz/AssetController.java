package com.xcloud.action.xz;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.record.RecordAssetUse;
import com.simi.po.model.record.RecordAssets;
import com.simi.po.model.user.UserFriendReq;
import com.simi.po.model.user.UserFriends;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyAssets;
import com.simi.service.ImgService;
import com.simi.service.ValidateService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.record.RecordAssetService;
import com.simi.service.record.RecordAssetUseService;
import com.simi.service.user.UserFriendReqService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyAssetService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.AssetSearchVo;
import com.simi.vo.partners.PartnerUserSearchVo;
import com.simi.vo.record.RecordAssetUseVo;
import com.simi.vo.record.RecordAssetVo;
import com.simi.vo.user.UserFriendSearchVo;
import com.simi.vo.xcloud.XcompanyAssetVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.common.Constant;

/**
 *
 * @author :hulj
 * @Date : 2016年4月21日下午5:46:10
 * @Description:
 *		
 *		行政--资产管理		
 */
@Controller
@RequestMapping(value = "/xz/assets/")
public class AssetController extends BaseController {
	
	@Autowired
	private RecordAssetService recordAssetService;
	
	@Autowired
	private XcompanyAssetService companAssetService;
	
	@Autowired
	private RecordAssetUseService recordAssetUseService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyStaffService xCompanyStaffService;

	@Autowired
	private UserMsgAsyncService userMsgAsyncService;

	@Autowired
	private ImgService imgService;
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private UserFriendService userFriendService;

	@Autowired
	private UserFriendReqService userFriendReqService;

	
	
	/* ********************************
	 * 			领用与借用模块
	 * ********************************
	 * 领用与借用列表
	 */
	@RequestMapping(value = "use_asset_list",method = RequestMethod.GET)
	public String usedList(HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		
		int pageNo = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		
		PageHelper.startPage(pageNo, pageSize);
		

		// 1. 获取登录的用户/公司 id
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long userId = accountAuth.getUserId();
		Long companyId = accountAuth.getCompanyId();
		
		model.addAttribute("companyId",companyId);
		model.addAttribute("userId", userId);
		
		
		AssetSearchVo searchVo = new AssetSearchVo();
		
		searchVo.setUserId(userId);
		searchVo.setCompanyId(companyId);
		
		List<RecordAssetUse> list = recordAssetUseService.selectBySearchVo(searchVo);
		
		RecordAssetUse assetUse = null;
		
		for (int i = 0; i < list.size(); i++) {
			
			assetUse = list.get(i);
			RecordAssetUseVo useVo = recordAssetUseService.getUserAssetVo(assetUse);
			list.set(i, useVo);
		}

		PageInfo result = new PageInfo(list);
		
		model.addAttribute("searchVoModel", searchVo);
		model.addAttribute("contentModel", result);
		
		return "xz/use-asset-list";
	}
	
	
	/**
	 * @throws UnsupportedEncodingException 
	 * 
	  * @Title: gotoUseAssetForm
	  *
	  *	@Date : 2016年4月26日下午7:37:04
	  * @Description: 
	  * 	 		公司资产领用详情接口
	  * @throws
	 */
	@RequestMapping(value = "use_asset_form",method = RequestMethod.GET)
	public String gotoUseAssetForm(HttpServletRequest request,Model model,
			@RequestParam("id")Long id) throws UnsupportedEncodingException{
		
		// 1. 获取登录的用户/公司 id
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long userId = accountAuth.getUserId();
		Long companyId = accountAuth.getCompanyId();
		
		model.addAttribute("companyId",companyId);
		model.addAttribute("userId", userId);
		
		// 2. 构造VO
		RecordAssetUse assetUse = recordAssetUseService.initRecordAssetUse();
		
		RecordAssetUseVo assetVo  = new RecordAssetUseVo();
		
		if(id != 0L){
			assetUse = recordAssetUseService.selectByPrimarykey(id);
			assetVo = recordAssetUseService.getUserAssetVo(assetUse);
		}
		
		// 3. 资产选择 list
		AssetSearchVo searchVo = new AssetSearchVo();
		List<XcompanyAssets> list = companAssetService.selectBySearchVo(searchVo);
		
		assetVo.setCompanList(list);
		
		model.addAttribute("contentModel", assetVo);
		
		return "xz/use-asset-form";
	}
	
	/**
	 * 
	  * @Title: postUseAsset
	  *
	  *	@Date : 2016年4月27日下午3:34:56
	  * @Description:	
	  * 		公司资产领用记录接口
	  * @param userId			当前登录的userId，也是经办人
	  * @param companyId		公司ID
	  * 	
	  * @param assetJson		领用资产内容，数组后json串, 每一个元素格式如下
								asset_id
								total
								
	  * @param name				领用人
	  * @param mobile			领用人手机号
	  * @param toUserId			领用人用户ID
	  * @param purpose			用途
	  * @param file				上传图片信息，支持多个		TODO 
	  * @return    设定文件
	  * @return AppResultData<Object>    返回类型
	  * @throws
	 */
	@RequestMapping(value = "post_asset_use.json",method = RequestMethod.POST)
	public AppResultData<Object> postUseAsset(
			@RequestParam("user_id")Long userId,
			@RequestParam("company_id")Long companyId,
			@RequestParam("asset_json")String assetJson,
			@RequestParam(value="name",required=false,defaultValue="")String name,
			@RequestParam(value="mobile",required=false,defaultValue="")String mobile,
			@RequestParam(value="to_user_id",required=false,defaultValue="0")Long toUserId,
			@RequestParam(value="purpose",required=false,defaultValue="")String purpose){
//			@RequestParam(value="imgs",required=false,defaultValue="")MultipartFile[] file){
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		AppResultData<Object> v = validateService.validateIsCompanyStaff(userId, companyId);

		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		if (StringUtil.isEmpty(name) && StringUtil.isEmpty(mobile) && toUserId.equals(0L)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("领用人为空.");
			return result;
		}

		// 会员信息, 如果有手机号，则默认注册会员.
		Users u = null;
		
		if (toUserId > 0L) {
			u = usersService.selectByPrimaryKey(toUserId);
		} else if (!StringUtil.isEmpty(mobile)) {
			u = usersService.selectByMobile(mobile);
			if (u == null) {
				u = usersService.genUser(mobile, name, (short) 3, "");
			}
		}
		
		if (u != null) {
			toUserId = u.getId();
			name = StringUtil.isEmpty(name) ? u.getName() : name;
			mobile = StringUtil.isEmpty(mobile) ? u.getMobile() : mobile;
		}
		
		// 发起好友请求.
//		if (toUserId > 0L && !userId.equals(toUserId)) {
//			UserFriendSearchVo searchVo = new UserFriendSearchVo();
//			searchVo.setUserId(userId);
//			searchVo.setFriendId(toUserId);
//			UserFriends userFriend = userFriendService.selectByIsFirend(searchVo);
//			UserFriendReq userFriendReq = userFriendReqService.selectByIsFirend(searchVo);
//
//			if (userFriend == null && userFriendReq == null) {
//				userFriendReq = userFriendReqService.initUserFriendReq();
//				userFriendReq.setUserId(toUserId);
//				userFriendReq.setFriendId(userId);
//				userFriendReq.setAddTime(TimeStampUtil.getNowSecond());
//				userFriendReq.setUpdateTime(TimeStampUtil.getNowSecond());
//				userFriendReqService.insert(userFriendReq);
//			}
//		}
		

		List<HashMap> assets = GsonUtil.GsonToList(assetJson, HashMap.class);
		List<HashMap> assetExt = new ArrayList<HashMap>();

		for (HashMap item : assets) {
			if (StringUtil.isEmpty(item.get("asset_id").toString()))
				continue;
			if (StringUtil.isEmpty(item.get("total").toString()))
				continue;
			Long assetId = Long.valueOf(item.get("asset_id").toString());
			Integer total = Integer.valueOf(item.get("total").toString());

			XcompanyAssets xcompanyAsset = companAssetService.selectByPrimarykey(assetId);
			Integer stock = xcompanyAsset.getStock();

			if (stock < total) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(xcompanyAsset.getName() + "库存不足");
				return result;
			}
			
			BigDecimal price = xcompanyAsset.getPrice();
			BigDecimal totalPrice = MathBigDecimalUtil.mul(price, new BigDecimal(total));
			item.put("name", xcompanyAsset.getName());
			item.put("asset_type_id", xcompanyAsset.getAssetTypeId());
			item.put("price", MathBigDecimalUtil.round2(price));
			item.put("total_price", MathBigDecimalUtil.round2(totalPrice));
			assetExt.add(item);
			
			//减库存
			stock = stock - total;
			xcompanyAsset.setStock(stock);
			companAssetService.updateByPrimaryKeySelective(xcompanyAsset);
		}

		String assetExtJson = GsonUtil.GsonString(assetExt);

		RecordAssetUse record = recordAssetUseService.initRecordAssetUse();
		record.setCompanyId(companyId);
		record.setAssetJson(assetExtJson);
		record.setUserId(userId);
		record.setToUserId(toUserId);
		record.setName(name);
		record.setMobile(mobile);
		record.setPurpose(purpose);
		recordAssetUseService.insertSelective(record);

//		// 处理图片上传的信息.
//		if (imgs != null && imgs.length > 0) {
//			Long linkId = record.getId();
//			imgService.insertImgs(imgs, userId, linkId, "record_asset_use");
//		}
//		
//		Long id = record.getId();
//		//异步产生首页消息信息.
//		String title = "资产领用";
//		String summary =  name + "进行了资产领用.";
//		userMsgAsyncService.newActionAppMsg(userId, id, "asset", title, summary, "");

		return result;
		
	}
	
	
	
	
	/* ********************************************
	 * 				  库存管理 模块
	 * ********************************************
	 * 库存管理列表
	 */
	@RequestMapping(value = "commpany_asset_list",method = RequestMethod.GET)
	public String xcompanyAssetList(HttpServletRequest request, Model model,
			AssetSearchVo searchVo){
		
		int pageNo = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		
		PageInfo info = companAssetService.selectByListPage(searchVo, pageNo, pageSize);
		
		model.addAttribute("contentModel", info);
		model.addAttribute("searchVoModel", searchVo);
		
		return "xz/commpany-asset-list";
	}
	
	
	
	
	/**
	 * 
	 * @Title: goToCompanyAssetForm
	  *
	  *	@Date : 2016年4月25日下午5:47:56
	  * @Description: 跳转到 公司资产登记 接口 form 页
	  * 
	  * @param id		对应 xcompany_assets表的主键。 在record_assets表中 就是 assetId 字段	
	  * @param name		对应 name
	  * @throws
	 */
	@RequestMapping(value = "commpany_asset_form", method = RequestMethod.GET)
	public String goToCompanyAssetForm(Model model,HttpServletRequest request,
			@RequestParam("id")Long id,
			@RequestParam("name")String name){
		
		// 1. 获取登录的用户/公司 id
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long userId = accountAuth.getUserId();
		
		Long companyId = accountAuth.getCompanyId();
		
		model.addAttribute("companyId",companyId);
		model.addAttribute("userId", userId);
		
		/*
		 * 2. form 页 vo 字段
		 */
		AssetSearchVo searchVo = new AssetSearchVo();
		
		//2-1. 下拉列表。选择公司资产
		List<XcompanyAssets> list = companAssetService.selectBySearchVo(searchVo);
		//2-2. 图片 url
		
		
		//2-3. po属性 
		RecordAssets assets = recordAssetService.initRecordAssets();
		
		if(id != 0L){
			
			searchVo.setCompanyId(companyId);
			searchVo.setAssetId(id);
			searchVo.setName(name);
			
			List<RecordAssets> list2 = recordAssetService.selectBySearchVo(searchVo);
			
			if (!list2.isEmpty()) {
				assets = list2.get(0);
			}
			
		}
		
		//3. 构造 vo
		RecordAssetVo assetVo = new RecordAssetVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(assets, assetVo);
		
		assetVo.setxCompAssetList(list);
		
		model.addAttribute("contentModel", assetVo);
		
		return "xz/commpany-asset-form";
	}
	
	
	/**
	 * 
	  * @Title: postAsset
	  *
	  *	@Date : 2016年4月26日下午3:54:28
	  * @Description: 
	  * 		公司资产登记接口
	  * @param userId		当前登录的userId
	  * @param companyId	公司ID
	  * @param assetTypeId	资产类别ID	对于第一条记录，该字段不是必须		
	  * @param name			资产名称
	  * @param total		数量
	  * @param price		单价
	  * @param barcode		条形码				TODO
	  * @param unit			单位/规格
	  * @param place		存放位置
	  * @param seq			编号
	  * @param imgs			上传图片信息，支持多个	TODO
	  * @throws
	 */
	@RequestMapping(value = "company_post_asset.json", method = RequestMethod.POST)
	public AppResultData<Object> postAsset(
			@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId,
			@RequestParam(value = "asset_type_id",required=false,defaultValue = "0") Long assetTypeId, 
			@RequestParam("name") String name, 
			@RequestParam("total") Integer total,
			@RequestParam("price") BigDecimal price, 
			@RequestParam(value = "barcode", required = false, defaultValue = "") String barcode,
			@RequestParam(value = "unit", required = false, defaultValue = "") String unit,
			@RequestParam(value = "place", required = false, defaultValue = "") String place,
			@RequestParam(value = "seq", required = false, defaultValue = "") String seq){ 
//			@RequestParam(value = "imgs", required = false) MultipartFile[] imgs){
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		AppResultData<Object> v = validateService.validateIsCompanyStaff(userId, companyId);

		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		name = name.trim();
		name = name.toLowerCase();

		// 查询公司资产表中是否有此资产名称
		AssetSearchVo asearchVo = new AssetSearchVo();
		asearchVo.setCompanyId(companyId);
		asearchVo.setAssetTypeId(assetTypeId);
		asearchVo.setName(name);

		List<XcompanyAssets> rs = companAssetService.selectBySearchVo(asearchVo);
		XcompanyAssets xcompanyAsset = companAssetService.initXcompanyAssets();

		if (!rs.isEmpty()) {
			xcompanyAsset = rs.get(0);
		}

		// 存储公司资产表
		xcompanyAsset.setCompanyId(companyId);
		xcompanyAsset.setAssetTypeId(assetTypeId);
		xcompanyAsset.setBarcode(barcode);
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
			companAssetService.updateByPrimaryKeySelective(xcompanyAsset);
		} else {
			companAssetService.insertSelective(xcompanyAsset);
		}

		Long assetId = xcompanyAsset.getAssetId();

		// 存储公司资产登记表
		RecordAssets record = recordAssetService.initRecordAssets();

		record.setCompanyId(companyId);
		record.setAssetId(assetId);
		record.setUserId(userId);
		record.setAssetTypeId(assetTypeId);
		record.setBarcode(barcode);
		record.setName(name);
		record.setTotal(total);
		record.setUnit(unit);
		record.setPrice(price);
		record.setTotalPrice(totalPrice);
		record.setPlace(place);
		record.setSeq(seq);

		recordAssetService.insertSelective(record);
		
		// 处理图片上传的信息.
//		if (imgs != null && imgs.length > 0) {
//			Long linkId = record.getId();
//			imgService.insertImgs(imgs, userId, linkId, "record_asset");
//		}
//		Long id = record.getId();
//
//		//异步产生首页消息信息.
//		String title = "资产登记";
//		String summary =  "你生成了新的资产登记";
//		userMsgAsyncService.newActionAppMsg(userId, id, "asset", title, summary, "");

		return result;
		
	}
	
}
