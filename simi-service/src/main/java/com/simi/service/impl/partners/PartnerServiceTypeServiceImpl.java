package com.simi.service.impl.partners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.simi.po.dao.partners.PartnerServiceTypeMapper;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.vo.partners.PartnerServicePriceDetailVo;
import com.simi.vo.partners.PartnerServicePriceDetailVoAll;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerServiceTypeVo;
import com.simi.vo.partners.PartnerUserServiceTypeVo;

@Service
public class PartnerServiceTypeServiceImpl implements PartnerServiceTypeService {

	@Autowired
	private PartnerServiceTypeMapper partnerServiceTypeMapper;
	
	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;
	
	@Override
	public int deleteByPrimaryKey(Long serviceTypeId) {
		return partnerServiceTypeMapper.deleteByPrimaryKey(serviceTypeId);
	}

	@Override
	public int insert(PartnerServiceType record) {
		return partnerServiceTypeMapper.insert(record);
	}

	@Override
	public int insertSelective(PartnerServiceType record) {
		return partnerServiceTypeMapper.insertSelective(record);
	}

	@Override
	public PartnerServiceType selectByPrimaryKey(Long serviceTypeId) {
		return partnerServiceTypeMapper.selectByPrimaryKey(serviceTypeId);
	}

	@Override
	public int updateByPrimaryKeySelective(PartnerServiceType record) {
		return partnerServiceTypeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PartnerServiceType record) {
		return partnerServiceTypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<PartnerServiceTypeVo> listChain(Short viewType, List<Long> partnerIds) {
		List<PartnerServiceTypeVo> listVo = new ArrayList<PartnerServiceTypeVo>();
		//根据parentId=0 查询出所用的父节点
		
		PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
		searchVo.setParentId(0L);
		searchVo.setViewType((short) 0);
		searchVo.setPartnerIds(partnerIds);
		//searchVo.setIsEnable((short)1);
		
		List<PartnerServiceType> list = selectBySearchVo(searchVo);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			PartnerServiceType partnerServiceType = (PartnerServiceType) iterator.next();
			PartnerServiceTypeVo vo = ToTree(partnerServiceType.getId(), viewType, partnerIds);
			listVo.add(vo);
		}
		return listVo;
	}
		
	@Override
	public List<PartnerServiceType> selectByIds(List<Long> ids) {
		return partnerServiceTypeMapper.selectByIds(ids);
	}	
	
	@Override
	public List<PartnerServiceType> selectBySearchVo(PartnerServiceTypeSearchVo searchVo) {
		return partnerServiceTypeMapper.selectBySearchVo(searchVo);
	}	
	
	/**
	 * 查询出根节点和所有的子节点，封装到Vo中
	 * @param id
	 * @return
	 */
	@Override
	public PartnerServiceTypeVo ToTree(Long id, Short viewType, List<Long> partnerIds) {
		PartnerServiceTypeVo partnerServiceTypeVo = new PartnerServiceTypeVo();
		
		//根据id查出某对象
		PartnerServiceType partnerServiceType = partnerServiceTypeMapper.selectByPrimaryKey(id);
		try {
			//赋值给树形结构的vo
			BeanUtils.copyProperties(partnerServiceTypeVo, partnerServiceType);
		}  catch (Exception e1) {
			e1.printStackTrace();
		}
		//已id作为parentId查询出所用的子节点
		PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
		searchVo.setParentId(id);
		searchVo.setViewType(viewType);
		searchVo.setPartnerIds(partnerIds);		
		
		
		List<PartnerServiceType> child = selectBySearchVo(searchVo);
		for (PartnerServiceType partnerServiceType2 : child) {
			PartnerServiceTypeVo vo =   ToTree(partnerServiceType2.getId().longValue(), viewType, partnerIds);
			partnerServiceTypeVo.getChildren().add(vo);
		}
		if (partnerServiceType != null) {
			if (partnerServiceType.getIsEnable() == 0) {
				partnerServiceTypeVo.setIsEnableName("已下架");
			}
			if (partnerServiceType.getIsEnable() == 1) {
				partnerServiceTypeVo.setIsEnableName("上架中");
			}
		}
		return partnerServiceTypeVo;
	}

