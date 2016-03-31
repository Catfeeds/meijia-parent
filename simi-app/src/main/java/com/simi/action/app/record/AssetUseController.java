package com.simi.action.app.record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.GsonUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.common.Imgs;
import com.simi.po.model.record.RecordAssets;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.ImgService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.record.RecordAssetService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyAssetService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.AssetSearchVo;
import com.simi.vo.ImgSearchVo;
import com.simi.vo.record.RecordAssetVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

@Controller
@RequestMapping(value = "/app/record")
public class AssetUseController extends BaseController {

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

	@Autowired
	private ImgService imgService;
	
	/**
	 * 
	 * @param userId
	 * @param companyId
	 * @param assetJson  
	 *          asset_id
	 *          total
	 * @param purpose
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/post_asset_use", method = { RequestMethod.POST })
	public AppResultData<Object> postAsset(
			@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId,
			@RequestParam("asset_json") String assetJson,
			@RequestParam(value = "purpose", required = false, defaultValue = "") String purpose
			) throws JsonParseException, JsonMappingException, IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		AppResultData<Object> v = validateApi(userId, companyId);

		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		
		List<Map> assets = GsonUtil.GsonToList(assetJson, Map.class);
		List<Map> assetExt = new ArrayList<Map>();
		for (Map item : assets) {
//			Long assetId =
		}
		
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/get_asset_use", method = { RequestMethod.GET })
	public AppResultData<Object> getList(
			@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

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
		
		List<RecordAssetVo> listvo = new ArrayList<RecordAssetVo>();
		for (RecordAssets item : list) {
			RecordAssetVo vo = new RecordAssetVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			ImgSearchVo isearchVo = new ImgSearchVo();
			isearchVo.setLinkId(vo.getId());
			isearchVo.setLinkType("record_asset");
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

		AppResultData<Object> v = validateApi(userId, companyId);

		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}

		RecordAssets record = recordAssetService.selectByPrimarykey(id);
		RecordAssetVo vo = new RecordAssetVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(record, vo);
		
		ImgSearchVo isearchVo = new ImgSearchVo();
		isearchVo.setLinkId(vo.getId());
		isearchVo.setLinkType("record_asset");
		List<Imgs> imgList = imgService.selectBySearchVo(isearchVo);
		vo.setImgs(imgList);
		result.setData(vo);

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

		// 判断员工是否为团队一员
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
