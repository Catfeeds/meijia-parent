package com.simi.action.app.xcloud;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.zxing.WriterException;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.op.AppTools;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.op.AppToolsService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.utils.XcompanyUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.po.AppToolsVo;
import com.simi.vo.xcloud.CompanySettingVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Controller
@RequestMapping(value = "/app/company")
public class CompanySeetingController extends BaseController {


	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	/**
	 * 公司配置信息接口
	 * @param settingType
	 * @param userId
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "get_company_setting", method = RequestMethod.GET)
	public AppResultData<Object> getAppTools(
			@RequestParam("setting_type") String settingType,
			@RequestParam("user_id") Long userId,
			@RequestParam("company_id") Long companyId) {
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		//若不是对用公司的员工责返回提示不再查询
		UserCompanySearchVo companysearchVo = new UserCompanySearchVo();
		companysearchVo.setUserId(userId);
		companysearchVo.setCompanyId(companyId);
		List<XcompanyStaff> xcompanyStaff = xcompanyStaffService.selectBySearchVo(companysearchVo);		
		if (xcompanyStaff.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_COMPANY);
			return result;
		}
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setSettingType(settingType);
		//searchVo.setName(name);
		
		List<CompanySettingVo> vo = new ArrayList<CompanySettingVo>();
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		for (XcompanySetting item : list) {
			CompanySettingVo listVo = new CompanySettingVo();
			listVo = xCompanySettingService.getCompantSettingVo(item);
			vo.add(listVo);
		}
		result.setData(vo);
		
		return result;
	}
	
}
