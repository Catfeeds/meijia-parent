package com.xcloud.action.company;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.ELState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;









import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.ExcelUtil;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.StringUtil;
import com.simi.vo.AppResultData;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.xcloud.StaffListVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;


@Controller
@RequestMapping(value = "/staff")
public class StaffController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyDeptService xcompanyDeptService;

	@AuthPassport
	@RequestMapping(value = "/staff-form", method = { RequestMethod.GET })
	public String staffUserForm(Model model, HttpServletRequest request,
			@RequestParam(value = "staff_id", required = false) Long staffId) {
		
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();
		
		Xcompany xCompany = xCompanyService.selectByPrimaryKey(companyId);
		
		if (!model.containsAttribute("contentModel")) {
		  
			XcompanyStaff xcompanyStaff = xcompanyStaffService.initXcompanyStaff();
			StaffListVo vo = new StaffListVo();
			
			BeanUtilsExp.copyPropertiesIgnoreNull(xcompanyStaff, vo);
			
			Users u = usersService.initUsers();

			Long userId = 0L;

			String jobNumber = xcompanyStaffService.getNextJobNumber(companyId);
			vo.setJobNumber(jobNumber);
			
			if (staffId > 0L) {
	
				xcompanyStaff = xcompanyStaffService.selectByPrimarykey(staffId);
				
				userId = xcompanyStaff.getUserId();
				
				u = usersService.selectByPrimaryKey(userId);
	
				BeanUtilsExp.copyPropertiesIgnoreNull(xcompanyStaff, vo);
	
				BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);
				
				
	
			}
			//注意这里user表id 和 xcompanyStaff表的id同名，所以需要手动设置
			vo.setId(xcompanyStaff.getId());
			vo.setCompanyId(companyId);
			
			model.addAttribute("contentModel", vo);
		}
		
		model.addAttribute("xCompany", xCompany);
		
		List<XcompanyDept> deptList = xcompanyDeptService.selectByXcompanyId(companyId);

		model.addAttribute("deptList", deptList);
		
		return "/staffs/staff-form";
	}

	/**
	 * 员工修改
	 */
	@AuthPassport
	@RequestMapping(value = "/staff-form", method = RequestMethod.POST)
	public String saveUserForm(HttpServletRequest request,
			 Model model, @ModelAttribute("contentModel") StaffListVo vo, BindingResult result) {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();
		
		Long staffId = 0L;
		if (vo.getId() != null)
			staffId = vo.getId();
		
		String mobile = vo.getMobile();
		String name = vo.getName();
		String jobNumber = vo.getJobNumber();
		
		if (StringUtil.isEmpty(mobile) || StringUtil.isEmpty(name)) {
        	result.addError(new FieldError("contentModel","mobile","手机号或姓名不能为空"));
        	return staffUserForm(model, request, staffId);
		}
		
		Users u = usersService.selectByMobile(mobile);
		Long userId = 0L;
		// 验证手机号是否已经注册，如果未注册，则自动注册用户，
		if (u == null) {
			u = usersService.genUser(mobile, vo.getName(), Constants.USER_XCOULD);
		}
		userId = u.getId();
		if (!u.getName().equals(vo.getName())) {
			u.setName(name);
			usersService.updateByPrimaryKeySelective(u);
		}
		
		XcompanyStaff xcompanyStaff = xcompanyStaffService.initXcompanyStaff();
		
		//新增要判断员工是否重复
		if (staffId.equals(0L)) {
			
			UserCompanySearchVo searchVo = new UserCompanySearchVo();
			searchVo.setCompanyId(companyId);
			searchVo.setUserId(userId);
			searchVo.setStatus((short) 1);
			
			List<XcompanyStaff> rsList = xcompanyStaffService.selectBySearchVo(searchVo);
			XcompanyStaff xcompanyStaffExist = null;
			if (!rsList.isEmpty()) {
				xcompanyStaffExist = rsList.get(0);
			}
			
			
			if (xcompanyStaffExist != null) {
				result.addError(new FieldError("contentModel","mobile","该用户已经为贵司员工,不需要重复添加."));
	        	return staffUserForm(model, request, staffId);
			}
			
			//判断工号是否有重复
			searchVo = new UserCompanySearchVo();
			searchVo.setCompanyId(companyId);
			searchVo.setJobNumber(jobNumber);
			searchVo.setStatus((short) 1);
			rsList =  xcompanyStaffService.selectBySearchVo(searchVo);
			xcompanyStaffExist = null;
			if (!rsList.isEmpty()) {
				xcompanyStaffExist = rsList.get(0);
			}
			
			if (xcompanyStaffExist != null) {
				result.addError(new FieldError("contentModel","mobile","工号有重复，请重新填写"));
	        	return staffUserForm(model, request, staffId);
			}
		}
		
		if (staffId > 0L) {
			xcompanyStaff = xcompanyStaffService.selectByPrimarykey(staffId);
		}
		xcompanyStaff.setId(staffId);
		xcompanyStaff.setCompanyId(companyId);
		xcompanyStaff.setUserId(userId);
		xcompanyStaff.setCompanyEmail(vo.getCompanyEmail());
		
		String joinDate = request.getParameter("joinDate");
		xcompanyStaff.setJoinDate(DateUtil.parse(joinDate));
		
		xcompanyStaff.setJobName(vo.getJobName());
		xcompanyStaff.setDeptId(vo.getDeptId());
		xcompanyStaff.setStaffType(vo.getStaffType());
		
		if (staffId > 0L) {
			xcompanyStaffService.updateByPrimaryKeySelective(xcompanyStaff);
		} else {
			xcompanyStaff.setJobNumber(xcompanyStaffService.getNextJobNumber(companyId));
			xcompanyStaffService.insertSelective(xcompanyStaff);
		}
		
		//todo 给相应的用户进行通知
		
		return "redirect:list";
	}
	
	@AuthPassport
	@RequestMapping(value = "/del", method = { RequestMethod.POST })
	public AppResultData<Object> deleteByRechargeCouponId(HttpServletRequest request,
			@RequestParam(value = "staff_id") Long staffId) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		if (staffId != null) {
			
			// 获取登录的用户
			AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
			XcompanyStaff vo = xcompanyStaffService.selectByPrimarykey(staffId);
			Long companyId = accountAuth.getCompanyId();			
			if (vo.getCompanyId().equals(companyId)) {
//				xcompanyStaffService.deleteByPrimaryKey(staffId);
				vo.setStatus((short) 0);
				vo.setJobNumber("0000");
				xcompanyStaffService.updateByPrimaryKeySelective(vo);
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/change-dept", method = RequestMethod.POST)
	public AppResultData<Object> changeDept(HttpServletRequest request,
			@RequestParam("company_id") Long companyId,
			@RequestParam("select_staff_ids") String selectStaffIds,
			@RequestParam("select_dept_id") Long deptId
			) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, null);
		
		if (StringUtil.isEmpty(selectStaffIds)) return result;
		
		String[] staffAry = StringUtil.convertStrToArray(selectStaffIds);
		
		for (int i = 0; i < staffAry.length; i++) {
			String staffId = staffAry[i];
			if (StringUtil.isEmpty(staffId)) continue;

			XcompanyStaff xcompanyStaff = xcompanyStaffService.selectByPrimarykey(Long.valueOf(staffId));
			if (xcompanyStaff == null) continue;
			
			xcompanyStaff.setDeptId(deptId);
			xcompanyStaffService.updateByPrimaryKeySelective(xcompanyStaff);
		}
		
		return result;
	}
	
	@AuthPassport
	@RequestMapping(value = "/staff-import", method = { RequestMethod.GET })
	public String staffImportPre(Model model, HttpServletRequest request) {
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();
		
		Xcompany xCompany = xCompanyService.selectByPrimaryKey(companyId);		
		
		model.addAttribute("xCompany", xCompany);
		
		return "/staffs/staff-import";
	}	
	
	@AuthPassport
	@RequestMapping(value = "/staff-import", method = { RequestMethod.POST })
	public String doStaffImportPre(Model model, HttpServletRequest request) throws Exception {
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();
				
		// 创建一个通用的多部分解析器.
		String path = "/data/attach/staff/";
		String fileToken = RandomUtil.randomNumber(6);
		String newFileName = "";
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		if (multipartResolver.isMultipart(request)) {
			// 判断 request 是否有文件上传,即多部分请求...
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);
			
			MultipartFile file = multiRequest.getFile("staff-file");
			if (file != null && !file.isEmpty()) {
				 // 存储的临时文件名 = 获取时间戳+随机六位数+"."图片扩展名
				String fileName = file.getOriginalFilename();
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				
				newFileName = companyId.toString() + "-" + fileToken + "." + ext;
				
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path,newFileName));
				
			}
			
		}
		
		if (StringUtil.isEmpty(newFileName)) {
			model.addAttribute("errors", "表格数据为空，请下载模板后填写.");
			return "/staffs/staff-import-error";
		}
		
		List<Object> excelDatas = new ArrayList<Object>();
		
		InputStream in = new FileInputStream(path + newFileName);
		excelDatas = ExcelUtil.readToList(path + newFileName, in, 0, 0);
		
		//校验表格是否正确.
		AppResultData<Object> validateResult = xcompanyStaffService.validateStaffImport(companyId, excelDatas);
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			model.addAttribute("errors", validateResult.getMsg());
			model.addAttribute("tableDatas", validateResult.getData());
			return "/staffs/staff-import-error";
		}
		
		//检测都有哪些重复数据
		List<Object> dupList = xcompanyStaffService.checkDuplication(companyId, excelDatas);
		
		
		int totalUpdateCount = 0;
		model.addAttribute("tableDatas", "");
		if (!dupList.isEmpty()) {
			totalUpdateCount = dupList.size();
			model.addAttribute("tableDatas", dupList);
		}
		
		model.addAttribute("totalUpdateCount", totalUpdateCount);
		
		int totalNewCount = excelDatas.size() - totalUpdateCount - 1;
		model.addAttribute("totalNewCount", totalNewCount);
		
		model.addAttribute("newFileName", newFileName);
		
		return "/staffs/staff-import-confirm";
	}		
	
	@AuthPassport
	@RequestMapping(value = "/staff-import-do", method = { RequestMethod.POST })
	public String doStaffImport(Model model, HttpServletRequest request) throws Exception {
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();

		// 创建一个通用的多部分解析器.
		String path = "/data/attach/staff/";
		String newFileName = request.getParameter("newFileName").toString();
		
		if (StringUtil.isEmpty(newFileName)) {
			model.addAttribute("errors", "表格数据为空，请下载模板后填写.");
			return "/staffs/staff-import-error";
		}
		
		List<Object> excelDatas = new ArrayList<Object>();
		
		InputStream in = new FileInputStream(path + newFileName);
		excelDatas = ExcelUtil.readToList(path + newFileName, in, 0, 0);
		
		AppResultData<Object> validateResult = xcompanyStaffService.staffImport(companyId, excelDatas);
		
		return "/staffs/staff-import-ok";
	}	
	/**
	 * 导出通讯录
	 * @param request
	 * @param response
	 * @param searchVo
	 * @throws IOException
	 */
	@AuthPassport
	@RequestMapping(value = "/staff-download", method = { RequestMethod.GET })
	public void download(HttpServletRequest request,HttpServletResponse response,UserCompanySearchVo searchVo) throws IOException{
		    	
		    String fileName="excel文件";    
		    String companyId = request.getParameter("companyId");
	   
	        //填充projects数据
		    searchVo.setCompanyId(Long.valueOf(companyId));
	        List<XcompanyStaff> xcompanyStaffs = xcompanyStaffService.selectByListPage(searchVo);
	        List<Map<String,Object>> list= createExcelRecord(xcompanyStaffs);

	        String columnNames[]={"姓名","手机号","工号","职位","员工类型","身份证号","入职时间(yyyy-mm-dd)","邮箱"};//列名
	        String keys[] = {"name","mobile","jobNumber","jobName","staffType","idCard","jobDate","companyEmail"};//map中的key
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        try {
	            ExcelUtil.createWorkBook(list,keys,columnNames).write(os);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);
	        // 设置response参数，可以打开下载页面
	        response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
	        ServletOutputStream out = response.getOutputStream();
	        BufferedInputStream bis = null;
	        BufferedOutputStream bos = null;
	        try {
	            bis = new BufferedInputStream(is);
	            bos = new BufferedOutputStream(out);
	            byte[] buff = new byte[2048];
	            int bytesRead;
	            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	                bos.write(buff, 0, bytesRead);
	            }
	        } catch (final IOException e) {
	            throw e;
	        } finally {
	            if (bis != null)
	                bis.close();
	            if (bos != null)
	                bos.close();
	        }
	}
	/**
	 * 创建Excle模板
	 */
    private List<Map<String, Object>> createExcelRecord(List<XcompanyStaff> list) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        XcompanyStaff xcompanyStaff=xcompanyStaffService.initXcompanyStaff();
        for (int j = 0; j < list.size(); j++) {
        	xcompanyStaff=list.get(j);
        	Users users = usersService.selectByPrimaryKey(xcompanyStaff.getUserId());
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("name",users.getName());
            mapValue.put("mobile",users.getMobile());
            mapValue.put("jobNumber",xcompanyStaff.getJobNumber());
            mapValue.put("jobName",xcompanyStaff.getJobName());
            if (xcompanyStaff.getStaffType() == 0) {
            	mapValue.put("staffType","全职");	
			}
            if (xcompanyStaff.getStaffType() == 1) {
            	mapValue.put("staffType","兼职");	
			}
            if (xcompanyStaff.getStaffType() == 2) {
            	mapValue.put("staffType","实习");	
			}
        //    mapValue.put("staffType",xcompanyStaff.getStaffType());
            mapValue.put("idCard",users.getIdCard());
            mapValue.put("jobDate",DateUtil.formatDate(xcompanyStaff.getJoinDate()));
            mapValue.put("companyEmail",xcompanyStaff.getCompanyEmail());
            listmap.add(mapValue);
        }
        return listmap;
    }
}
