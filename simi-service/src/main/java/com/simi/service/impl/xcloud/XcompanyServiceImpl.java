package com.simi.service.impl.xcloud;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyAdminService;
import com.simi.vo.xcloud.CompanyAdminSearchVo;
import com.simi.vo.xcloud.CompanySearchVo;
import com.simi.vo.xcloud.XcompanyVo;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyAdmin;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyMapper;

@Service
public class XcompanyServiceImpl implements XCompanyService {

	@Autowired
	XcompanyMapper xCompanyMapper;
	
	@Autowired
	private XcompanyAdminService xCompanyAdminService;

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

}