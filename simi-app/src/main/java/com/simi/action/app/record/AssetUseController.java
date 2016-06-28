package com.simi.action.app.record;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.common.Imgs;
import com.simi.po.model.record.RecordAssetUse;
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
import com.simi.service.xcloud.XcompanyAssetService;
import com.simi.vo.AppResultData;
import com.simi.vo.AssetSearchVo;
import com.simi.vo.ImgSearchVo;
import com.simi.vo.record.RecordAssetUseVo;
import com.simi.vo.user.UserFriendSearchVo;

@Controller
@RequestMapping(value = "/app/record")
public class AssetUseController extends BaseController {

	@Autowired
	private XcompanyAssetService xcompanyAssetService;

	@Autowired
	private RecordAssetService recordAssetService;

	@Autowired
	private RecordAssetUseService recordAssetUseService;

	@Autowired
	private UsersService userService;

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

	/**
	 * 
	 * @param userId
	 * @param companyId
	 * @param assetJson
	 *            asset_id total 
	 * @param purpose
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/post_asset_use", method = { RequestMethod.POST })
	public AppResultData<Object> postAsset(
			@RequestParam("user_id") Long userId, @RequestParam("company_id") Long companyId,
			@RequestParam("asset_json") String assetJson, 
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "mobile", required = false, defaultValue = "") String mobile,
			@RequestParam(value = "to_user_id", required = false, defaultValue = "0") Long toUserId,
			@RequestParam(value = "purpose", required = false, defaultValue = "") String purpose,
			@RequestParam(value = "imgs", required = false) MultipartFile[] imgs) throws IOException {

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
			u = userService.selectByPrimaryKey(toUserId);
		} else if (!StringUtil.isEmpty(mobile)) {
			u = userService.selectByMobile(mobile);
			if (u == null) {
				u = userService.genUser(mobile, name, (short) 3, "");
			}
		}
		
		if (u != null) {
			toUserId = u.getId();
			name = StringUtil.isEmpty(name) ? u.getName() : name;
			mobile = StringUtil.isEmpty(mobile) ? u.getMobile() : mobile;
		}
		
		// 发起好友请求.
		if (toUserId > 0L && !userId.equals(toUserId)) {
			UserFriendSearchVo searchVo = new UserFriendSearchVo();
			searchVo.setUserId(userId);
			searchVo.setFriendId(toUserId);
			UserFriends userFriend = userFriendService.selectByIsFirend(searchVo);
			List<UserFriendReq> userFriendReqs = userFriendReqService.selectBySearchVo(searchVo);

			if (userFriend == null && userFriendReqs.isEmpty()) {
				UserFriendReq userFriendReq = userFriendReqService.initUserFriendReq();
				userFriendReq.setUserId(toUserId);
				userFriendReq.setFriendId(userId);
				userFriendReq.setAddTime(TimeStampUtil.getNowSecond());
				userFriendReq.setUpdateTime(TimeStampUtil.getNowSecond());
				userFriendReqService.insert(userFriendReq);
			}
		}
		

		List<HashMap> assets = GsonUtil.GsonToList(assetJson, HashMap.class);
		List<HashMap> assetExt = new ArrayList<HashMap>();

		for (HashMap item : assets) {
			if (StringUtil.isEmpty(item.get("asset_id").toString()))
				continue;
			if (StringUtil.isEmpty(item.get("total").toString()))
				continue;
			Long assetId = Long.valueOf(item.get("asset_id").toString());
			Integer total = Integer.valueOf(item.get("total").toString());

			XcompanyAssets xcompanyAsset = xcompanyAssetService.selectByPrimarykey(assetId);
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
			xcompanyAssetService.updateByPrimaryKeySelective(xcompanyAsset);
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

		// 处理图片上传的信息.
		if (imgs != null && imgs.length > 0) {
			Long linkId = record.getId();
			imgService.insertImgs(imgs, userId, linkId, "record_asset_use");
		}
		
		Long id = record.getId();
		//异步产生首页消息信息.
		String title = "资产领用";
		String summary =  name + "进行了资产领用.";
		userMsgAsyncService.newActionAppMsg(userId, id, "asset", title, summary, "");

		return result;
	}

	@RequestMapping(value = "/get_asset_use", method = { RequestMethod.GET })
	public AppResultData<Object> getList(
			@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		AppResultData<Object> v = validateService.validateIsCompanyStaff(userId, companyId);

		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		AssetSearchVo searchVo = new AssetSearchVo();
		searchVo.setUserId(userId);
		searchVo.setCompanyId(companyId);

		PageInfo pageInfo = recordAssetUseService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<RecordAssetUse> list = pageInfo.getList();
		
		List<RecordAssetUseVo> listvo = new ArrayList<RecordAssetUseVo>();
		for (RecordAssetUse item : list) {
			RecordAssetUseVo vo = new RecordAssetUseVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			Users fromUser = userService.selectByPrimaryKey(vo.getUserId());
			vo.setFromHeadImg(fromUser.getHeadImg());
			vo.setFromName(fromUser.getName());
			vo.setFromMobile(fromUser.getMobile());
			
			vo.setToHeadImg("");
			
			if (vo.getToUserId() > 0L) {
				Users toUser = userService.selectByPrimaryKey(vo.getToUserId());
				vo.setToHeadImg(toUser.getHeadImg());
			}
			
			
			ImgSearchVo isearchVo = new ImgSearchVo();
			isearchVo.setLinkId(vo.getId());
			isearchVo.setLinkType("record_asset_use");
			List<Imgs> imgList = imgService.selectBySearchVo(isearchVo);
			vo.setImgs(imgList);
			
			listvo.add(vo);
		}
		result.setData(listvo);

		return result;
	}

	@RequestMapping(value = "/get_use_detail", method = { RequestMethod.GET })
	public AppResultData<Object> getDetail(
			@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId, 
			@RequestParam("id") Long id) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		AppResultData<Object> v = validateService.validateIsCompanyStaff(userId, companyId);

		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}

		RecordAssetUse record = recordAssetUseService.selectByPrimarykey(id);
		RecordAssetUseVo vo = new RecordAssetUseVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(record, vo);
		
		Users fromUser = userService.selectByPrimaryKey(vo.getUserId());
		vo.setFromHeadImg(fromUser.getHeadImg());
		vo.setFromName(fromUser.getName());
		vo.setFromMobile(fromUser.getMobile());
		
		vo.setToHeadImg("");
		
		if (vo.getToUserId() > 0L) {
			Users toUser = userService.selectByPrimaryKey(vo.getToUserId());
			vo.setToHeadImg(toUser.getHeadImg());
		}
		
		ImgSearchVo isearchVo = new ImgSearchVo();
		isearchVo.setLinkId(vo.getId());
		isearchVo.setLinkType("record_asset");
		List<Imgs> imgList = imgService.selectBySearchVo(isearchVo);
		vo.setImgs(imgList);
		result.setData(vo);

		return result;
	}
}
