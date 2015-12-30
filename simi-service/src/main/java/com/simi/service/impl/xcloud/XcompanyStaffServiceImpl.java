package com.simi.service.impl.xcloud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.ExcelUtil;
import com.meijia.utils.RegexUtil;
import com.meijia.utils.StringUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.dao.xcloud.XcompanyStaffMapper;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.utils.XcompanyUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.UserCompanySearchVo;
import com.simi.vo.xcloud.StaffListVo;

@Service
public class XcompanyStaffServiceImpl implements XcompanyStaffService {

	@Autowired
	XcompanyStaffMapper xCompanyStaffMapper;

	@Autowired
	XcompanyDeptService xcompanyDeptService;

	@Autowired
	UsersService usersService;

	@Override
	public XcompanyStaff initXcompanyStaff() {

		XcompanyStaff record = new XcompanyStaff();

		record.setId(0L);
		record.setUserId(0L);
		record.setCompanyId(0L);
		record.setDeptId(0L);
		record.setCityId(0L);
		record.setTel("");
		record.setTelExt("");
		record.setCompanyEmail("");
		record.setCompanyFax("");
		record.setStaffType((short) 0L);
		record.setJobName("");
		record.setJobNumber("0001");
		record.setJoinDate(new Date());
		record.setRegularDate(new Date());

		return record;
	}

