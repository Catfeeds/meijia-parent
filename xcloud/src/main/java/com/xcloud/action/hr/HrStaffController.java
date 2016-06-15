package com.xcloud.action.hr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.RegexUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.wx.utils.JsonUtil;
import com.simi.common.Constants;
import com.simi.po.model.common.Imgs;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.ImgService;
import com.simi.service.async.FileUploadAsyncService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.ImgSearchVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.xcloud.StaffListVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.simi.vo.xcloud.json.StaffJsonInfo;
import com.xcloud.action.BaseController;
import com.xcloud.action.DateEditor;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;

/**
 * 
* @author hulj 
* @date 2016年6月8日 下午6
*	
*	人事--员工管理--人事档案
*		
 */
@Controller
@RequestMapping(value = "/hr")
public class HrStaffController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyDeptService xcompanyDeptService;
	
	@Autowired
	private  FileUploadAsyncService fileUploadAsyncService;
	
	@Autowired
	private ImgService imgService;
	
	//前后台  date 类型属性的转换
	@InitBinder
	protected void initBinder(HttpServletRequest request,
	                              ServletRequestDataBinder binder) throws Exception {
	    //对于需要转换为Date类型的属性，使用DateEditor进行处理
	    binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	
	/**
	 * 
	 *  人事档案列表
	 * 
	 */
	@AuthPassport
	@RequestMapping(value = "/staff-list", method = { RequestMethod.GET })
	public String staffList(Model model, HttpServletRequest request, 
			UserCompanySearchVo searchVo,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "staff_id", required = false) Long staffId) {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();
		
		model.addAttribute("companyId", companyId);
				
		List<XcompanyDept> deptList = xcompanyDeptService.selectByXcompanyId(companyId);

		model.addAttribute("deptList", deptList);
		
		if (searchVo == null) searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		
		searchVo.setStatus((short) 1);
		
		//判断姓名或者手机号 
		String keyword = searchVo.getKeyword();
		if (!StringUtil.isEmpty(keyword)) {
			if (RegexUtil.isDigits(keyword)) {
				searchVo.setMobile(keyword);
			} else {
				searchVo.setName(keyword);
			}
		}
		
		PageInfo result = xcompanyStaffService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		
		model.addAttribute("contentModel", result);
		
		return "hr/staff-list";
	}
	
	
	/**
	 *  跳转到 人事档案 form页
	 */
	
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
			
			StaffListVo vo = xcompanyStaffService.initStaffListVO();

			BeanUtilsExp.copyPropertiesIgnoreNull(xcompanyStaff, vo);

			Users u = usersService.initUsers();
			
			
			Long userId = 0L;

			//json 格式字段的映射 对象, 先初始化一个
			StaffJsonInfo staffJsonInfo = xcompanyStaffService.initJsonInfo();
			
			if (staffId > 0L) {

				xcompanyStaff = xcompanyStaffService.selectByPrimarykey(staffId);
				
				userId = xcompanyStaff.getUserId();

				u = usersService.selectByPrimaryKey(userId);

				/*
				 *  1. 修改 form时,json 字段转换为 vo字段
				 *  
				 *  2. （1）对于 取数据时的  “json中的中文” 乱码 已经在  JsonTypeHandler 中处理
				 *  
				 *     （2）并 与 json 字段 对应的  对象 StaffJsonInfo 完成映射
				 *  	
				 */
				
				Object jsonInfo = xcompanyStaff.getJsonInfo();
				
				if(jsonInfo != null){
					staffJsonInfo = (StaffJsonInfo) jsonInfo;
				}
			}
			
			BeanUtilsExp.copyPropertiesIgnoreNull(xcompanyStaff, vo);
			
			if(staffId == 0L){
				//新增操作需要自动生成一个工号
				String jobNumber = xcompanyStaffService.getNextJobNumber(companyId);
				vo.setJobNumber(jobNumber);
			}
			
			
			BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);
			
			BeanUtilsExp.copyPropertiesIgnoreNull(staffJsonInfo, vo);
			
			// string 类型的日期 转为 date 类型   
			vo.setContractBeginDate(DateUtil.parse(staffJsonInfo.getContractBeginDate()));
			
			// 注意这里user表id 和 xcompanyStaff表的id同名，所以需要手动设置
			vo.setId(xcompanyStaff.getId());
			vo.setCompanyId(companyId);
			
			vo.setUserName(u.getName());
			
			//身份证号 图片
			ImgSearchVo imgSearchVo = new ImgSearchVo();
			
			imgSearchVo.setUserId(userId);
			
			List<Imgs> imgList = imgService.selectBySearchVo(imgSearchVo);
			
			for (Imgs imgs : imgList) {
				
				String linkType = imgs.getLinkType();
				
				if(linkType.equalsIgnoreCase(
						Constants.IMGS_LINK_TYPE_IDCARD_FRONT)){
					//身份证正面
					vo.setIdCardFront(imgs.getImgUrl());
				}
				
				if(linkType.equalsIgnoreCase(
						Constants.IMGS_LINK_TYPE_IDCARD_BACK)){
					//身份证背面
					vo.setIdCardBack(imgs.getImgUrl());
				}
				
			}
			
			model.addAttribute("contentModel", vo);
		}

		model.addAttribute("xCompany", xCompany);

		List<XcompanyDept> deptList = xcompanyDeptService.selectByXcompanyId(companyId);

		model.addAttribute("deptList", deptList);

		return "/hr/staff-form";
	}
	
	/**
	 * 提交 人事档案
	 * @throws IOException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@AuthPassport
	@RequestMapping(value = "/staff-form",method = RequestMethod.POST)
	public String submitStaffForm(HttpServletRequest request,Model model,
			@ModelAttribute("contentModel") StaffListVo vo, BindingResult result) throws IOException, InterruptedException, ExecutionException{
		 
		/*
		 * 数据校验 
		 * 1. 工号、身份证号重复
		 * 2. 字段非空等 
		 */
		// 获取登录的用户, 就是 为了 得到 一个  companyId
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		Long staffId = 0L;
		if (vo.getId() != null)
			staffId = vo.getId();

		String userMobile = vo.getMobile();
		
		// 属性名为 name 不能传参， 改为使用 userName
