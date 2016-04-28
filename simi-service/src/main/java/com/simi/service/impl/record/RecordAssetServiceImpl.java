package com.simi.service.impl.record;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.record.RecordAssetsMapper;
import com.simi.po.model.record.RecordAssets;
import com.simi.po.model.user.Users;
import com.simi.service.record.RecordAssetService;
import com.simi.service.user.UsersService;
import com.simi.vo.AssetSearchVo;
import com.simi.vo.record.RecordAssetVo;


@Service
public class RecordAssetServiceImpl implements RecordAssetService {

	@Autowired
	RecordAssetsMapper recordAssetMapper;
	
	@Autowired
	private UsersService userService;
	
	@Override
	public RecordAssets initRecordAssets() {

		RecordAssets record = new RecordAssets();
		record.setId(0L);
		record.setCompanyId(0L);
		record.setAssetId(0L);
		record.setUserId(0L);
		record.setAssetTypeId(0L);
		record.setBarcode("");
		record.setName("");
		record.setTotal(0);
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
		
		List<RecordAssets> list = recordAssetMapper.selectByListPage(searchVo);
		RecordAssets assets = null;
		for (int i = 0; i < list.size(); i++) {
			assets = list.get(i);
			
			RecordAssetVo assetVo = new RecordAssetVo();
			
			BeanUtilsExp.copyPropertiesIgnoreNull(assets, assetVo);
			
			assetVo.setAddTimeStr(TimeStampUtil.fromTodayStr(assets.getAddTime() * 1000));
			
			list.set(i, assetVo);
		}
		
		PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public List<RecordAssets> selectBySearchVo(AssetSearchVo searchVo) {
		return recordAssetMapper.selectBySearchVo(searchVo);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return recordAssetMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insertSelective(RecordAssets RecordAssets) {
		return recordAssetMapper.insertSelective(RecordAssets);
	}

	@Override
	public RecordAssets selectByPrimarykey(Long id) {
		return recordAssetMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RecordAssets RecordAssets) {
		return recordAssetMapper.updateByPrimaryKeySelective(RecordAssets);
	}
	
	@Override
	public RecordAssetVo getListVo(RecordAssets assets) {
		
		RecordAssetVo assetVo = new RecordAssetVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(assets, assetVo);
		
		Long userId = assets.getUserId();
		
		Users users = userService.selectByPrimaryKey(userId);
		
//		assetVo.setUserName(users.getName());
//		assetVo.setUserMobile(users.getMobile());
//		assetVo.setAddTimeStr(TimeStampUtil.fromTodayStr(assets.getAddTime() * 1000));
		
		return assetVo;
	}
}
