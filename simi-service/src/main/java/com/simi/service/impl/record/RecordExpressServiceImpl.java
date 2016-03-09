package com.simi.service.impl.record;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.ImgService;
import com.simi.service.dict.DictService;
import com.simi.service.record.RecordExpressService;
import com.simi.vo.record.RecordExpressSearchVo;
import com.simi.vo.record.RecordExpressVo;
import com.simi.po.model.dict.DictExpress;
import com.simi.po.model.record.RecordExpress;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.record.RecordExpressMapper;

@Service
public class RecordExpressServiceImpl implements RecordExpressService {
	@Autowired
	RecordExpressMapper recordExpressMapper;
	
	@Autowired
	ImgService imgService;
	
	@Autowired
	DictService dictService;
		
	@Override
	public RecordExpress initRecordExpress() {
		RecordExpress record = new RecordExpress();
		record.setId(0L);
		record.setCompanyId(0L);
	    record.setUserId(0L);
	    record.setToUserId(0L);
	    record.setExpressId(0L);
	    record.setExpressNo("");
	    record.setExpressType((short) 0);
	    record.setIsDone((short) 0);
	    record.setIsClose((short) 0);
	    record.setPayType((short) 0);
	    record.setFromCityId(0L);
	    record.setFromAddr("");
	    record.setFromName("");
	    record.setFromTel("");
	    record.setFromCompanyName("");
	    record.setToCityId(0L);
	    record.setToAddr("");
	    record.setToName("");
	    record.setToTel("");
	    record.setToCompanyName("");
	    record.setRemarks("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		
		return record;
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return recordExpressMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insert(RecordExpress record) {
		return recordExpressMapper.insert(record);
	}
	
	@Override
	public Long insertSelective(RecordExpress record) {
		return recordExpressMapper.insertSelective(record);
	}
	
	@Override
	public int updateByPrimaryKey(RecordExpress record) {
		return recordExpressMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(RecordExpress record) {
		return recordExpressMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public RecordExpress selectByPrimaryKey(Long id) {
		return recordExpressMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public RecordExpress selectByExpressNo(String expressNo) {
		return recordExpressMapper.selectByExpressNo(expressNo);
	}
	
	@Override
	public List<RecordExpress> selectBySearchVo(RecordExpressSearchVo searchVo) {
		return recordExpressMapper.selectBySearchVo(searchVo);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(RecordExpressSearchVo searchVo, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		
		List<RecordExpress> list = new ArrayList<RecordExpress>();
			
		list = recordExpressMapper.selectByListPage(searchVo);
		
		PageInfo result = new PageInfo(list);
		return result;
	}	
	
	@Override
	public List<RecordExpressVo> getListVos (List<RecordExpress> list) {
		List<RecordExpressVo> result = new ArrayList<RecordExpressVo>();
		
		if (list.isEmpty()) return result;
		
		List<DictExpress> expressList = dictService.loadExpressData();
		
		for (int i = 0; i < list.size(); i++) {
			RecordExpress item = list.get(i);
			RecordExpressVo vo = new RecordExpressVo();
			
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			//快递服务商信息
			vo.setExpressName("");
			if (item.getExpressId() > 0L) {
				for (DictExpress e : expressList) {
					if (e.getExpressId().equals(item.getExpressId())) {
						vo.setExpressName(e.getName());
						break;
					}
				}
			}
			
			
			vo.setAddTimeStr(TimeStampUtil.fromTodayStr(item.getAddTime() * 1000));
			vo.setUpdateTimeStr(TimeStampUtil.fromTodayStr(item.getUpdateTime() * 1000));
			
			result.add(vo);
		}
		
		
		return result;
	}
	
	@Override
	public RecordExpressVo getListVo (RecordExpress item) {

		List<DictExpress> expressList = dictService.loadExpressData();
		
		RecordExpressVo vo = new RecordExpressVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
		
		//快递服务商信息
		vo.setExpressName("");
		if (item.getExpressId() > 0L) {
			for (DictExpress e : expressList) {
				if (e.getExpressId().equals(item.getExpressId())) {
					vo.setExpressName(e.getName());
					break;
				}
			}
		}
		
		
		vo.setAddTimeStr(TimeStampUtil.fromTodayStr(item.getAddTime() * 1000));
		vo.setUpdateTimeStr(TimeStampUtil.fromTodayStr(item.getUpdateTime() * 1000));

		return vo;
	}	
			
}