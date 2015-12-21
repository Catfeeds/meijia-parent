package com.simi.service.impl.op;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.op.AppToolsService;
import com.simi.service.op.OpAdService;
import com.simi.service.op.OpChannelService;
import com.simi.vo.op.OpAdVo;
import com.simi.vo.po.AdSearchVo;
import com.simi.po.dao.op.AppToolsMapper;
import com.simi.po.dao.op.OpAdMapper;
import com.simi.po.model.op.AppTools;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.op.OpChannel;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class AppToolsServiceImpl implements AppToolsService {

	@Autowired
	private AppToolsMapper appToolsMapper;
	
	@Autowired
	private OpChannelService opChannelService;		


	@Override
	public AppTools initAppTools() {

		    AppTools record = new AppTools();
		    
		    record.settId(0L);
		    record.setNo((short)0L);
		    record.setName("");
		    record.setLogo("");
		    record.setAppType("");
		    record.setMenuType("");
		    record.setOpenType("");
		    record.setUrl("");
		    record.setIsPartner((short)0L);
		    record.setAuthUrl("");
		    record.setAddTime(TimeStampUtil.getNow()/1000);
			return record;
		}


	@Override
	public AppTools selectByPrimaryKey(Long tId) {
		
		return appToolsMapper.selectByPrimaryKey(tId);
	}


	@Override
	public int updateByPrimaryKeySelective(AppTools record) {
		
		return appToolsMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public int insertSelective(AppTools record) {
		
		return appToolsMapper.insertSelective(record);
	}


	@Override
	public int deleteByPrimaryKey(Long tId) {

		return appToolsMapper.deleteByPrimaryKey(tId);
	}


	@Override
	public PageInfo selectByListPage(int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		
		List<AppTools> list = appToolsMapper.selectByListPage();
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}


	@Override
	public List<AppTools> selectByAppType(String appType) {
		
		return appToolsMapper.selectByAppType(appType);
	}
	
}