	@Override
	public PartnerServiceType initPartnerServiceType() {
		PartnerServiceType partnerServiceType = new PartnerServiceType();
		partnerServiceType.setParentId(0L);
		partnerServiceType.setName("");
		partnerServiceType.setId(0L);
		partnerServiceType.setIsEnable((short)1);
		partnerServiceType.setViewType((short) 0);
		partnerServiceType.setNo(0);
		partnerServiceType.setPartnerId(0L);
		return partnerServiceType;
	}

	@Override
	public List<PartnerServiceType> selectByParentId(Long parentId) {
		
		return partnerServiceTypeMapper.selectByParentId(parentId);
	}

	@Override
	public List<PartnerServiceType> selectByPartnerIdIn(Long partnerId) {
		
		return partnerServiceTypeMapper.selectByPartnerIdIn(partnerId);
	}

	@Override
	public PartnerServicePriceDetailVoAll getPartnerPriceList(
			PartnerServiceType item,Long userId) {
		PartnerServicePriceDetailVoAll vo = new PartnerServicePriceDetailVoAll();
		PartnerServicePriceDetail priceDetail = partnerServicePriceDetailService.initPartnerServicePriceDetail();
		PartnerServicePriceDetail detail = partnerServicePriceDetailService.selectByServicePriceId(item.getId());
	
		if (detail== null) {
			detail = priceDetail;
		}
		BeanUtilsExp.copyPropertiesIgnoreNull(detail, vo);
		vo.setPartnerId(item.getPartnerId());
		vo.setName(item.getName());
		vo.setNo(item.getNo());
		vo.setParentId(item.getParentId());
		vo.setUserId(userId);
		if (item != null) {
			if (item.getIsEnable() != null && item.getIsEnable() == 0) {
				vo.setIsEnableName("已下架");
			}
			if (item.getIsEnable() != null && item.getIsEnable() == 1) {
				vo.setIsEnableName("未下架");
			}
			
		}
		return vo;
		
	}

	@Override
	public PageInfo selectByListPage(PartnerUserServiceTypeVo searchVo, int pageNo,
			int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		//找出服务商对应的服务大类的集合
	//	List<PartnerServiceType> list = partnerServiceTypeMapper.selectByPartnerIdIn(searchVo.getPartnerId());
		//得到服务大类id的集合
		/*List<Long> serviceTypeIds = new ArrayList<Long>();
		for (int i = 0; i < list.size(); i++) {
			
			PartnerServiceType serviceType =list.get(i);
			serviceTypeIds.add(serviceType.getId());
		}*/
		//searchVo.setServiceTypeIds(serviceTypeIds);
		List<PartnerServicePriceDetail> detailList = partnerServicePriceDetailService.selectByListPage(searchVo);
			for (int i =0 ; i < detailList.size(); i++) {
				PartnerServicePriceDetail item = detailList.get(i);
				PartnerServicePriceDetailVo vo = this.changeToDetailVo(item) ;
				
				detailList.set(i, vo);
			}
		PageInfo pageInfo = new PageInfo(detailList);
		return pageInfo;
	}

	private PartnerServicePriceDetailVo changeToDetailVo(
			PartnerServicePriceDetail item) {
		
		PartnerServicePriceDetailVo vo = new PartnerServicePriceDetailVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
		
		PartnerServiceType serviceType = partnerServiceTypeMapper.selectByPrimaryKey(item.getServicePriceId());
		vo.setName(serviceType.getName());
		vo.setNo(serviceType.getNo());
		vo.setServiceTypeId(serviceType.getId());
		vo.setParentId(serviceType.getParentId());
		vo.setPartnerId(serviceType.getPartnerId());
		vo.setIsEnable(serviceType.getIsEnable());
		return vo;
	}
	
	

}
