package com.simi.action.xcloud;

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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.meijia.wx.utils.JsonUtil;
import com.simi.action.BaseController;
import com.simi.common.Constants;
import com.simi.oa.auth.AccountAuth;
import com.simi.oa.auth.AuthHelper;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.CompanySettingVo;
import com.simi.vo.xcloud.json.SettingJsonSettingValue;

/**
 * 
 * @author hulj
 * @date 2016年6月14日 下午2:56:45
 * @Description:
 * 
 * 				运营平台--系统设置--社保公积金基数
 *
 */
@Controller
@RequestMapping(value = "/insurance")
public class XcompanySettingController extends BaseController {
	
	@Autowired
	private XCompanySettingService settingService;
	
	/**
	 *  社保公积金基数列表
	 */
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String userTokenList(HttpServletRequest request, Model model, CompanySettingSearchVo searchVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME,
				ConstantOa.DEFAULT_PAGE_SIZE);

		// 分页
		PageHelper.startPage(pageNo, pageSize);
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		if(searchVo == null){
			searchVo = new CompanySettingSearchVo();
		}
		
		searchVo.setUserId(accountAuth.getId());
		
		//对于社保公积金, settingType = "insurance"
		searchVo.setSettingType(Constants.SETTING_TYPE_INSURANCE);
		
		List<XcompanySetting> lists = settingService.selectBySearchVo(searchVo);
		
		for (int i = 0; i < lists.size(); i++) {
			
			XcompanySetting setting = lists.get(i);
			
			CompanySettingVo vo = new CompanySettingVo();
			
			if(setting.getSettingValue() != null){
				vo = settingService.transToVo(setting);
			}
			
			lists.set(i, vo);
		}

		PageInfo result = new PageInfo(lists);

		model.addAttribute("searchModel", searchVo);
		model.addAttribute("contentModel", result);

		return "xcloud/insuranceList";
	}
	
	/*
	 * 跳转到 form 表单
	 */
	@AuthPassport
	@RequestMapping(value = "/form",method = RequestMethod.GET)
	public String goToInsuranceForm(Model model,
			@RequestParam("id")Long id){
		
		if(id == null){
			id = 0L;
		}
		
		XcompanySetting setting = settingService.initXcompanySetting();
		
		if(id > 0L){
			setting = settingService.selectByPrimaryKey(id);
		}
		
		SettingJsonSettingValue settingValue = (SettingJsonSettingValue) setting.getSettingValue();
		
		CompanySettingVo vo = settingService.initSettingVo();
		
		//基础 属性
		BeanUtilsExp.copyPropertiesIgnoreNull(setting, vo);
		
		//json 属性
		BeanUtilsExp.copyPropertiesIgnoreNull(settingValue, vo);
		
		model.addAttribute("contentModel", vo);
		
		return "xcloud/insuranceForm";
	}
	
	
	
	/*
	 * 提交表单
	 */
	@AuthPassport
	@RequestMapping(value = "/form",method = RequestMethod.POST)
	public String submitInsuranceForm(HttpServletRequest request,
			@ModelAttribute("contentModel")CompanySettingVo settingVo,BindingResult result){
		
		Long id = settingVo.getId();
		
		XcompanySetting xcompanySetting = settingService.initXcompanySetting();
		
		// 获取登录的用户,设置 userId 字段
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long userId = accountAuth.getId();
		
		
		SettingJsonSettingValue jsonSettingValue = settingService.initJsonSettingValue();
		
		//json 字段的 8项数据
		jsonSettingValue.setCityId(settingVo.getCityId());
		jsonSettingValue.setRegionId(settingVo.getRegionId());
		jsonSettingValue.setFund(settingVo.getFund());
		jsonSettingValue.setInjury(settingVo.getInjury());
		jsonSettingValue.setMedical(settingVo.getMedical());
		jsonSettingValue.setPension(settingVo.getPension());
		jsonSettingValue.setUnemployment(settingVo.getUnemployment());
		jsonSettingValue.setBirth(settingVo.getBirth());
		
		String json = JsonUtil.objecttojson(jsonSettingValue);
		
		if(id > 0L){
			xcompanySetting	= settingService.selectByPrimaryKey(id);
			
			BeanUtilsExp.copyPropertiesIgnoreNull(settingVo, xcompanySetting);
			
			xcompanySetting.setUpdateTime(TimeStampUtil.getNowSecond());
			xcompanySetting.setSettingValue(json);
			xcompanySetting.setUserId(userId);
			
			settingService.updateByPrimaryKey(xcompanySetting);
			
		}else{
			
			BeanUtilsExp.copyPropertiesIgnoreNull(settingVo, xcompanySetting);
			
			xcompanySetting.setSettingValue(json);
			xcompanySetting.setUserId(userId);
			
			settingService.insert(xcompanySetting);
		}
		
		return "redirect:list";
	}
	
}
