package com.simi.service.impl.op;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.op.AppCardTypeService;
import com.simi.service.op.AppHelpService;
import com.simi.service.op.AppIndexService;
import com.simi.vo.po.AppCardTypeVo;
import com.simi.po.dao.op.AppCardTypeMapper;
import com.simi.po.dao.op.AppHelpMapper;
import com.simi.po.dao.op.AppIndexMapper;
import com.simi.po.model.op.AppCardType;
import com.simi.po.model.op.AppHelp;
import com.simi.po.model.op.AppIndex;
import com.simi.po.model.op.AppTools;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;

@Service
public class AppHelpServiceImpl implements AppHelpService {

	@Autowired
	private AppHelpMapper appHelpMapper;

	@Override
	public AppHelp initAppHelp() {

		AppHelp record = new AppHelp();
		    record.setId(0L);
		    record.setAppType("");
		    record.setAction("");
		    record.setGotoUrl("");
		    record.setContent("");
		    record.setImgUrl("");
		    record.setAddTime(TimeStampUtil.getNow()/1000);
			return record;
		}


	@Override
	public AppHelp selectByPrimaryKey(Long id) {
		
		return appHelpMapper.selectByPrimaryKey(id);
	}


	@Override
	public int updateByPrimaryKeySelective(AppHelp record) {
		
		return appHelpMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public int insertSelective(AppHelp record) {
		
		return appHelpMapper.insertSelective(record);
	}
	
	@Override
	public int insert(AppHelp record) {
		
		return appHelpMapper.insert(record);
	}

	@Override
	public int deleteByPrimaryKey(Long tId) {

		return appHelpMapper.deleteByPrimaryKey(tId);
	}


	@Override
	public PageInfo selectByListPage(int pageNo, int pageSize) {

			PageHelper.startPage(pageNo, pageSize);
			
			List<AppHelp> list = appHelpMapper.selectByListPage();
			
			PageInfo result = new PageInfo(list);
			
			return result;


	}


}
