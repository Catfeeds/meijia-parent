package com.simi.service.impl.op;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.op.AppCardTypeService;
import com.simi.vo.po.AppCardTypeVo;
import com.simi.po.dao.op.AppCardTypeMapper;
import com.simi.po.model.op.AppCardType;
import com.simi.po.model.op.AppTools;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;

@Service
public class AppCardTypeServiceImpl implements AppCardTypeService {

	@Autowired
	private AppCardTypeMapper appCardTypeMapper;

	@Override
	public AppCardType initAppCardType() {

		AppCardType record = new AppCardType();
		    record.setCardTypeId(0L);
		    record.setNo((short)0L);
		    record.setAppType("");
		    record.setCardType((short)0);
		    record.setName("");
		    record.setCardIcon("");
		    record.setAddTime(TimeStampUtil.getNow()/1000);
			return record;
		}


	@Override
	public AppCardType selectByPrimaryKey(Long cardTypeId) {
		
		return appCardTypeMapper.selectByPrimaryKey(cardTypeId);
	}


	@Override
	public int updateByPrimaryKeySelective(AppCardType record) {
		
		return appCardTypeMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public int insertSelective(AppCardType record) {
		
		return appCardTypeMapper.insertSelective(record);
	}


	@Override
	public int deleteByPrimaryKey(Long tId) {

		return appCardTypeMapper.deleteByPrimaryKey(tId);
	}


	@Override
	public PageInfo selectByListPage(int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		
		List<AppTools> list = appCardTypeMapper.selectByListPage();
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}


	@Override
	public List<AppCardType> selectByAppType(String appType) {
		
		return appCardTypeMapper.selectByAppType(appType);
	}


	@Override
	public AppCardTypeVo getAppCardTypeVo(AppCardType item) {
		AppCardTypeVo vo = new AppCardTypeVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
		//添加时间返回‘yyyy-mm-dd’
		Long addTime = item.getAddTime()*1000;
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime, "yyyy-MM-dd"));
		
		return vo;
	}

	
}
