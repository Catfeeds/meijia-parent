package com.simi.service.impl.op;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.op.AppCardTypeService;
import com.simi.service.op.AppIndexService;
import com.simi.vo.po.AppCardTypeVo;
import com.simi.po.dao.op.AppCardTypeMapper;
import com.simi.po.dao.op.AppIndexMapper;
import com.simi.po.model.op.AppCardType;
import com.simi.po.model.op.AppIndex;
import com.simi.po.model.op.AppTools;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;

@Service
public class AppIndexServiceImpl implements AppIndexService {

	@Autowired
	private AppIndexMapper appIndexMapper;

	@Override
	public AppIndex initAppIndex() {

		AppIndex record = new AppIndex();
		
		    record.setId(0L);
		    record.setAppType("");
		    record.setSerialNo(0L);
		    record.setCategory("");
		    record.setAction("");
		    record.setParams("");
		    record.setGotoUrl("");
		    record.setIsOnline((short) 0);
		    record.setTitle("");
		    record.setIconUrl("");
		    record.setAddTime(TimeStampUtil.getNow()/1000);
		    record.setUpdateTime(TimeStampUtil.getNow()/1000);
			return record;
		}


	@Override
	public AppIndex selectByPrimaryKey(Long id) {
		
		return appIndexMapper.selectByPrimaryKey(id);
	}


	@Override
	public int updateByPrimaryKeySelective(AppIndex record) {
		
		return appIndexMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public int insertSelective(AppIndex record) {
		
		return appIndexMapper.insertSelective(record);
	}
	
	@Override
	public int insert(AppIndex record) {
		
		return appIndexMapper.insert(record);
	}

	@Override
	public int deleteByPrimaryKey(Long tId) {

		return appIndexMapper.deleteByPrimaryKey(tId);
	}


	@Override
	public PageInfo selectByListPage(int pageNo, int pageSize) {

			PageHelper.startPage(pageNo, pageSize);
			
			List<AppIndex> list = appIndexMapper.selectByListPage();
			
			PageInfo result = new PageInfo(list);
			
			return result;


	}


	@Override
	public List<AppIndex> selectByAppType(String appType) {
		
		return appIndexMapper.selectByAppType(appType);
		
	}

}
