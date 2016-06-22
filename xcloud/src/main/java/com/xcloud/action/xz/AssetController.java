package com.xcloud.action.xz;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.StringUtil;
import com.simi.po.model.op.AppTools;
import com.simi.po.model.record.RecordAssetUse;
import com.simi.po.model.record.RecordAssets;
import com.simi.po.model.xcloud.XcompanyAssets;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.ImgService;
import com.simi.service.ValidateService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.op.AppToolsService;
import com.simi.service.record.RecordAssetService;
import com.simi.service.record.RecordAssetUseService;
import com.simi.service.user.UserFriendReqService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyAssetService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.ApptoolsSearchVo;
import com.simi.vo.AssetSearchVo;
import com.simi.vo.record.RecordAssetUseVo;
import com.simi.vo.record.RecordAssetVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

/**
 *
 * @author :hulj
 * @Date : 2016年4月21日下午5:46:10
 * @Description:
 *
 *               行政--资产管理
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

	@Autowired
	private XCompanySettingService settingService;

	@Autowired
	private AppToolsService appToolsService;

	/* ********************************
	 * 领用与借用模块 ******************************** 领用与借用列表
	 */
	@AuthPassport
	@RequestMapping(value = "use_asset_list", method = RequestMethod.GET)
	public String usedList(HttpServletRequest request, Model model) throws UnsupportedEncodingException {

		int pageNo = ServletRequestUtils.getIntParameter(request, Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);

		PageHelper.startPage(pageNo, pageSize);

		// 1. 获取登录的用户/公司 id
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();
		Long companyId = accountAuth.getCompanyId();

		model.addAttribute("companyId", companyId);
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
	 * @Date : 2016年4月26日下午7:37:04
	 * @Description: 公司资产领用详情接口
	 * @throws
	 */
	@AuthPassport
	@RequestMapping(value = "use_asset_form", method = RequestMethod.GET)
	public String gotoUseAssetForm(HttpServletRequest request, Model model, @RequestParam("id") Long id) throws UnsupportedEncodingException {

		// 1. 获取登录的用户/公司 id
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();
		Long companyId = accountAuth.getCompanyId();

		model.addAttribute("companyId", companyId);
		model.addAttribute("userId", userId);

		// 2. 构造VO
		RecordAssetUse assetUse = recordAssetUseService.initRecordAssetUse();

		RecordAssetUseVo assetVo = new RecordAssetUseVo();

		if (id != 0L) {
			assetUse = recordAssetUseService.selectByPrimarykey(id);
			assetVo = recordAssetUseService.getUserAssetVo(assetUse);
		}

		// 3. 资产选择 list
		AssetSearchVo searchVo = new AssetSearchVo();
		List<XcompanyAssets> list = companAssetService.selectBySearchVo(searchVo);

		assetVo.setCompanList(list);

		model.addAttribute("contentModel", assetVo);

		// 二维码
		String qrCode = "";
		ApptoolsSearchVo searchVo1 = new ApptoolsSearchVo();
		searchVo1.setAction("assets");
		List<AppTools> apptools = appToolsService.selectBySearchVo(searchVo1);
		if (!apptools.isEmpty()) {
			AppTools a = apptools.get(0);
			qrCode = a.getQrCode();
		}

		if (StringUtil.isEmpty(qrCode))
			qrCode = "/assets/img/erweima.png";
		model.addAttribute("qrCode", qrCode);

		return "xz/use-asset-form";
	}

	/* ********************************************
	 * 库存管理 模块 ******************************************** 库存管理列表
	 */
	@AuthPassport
	@RequestMapping(value = "commpany_asset_list", method = RequestMethod.GET)
	public String xcompanyAssetList(HttpServletRequest request, Model model, AssetSearchVo searchVo) {

		int pageNo = ServletRequestUtils.getIntParameter(request, Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);

		// PageInfo info = recordAssetUseService.selectByListPage(searchVo,
		// pageNo, pageSize);
		
		// 1. 获取登录的用户/公司 id
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();
		Long companyId = accountAuth.getCompanyId();
		
		searchVo.setCompanyId(companyId);
		
		PageInfo info = recordAssetService.selectByListPage(searchVo, pageNo, pageSize);

		model.addAttribute("contentModel", info);
		model.addAttribute("searchVoModel", searchVo);

		CompanySettingSearchVo s = new CompanySettingSearchVo();

		s.setSettingType("asset_type");

		List<XcompanySetting> assetTypeList = settingService.selectBySearchVo(s);

		model.addAttribute("assetTypes", assetTypeList);

		return "xz/commpany-asset-list";
	}

	/**
	 * 
	 * @Title: goToCompanyAssetForm
	 *
	 * @Date : 2016年4月25日下午5:47:56
	 * @Description: 跳转到 公司资产登记 接口 form 页
	 * 
	 * @param id
	 *            对应 xcompany_assets表的主键。 在record_assets表中 就是 assetId 字段
	 * @param name
	 *            对应 name
	 * @throws
	 */
	@AuthPassport
	@RequestMapping(value = "commpany_asset_form", method = RequestMethod.GET)
	public String goToCompanyAssetForm(Model model, HttpServletRequest request, @RequestParam("id") Long id) {

		// 1. 获取登录的用户/公司 id
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long userId = accountAuth.getUserId();

		Long companyId = accountAuth.getCompanyId();

		model.addAttribute("companyId", companyId);
		model.addAttribute("userId", userId);

		/*
		 * 2. form 页 vo 字段
		 */

		// 2-1. 下拉列表。选择公司资产
		CompanySettingSearchVo s = new CompanySettingSearchVo();

		s.setSettingType("asset_type");

		List<XcompanySetting> assetTypeList = settingService.selectBySearchVo(s);

		// 2-2. 图片 url TODO

		// 2-3. po属性
		RecordAssets assets = recordAssetService.initRecordAssets();

		if (id > 0L) {
			assets = recordAssetService.selectByPrimarykey(id);
		}

		// 3. 构造 vo
		RecordAssetVo assetVo = new RecordAssetVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(assets, assetVo);

		assetVo.setxCompSettingList(assetTypeList);

		model.addAttribute("contentModel", assetVo);

		// 二维码
		String qrCode = "";
		ApptoolsSearchVo searchVo1 = new ApptoolsSearchVo();
		searchVo1.setAction("assets");
		List<AppTools> apptools = appToolsService.selectBySearchVo(searchVo1);
		if (!apptools.isEmpty()) {
			AppTools a = apptools.get(0);
			qrCode = a.getQrCode();
		}

		if (StringUtil.isEmpty(qrCode))
			qrCode = "/assets/img/erweima.png";
		model.addAttribute("qrCode", qrCode);

		return "xz/commpany-asset-form";
	}

}
