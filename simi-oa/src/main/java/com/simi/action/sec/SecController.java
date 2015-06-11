package com.simi.action.sec;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.BaseController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.sec.Sec;
import com.simi.service.sec.SecService;



@Controller
@RequestMapping(value="/sec")
public class SecController extends BaseController{

	@Autowired
	private SecService secService;
	
	/**秘书表单列表显示
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model
		) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result=secService.searchVoListPage( pageNo, pageSize);

		model.addAttribute("secModel",result);

		return "sec/list";
	}

	/**
	 * 秘书表单方法
	 * @param model
	 * @param id
	 * @param request
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/listForm", method = { RequestMethod.GET })
	public String register(HttpServletRequest request,Model model,
			               @RequestParam(value = "id") Long id) {
		
		if (id == null) {
			id = 0L;
		}
		
		Sec sec=secService.initSec();
		model.addAttribute("secModel", sec);
	
		return "sec/listForm";
	}
	/**
	 * 新增秘书表单信息
	 * @param model
	 * @param id
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@AuthPassport
	@RequestMapping(value = "/listForm", method = { RequestMethod.POST })
	public String doServiceTypeForm(HttpServletRequest request, Model model,
			@ModelAttribute("sec") Sec sec,
			BindingResult result) throws IOException, ParseException{

		    Long id = Long.valueOf(request.getParameter("id"));	
		    //创建一个通用的多部分解析器.
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
	        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/sec");
	        String addr = request.getRemoteAddr();
	        int port = request.getServerPort();
			 if(multipartResolver.isMultipart(request))
		        {
		             //判断 request 是否有文件上传,即多部分请求...
		            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)(request);
		            Iterator<String> iter = multiRequest.getFileNames();
		            while(iter.hasNext()){
		                MultipartFile file = multiRequest.getFile(iter.next());
		                if(file!=null && !file.isEmpty()){

		                	  String fileName = file.getOriginalFilename();
		                      String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		                      // 新的图片文件名 = 获取时间戳+随机六位数+"."图片扩展名
		                      String before = TimeStampUtil.getNow()+String.valueOf(RandomUtil.randomNumber());
		                      String newFileName = String.valueOf(before+"."+extensionName);
		                     //获取系统发布后upload路径
		                     FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path,newFileName));
		                     String headImg =  "/onecare-oa/upload/sec/"+newFileName;
		                     sec.setHeadImg(headImg);

		                     //生成缩略图
		                     BufferedImage bufferedImage1 = new BufferedImage(60,60,BufferedImage.TYPE_INT_BGR);
		                     BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
		                     Image image = bufferedImage.getScaledInstance(60,60,Image.SCALE_DEFAULT);
		                     bufferedImage1.getGraphics().drawImage(image, 0, 0, null);
		                     String newFileName1 = String.valueOf(before+"_small."+extensionName);

		                     FileOutputStream out = new FileOutputStream(path + "/" + newFileName1);
		                     ImageIO.write(bufferedImage1, "jpg",out);//把图片输出
		                }
		            }
		        }
 
		String birthDay = request.getParameter("birthDay");
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date da = sp.parse(birthDay);
		
		String status = request.getParameter("status");
		
		sec.setBirthDay(da);
		sec.setId(Long.valueOf(request.getParameter("id")));
		sec.setAddTime(TimeStampUtil.getNow() / 1000);
		sec.setUpdateTime(0L);
		sec.setStatus(Boolean.valueOf(status));
		//sec.setStatus(null);
		secService.insertSelective(sec);
	        
		return "redirect:list";
	}
	/**
	 * 根据id删除记录
	 */
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.GET })
	public String deleterAdminRole(Model model,
			@PathVariable(value = "id") String id, HttpServletRequest response) {
		Long ids = Long.valueOf(id.trim());
		String path = "redirect:/sec/list";
		int result = secService.deleteByPrimaryKey(ids);
		if (result >= 0) {
			return path;
		} else {
			return "error";
		}
	}}
