package com.simi.action.user;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.action.admin.AdminController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.op.OpChannel;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserRef;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UserRefService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.meijia.utils.ExcelUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.user.UserRefSearchVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;

@Controller
@RequestMapping(value = "/user")
public class UserController extends AdminController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
	private UserDetailPayService userDetailPayService;
	
	@Autowired
	private XCompanySettingService settingService;
	
	@Autowired
	private UserRefService userRefService;

	@RequestMapping(value = "/update_name", method = { RequestMethod.POST })
	public AppResultData<Object> updateName(
			@RequestParam("pk") Long userId,
			@RequestParam("value") String userName) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (userId == null || userName == null) {
			return result;
		}
		Users user = usersService.selectByPrimaryKey(userId);
		if (user == null) {
			return result;
		}

		user.setName(userName);
		user.setUpdateTime(TimeStampUtil.getNow() / 1000);

		usersService.updateByPrimaryKeySelective(user);
		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, user);
		return result;
	}

	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String userList(HttpServletRequest request, Model model,
			UserSearchVo searchVo,
			@RequestParam(value="mobile", required = false, defaultValue = "") String mobile,
			@RequestParam(value="name", required = false, defaultValue = "" ) String name) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		if (!StringUtil.isEmpty(mobile)) searchVo.setMobile(mobile);
		if (!StringUtil.isEmpty(name)) searchVo.setName(name);
		PageInfo result = usersService.selectByListPage(searchVo, pageNo, pageSize);
		model.addAttribute("contentModel", result);

		return "user/userList";
	}
	
	@AuthPassport
	@RequestMapping(value = "/userForm", method = { RequestMethod.GET })
	public String userForm(
		HttpServletRequest request, Model model, @RequestParam(value = "id") Long id) {

		Users u = usersService.selectByPrimaryKey(id);
		
		model.addAttribute("contentModel", u);
		
		//渠道列表
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setSettingType("op_ext");
		List<XcompanySetting> opExtList = settingService.selectBySearchVo(searchVo);
		model.addAttribute("opExtList", opExtList);
		return "user/userForm";
	}
	
	@AuthPassport
	@RequestMapping(value = "/userForm", method = { RequestMethod.POST })
	public String doUserForm(
		HttpServletRequest request, Model model, @RequestParam(value = "id") Long id) {

		Users u = usersService.selectByPrimaryKey(id);
		
		String opExtIdStr = request.getParameter("op_ext_id").toString();
		
		if (!StringUtil.isEmpty(opExtIdStr)) {
			Long opExtId = Long.valueOf(opExtIdStr);
			//保存到
			UserRefSearchVo searchVo = new UserRefSearchVo();
			searchVo.setUserId(id);
			searchVo.setRefType("op_ext");
			List<UserRef> rs = userRefService.selectBySearchVo(searchVo);
			
			UserRef record = userRefService.initUserRef();
			if (!rs.isEmpty()) record = rs.get(0);
			
			record.setUserId(id);
			record.setRefId(opExtId);
			record.setRefType("op_ext");
			
			if (record.getId() > 0L) {
				userRefService.updateByPrimaryKeySelective(record); 
			} else {
				userRefService.insert(record);
			}
		}
		
		return "user/list";
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param searchVo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/download_project", method = { RequestMethod.GET })
	public String download(HttpServletRequest request,HttpServletResponse response,UserSearchVo searchVo) throws IOException{
        String fileName="excel文件";


        //填充projects数据
        List<Users> userses= usersService.selectByAll();

        List<Map<String,Object>> list= createExcelRecord(userses);

        String columnNames[]={"序号","手机号","手机归属地","用户姓名","用户余额","用户积分","用户类型","用户来源","添加时间","更新时间"};//列名
        String keys[]   =    {"id","mobiles","province_names","names","rest_moneys","scores","user_types","add_froms","add_times","update_times"};//map中的key
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
            // Simple read/write loop.
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
        return null;
    }

    private List<Map<String, Object>> createExcelRecord(List<Users> userses) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        Users users=null;
        for (int j = 0; j < userses.size(); j++) {
        	users=userses.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("ids",users.getId());
            mapValue.put("mobiles",users.getMobile());
			mapValue.put("province_names",users.getProvinceName());
			mapValue.put("names",users.getName());
			mapValue.put("rest_moneys",users.getRestMoney());
			mapValue.put("scores",users.getScore());
			mapValue.put("user_types",users.getUserType());
			mapValue.put("add_froms",users.getAddFrom());
			mapValue.put("add_times",users.getAddTime());
			mapValue.put("update_times",users.getUpdateTime());

            listmap.add(mapValue);
        }
        return listmap;
    }
	@RequestMapping(value = "/pay_detail", method = { RequestMethod.GET })
	public String payDetailList(HttpServletRequest request, Model model,
			UserSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result = userDetailPayService.searchVoListPage(searchVo,
				pageNo, pageSize);
		model.addAttribute("contentModel", result);

		return "user/userDetailPayList";
	}
	
/*	@RequestMapping(value = "/userStat-total", method = { RequestMethod.GET })
	public List<User> selectUserStat(HttpServletRequest request, Model model) {
		return usersService.selectUserStat();
	}*/
	@RequestMapping(value = "/feedback", method = { RequestMethod.GET })
	public String userFeedbackList(HttpServletRequest request, Model model) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());


		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result = userDetailPayService.searchUserFeedbackListPage(
				pageNo, pageSize);
		model.addAttribute("contentModel", result);

		return "user/userFeedbackList";
	}

	
}
