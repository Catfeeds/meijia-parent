package com.simi.service.impl.xcloud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.async.UserScoreAsyncService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyAdminService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.utils.XcompanyUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.CompanyAdminSearchVo;
import com.simi.vo.xcloud.CompanySearchVo;
import com.simi.vo.xcloud.XcompanyVo;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyAdmin;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.google.zxing.WriterException;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyMapper;

@Service
public class XcompanyServiceImpl implements XCompanyService {

	@Autowired
	XcompanyMapper xCompanyMapper;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private XcompanyAdminService xCompanyAdminService;
	
	@Autowired
	private XcompanyDeptService xCompanyDeptService;

	@Autowired
	private XcompanyStaffService xCompanyStaffService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	
	@Autowired
	private UserScoreAsyncService userScoreAsyncService;

	@Override
	public Xcompany initXcompany() {
		Xcompany record = new Xcompany();
		record.setCompanyId(0L);
		record.setCompanyType((short) 0);
		record.setCompanyName("");
		record.setCompanyTrade(0L);
		record.setCompanySize((short) 0);
		record.setProvinceId(0L);
		record.setCityId(0L);
		record.setRegionId(0L);
		record.setLongitude(" ");
		record.setLatitude(" ");
		record.setPoiType((short) 0);
		record.setAddrName(" ");
		record.setAddr("");
		record.setLinkMan("");
		record.setInvitationCode("");
		record.setQrCode("");
		record.setEmail("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return xCompanyMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(Xcompany record) {
		return xCompanyMapper.insert(record);
	}

	@Override
	public int insertSelective(Xcompany record) {
		return xCompanyMapper.insertSelective(record);
	}

	@Override
	public Xcompany selectByPrimaryKey(Long id) {
		return xCompanyMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Xcompany record) {
		return xCompanyMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Xcompany record) {
		return xCompanyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Xcompany> selectByListPage(CompanySearchVo searchVo, int pageNo, int pageSize) {
		List<Xcompany> list = xCompanyMapper.selectByListPage(searchVo);
		return list;
	}
	
	@Override
	public List<Xcompany> selectBySearchVo(CompanySearchVo searchVo) {
		return xCompanyMapper.selectBySearchVo(searchVo);
	}

	@Override
	public List<Xcompany> selectByIds(List<Long> ids) {
		return xCompanyMapper.selectByIds(ids);
	}
	
	@Override
	public List<XcompanyVo> getVos(List<Xcompany> list) {
		List<XcompanyVo> result = new ArrayList<XcompanyVo>();
		
		if (list.isEmpty()) return result;
		
		List<Long> companyIds = new ArrayList<Long>();
		for (Xcompany item : list) {
			if (!companyIds.contains(item.getCompanyId())) {
				companyIds.add(item.getCompanyId());
			}
		}
		
		CompanyAdminSearchVo searchVo = new CompanyAdminSearchVo();
		searchVo.setCompanyIds(companyIds);
		searchVo.setIsCreate((short) 1);
		
		List<XcompanyAdmin> companyAdmins = xCompanyAdminService.selectBySearchVo(searchVo);
		
		for (int i = 0; i < list.size(); i++) {
			Xcompany item = list.get(i);
			XcompanyVo vo = new XcompanyVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			for (XcompanyAdmin x : companyAdmins) {
				if (x.getCompanyId().equals(vo.getCompanyId())) {
					vo.setUserName(x.getUserName());
					break;
				}
			}
			result.add(vo);
		}
		
		return result;
	}
	
	@Override
	public AppResultData<Object> regExtend(Long userId, Long companyId) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		
		Users u = usersService.selectByPrimaryKey(userId);
		
		Xcompany xCompany = this.selectByPrimaryKey(companyId);
		// 生成团队唯一邀请码
		String invitationCode = StringUtil.generateShortUuid();
		xCompany.setInvitationCode(invitationCode);

		// 生成团队邀请二维码
		String imgUrl = "";
		try {
			imgUrl = XcompanyUtil.genQrCode(invitationCode);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		xCompany.setQrCode(imgUrl);
		this.updateByPrimaryKeySelective(xCompany);		
		
		
		//积分赠送
		userScoreAsyncService.sendScoreCompany(userId, companyId);

		// 团队部门预置信息.
		List<String> defaultDepts = MeijiaUtil.getDefaultDept();
		for (int i = 0; i < defaultDepts.size(); i++) {
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
		// 将用户加入团队员工中
		XcompanyStaff record = xCompanyStaffService.initXcompanyStaff();
		record.setUserId(u.getId());
		record.setCompanyId(companyId);
		record.setDeptId(deptId);
		record.setIsDefault((short) 1);
		record.setJobNumber(xCompanyStaffService.getNextJobNumber(companyId));
		xCompanyStaffService.insertSelective(record);
		
		return result;
	}
}