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
import com.simi.po.model.partners.PartnerServicePrice;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.partners.PartnerServicePriceService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerServiceTypeTreeVo;
import com.simi.vo.partners.PartnerServicePriceSearchVo;

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
	public List<PartnerServiceType> selectByPartnerId(Long parentId) {
		return partnerServiceTypeMapper.selectByParentId(parentId);
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
	public List<PartnerServiceTypeTreeVo> listChain(List<Long> partnerIds) {
		List<PartnerServiceTypeTreeVo> listVo = new ArrayList<PartnerServiceTypeTreeVo>();
		// 根据parentId=0 查询出所用的父节点

		PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
		searchVo.setParentId(0L);
		searchVo.setPartnerIds(partnerIds);
		// searchVo.setIsEnable((short)1);

		List<PartnerServiceType> list = selectBySearchVo(searchVo);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			PartnerServiceType partnerServiceType = (PartnerServiceType) iterator.next();
			PartnerServiceTypeTreeVo vo = ToTree(partnerServiceType.getId(), partnerIds);
			listVo.add(vo);
		}
		return listVo;
	}

	@Override
	public List<PartnerServiceType> selectBySearchVo(PartnerServiceTypeSearchVo searchVo) {
		return partnerServiceTypeMapper.selectBySearchVo(searchVo);
	}

	/**
	 * 查询出根节点和所有的子节点，封装到Vo中
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public PartnerServiceTypeTreeVo ToTree(Long id, List<Long> partnerIds) {
		PartnerServiceTypeTreeVo partnerServiceTypeVo = new PartnerServiceTypeTreeVo();

		// 根据id查出某对象
		PartnerServiceType partnerServiceType = partnerServiceTypeMapper.selectByPrimaryKey(id);
		try {
			// 赋值给树形结构的vo
			BeanUtils.copyProperties(partnerServiceTypeVo, partnerServiceType);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 已id作为parentId查询出所用的子节点
		PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
		searchVo.setParentId(id);
		searchVo.setPartnerIds(partnerIds);

		List<PartnerServiceType> child = selectBySearchVo(searchVo);
		for (PartnerServiceType partnerServiceType2 : child) {
			PartnerServiceTypeTreeVo vo = ToTree(partnerServiceType2.getId(), partnerIds);
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
		partnerServiceType.setIsEnable((short) 1);
		partnerServiceType.setNo(0);
		partnerServiceType.setPartnerId(0L);
		return partnerServiceType;
	}

	@Override
	public List<PartnerServiceType> selectByParentId(Long parentId) {

		return partnerServiceTypeMapper.selectByParentId(parentId);
	}



	@SuppressWarnings("rawtypes")
	@Override
	public PageInfo selectByListPage(PartnerServiceTypeSearchVo searchVo, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		
		List<PartnerServiceType> list = partnerServiceTypeMapper.selectByListPage(searchVo);
		
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
	}

}
