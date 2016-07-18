package com.simi.service.impl.partners;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.dict.DictCityMapper;
import com.simi.po.dao.dict.DictRegionMapper;
import com.simi.po.dao.partners.PartnerRefRegionMapper;
import com.simi.po.dao.partners.PartnerRefServiceTypeMapper;
import com.simi.po.dao.partners.PartnerServiceTypeMapper;
import com.simi.po.dao.partners.PartnersMapper;
import com.simi.po.dao.partners.SpiderPartnerMapper;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictRegion;
import com.simi.po.model.partners.PartnerRefRegion;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.user.Users;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.partners.PartnerRefCityService;
import com.simi.service.partners.PartnerRefRegionService;
import com.simi.service.partners.PartnerRefServiceTypeService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.UsersService;
import com.simi.vo.partners.PartnerFormVo;
import com.simi.vo.partners.PartnersSearchVo;
import com.simi.vo.partners.PartnersVo;
import com.simi.vo.user.UserSearchVo;

@Service
public class PartnersServiceImpl implements PartnersService {

	@Autowired
	private PartnersMapper partnersMapper;
	
	@Autowired
	private UsersService usersService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private PartnerRefServiceTypeService partnerRefServiceTypeService;

	@Autowired
	private PartnerRefCityService partnerRefCityService;

	@Autowired
	private PartnerRefRegionService partnerRefRegionService;
	
	@Autowired
	private PartnerUserService partnerUserService;
	
	@Autowired
	private AdminAccountService adminAccountService;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return partnersMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(Partners record) {
		return partnersMapper.insert(record);
	}

	@Override
	public Long insertSelective(Partners record) {
		return partnersMapper.insertSelective(record);
	}

