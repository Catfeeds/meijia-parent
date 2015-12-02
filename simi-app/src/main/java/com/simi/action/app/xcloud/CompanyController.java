package com.simi.action.app.xcloud;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.QrCodeUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;


@Controller
@RequestMapping(value = "/app/company")
public class CompanyController extends BaseController {
	
	@Autowired
	private XCompanyService xCompanyService;
	
	@Autowired
	private UserSmsTokenService smsTokenService;	
	
	@Autowired
	private UsersService usersService;	
	
	@Autowired
	private XcompanyDeptService xCompanyDeptService;
	
	@Autowired
	private XcompanyStaffService xCompanyStaffService;		
		
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/reg", method = {RequestMethod.POST})
	public AppResultData<Object> companyReg(
			@RequestParam("user_name") String userName,
			@RequestParam("sms_token") String smsToken,
			@RequestParam(value = "company_id", required = false, defaultValue = "0") Long companyId,
			@RequestParam("company_name") String companyName,
			@RequestParam("short_name") String shortName,
			@RequestParam("password") String password
			) throws NoSuchAlgorithmException, WriterException, IOException {	
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, "");
		
		if (StringUtil.isEmpty(userName) || 
		    StringUtil.isEmpty(smsToken) ||
		    StringUtil.isEmpty(companyName) ||
		    StringUtil.isEmpty(shortName) ||
		    StringUtil.isEmpty(password)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("数据异常");
			return result;
		}
		
		String mobile = userName;
		AppResultData<Object> validateResult = smsTokenService.validateSmsToken(mobile, smsToken, (short) 3);
		
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("验证码错误");
			return result;
		}
		
		//然后判断用户是否已经存在，不存在则添加新用户
		Users u = usersService.selectByMobile(mobile);
		
		if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
			u = usersService.genUser(mobile, "", Constants.User_XCOULD);
		}		
		
		//验证是否出现重名的情况.
		Xcompany xCompany = xCompanyService.selectByCompanyNameAndUserName(companyName, userName);
		if (xCompany != null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("您已经注册过"+companyName);
			return result;
		}		
		
		String passwordMd5 = StringUtil.md5(password.trim());
		
		xCompany = xCompanyService.initXcompany();
		if (companyId > 0L) {
			xCompany = xCompanyService.selectByPrimaryKey(companyId);
		}
		
		xCompany.setUserName(userName);
		xCompany.setPassword(passwordMd5);
		xCompany.setCompanyName(companyName);
		xCompany.setShortName(shortName);
		
		if (companyId > 0L) {
			xCompany.setUpdateTime(TimeStampUtil.getNowSecond());
			xCompanyService.updateByPrimaryKeySelective(xCompany);
		} else {
			
			//生成公司唯一邀请码
			String invitationCode = StringUtil.generateShortUuid();
			xCompany.setInvitationCode(invitationCode);
			xCompanyService.insert(xCompany);
			companyId = xCompany.getCompanyId();
			
			//生成公司邀请二维码
			String qrCodeLogo = "http://img.51xingzheng.cn/c9778e512787866532e425e550023262";
			Map<String, String> contents = new HashMap<String, String>();
			
			contents.put("tag", "xcloud");
			contents.put("company_id", companyId.toString());
			contents.put("company_name", xCompany.getCompanyName());
			contents.put("invitation_code", xCompany.getInvitationCode());
			
			 ObjectMapper objectMapper = new ObjectMapper();
			 String jsonContents = objectMapper.writeValueAsString(contents);
			
			BufferedImage qrCodeImg = QrCodeUtil.genBarcode(jsonContents, 800, 800, qrCodeLogo);
			
			ByteArrayOutputStream imageStream = new ByteArrayOutputStream();  
	        try {  
	            boolean resultWrite = ImageIO.write(qrCodeImg, "png", imageStream);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        imageStream.flush();  
	        byte[] imgBytes = imageStream.toByteArray();  
			
	        String url = Constants.IMG_SERVER_HOST + "/upload/";
			String sendResult = ImgServerUtil.sendPostBytes(url, imgBytes, "png");

			ObjectMapper mapper = new ObjectMapper();

			HashMap<String, Object> o = mapper.readValue(sendResult, HashMap.class);

			String ret = o.get("ret").toString();

			HashMap<String, String> info = (HashMap<String, String>) o.get("info");

			String imgUrl = Constants.IMG_SERVER_HOST + "/"+ info.get("md5").toString();				
			
			xCompany.setQrCode(imgUrl);
			xCompanyService.updateByPrimaryKeySelective(xCompany);
		}
		
		//公司部门预置信息.
		List<String> defaultDepts = MeijiaUtil.getDefaultDept();
		for (int i = 0 ; i < defaultDepts.size(); i++) {
			XcompanyDept dept = xCompanyDeptService.initXcompanyDept();
			dept.setName(defaultDepts.get(i));
			dept.setCompanyId(companyId);
			dept.setParentId(0L);
			xCompanyDeptService.insert(dept);
		}

		
		
		XcompanyDept defaultDept = xCompanyDeptService.selectByXcompanyIdAndDeptName(companyId, "未提交");
		Long deptId = 0L;
		if (defaultDept != null) {
			deptId = defaultDept.getDeptId();
		}
		//将用户加入公司员工中
		XcompanyStaff record = xCompanyStaffService.initXcompanyStaff();
		record.setUserId(u.getId());
		record.setCompanyId(companyId);
		record.setDeptId(deptId);
		xCompanyStaffService.insertSelective(record);
		
		return result;
	
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/join", method = {RequestMethod.POST})
	public AppResultData<Object> companyReg(
			@RequestParam("user_name") String userName,
			@RequestParam("sms_token") String smsToken,
			@RequestParam("invitation_code") String invitationCode
			) {	
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, "");
		
		if (StringUtil.isEmpty(userName) || 
		    StringUtil.isEmpty(smsToken) ||
		    StringUtil.isEmpty(invitationCode) ) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("数据异常");
			return result;
		}
		
		String mobile = userName;
		AppResultData<Object> validateResult = smsTokenService.validateSmsToken(mobile, smsToken, (short) 3);
		
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("验证码错误");
			return result;
		}
		
		Users u = usersService.selectByMobile(userName);
		Long userId = 0L;
		if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
			u = usersService.genUser(userName, "", Constants.User_XCOULD);
		}
		userId = u.getId();
		
		Xcompany xCompany = xCompanyService.selectByInvitationCode(invitationCode);
		Long companyId = 0L;
		if (xCompany == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("邀请码不存在!");
			return result;
		}
		companyId = xCompany.getCompanyId();
		
		XcompanyStaff  vo = xCompanyStaffService.selectByCompanyIdAndUserId(companyId, userId);
		
		if (vo != null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("您已经加入该公司");
			return result;
		}
		

		XcompanyDept defaultDept = xCompanyDeptService.selectByXcompanyIdAndDeptName(companyId, "未提交");
		Long deptId = 0L;
		if (defaultDept != null) {
			deptId = defaultDept.getDeptId();
		}
		//将用户加入公司员工中
		XcompanyStaff record = xCompanyStaffService.initXcompanyStaff();
		record.setUserId(u.getId());
		record.setCompanyId(companyId);
		record.setDeptId(deptId);
		xCompanyStaffService.insertSelective(record);
		
		return result;
	
	}	
}
