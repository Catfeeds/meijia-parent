package com.simi.service.impl.partners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.po.dao.partners.PartnerServiceTypeMapper;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerServiceTypeVo;

@Service
public class PartnerServiceTypeServiceImpl implements PartnerServiceTypeService {

	@Autowired
	private PartnerServiceTypeMapper partnerServiceTypeMapper;
	
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
		return partnerServiceTypeVo;
	}

	@Override
	public PartnerServiceType initPartnerServiceType() {
		PartnerServiceType partnerServiceType = new PartnerServiceType();
		partnerServiceType.setParentId(0L);
		partnerServiceType.setName("");
		partnerServiceType.setId(0L);
		partnerServiceType.setViewType((short) 0);
		partnerServiceType.setNo(0);
		partnerServiceType.setPartnerId(0L);
		return partnerServiceType;
	}
	
	

}
