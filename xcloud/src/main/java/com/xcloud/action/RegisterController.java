package com.xcloud.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.QrCodeUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
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
@RequestMapping(value = "/")
public class RegisterController extends BaseController {

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private UsersService usersService;	
	
	@Autowired
	private UserSmsTokenService smsTokenService;	
	
	@Autowired
	private XcompanyDeptService xCompanyDeptService;
	
	@Autowired
	private XcompanyStaffService xCompanyStaffService;
	
	@RequestMapping(value="/register", method = {RequestMethod.GET})
    public String register(Model model) {
		
		if (!model.containsAttribute("contentModel")) {
			Xcompany xCompany = xCompanyService.initXcompany();
			model.addAttribute("contentModel", xCompany);
		}
		
        return "/home/register";
    }	
	
	
	@RequestMapping(value="/register", method = {RequestMethod.POST})
    public String doRegister(HttpServletRequest request, 
    						 HttpServletResponse response,  
    						 Model model,
    						 @ModelAttribute("contentModel") Xcompany xCompanyVo, 
    						 BindingResult result) throws NoSuchAlgorithmException, WriterException, IOException {
        
		String mobile = xCompanyVo.getUserName();
		String smsToken = request.getParameter("sms_token");
		
		AppResultData<Object> validateResult = smsTokenService.validateSmsToken(mobile, smsToken, (short) 3);
		
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			result.addError(new FieldError("contentModel","userName","验证码错误"));
			model.addAttribute("contentModel", xCompanyVo);
			return register(model);
		}
		
		//然后判断用户是否已经存在，不存在则添加新用户
		Users u = usersService.selectByMobile(mobile);
		
		if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
			u = usersService.genUser(mobile, "", Constants.User_XCOULD);
		}
		Long userId = u.getId();
		
		String companyName = xCompanyVo.getCompanyName().trim();
		//验证公司与用户是否已经存在
		Xcompany xCompanyExist = xCompanyService.selectByCompanyNameAndUserName(companyName, mobile);
		
		if (xCompanyExist != null) {
			result.addError(new FieldError("contentModel","userName","您已经注册过此公司."));
			model.addAttribute("contentModel", xCompanyVo);
			return register(model);
		}
		
		//获得注册的form信息
		Long companyId = xCompanyVo.getCompanyId();
		
		String password = xCompanyVo.getPassword();
		
		String passwordMd5 = StringUtil.md5(password.trim());
		
		Xcompany xCompany = xCompanyService.initXcompany();
		if (companyId > 0L) {
			xCompany = xCompanyService.selectByPrimaryKey(companyId);
		}
		
		BeanUtilsExp.copyPropertiesIgnoreNull(xCompanyVo, xCompany);
		
		xCompany.setPassword(passwordMd5);
		
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
		
		XcompanyDept defaultDept = xCompanyDeptService.selectByXcompanyIdAndDeptName(companyId, "未分配");
		Long deptId = 0L;
		if (defaultDept != null) {
			deptId = defaultDept.getDeptId();
		}
		
		
		//将用户加入公司员工中
		XcompanyStaff record = xCompanyStaffService.selectByCompanyIdAndUserId(companyId, userId);
		if (record == null) {
			record = xCompanyStaffService.initXcompanyStaff();
		}
		record.setUserId(userId);
		record.setCompanyId(companyId);
		record.setDeptId(deptId);
		record.setJobNumber(xCompanyStaffService.getMaxJobNumber(companyId));
		xCompanyStaffService.insertSelective(record);
		
		return "redirect:/register-ok";
    }	
	
	@RequestMapping(value="/register-ok", method = {RequestMethod.GET})
    public String registerOk(Model model) {
        return "/home/register-ok";
    }	
	
}