//		String userName = vo.getName();
		
		String userName = vo.getUserName();
		
		// 1. 必要字段 姓名、手机号的判空
		if (StringUtil.isEmpty(userMobile) || StringUtil.isEmpty(userName)) {
			
			result.addError(new FieldError("contentModel", "mobile", "手机号或姓名不能为空"));
			return staffUserForm(model, request, staffId);
		}
		
		/*
		 *  2. 	处理  user 表  vo字段
		 *  
		 *  	主要是 手机号的验重  
		 */
		Users u = usersService.selectByMobile(userMobile);
	
		// 验证手机号是否已经注册，如果未注册，则自动注册用户
		if (u == null) {
			
			// 生成一个 基本的用户信息，包含  form表单中的    mobile、name
			u = usersService.genUser(userMobile, userName, Constants.USER_XCOULD, "");
			
		}
		Long userId  = u.getId();
		
		/*
		 * 校验不重复
		 */
		UserCompanySearchVo companySearchVo = new UserCompanySearchVo();
		companySearchVo.setCompanyId(companyId);
		companySearchVo.setStatus((short) 1);
		
		List<XcompanyStaff> companyList = new ArrayList<XcompanyStaff>();
		
		XcompanyStaff xcompanyStaffExist = null;
		
		
		// 1. 校验手机号不重复, 根据手机号得到 一个 userId ,去 xcompanyStaff 查找
		
		companySearchVo.setUserId(userId);
		
		companyList = xcompanyStaffService.selectBySearchVo(companySearchVo);
		
		if (!companyList.isEmpty()) {
			xcompanyStaffExist = companyList.get(0);
		}

		if (xcompanyStaffExist != null) {
			
			if(!xcompanyStaffExist.getUserId().equals(userId)){
				result.addError(new FieldError("contentModel", "mobile", "手机号有重复，请重新填写"));
				return staffUserForm(model, request, staffId);
			}
			
		}
		
		//2. 校验身份证号, 根据身份证号得到一个 userId
		UserSearchVo userSearchVo = new UserSearchVo();
		
		userSearchVo.setIdCard(vo.getIdCard());
		
		List<Users> idCardUserList = usersService.selectBySearchVo(userSearchVo);
		
		if(!idCardUserList.isEmpty()){
			u = idCardUserList.get(0);
			
			userId = u.getId();
			
			companySearchVo.setUserId(userId);
			
			companyList = xcompanyStaffService.selectBySearchVo(companySearchVo);
			
			xcompanyStaffExist = null;
			
			if (!companyList.isEmpty()) {
				xcompanyStaffExist = companyList.get(0);
			}

			if (xcompanyStaffExist != null){
				
				if(!xcompanyStaffExist.getUserId().equals(userId)){
					
					result.addError(new FieldError("contentModel", "idCard", "身份证号有重复,请重新填写"));
					return staffUserForm(model, request, staffId);
				}
			}
		}
		
		u.setIdCard(vo.getIdCard());
		u.setName(userName);
		u.setMobile(vo.getMobile());
		
		// 设置 user 中的 可变属性后，无论是 新增还是修改 form,都需要 update
		u.setUpdateTime(TimeStampUtil.getNowSecond());
		u.setSex(vo.getSex());
		usersService.updateByPrimaryKeySelective(u);

		/*
		 * 3.  处理 xcompanyStaff 表的 vo 属性
		 * 
		 *     (1)新增时判断 员工 是否重复
		 *     (2)组装json 格式的字段 ，vo属性的赋值
		 */
		XcompanyStaff xcompanyStaff = xcompanyStaffService.initXcompanyStaff();
		
		
		if (staffId.equals(0L)) {

			UserCompanySearchVo searchVo = new UserCompanySearchVo();
			searchVo.setCompanyId(companyId);
			searchVo.setUserId(userId);
			searchVo.setStatus((short) 1);

			List<XcompanyStaff> rsList = xcompanyStaffService.selectBySearchVo(searchVo);
			xcompanyStaffExist = null;
			if (!rsList.isEmpty()) {
				xcompanyStaffExist = rsList.get(0);
			}

			if (xcompanyStaffExist != null) {
				result.addError(new FieldError("contentModel", "mobile", "该用户已经为贵司员工,不需要重复添加."));
				return staffUserForm(model, request, staffId);
			}

			// 判断工号是否有重复
			searchVo = new UserCompanySearchVo();
			searchVo.setCompanyId(companyId);
			searchVo.setJobNumber(vo.getJobNumber());
			searchVo.setStatus((short) 1);
			rsList = xcompanyStaffService.selectBySearchVo(searchVo);
			xcompanyStaffExist = null;
			if (!rsList.isEmpty()) {
				xcompanyStaffExist = rsList.get(0);
			}

			if (xcompanyStaffExist != null) {
				result.addError(new FieldError("contentModel", "jobNumber", "工号有重复，请重新填写"));
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
		
		String regularDate = request.getParameter("regularDate");
		xcompanyStaff.setRegularDate(DateUtil.parse(regularDate));
		
		xcompanyStaff.setJobName(vo.getJobName());
		xcompanyStaff.setDeptId(vo.getDeptId());
		xcompanyStaff.setStaffType(vo.getStaffType());
		
		// TODO 其中 cityId、 tel、 telTxt、companyFax 字段属性均为 默认值 

		/*
		 *  组装 json 字段
		 */
		StaffJsonInfo info = xcompanyStaffService.initJsonInfo();
		
		info.setBankCardNo(vo.getBankCardNo());
		info.setBankName(vo.getBankName());
		
		String contractBeginDate = request.getParameter("contractBeginDate");
		info.setContractBeginDate(contractBeginDate);
		
		info.setContractLimit(vo.getContractLimit());
		
		String json = JsonUtil.objecttojson(info);
		
		xcompanyStaff.setJsonInfo(json);
		
		if (staffId > 0L) {
			xcompanyStaffService.updateByPrimaryKeySelective(xcompanyStaff);
		} else {
			xcompanyStaff.setJobNumber(xcompanyStaffService.getNextJobNumber(companyId));
			xcompanyStaffService.insertSelective(xcompanyStaff);
		}
		
		
		//异步处理 图片 上传 返回值，返回 <vo字段属性名 : 图片服务器url>
		
		//处理  多文件 上传 
		Future<Map<String, String>> async = fileUploadAsyncService.multiFileUpLoadAsync(request);
		
		Map<String, String> map = async.get();
		
		String headImgUrl =  map.get("headImg");
		
		if(StringUtil.isEmpty(headImgUrl)){
			headImgUrl = Constants.DEFAULT_HEAD_IMG;
		}
		//用户头像
		u.setHeadImg(headImgUrl);
		
		usersService.updateByPrimaryKeySelective(u);
		
		//身份证正反面
		String idCardFrontUrl =  map.get("idCardFront");
		String idCardBackUrl = map.get("idCardBack");
		
		if(StringUtil.isEmpty(idCardFrontUrl)){
			//如果没有 上传，则使用默认 头像的图片
			idCardFrontUrl = Constants.DEFAULT_HEAD_IMG;
		}
		
		if(StringUtil.isEmpty(idCardBackUrl)){
			idCardBackUrl = Constants.DEFAULT_HEAD_IMG;
		}
		
		
		if(staffId == 0L){
			
			Imgs img1 = imgService.initImg();
			
			img1.setUserId(u.getId());
			img1.setImgUrl(idCardFrontUrl);
			img1.setLinkType(Constants.IMGS_LINK_TYPE_IDCARD_FRONT);
			
			imgService.insert(img1);
			
			Imgs img2 = imgService.initImg();
			
			img2.setUserId(u.getId());
			img2.setImgUrl(idCardBackUrl);
			img2.setLinkType(Constants.IMGS_LINK_TYPE_IDCARD_BACK);
			
			imgService.insert(img2);
			
		}else{
			
			ImgSearchVo imgSearchVo = new ImgSearchVo();
			
			imgSearchVo.setUserId(userId);
			
			List<Imgs> imgList = imgService.selectBySearchVo(imgSearchVo);
			
			//修改
			for (Imgs imgs : imgList) {
				
				if(imgs.getLinkType()
						.equalsIgnoreCase(Constants.IMGS_LINK_TYPE_IDCARD_FRONT)){
					
					//身份证正面
					imgs.setImgUrl(idCardFrontUrl);
					imgService.updateByPrimaryKey(imgs);
				}
				
				
				if(imgs.getLinkType()
						.equalsIgnoreCase(Constants.IMGS_LINK_TYPE_IDCARD_BACK)){
					
					//身份证背面
					imgs.setImgUrl(idCardBackUrl);
					imgService.updateByPrimaryKey(imgs);
				}
			}
		}
		
		
		return "redirect:staff-list";
	}
	
	
}
