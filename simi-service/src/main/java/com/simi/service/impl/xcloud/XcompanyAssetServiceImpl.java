package com.simi.service.impl.xcloud;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyAssetsMapper;
import com.simi.po.model.xcloud.XcompanyAssets;
import com.simi.service.xcloud.XcompanyAssetService;
import com.simi.vo.AssetSearchVo;
import com.simi.vo.xcloud.XcompanyAssetVo;


@Service
public class XcompanyAssetServiceImpl implements XcompanyAssetService {

	@Autowired
	XcompanyAssetsMapper xCompanyAssetMapper;

	@Override
	public XcompanyAssets initXcompanyAssets() {

		XcompanyAssets record = new XcompanyAssets();

		record.setAssetId(0L);
		record.setCompanyId(0L);
		record.setAssetTypeId(0L);
		record.setBarcode("");
		record.setName("");
		record.setStock(0);
		record.setUnit("");
		record.setPrice(new BigDecimal(0));
		record.setTotalPrice(new BigDecimal(0));
		record.setPlace("");
		record.setSeq("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(AssetSearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<XcompanyAssets> list = xCompanyAssetMapper.selectByListPage(searchVo);
		
		for (int i = 0; i < list.size(); i++) {
			XcompanyAssets assets = list.get(i);
			
			XcompanyAssetVo assetVo = new XcompanyAssetVo();
			
			BeanUtilsExp.copyPropertiesIgnoreNull(assets, assetVo);
			
			assetVo.setAddTimeStr(TimeStampUtil.fromTodayStr(assets.getAddTime() * 1000));
			
			list.set(i, assetVo);
		}
		
		PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public List<XcompanyAssets> selectBySearchVo(AssetSearchVo searchVo) {
		return xCompanyAssetMapper.selectBySearchVo(searchVo);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return xCompanyAssetMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insertSelective(XcompanyAssets XcompanyAssets) {
		return xCompanyAssetMapper.insertSelective(XcompanyAssets);
	}

	@Override
	public XcompanyAssets selectByPrimarykey(Long id) {
		return xCompanyAssetMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyAssets XcompanyAssets) {
		return xCompanyAssetMapper.updateByPrimaryKeySelective(XcompanyAssets);
	}
}
