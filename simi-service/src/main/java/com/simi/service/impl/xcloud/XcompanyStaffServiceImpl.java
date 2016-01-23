package com.simi.service.impl.xcloud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
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
import com.simi.vo.xcloud.StaffListVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

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
		record.setIsDefault((short) 0);
		record.setStatus((short) 1);
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
	public List<XcompanyStaff> selectBySearchVo(UserCompanySearchVo searchVo) {

		return xCompanyStaffMapper.selectBySearchVo(searchVo);

	}

	@Override
	public int deleteByPrimaryKey(Long id) {

		return xCompanyStaffMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public List<XcompanyStaff> selectByListPage(UserCompanySearchVo searchVo) {
		
		return xCompanyStaffMapper.selectByListPage(searchVo);
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
	public int insertSelective(XcompanyStaff xcompanyStaff) {

		return xCompanyStaffMapper.insertSelective(xcompanyStaff);
	}

	@Override
	public XcompanyStaff selectByPrimarykey(Long id) {

		return xCompanyStaffMapper.selectByPrimaryKey(id);

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
			int num = Integer.parseInt(curJobNumber);
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
			result.setData(validateDataExist);
			return result;
		}
		
		return result;
	}
	
	// 导入通讯录，校验方法
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> checkDuplication(Long companyId, List<Object> excelDatas) throws Exception {
		
		//公司所有的员工列表
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> existLists = this.selectBySearchVo(searchVo);
		List<StaffListVo> existStaffs = this.changeToStaffLisVos(companyId, existLists);	
		
		List<Object> result = new ArrayList<Object>();
		for (int i = 1; i < excelDatas.size(); i++) {
			List<String> item = (List<String>) excelDatas.get(i);
			
//			String name = item.get(0);
//			String mobile = item.get(1);
			String jobNumber = item.get(2);
			jobNumber = jobNumber.replace(",", "");
			jobNumber = String.format("%04d", Integer.parseInt(jobNumber));
					
			
			for (StaffListVo vo : existStaffs) {
				if (vo.getJobNumber().equals(jobNumber)) {
					
					item.add(String.valueOf(i+1));
					for (int j = 0; j < item.size(); j++) {
						String v = item.get(j);
						
						if (j == 2) {
							if (!StringUtil.isEmpty(v)) {
								v = v.replace(",", "");
								v = String.format("%04d", Integer.parseInt(v));
							}
						}
						
						v = StringUtil.setDoubleQuote(v);
						
						item.set(j, v);
						
						
						
					}
					result.add(item);
					break;
				}
			}
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
				String jobNumber = item.get(2);
				jobNumber = jobNumber.replace(",", "");
				if (!RegexUtil.isInteger(jobNumber)) {
					error+= "工号填写不正确<br>";		
				} else {
					int jobNum = Integer.parseInt(jobNumber);
					if (jobNum < 1 || jobNum > 9999) {
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
				System.out.println("入职时间 = " + item.get(6));
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
				
				for (int j = 0; j < errorItem.size(); j++) {
					String v = errorItem.get(j);
					
					if (j == 2) {
						if (!StringUtil.isEmpty(v)) {
							v = v.replace(",", "");
							v = String.format("%04d", Integer.parseInt(v));
						}
					}
					
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
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> existLists = this.selectBySearchVo(searchVo);
		List<StaffListVo> existStaffs = this.changeToStaffLisVos(companyId, existLists);
//		int totalNum = 0;
		for (int i = 1; i < datas.size(); i++) {
			List<String> item = (List<String>) datas.get(i);
			List<String> errorItem = item;
			
			String name = item.get(0).trim();
			String mobile = item.get(1).trim();
			String jobNumber = item.get(2).trim();
			jobNumber = jobNumber.replace(",", "");
			jobNumber = String.format("%04d", Integer.parseInt(jobNumber));
			for (StaffListVo vo : existStaffs) {
				if (vo.getJobNumber().equals(jobNumber)) {
					if (!vo.getName().equals(name) || !vo.getMobile().equals(mobile)) {
						errorItem.add(String.valueOf(i+1));
						errorItem.add("工号有冲突,已存在工号为"+jobNumber +",姓名:" + vo.getName() + ",手机号:"+vo.getMobile() + "<br>");
						
						for (int j =0; j < errorItem.size(); j++) {
							String v = errorItem.get(j);
							
							if (j == 2) {
								if (!StringUtil.isEmpty(v)) {
									v = v.replace(",", "");
									v = String.format("%04d", Integer.parseInt(v));
								}
							}
							
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

	@SuppressWarnings("unchecked")
	@Override
	public AppResultData<Object> staffImport(Long companyId, List<Object> datas) throws Exception {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
				
		//公司所有的员工列表
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> existLists = this.selectBySearchVo(searchVo);
//		List<StaffListVo> existStaffs = this.changeToStaffLisVos(companyId, existLists);
		
		for (int i = 1; i < datas.size(); i++) {
			List<String> item = (List<String>) datas.get(i);
			String name = item.get(0).trim();
			String mobile = item.get(1).trim();
			String jobNumber = item.get(2).trim();
			jobNumber = jobNumber.replace(",", "");
			jobNumber = String.format("%04d", Integer.parseInt(jobNumber));
			String jobName = item.get(3).trim();
			String staffTypeName = item.get(4).trim();
			String idCard = item.get(5).trim();
			String joinDateStr = item.get(6).trim();
			String companyEmail = item.get(7).trim();
			
			
			//先处理注册用户表的情况。
			Users u = usersService.selectByMobile(mobile);

			if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
				u = usersService.genUser(mobile, name, Constants.USER_XCOULD);
			}
			
			Long userId = u.getId();
			
			if (!u.getName().equals(name)) {
				u.setName(name);
				
			}			
			if (!StringUtil.isEmpty(idCard)) u.setIdCard(idCard);
			
			usersService.updateByPrimaryKeySelective(u);
			
			//再处理公司员工表的情况:
			XcompanyStaff record = this.initXcompanyStaff();
			for (XcompanyStaff vo : existLists) {
				if (vo.getUserId().equals(userId)) {
					record = vo;
					break;
				}
			}
			
			record.setCompanyId(companyId);
			record.setUserId(userId);
			record.setJobNumber(jobNumber);
			record.setJobName(jobName);
			record.setStaffType(XcompanyUtil.getStaffType(staffTypeName));
			
			if (!StringUtil.isEmpty(joinDateStr)) {
				Date joinDate = DateUtil.parse(joinDateStr);
				record.setJoinDate(joinDate);
			}
			
			if (!StringUtil.isEmpty(companyEmail)) {
				record.setCompanyEmail(companyEmail);
			}
			
			
			
			if (record.getId() > 0L) {
				updateByPrimaryKeySelective(record);
			} else {
				insertSelective(record);
			}
			
		}
		
		return result;
	}

		
	
	
	

}
