package com.simi.action.setting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.InitBeanUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.wx.utils.JsonUtil;
import com.simi.action.BaseController;
import com.simi.common.Constants;
import com.simi.oa.auth.AccountAuth;
import com.simi.oa.auth.AuthHelper;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.dict.DictRegion;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.dict.DictService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.setting.InsuranceVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.XCompanySettingVo;

/**
 * 
 * @author hulj
 * @date 2016年6月14日 下午2:56:45
 * @Description:
 * 
 *               运营平台--系统设置--社保公积金基数
 *
 */
@Controller
@RequestMapping(value = "/insurance")
public class InsuranceController extends BaseController {

	@Autowired
	private XCompanySettingService settingService;
	
	@Autowired
	private DictService dictService;

	/**
	 * 社保公积金基数列表
	 */
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String userTokenList(HttpServletRequest request, Model model, CompanySettingSearchVo searchVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		// 分页
		PageHelper.startPage(pageNo, pageSize);

		if (searchVo == null) {
			searchVo = new CompanySettingSearchVo();
		}

		// 对于社保公积金, settingType = "insurance"
		searchVo.setSettingType(Constants.SETTING_TYPE_INSURANCE);

		
		PageInfo p = settingService.selectByListPage(searchVo, pageNo, pageSize);
		List<XcompanySetting> lists = p.getList();
		for (int i = 0; i < lists.size(); i++) {

			XcompanySetting setting = lists.get(i);

			XCompanySettingVo vo = settingService.getXCompantSettingVo(setting);
			
			InsuranceVo settingVo = new InsuranceVo();
			
			settingVo = (InsuranceVo)InitBeanUtil.initObject(settingVo);
			if (setting.getSettingValue() != null) {
				JSONObject setValue = (JSONObject) setting.getSettingValue();
				settingVo = JSON.toJavaObject(setValue, InsuranceVo.class);
			}
			
			vo.setSettingValueVo(settingVo);
			lists.set(i, vo);
		}

		PageInfo result = new PageInfo(lists);
		
		Long total = result.getTotal();
		
		//获得北上广深所有区县的总数.
//		List<DictRegion> regionB = dictService.getRegionByCityId(2L);
//		List<DictRegion> regionS = dictService.getRegionByCityId(74L);
//		List<DictRegion> regionG = dictService.getRegionByCityId(198L);
//		List<DictRegion> regionSZ = dictService.getRegionByCityId(200L);
//		int totalNeed = regionB.size() + regionS.size() + regionG.size() + regionSZ.size();
		
		int totalNeed = 4 - total.intValue();
		
		if (totalNeed < 0) totalNeed = 0;
		
		model.addAttribute("searchModel", searchVo);
		model.addAttribute("contentModel", result);
		model.addAttribute("totalNeed", totalNeed);

		return "setting/insuranceList";
	}

	/*
	 * 跳转到 form 表单
	 */
	@AuthPassport
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String goToInsuranceForm(Model model, @RequestParam("id") Long id) {

		if (id == null) {
			id = 0L;
		}

		XcompanySetting setting = settingService.initXcompanySetting();

		if (id > 0L) {
			setting = settingService.selectByPrimaryKey(id);
		}
		
		XCompanySettingVo vo = new XCompanySettingVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(setting, vo);

		InsuranceVo settingVo = new InsuranceVo();

		settingVo = (InsuranceVo) InitBeanUtil.initObject(settingVo);

		if (id > 0L) {
			JSONObject setValue = (JSONObject) setting.getSettingValue();
			settingVo = JSON.toJavaObject(setValue, InsuranceVo.class);
		}
		
		vo.setSettingValueVo(settingVo);
		
		model.addAttribute("id", id);
		model.addAttribute("contentModel", settingVo);

		return "setting/insuranceForm";
	}

	/*
	 * 提交表单
	 */
	@AuthPassport
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String submitInsuranceForm(HttpServletRequest request, @ModelAttribute("contentModel") InsuranceVo settingVo, BindingResult result) {

		Long id = Long.valueOf(request.getParameter("id").toString());

		XcompanySetting xcompanySetting = settingService.initXcompanySetting();

		// 获取登录的用户,设置 userId 字段
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getId();

//		InsuranceVo settingVo = (InsuranceVo) vo.getSettingValueVo();
		
		Long cityId = settingVo.getCityId();
		
		if (cityId > 0L) {
			String cityName = dictService.getCityName(cityId);
			settingVo.setCityName(cityName);
		}
		
//		Long regionId = settingVo.getRegionId();
//		if (regionId > 0L) {
//			String regionName = dictService.getRegionName(regionId);
//			settingVo.setRegionName(regionName);
//		}
		settingVo.setRegionId(0L);
		settingVo.setRegionName("");

		String json = JsonUtil.objecttojson(settingVo);

		if (id > 0L) {
			xcompanySetting = settingService.selectByPrimaryKey(id);

//			BeanUtilsExp.copyPropertiesIgnoreNull(settingVo, xcompanySetting);

			xcompanySetting.setUpdateTime(TimeStampUtil.getNowSecond());
			xcompanySetting.setSettingValue(json);
//			xcompanySetting.setUserId(userId);

			settingService.updateByPrimaryKey(xcompanySetting);

		} else {

//			BeanUtilsExp.copyPropertiesIgnoreNull(settingVo, xcompanySetting);
			xcompanySetting.setSettingType(Constants.SETTING_TYPE_INSURANCE);
			xcompanySetting.setSettingValue(json);
			xcompanySetting.setUserId(userId);

			settingService.insert(xcompanySetting);
		}

		return "redirect:list";
	}

}
