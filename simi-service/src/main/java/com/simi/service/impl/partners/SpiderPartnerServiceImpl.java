package com.simi.service.impl.partners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.partners.SpiderPartnerMapper;
import com.simi.po.model.partners.SpiderPartner;
import com.simi.service.partners.SpiderPartnerService;
import com.simi.vo.partners.PartnersSearchVo;

@Service
public class SpiderPartnerServiceImpl implements SpiderPartnerService {

	@Autowired
	private SpiderPartnerMapper spiderPartnerMapper;
	@Override
	public int deleteByPrimaryKey(Long spiderPartnerId) {
		return spiderPartnerMapper.deleteByPrimaryKey(spiderPartnerId);
	}

	@Override
	public int insert(SpiderPartner record) {
		return spiderPartnerMapper.insert(record);
	}

	@Override
	public int insertSelective(SpiderPartner record) {
		return spiderPartnerMapper.insertSelective(record);
	}

	@Override
	public SpiderPartner selectByPrimaryKey(Long spiderPartnerId) {
		return spiderPartnerMapper.selectByPrimaryKey(spiderPartnerId);
	}

	@Override
	public int updateByPrimaryKeySelective(SpiderPartner record) {
		return spiderPartnerMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(SpiderPartner record) {
		return spiderPartnerMapper.updateByPrimaryKeyWithBLOBs(record);
	}
	
	@Override
	public int updateByPrimaryKey(SpiderPartner record) {
		return spiderPartnerMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageInfo searchVoListPage(PartnersSearchVo partnersSearchVo, int pageNo, int pageSize) {
		Map<String,Object> conditions = new HashMap<String, Object>();
		String companyName = partnersSearchVo.getCompanyName();
		Short status = partnersSearchVo.getStatus();
		String serviceType = partnersSearchVo.getServiceType();
		if(!StringUtil.isEmpty(companyName)){
			conditions.put("companyName",companyName.trim());
		}
		if(!StringUtil.isEmpty(serviceType) && !serviceType.trim().equals("全部") ){
			conditions.put("serviceType",serviceType.trim());
		}
		if(status !=null && status !=8){
			conditions.put("status", status);
		}
		PageHelper.startPage(pageNo, pageSize);
		List<SpiderPartner> list = spiderPartnerMapper.selectByListPage(conditions);;
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
	}

	@Override
	public SpiderPartner initSpiderPartner() {
		SpiderPartner spiderPartner = new SpiderPartner();
		spiderPartner.setAddr("");
		spiderPartner.setAddTime(TimeStampUtil.getNowSecond());
		spiderPartner.setCertification("");
		spiderPartner.setCompanyDesc("");
		spiderPartner.setCompanyDescImg("");
		spiderPartner.setCompanyLogo("");
		spiderPartner.setCompanySize((short)0);
		spiderPartner.setCompanyName("");
		spiderPartner.setCreditFileUrl("");
		spiderPartner.setLastSpiderTime(TimeStampUtil.getNowSecond());
		spiderPartner.setLinkMan("");
		spiderPartner.setLinkTel("");
		spiderPartner.setRegisterTime("");
		spiderPartner.setServiceArea("");
		spiderPartner.setServiceType("");
		spiderPartner.setServiceTypeId(0L);
		spiderPartner.setServiceTypeSub("");
		spiderPartner.setSpiderPartnerId(0L);
		spiderPartner.setSpiderUrl("");
		spiderPartner.setStatus((short)0);
		spiderPartner.setStatusRemark("");
		spiderPartner.setTotalBrowse(0);
		spiderPartner.setTotalOrder(0);
		spiderPartner.setTotalRate((float)0);
		spiderPartner.setWebsite("");
		return spiderPartner;
	}
	

}
