package com.simi.service.impl.partners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.po.dao.partners.PartnerServicePricesMapper;
import com.simi.po.model.partners.PartnerServicePrices;
import com.simi.service.partners.PartnerServicePriceService;
import com.simi.vo.partners.PartnerServicePriceVo;


@Service
public class PartnerServicePriceServiceImpl implements PartnerServicePriceService {

	@Autowired
	private PartnerServicePricesMapper partnerServicePriceMapper;
	
	@Override
	public int deleteByPrimaryKey(Long servicePriceId) {
		return partnerServicePriceMapper.deleteByPrimaryKey(servicePriceId);
	}

	@Override
	public Long insert(PartnerServicePrices record) {
		return partnerServicePriceMapper.insert(record);
	}

	@Override
	public Long insertSelective(PartnerServicePrices record) {
		return partnerServicePriceMapper.insertSelective(record);
	}

	@Override
	public PartnerServicePrices selectByPrimaryKey(Long servicePriceId) {
		return partnerServicePriceMapper.selectByPrimaryKey(servicePriceId);
	}
	
	@Override
	public int updateByPrimaryKeySelective(PartnerServicePrices record) {
		return partnerServicePriceMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PartnerServicePrices record) {
		return partnerServicePriceMapper.updateByPrimaryKey(record);
	}
	
	@Override 
	public List<PartnerServicePrices> selectByParentId(Long parentId) {
		return partnerServicePriceMapper.selectByParentId(parentId);
	}

	@Override
	public List<PartnerServicePriceVo> listChain() {
		List<PartnerServicePriceVo> listVo = new ArrayList<PartnerServicePriceVo>();
		//根据parentId=0 查询出所用的父节点
		List<PartnerServicePrices> list = partnerServicePriceMapper.selectByParentId(0L);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			PartnerServicePrices partnerServicePrice = (PartnerServicePrices) iterator.next();
			PartnerServicePriceVo vo = ToTree(partnerServicePrice.getServicePriceId());
			listVo.add(vo);
		}
		return listVo;
	}
	/**
	 * 查询出根节点和所有的子节点，封装到Vo中
	 * @param id
	 * @return
	 */
	@Override
	public PartnerServicePriceVo ToTree(Long id) {
		PartnerServicePriceVo partnerServicePriceVo = new PartnerServicePriceVo();
		
		//根据id查出某对象
		PartnerServicePrices partnerServicePrice = partnerServicePriceMapper.selectByPrimaryKey(id);
		try {
			//赋值给树形结构的vo
			BeanUtils.copyProperties(partnerServicePriceVo, partnerServicePrice);
			partnerServicePriceVo.setId(partnerServicePrice.getServicePriceId().intValue());
		}  catch (Exception e1) {
			e1.printStackTrace();
		}
		//已id作为parentId查询出所用的子节点
		
		List<PartnerServicePrices> child = partnerServicePriceMapper.selectByParentId(id);
		for (PartnerServicePrices partnerServicePrice2 : child) {
			PartnerServicePriceVo vo =   ToTree(partnerServicePrice2.getServicePriceId().longValue());
			partnerServicePriceVo.getChildren().add(vo);
		}
		return partnerServicePriceVo;
	}

	@Override
	public PartnerServicePrices initPartnerServicePrices() {
		PartnerServicePrices record = new PartnerServicePrices();
		record.setParentId(0L);
		record.setName("");
		return record;
	}
	
	

}
