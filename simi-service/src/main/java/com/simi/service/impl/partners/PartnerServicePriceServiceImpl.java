package com.simi.service.impl.partners;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.partners.PartnerServicePriceMapper;
import com.simi.po.model.partners.PartnerServicePrice;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.partners.PartnerServicePriceService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.vo.partners.PartnerServicePriceSearchVo;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerServiceTypeTreeVo;

@Service
public class PartnerServicePriceServiceImpl implements PartnerServicePriceService {

	@Autowired
	private PartnerServicePriceMapper partnerServicePriceMapper;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Override
	public int deleteByPrimaryKey(Long servicePriceId) {
		return partnerServicePriceMapper.deleteByPrimaryKey(servicePriceId);
	}

	@Override
	public Long insert(PartnerServicePrice record) {
		return partnerServicePriceMapper.insert(record);
	}

	@Override
	public Long insertSelective(PartnerServicePrice record) {
		return partnerServicePriceMapper.insertSelective(record);
	}

	@Override
	public PartnerServicePrice selectByPrimaryKey(Long servicePriceId) {
		return partnerServicePriceMapper.selectByPrimaryKey(servicePriceId);
	}

	@Override
	public int updateByPrimaryKeySelective(PartnerServicePrice record) {
		return partnerServicePriceMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PartnerServicePrice record) {
		return partnerServicePriceMapper.updateByPrimaryKey(record);
	}

	@Override
	public PartnerServicePrice initPartnerServicePrice() {
		PartnerServicePrice record = new PartnerServicePrice();

		record.setServicePriceId(0L);
		record.setPartnerId(0L);
		record.setServiceTypeId(0L);
		record.setName("");
		record.setServiceTitle("");
		record.setImgUrl("");
		record.setPrice(new BigDecimal(0));
		record.setDisPrice(new BigDecimal(0));
		record.setOrderType((short) 0);
		record.setOrderDuration((short) 0);
		record.setIsAddr((short) 0);
		record.setContentStandard("");
		record.setContentDesc("");
		record.setContentFlow("");
		record.setVideoUrl("");
		record.setCategory("");
		record.setAction("");
		record.setParams("");
		record.setGotoUrl("");
		record.setExtendId(0L);
		record.setIsEnable((short) 1);
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public PageInfo selectByListPage(PartnerServicePriceSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<PartnerServicePrice> list = partnerServicePriceMapper.selectByListPage(searchVo);
		
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
	}

	@Override
	public List<PartnerServicePrice> selectBySearchVo(PartnerServicePriceSearchVo searchVo) {
		return partnerServicePriceMapper.selectBySearchVo(searchVo);
	}
	
	@Override
	public List<PartnerServiceTypeTreeVo> listChain(List<Long> partnerIds) {
		List<PartnerServiceTypeTreeVo> listVo = new ArrayList<PartnerServiceTypeTreeVo>();
		// 根据parentId=0 查询出所用的父节点
		
		PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
		searchVo.setParentId(0L);
		searchVo.setPartnerIds(partnerIds);
		
		List<PartnerServiceType> list = partnerServiceTypeService.selectBySearchVo(searchVo);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			PartnerServiceType partnerServiceType = (PartnerServiceType) iterator.next();
			
			PartnerServiceTypeTreeVo vo = new PartnerServiceTypeTreeVo();
			
			BeanUtilsExp.copyPropertiesIgnoreNull(partnerServiceType, vo);
			
			PartnerServicePriceSearchVo searchVo1 = new PartnerServicePriceSearchVo();
			searchVo1.setServiceTypeId(partnerServiceType.getId());
			List<PartnerServicePrice> childs = selectBySearchVo(searchVo1);
			
			for (PartnerServicePrice servicePrice : childs) {
				PartnerServiceTypeTreeVo child = new PartnerServiceTypeTreeVo();
				child.setParentId(partnerServiceType.getId());
				child.setId(servicePrice.getServicePriceId().intValue());
				child.setName(servicePrice.getName());
				if (servicePrice != null) {
					if (servicePrice.getIsEnable() == 0) {
						child.setIsEnableName("已下架");
					}
					if (servicePrice.getIsEnable() == 1) {
						child.setIsEnableName("上架中");
					}
				}
				vo.getChildren().add(child);
				
				
			}
			
			listVo.add(vo);
		}
		return listVo;
	}
	

}