	@Override
	public PageInfo selectByListPage(UserCompanySearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<XcompanyStaff> list = xCompanyStaffMapper.selectByListPage(searchVo);

		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				XcompanyStaff item = list.get(i);
				StaffListVo vo = changeToStaffLisVo(item);
				list.set(i, vo);
			}
		}
		PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public List<XcompanyStaff> selectBySearchVo(Long companyId, Long deptId) {

		return xCompanyStaffMapper.selectBySearchVo(companyId, deptId);

	}

	@Override
	public List<XcompanyStaff> selectByCompanyIdAndDeptId(Long companyId, Long deptId) {

		return xCompanyStaffMapper.selectByCompanyIdAndDeptId(companyId, deptId);
	}

	@Override
	public XcompanyStaff selectByCompanyIdAndUserId(Long companyId, Long userId) {

		return xCompanyStaffMapper.selectByCompanyIdAndUserId(companyId, userId);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {

		return xCompanyStaffMapper.deleteByPrimaryKey(id);
	}

	@Override
	public StaffListVo changeToStaffLisVo(XcompanyStaff item) {

		StaffListVo vo = new StaffListVo();
		Long deptId = item.getDeptId();

		Users users = usersService.selectByPrimaryKey(item.getUserId());

		BeanUtilsExp.copyPropertiesIgnoreNull(users, vo);

		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);

		vo.setId(item.getId());

		Long companyId = item.getCompanyId();

		vo.setStaffType(item.getStaffType());
		vo.setStaffTypeName(XcompanyUtil.getStaffTypeName(item.getStaffType()));
		// 部门名称
		XcompanyDept xcompanyDept = xcompanyDeptService.selectByXcompanyIdAndDeptId(companyId, deptId);
		if (xcompanyDept != null) {
			vo.setDeptName(xcompanyDept.getName());
		} else {
			vo.setDeptName("未分配");
		}
		return vo;
	}
	
	@Override
	public List<StaffListVo> changeToStaffLisVos(Long companyId, List<XcompanyStaff> list) {
		
		List<StaffListVo> result = new ArrayList<StaffListVo>();
		
		if (list.isEmpty()) return result;
		
		List<Long> userIds = new ArrayList<Long>();
		
		for (XcompanyStaff item : list) {
			if (!userIds.contains(item.getUserId())) {
				userIds.add(item.getUserId());
			}
		}
		
		List<Users> userLists = new ArrayList<Users>();
		if (!userIds.isEmpty()) {
			userLists = usersService.selectByUserIds(userIds);
		}
		
		List<XcompanyDept> deptList = new ArrayList<XcompanyDept>();
		deptList = xcompanyDeptService.selectByXcompanyId(companyId);
		
		for (int i = 0 ; i < list.size(); i++) {
			XcompanyStaff item = list.get(i);
		
			StaffListVo vo = new StaffListVo();
			Long deptId = item.getDeptId();
	
			Users users = null;
			
			for (Users u : userLists) {
				if (u.getId().equals(item.getUserId())) {
					users = u;
					break;
				}
			}
	
			BeanUtilsExp.copyPropertiesIgnoreNull(users, vo);
	
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
	
			vo.setId(item.getId());
	
			vo.setStaffType(item.getStaffType());
			vo.setStaffTypeName(XcompanyUtil.getStaffTypeName(item.getStaffType()));
			// 部门名称
			
			vo.setDeptName("未分配");
			for (XcompanyDept dept : deptList) {
				if (dept.getDeptId().equals(item.getDeptId())) {
					vo.setDeptName(dept.getName());
					break;
				}
			}

			result.add(vo);
		}
		
		return result;
	}	

	@Override
	public List<XcompanyStaff> selectByCompanyId(Long companyId) {

		return xCompanyStaffMapper.selectByCompanyId(companyId);
	}

	@Override
	public int insertSelective(XcompanyStaff xcompanyStaff) {

		return xCompanyStaffMapper.insertSelective(xcompanyStaff);
	}

	@Override
	public XcompanyStaff selectByPrimarykey(Long id) {

		return xCompanyStaffMapper.selectByPrimaryKey(id);

	}

	@Override
	public List<XcompanyStaff> selectByUserId(Long userId) {

		return xCompanyStaffMapper.selectByUserId(userId);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyStaff xcompanyStaff) {

		return xCompanyStaffMapper.updateByPrimaryKeySelective(xcompanyStaff);
	}

	// 获得最大的员工编号
	@Override
	public String getNextJobNumber(Long companyId) {

		String maxJobNumber = "0001";
		String curJobNumber = xCompanyStaffMapper.getNextJobNumber(companyId);
		if (!StringUtil.isEmpty(curJobNumber)) {
			int num = Integer.parseInt(curJobNumber) + 1;
			maxJobNumber = String.format("%04d", num);
		}
		return maxJobNumber;
	}
	
	// 导入通讯录，校验方法
	@SuppressWarnings("unchecked")
	@Override
	public AppResultData<Object> validateStaffImport(Long companyId, List<Object> excelDatas) throws Exception {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		//1. 检测数据是否为空
		if (excelDatas.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("Excel表格数据为空，请下载模板后填写.");
			return result;
		}

		//2. 检测表头是否正确
		List<String> tableHeaderDatas = (List<String>) excelDatas.get(0);
		String tableHeaderError = this.validateTableHeader(tableHeaderDatas);
		if (!StringUtil.isEmpty(tableHeaderError)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(tableHeaderError);
			return result;
		}
		
		//3. 检测表格数据是否正确.
		List<Object> validateDatas = this.validateDatas(companyId, excelDatas);
		if (!validateDatas.isEmpty() && validateDatas.size() > 0) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("表格数据填写有误，请查看");
			result.setData(validateDatas);
			return result;
		}
		
		//4. 检测表格数据与库中是否有冲突，通过工号来排除
		List<Object> validateDataExist = this.validateDataExist(companyId, excelDatas);
		if (!validateDataExist.isEmpty() && validateDataExist.size() > 0) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("表格数据填写有冲突，请查看");
			result.setData(validateDatas);
			return result;
		}
		
		return result;
	}
	
	// 导入通讯录，校验方法
	@SuppressWarnings("unchecked")
	@Override
	public AppResultData<Object> staffImport(String fileName) throws Exception {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		//先进行excel 解析
		List<Object> excelDatas = new ArrayList<Object>();
		
		InputStream in = new FileInputStream(fileName);
		excelDatas = ExcelUtil.readToList(fileName, in, 0, 0);
		
		for (int i = 1; i < excelDatas.size(); i++) {
			List<String> item = (List<String>) excelDatas.get(i);
			
			String name = item.get(0);
			String mobile = item.get(1);
			String jobName = item.get(2);

			
//			Users u = usersService.selectByMobile(mobile);
//			
//			if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
//				u = usersService.genUser(mobile, "", Constants.USER_APP);
//			}
//			
//			u.setName(name);
			
			
		}			
		
		
		
		return result;
	}	
	
	
	private String validateTableHeader(List<String> datas) {
		String error = "";

		Boolean tableHeaderFalg = true;
		
		if (datas.isEmpty() || datas.size() < 7) {
			tableHeaderFalg = false;
			System.out.println("表格表头不对，请按照模板的格式填写.");
			error = "表格表头不对，请按照模板的格式填写.";
			return error;
		}
		
//		for (int i = 0; i < datas.size(); i++) {
//			System.out.println(datas.get(i));
//		}
		
		
		if (!datas.get(0).equals("*姓名")) tableHeaderFalg = false;
		if (!datas.get(1).equals("*手机号")) tableHeaderFalg = false;
		if (!datas.get(2).equals("*工号")) tableHeaderFalg = false;
		if (!datas.get(3).equals("*职位")) tableHeaderFalg = false;
		if (!datas.get(4).equals("*员工类型")) tableHeaderFalg = false;
		if (!datas.get(5).equals("身份证号")) tableHeaderFalg = false;
		if (!datas.get(6).equals("入职时间(yyyy-mm-dd)")) tableHeaderFalg = false;
		if (!datas.get(7).equals("邮箱")) tableHeaderFalg = false;
		
		if (!tableHeaderFalg) {
			System.out.println("表格表头不对，请按照模板的格式填写.");
			error = "表格表头不对，请按照模板的格式填写.";
		}		
		
		return error;
	}	
	
	@SuppressWarnings("unchecked")
	private List<Object> validateDatas(Long companyId, List<Object> datas) {
		List<Object> result = new ArrayList<Object>();
		
//		int totalNum = 0;
		for (int i = 1; i < datas.size(); i++) {
			List<String> item = (List<String>) datas.get(i);
			List<String> errorItem = item;
			String error = "";
			
			//姓名为必填项
			if (StringUtil.isEmpty(item.get(0))) error+= "姓名为必填项<br>";		

			//手机号为必填项
			if (StringUtil.isEmpty(item.get(1))) {
				error+= "手机号为必填项<br>";
			} else {
				//手机号校验
				if (!RegexUtil.isMobile(item.get(1))) {
					error+= "手机号填写不正确<br>";
				}
			}
			
			//如果工号不为空，则需要验证工号是否正确
			if (StringUtil.isEmpty(item.get(2))) {
				error+= "工号为必填项<br>";		
			} else {
				if (!RegexUtil.isInteger(item.get(2))) {
					error+= "工号填写不正确<br>";		
				} else {
					int jobNumber = Integer.parseInt(item.get(2));
					if (jobNumber < 1 || jobNumber > 9999) {
						error+= "工号范围在 0001 - 9999区间<br>";		
					}
				}
			}
			
			//职位为必填项
			if (StringUtil.isEmpty(item.get(3))) error+= "职位为必填项<br>";		
			
			//员工类型为必填项
			if (StringUtil.isEmpty(item.get(4))) error+= "员工类型为必填项<br>";		
			
			//如果身份证号不为空，则需要检测身份证是否正确
			if (!StringUtil.isEmpty(item.get(5))) {
				if (!RegexUtil.isIdCardNo(item.get(5))) {
					error+= "身份证号填写不正确<br>";		
				}
			}

			//如果入职时间不为空，则需要判断是否是否为正确格式 yyyy-mm-dd
			if (!StringUtil.isEmpty(item.get(6))) {
				if (!DateUtil.isDate(item.get(6))) {
					error+= "入职时间格式不正确<br>";	
				}
			}
			
			//如果邮箱不为空,则检测邮箱是否正确
			if (!StringUtil.isEmpty(item.get(7))) {
				if (!RegexUtil.isEmail(item.get(7))) {
					error+= "邮箱填写不正确<br>";		
				}
			}
			
			if (!StringUtil.isEmpty(error)) {
				errorItem.add(String.valueOf(i+1));
				errorItem.add(error);
				
				for (int j =0; j < errorItem.size(); j++) {
					String v = errorItem.get(j);
					v = StringUtil.setDoubleQuote(v);
					errorItem.set(j, v);
				}
				
				result.add(errorItem);
			}
			
		}		
		
		return result;
	}	
	
	@SuppressWarnings("unchecked")
	private List<Object> validateDataExist(Long companyId, List<Object> datas) {
		List<Object> result = new ArrayList<Object>();
		
		//公司所有的员工列表
		List<XcompanyStaff> existLists = this.selectByCompanyId(companyId);
		List<StaffListVo> existStaffs = this.changeToStaffLisVos(companyId, existLists);
//		int totalNum = 0;
		for (int i = 1; i < datas.size(); i++) {
			List<String> item = (List<String>) datas.get(i);
			List<String> errorItem = item;
			String error = "";
			
			String name = item.get(0).trim();
			String mobile = item.get(1).trim();
			String jobNumber = item.get(2).trim();
			
			for (StaffListVo vo : existStaffs) {
				if (vo.getJobNumber().equals(jobNumber)) {
					if (!vo.getName().equals(name) || !vo.getMobile().equals(mobile)) {
						errorItem.add(String.valueOf(i+1));
						errorItem.add("工号有冲突,已存在工号为"+jobNumber +",姓名:" + vo.getName() + ",手机号:"+vo.getMobile() + "<br>");
						
						for (int j =0; j < errorItem.size(); j++) {
							String v = errorItem.get(j);
							v = StringUtil.setDoubleQuote(v);
							errorItem.set(j, v);
						}
						
						result.add(errorItem);
						break;
					}
				}
			}
		}
		
		return result;
	}		
	
	
	

}
