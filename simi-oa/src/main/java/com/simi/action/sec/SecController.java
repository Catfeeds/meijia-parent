package com.simi.action.sec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.admin.AdminController;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.UserSmsToken;
import com.simi.po.model.user.Users;
import com.simi.service.user.TagsService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.UsersSearchVo;
import com.simi.vo.UsersSmsTokenVo;
import com.simi.vo.user.UserApplyVo;
@Controller
@RequestMapping(value = "/sec")
public class SecController extends AdminController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UserSmsTokenService userSmsTokenService;
	

	@Autowired
	private TagsService tagsService;

	@Autowired
	private TagsUsersService tagsUsersService;
	
	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
	private UserDetailPayService userDetailPayService;

	
	   /**
     * 验证码列表
     * @param request
     * @param model
     * @param usersSmsTokenVo
     * @return
     */
    @AuthPassport
	@RequestMapping(value = "/token_list", method = { RequestMethod.GET })
	public String userTokenList(HttpServletRequest request, Model model,
			UsersSmsTokenVo usersSmsTokenVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		// 分页
		PageHelper.startPage(pageNo, pageSize);

		List<UserSmsToken> lists = userSmsTokenService.selectByListPage(
				usersSmsTokenVo, pageNo, pageSize);

		PageInfo result = new PageInfo(lists);
		model.addAttribute("usersSmsTokenVo", usersSmsTokenVo);
		model.addAttribute("userSmsTokenModel", result);

		return "user/userTokenList";
	}
	/**
	 *  秘书列表
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
    @AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String secList(HttpServletRequest request, UsersSearchVo usersSearchVo,Model model) throws UnsupportedEncodingException {
    	
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
	
		/*PageInfo result = usersService.selectByIsAppRovalYes(pageNo,
				pageSize);*/
		
		/*PageInfo result = usersService.selectByIsAppRoval(pageNo,
				pageSize);
		*/
		//分页
		PageHelper.startPage(pageNo, pageSize);
		//若搜索条件为空，则展示全部
		if (usersSearchVo == null) {
			usersSearchVo = new UsersSearchVo();
		}
		//助理人员的默认id为1
		usersSearchVo.setUserType((short)1);
		
		//设置中文 参数 编码，解决 中文 乱码
	    usersSearchVo.setName(new String(usersSearchVo.getName().getBytes("iso-8859-1"),"utf-8"));
	
		
		List<Users> list = usersService.selectByListPageYes(usersSearchVo, pageNo, pageSize);
		
		
		PageInfo result = new PageInfo(list);
		model.addAttribute("userSearchVoModel",usersSearchVo);
		
		model.addAttribute("contentModel", result);

		return "sec/list";
	}
	
	/**
	 *  秘书申请列表
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
    @AuthPassport
	@RequestMapping(value = "/applyList", method = { RequestMethod.GET })
	public String applyList(HttpServletRequest request,UsersSearchVo usersSearchVo, Model model) throws UnsupportedEncodingException {
    	
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
	
		/*PageInfo result = usersService.selectByIsAppRoval(pageNo,
				pageSize);
		*/
		//分页
		PageHelper.startPage(pageNo, pageSize);
		//若搜索条件为空，则展示全部
		if (usersSearchVo == null) {
			usersSearchVo = new UsersSearchVo();
		}
		//助理人员的默认id为1
		usersSearchVo.setUserType((short)1);
		
		//设置中文 参数 编码，解决 中文 乱码
		usersSearchVo.setName(new String(usersSearchVo.getName().getBytes("iso-8859-1"),"utf-8"));
		
		List<Users> list = usersService.selectByListPage(usersSearchVo, pageNo, pageSize);
		
		
		PageInfo result = new PageInfo(list);
		model.addAttribute("userSearchVoModel",usersSearchVo);
		model.addAttribute("contentModel", result);

		return "sec/applyList";
	}
    /**
     * 秘书详细信息列表展示
     * @param id
     * @param model
     * @return
     */
    @AuthPassport
	@RequestMapping(value = "/applyForm",method = RequestMethod.GET)
	public String  orderDetail(Long id ,Model model){
		
		Users u = usersService.selectByPrimaryKey(id);
		UserApplyVo vo = new UserApplyVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);
		
		//是否审批名称
		short isApproval = u.getIsApproval();
		if (isApproval == 0) {
			vo.setIsApprovalName("未审批");
		}else {
			vo.setIsApprovalName("已审批");
		}
		
		//学历名称
		/*short degreeId = u.getDegreeId();
		if (degreeId == 0) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}if (degreeId == 1) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}if (degreeId == 2) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}if (degreeId == 3) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}if (degreeId == 4) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}
		if (degreeId == 5) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}
		if (degreeId == 6) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}*/
		
		//1表示秘书
		Short tagType = 1;
		//标签列表 
		List<Tags> tagList = tagsService.selectByTagType(tagType);

		//用户对应的标签列表
		String tagNames = "";
		String tagIds = "";
		List<Tags> tags = new ArrayList<Tags>();
		List<TagUsers> tagUserslist = tagsUsersService.selectByUserId(id);
		// 处理 form中 标签的 显示 和传值
		List<Long> tagIdList = new ArrayList<Long>();
		for (TagUsers item : tagUserslist) {
			tagIdList.add(item.getTagId());
			tagIds += item.getTagId().toString() + ",";
		}
		//处理 列表中 标签字段的 展示
		if (tagIdList.size() > 0) {
			tags = tagsService.selectByTagIds(tagIdList);
			for (Tags item : tags) {
				// 查找 tagId对应的 tagName
				tagNames += item.getTagName() + "" ;
			}
		}
		vo.setTagNames(tagNames);
		vo.setTagIds(tagIds);
		vo.setTagList(tags);
		
		model.addAttribute("tagList", tagList);
		model.addAttribute("contentModel", vo);
		
		return "sec/applyListForm";
	}
    
    /**
     * 审批结果保存
     * @param model
     * @param id
     * @param request
     * @return
     */
    @AuthPassport
	@RequestMapping(value = "/saveApplyListForm", method = { RequestMethod.POST})
	public String adForm(Model model,
			/*@RequestParam(value = "id") Long id,
			@RequestParam(value = "isApproval") short isApproval,*/
			@ModelAttribute("contentModel") UserApplyVo userApplyVo,
			BindingResult result,HttpServletRequest request)throws IOException {
    	
    	Long userId = Long.valueOf(request.getParameter("id"));
    	
    	Users users = usersService.initUsers();
    	// 创建一个通用的多部分解析器.
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/sec");
		String addr = request.getRemoteAddr();
		int port = request.getServerPort();
		if (multipartResolver.isMultipart(request)) {
			// 判断 request 是否有文件上传,即多部分请求...
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null && !file.isEmpty()) {
					String url = Constants.IMG_SERVER_HOST + "/upload/";
					String fileName = file.getOriginalFilename();
					String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
					fileType = fileType.toLowerCase();
					String sendResult = ImgServerUtil.sendPostBytes(url, file.getBytes(), fileType);

					ObjectMapper mapper = new ObjectMapper();

					HashMap<String, Object> o = mapper.readValue(sendResult, HashMap.class);

					String ret = o.get("ret").toString();

					HashMap<String, String> info = (HashMap<String, String>) o.get("info");

					String headImg = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();
					
					userApplyVo.setHeadImg(headImg);

				}
			}
		}
    	BeanUtilsExp.copyPropertiesIgnoreNull(userApplyVo, users);
    
    	if (userId > 0L ) {
    		users.setUserType((short)1);
			users.setUpdateTime(TimeStampUtil.getNow()/1000);
			usersService.updateByPrimaryKeySelective(users);
		}else {
			usersService.insertSelective(users);
		}
    	//存储标签
    	String tagListStr = request.getParameter("tagIds");
    	
    	if (!StringUtil.isEmpty(tagListStr)) {
			tagsUsersService.deleteByUserId(userId);
			String[] tagList = StringUtil.convertStrToArray(tagListStr);
			for (int i = 0; i < tagList.length; i++) {
				if (StringUtil.isEmpty(tagList[i])) continue;
				
				TagUsers record = new TagUsers();
				
				record.setId(0L);
				record.setUserId(users.getId());
				record.setTagId(Long.valueOf(tagList[i]));
				record.setAddTime(TimeStampUtil.getNowSecond());
				tagsUsersService.insert(record);
			}
		}
    	//秘书审核通过(IsApproval==2)后通知助理
    	if (users.getIsApproval()==2) {
    		usersService.userSecToUserPushSms(users);
		}
    	
    	return "redirect:/sec/applyList";
    }
 
}
