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
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.dict.DictRegion;
import com.simi.po.model.partners.PartnerRefRegion;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.Partners;
import com.simi.service.partners.PartnersService;
import com.simi.vo.partners.PartnerFormVo;
import com.simi.vo.partners.PartnersSearchVo;
import com.simi.vo.partners.PartnersVo;

@Service
public class PartnersServiceImpl implements PartnersService {

	@Autowired
	private PartnersMapper partnersMapper;
	
	@Autowired
	private PartnerRefServiceTypeMapper partnerRefServiceTypeMapper;
	
	@Autowired
	private PartnerServiceTypeMapper partnerServiceTypeMapper;
	
	@Autowired
	private PartnerRefRegionMapper partnerRefRegionMapper;
	
	@Autowired
	private DictCityMapper dictCityMapper;
	
	@Autowired
	private DictRegionMapper dictRegionMapper;
	
	@Autowired
	private SpiderPartnerMapper spiderPartnerMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return partnersMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Partners record) {
		return partnersMapper.insert(record);
	}

	@Override
	public int insertSelective(Partners record) {
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
	public PageInfo searchVoListPage(PartnersSearchVo partnersSearchVo, int pageNo, int pageSize) {
		Map<String,Object> conditions = new HashMap<String, Object>();
		String companyName = partnersSearchVo.getCompanyName();
		Short status = partnersSearchVo.getStatus();
		Short companySize = partnersSearchVo.getCompanySize();
		Short isCooperate = partnersSearchVo.getIsCooperate();
		
		if(!StringUtil.isEmpty(companyName)){
			conditions.put("companyName",companyName.trim());
		}
		if(status !=null && status !=8){
			conditions.put("status", status);
		}
		if(companySize !=null && companySize !=8){
			conditions.put("companySize", companySize);
		}
		if(isCooperate !=null && isCooperate !=8){
			conditions.put("isCooperate", isCooperate);
		}
		PageHelper.startPage(pageNo, pageSize);
		List<Partners> list = partnersMapper.selectByListPage(conditions);
		
		
		 if(list!=null && list.size()!=0){
             List<PartnersVo> orderViewList = this.getPartnersViewList(list);

             for(int i = 0; i < list.size(); i++) {
            	 if (orderViewList.get(i) != null) {
            		 list.set(i, orderViewList.get(i));
            	 }
             }
         }

		/*List<PartnersVo> listVo = new ArrayList<PartnersVo>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Partners partners = (Partners) iterator.next();
			PartnersVo partnersVo = new PartnersVo();
			SpiderPartner spiderPartner = spiderPartnerMapper.selectByPrimaryKey(partners.getSpiderPartnerId());
			try {
				BeanUtils.copyProperties(partnersVo, spiderPartner);
				partnersVo.setRegisterTime(spiderPartner.getRegisterTime());
				partnersVo.setSpiderUrl(spiderPartner.getSpiderUrl());
			}catch (Exception e) {
				e.printStackTrace();
			}
			listVo.add(partnersVo);
			
		}*/
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
		
	}
	public List<PartnersVo> getPartnersViewList(List<Partners> list) {

	
	//	List<Long> spiderPartnerIds = new ArrayList<Long>();
		List<Long> partnerIds = new ArrayList<Long>();
	     Partners item = null;
	    //获得所有spiderPartnerId集合
	     for (int i = 0 ; i < list.size(); i ++) {
	     	item = list.get(i);
	     	partnerIds.add(item.getSpiderPartnerId());
	     }
	     //根据集合查询出所有对应的SpiderPartner集合
	   // List<SpiderPartner> spiderPartnersList = spiderPartnerMapper.selectBySpiderIds(spiderPartnerIds);
	     List<Partners> partnersList = partnersMapper.selectBySpiderIds(partnerIds);
	    List<PartnersVo> result = new ArrayList<PartnersVo>();
	    
   //  Long spiderPartnerId = 0L;
     Long partnerId = 0L;
     for (int i = 0 ; i < list.size(); i ++) {
     	item = list.get(i);
     	partnerId = item.getPartnerId();
     	PartnersVo vo = new PartnersVo();
     	try {
     		BeanUtilsExp.copyPropertiesIgnoreNull(item,vo);
			//BeanUtils.copyProperties(vo,item);
		} catch (Exception e) {
			e.printStackTrace();
		}
     	//String registerTime = "";
     	String spiderUrl = "";
     	
     	Partners partners = null;
     	for(int n = 0; n < partnersList.size(); n++) {
     		partners = partnersList.get(n);
     		if (partners.getSpiderPartnerId().equals(partnerId)) {
     			//registerTime = partners.getRegisterTime().toString();
     			spiderUrl = partners.getSpiderUrl();
     			break;
     		}
     	}
     	/*SpiderPartner spiderPartner = null;
     	for(int n = 0; n < spiderPartnersList.size(); n++) {
     		spiderPartner = spiderPartnersList.get(n);
     		if (spiderPartner.getSpiderPartnerId().equals(spiderPartnerId)) {
     			registerTime = spiderPartner.getRegisterTime();
     			spiderUrl = spiderPartner.getSpiderUrl();
     			break;
     		}
     	}*/
     //	vo.setRegisterTime(DateUtil.parse(registerTime));
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
		vo.setSpiderPartnerId(0L);
		vo.setCompanyName("");
		vo.setRegisterType((short)0L);
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
		vo.setIsDoor((short)0);
		vo.setKeywords("");
		vo.setStatus((short)0);
		vo.setCompanySize((short)0);
		vo.setIsCooperate((short)0);
		vo.setStatusRemark("");
		vo.setBusinessDesc("");
		vo.setWeixin("");
		vo.setQq("");
		vo.setProvinceId(0L);
		vo.setEmail("");
		vo.setCityId(0L);
		vo.setIsCooperate((short)0);
		vo.setFax("");
		vo.setPayType((short) 0);
		vo.setDiscout(new BigDecimal(0));
		vo.setRemark("");
		vo.setAdminId(0L);
		vo.setAddTime(TimeStampUtil.getNow()/1000);
		vo.setUpdateTime(TimeStampUtil.getNowSecond());
		return vo;
	}

	@Override
	public Partners selectBySpiderPartnerId(Long spiderPartnerId) {
		return partnersMapper.selectBySpiderPartnerId(spiderPartnerId);
	}
	
	/**
	 * 根据PartnerId查询出所对应的服务类别
	 * 包装PartnerFormVo提供使用
	 */
	@Override
	public PartnerFormVo selectPartnerFormVoByPartnerFormVo(PartnerFormVo partnerFormVo) {

	
		List<PartnerServiceType> list = partnerFormVo.getChildList();
		List<PartnerRefServiceType> partnerServiceTypes =partnerRefServiceTypeMapper.selectByPartnerId(partnerFormVo.getPartnerId());
		for (Iterator iterator = partnerServiceTypes.iterator(); iterator.hasNext();) {
			PartnerRefServiceType partnerRefServiceType = (PartnerRefServiceType) iterator.next();
			if(partnerRefServiceType != null){
				list.add(partnerServiceTypeMapper.selectByPrimaryKey(partnerRefServiceType.getServiceTypeId()));
			}
		}
		return partnerFormVo;
	}
	
	
	/**
	 * 将选中的服务类别添加到服务商服务类别关联表
	 */
	@Override
	public void savePartnerToPartnerType(Long partnerId, Long[] partnerTypeIds) {
		
		int results = partnerRefServiceTypeMapper.deleteByPartnerId(partnerId);
		
		if(results >=0){
			if(partnerTypeIds.length >0){
				for (Long serviceTypeId : partnerTypeIds) {
					PartnerRefServiceType partnerRefServiceType = new PartnerRefServiceType();
					PartnerServiceType partnerServiceType = partnerServiceTypeMapper.selectByPrimaryKey(serviceTypeId); 
					partnerRefServiceType.setPartnerId(partnerId);
					partnerRefServiceType.setServiceTypeId(serviceTypeId);
					partnerRefServiceType.setParentId(partnerServiceType.getParentId());
					partnerRefServiceType.setName(partnerServiceType.getName());
					partnerRefServiceType.setPrice(new BigDecimal(0));
					partnerRefServiceTypeMapper.insertSelective(partnerRefServiceType);
				}
			}
		}
	}

	/**
	 * 根据PartnerId查询出对应的PartnerRefRegion
	 */
	@Override
	public List<PartnerRefRegion> selectByPartnerId(Long partnerId) {
		return partnerRefRegionMapper.selectByPartnerId(partnerId);
	}

	@Override
	public int deleteRegionByPartnerId(Long partnerId) {
		return partnerRefRegionMapper.deleteByPartnerId(partnerId);
	}

	@Override
	public List<DictCity> selelctDictCities() {
		return dictCityMapper.selectAll();
	}

	@Override
	public List<DictRegion> selectDictRegions() {
		return dictRegionMapper.selectAll();
	}

	@Override
	public List<PartnerRefServiceType> selectServiceTypeByPartnerIdAndParentId(Long partnerId, Long parentId) {
		return partnerRefServiceTypeMapper.selectServiceTypeByPartnerIdAndParentId(partnerId, parentId);
	}

	@Override
	public List<PartnerRefServiceType> selectSubServiceTypeByPartnerIdAndParentId(Long partnerId, Long parentId) {
		return partnerRefServiceTypeMapper.selectSubServiceTypeByPartnerIdAndParentId(partnerId, parentId);
	}

	@Override
	public List<Partners> selectByCompanyName(String companyName) {
		return partnersMapper.selectByCompanyName(companyName);
	}

	@Override
	public Partners selectByCompanyNames(String companyName) {
		
		return partnersMapper.selectByCompanyNames(companyName);
	}
	
	
	
	
	
	
	
}