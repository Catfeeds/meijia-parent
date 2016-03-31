package com.simi.service.impl.record;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.record.RecordAssetUseMapper;
import com.simi.po.model.record.RecordAssetUse;
import com.simi.service.record.RecordAssetUseService;
import com.simi.vo.AssetSearchVo;


@Service
public class RecordAssetUseServiceImpl implements RecordAssetUseService {

	@Autowired
	RecordAssetUseMapper recordAssetUseMapper;

	@Override
	public RecordAssetUse initRecordAssetUse() {

		RecordAssetUse record = new RecordAssetUse();
		record.setId(0L);
		record.setCompanyId(0L);
		record.setAssetJson("");
		record.setUserId(0L);
		record.setToUserId(0L);
		record.setName("");
		record.setMobile("");
		record.setPurpose("");
		record.setStatus((short) 0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(AssetSearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<RecordAssetUse> list = recordAssetUseMapper.selectByListPage(searchVo);

		PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public List<RecordAssetUse> selectBySearchVo(AssetSearchVo searchVo) {
		return recordAssetUseMapper.selectBySearchVo(searchVo);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return recordAssetUseMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insertSelective(RecordAssetUse RecordAssetUse) {
		return recordAssetUseMapper.insertSelective(RecordAssetUse);
	}

	@Override
	public RecordAssetUse selectByPrimarykey(Long id) {
		return recordAssetUseMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RecordAssetUse RecordAssetUse) {
		return recordAssetUseMapper.updateByPrimaryKeySelective(RecordAssetUse);
	}
}