	@Override
	public Partners selectByPrimaryKey(Long id) {
		return partnersMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Partners record) {
		return partnersMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Partners record) {
		return partnersMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Partners> selectBySearchVo(PartnersSearchVo searchVo) {
		return partnersMapper.selectBySearchVo(searchVo);
	}

	@Override
	public PageInfo selectByListPage(PartnersSearchVo partnersSearchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<Partners> list = partnersMapper.selectByListPage(partnersSearchVo);

		if (list != null && list.size() != 0) {
			List<PartnersVo> orderViewList = this.getPartnerVos(list);

			for (int i = 0; i < list.size(); i++) {
				if (orderViewList.get(i) != null) {
					list.set(i, orderViewList.get(i));
				}
			}
		}

		/*
		 * List<PartnersVo> listVo = new ArrayList<PartnersVo>(); for (Iterator
		 * iterator = list.iterator(); iterator.hasNext();) { Partners partners
		 * = (Partners) iterator.next(); PartnersVo partnersVo = new
		 * PartnersVo(); SpiderPartner spiderPartner =
		 * spiderPartnerMapper.selectByPrimaryKey
		 * (partners.getSpiderPartnerId()); try {
		 * BeanUtils.copyProperties(partnersVo, spiderPartner);
		 * partnersVo.setRegisterTime(spiderPartner.getRegisterTime());
		 * partnersVo.setSpiderUrl(spiderPartner.getSpiderUrl()); }catch
		 * (Exception e) { e.printStackTrace(); } listVo.add(partnersVo);
		 * 
		 * }
		 */
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;

	}

	@Override
	public List<PartnersVo> getPartnerVos(List<Partners> list) {
		
		Long userId = 0L;
		Long adminId = 0L;
		Long partnerId = 0L;
		List<Long> userIds = new ArrayList<Long>();
		List<Long> adminIds = new ArrayList<Long>();
		List<Long> spiderPartnerIds = new ArrayList<Long>();
		List<Long> partnerIds = new ArrayList<Long>();
		Partners item = null;
		// 获得所有spiderPartnerId集合
		for (int i = 0; i < list.size(); i++) {
			item = list.get(i);
			userId = item.getUserId();
			if (!userId.equals(0L) && !userIds.contains(userId)) {
				userIds.add(userId);
			}
			
			partnerId = item.getPartnerId();
			
			if (!partnerId.equals(0L) && !partnerIds.contains(partnerId)) {
				partnerIds.add(partnerId);
			}
			
			adminId = item.getAdminId();
			if (!adminId.equals(0L) && !adminIds.contains(adminId)) {
				adminIds.add(adminId);
			}
			
			Long spiderPartnerId = item.getSpiderPartnerId();
			if (!spiderPartnerId.equals(0L) && !spiderPartnerIds.contains(spiderPartnerId)) {
				spiderPartnerIds.add(item.getSpiderPartnerId());
			}
		}
		
		List<Users> users = new ArrayList<Users>();
		
		if (!userIds.isEmpty()) {
			UserSearchVo usearchVo = new UserSearchVo();
			usearchVo.setUserIds(userIds);
			users = usersService.selectBySearchVo(usearchVo);
		}
		
		List<AdminAccount> admins = new ArrayList<AdminAccount>();
		
		if (!adminIds.isEmpty()) {
			admins = adminAccountService.selectByIds(adminIds);
		}
		
		List<HashMap> totalUsers = new ArrayList<HashMap>();
		if (!partnerIds.isEmpty()) {
			totalUsers = partnerUserService.totalUserByPartnerIds(partnerIds);
		}
		
		
		// 根据集合查询出所有对应的SpiderPartner集合
		// List<SpiderPartner> spiderPartnersList =
		// spiderPartnerMapper.selectBySpiderIds(spiderPartnerIds);
		List<Partners> spiderPartnersList = new ArrayList<Partners>();
		
		if (!spiderPartnerIds.isEmpty()) {
			PartnersSearchVo searchVo = new PartnersSearchVo();
			searchVo.setSpiderPartnerIds(spiderPartnerIds);
			spiderPartnersList = partnersMapper.selectBySearchVo(searchVo);
		}
		
		List<PartnersVo> result = new ArrayList<PartnersVo>();

		// Long spiderPartnerId = 0L;
		for (int i = 0; i < list.size(); i++) {
			item = list.get(i);
			partnerId = item.getPartnerId();
			userId = item.getUserId();
			PartnersVo vo = new PartnersVo();
			try {
				BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
				// BeanUtils.copyProperties(vo,item);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// String registerTime = "";
			//创建人.
			if (!users.isEmpty()) {
				for (Users u : users) {
					if (u.getId().equals(userId)) {
						String name = u.getName();
						vo.setUserName(name);
						vo.setCreate("用户注册");
						vo.setMobile(u.getMobile());
						break;
					}
				}
			}
			
			if (!admins.isEmpty()) {
				for (AdminAccount admin : admins) {
					if (admin.getId().equals(item.getAdminId())) {
						vo.setUserName(admin.getName());
						vo.setCreate("管理员创建");
						break;
					}
				}
			}
			
			if (StringUtil.isEmpty(vo.getUserName())) {
				vo.setUserName("");
				vo.setCreate("采集");
			}
			
			//总业务人员数
			vo.setTotalUser(0);
			if (!totalUsers.isEmpty()) {
				for (HashMap totalUser : totalUsers) {
					Long tmpPartnerId = Long.valueOf(totalUser.get("partner_id").toString());
					if (tmpPartnerId.equals(partnerId)) {
						int total = Integer.valueOf(totalUser.get("total").toString());
						vo.setTotalUser(total);
						break;
					}
				}
			}

			//采集网址
			String spiderUrl = "";
			Partners partners = null;
			for (int n = 0; n < spiderPartnersList.size(); n++) {
				partners = spiderPartnersList.get(n);
				if (partners.getSpiderPartnerId().equals(partnerId)) {
					// registerTime = partners.getRegisterTime().toString();
					spiderUrl = partners.getSpiderUrl();
					break;
				}
			}
			vo.setSpiderUrl(spiderUrl);

			result.add(vo);
		}
		return result;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Partners record) {
		return partnersMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public Partners iniPartners() {
		Partners vo = new Partners();
		vo.setPartnerId(0L);
		vo.setUserId(0L);
		vo.setSpiderPartnerId(0L);
		vo.setCompanyName("");
		vo.setRegisterType((short) 0L);
		vo.setShortName("");
		vo.setAddr("");
		vo.setCreditFileUrl("");
		vo.setWebsite("");
		vo.setRegisterTime(new Date());
		vo.setSpiderUrl("");
		vo.setServiceArea("");
		vo.setServiceType("");
		vo.setCompanyDesc("");
		vo.setCompanyDescImg("");
		vo.setCompanySize((short) 0);
		vo.setIsCooperate((short) 0);
		vo.setCompanyLogo("");
		vo.setIsDoor((short) 0);
		vo.setKeywords("");
		vo.setStatus((short) 0);
		vo.setCompanySize((short) 0);
		vo.setIsCooperate((short) 0);
		vo.setStatusRemark("");
		vo.setBusinessDesc("");
		vo.setWeixin("");
		vo.setQq("");
		vo.setProvinceId(0L);
		vo.setEmail("");
		vo.setCityId(0L);
		vo.setIsCooperate((short) 0);
		vo.setFax("");
		vo.setPayType((short) 0);
		vo.setDiscout(new BigDecimal(0));
		vo.setRemark("");
		vo.setAdminId(0L);
		vo.setAddTime(TimeStampUtil.getNow() / 1000);
		vo.setUpdateTime(TimeStampUtil.getNowSecond());
		return vo;
	}

	/**
	 * 根据PartnerId查询出所对应的服务类别 包装PartnerFormVo提供使用
	 */
	@Override
	public PartnerFormVo selectPartnerFormVoByPartnerFormVo(PartnerFormVo partnerFormVo) {

		List<PartnerServiceType> list = partnerFormVo.getChildList();

		PartnersSearchVo searchVo = new PartnersSearchVo();
		searchVo.setPartnerId(partnerFormVo.getPartnerId());
		List<PartnerRefServiceType> partnerServiceTypes = partnerRefServiceTypeService.selectBySearchVo(searchVo);
		for (Iterator iterator = partnerServiceTypes.iterator(); iterator.hasNext();) {
			PartnerRefServiceType partnerRefServiceType = (PartnerRefServiceType) iterator.next();
			if (partnerRefServiceType != null) {
				list.add(partnerServiceTypeService.selectByPrimaryKey(partnerRefServiceType.getServiceTypeId()));
			}
		}
		return partnerFormVo;
	}

	/**
	 * 将选中的服务类别添加到服务商服务类别关联表
	 */
	@Override
	public void savePartnerToPartnerType(Long partnerId, Long[] partnerTypeIds) {

		int results = partnerRefServiceTypeService.deleteByPartnerId(partnerId);

		if (results >= 0) {
			if (partnerTypeIds.length > 0) {
				for (Long serviceTypeId : partnerTypeIds) {
					PartnerRefServiceType partnerRefServiceType = new PartnerRefServiceType();
					PartnerServiceType partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
					partnerRefServiceType.setPartnerId(partnerId);
					partnerRefServiceType.setServiceTypeId(serviceTypeId);
					partnerRefServiceType.setParentId(partnerServiceType.getParentId());
					partnerRefServiceType.setName(partnerServiceType.getName());
					partnerRefServiceType.setPrice(new BigDecimal(0));
					partnerRefServiceTypeService.insertSelective(partnerRefServiceType);
				}
			}
		}
	}

	@Override
	public Partners selectBySpiderPartnerId(Long spiderPartnerId) {
		return partnersMapper.selectBySpiderPartnerId(spiderPartnerId);
	}
}