package com.simi.service.impl.op;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.op.AppToolsService;
import com.simi.service.op.OpChannelService;
import com.simi.vo.po.AppToolsVo;
import com.simi.po.dao.op.AppToolsMapper;
import com.simi.po.model.op.AppTools;
import com.meijia.utils.BeanUtilsExp;
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
		    record.setAction("");
		    record.setParams("");
		    record.setIsDefault((short)0L);
		    record.setIsDel((short)0L);
		    record.setIsPartner((short)0L);
		    record.setIsOnline((short)0);
		    record.setAppProvider("");
		    record.setAppDescribe("");
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


	@Override
	public AppToolsVo getAppToolsVo(AppTools item) {
		
		AppToolsVo vo = new AppToolsVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
		//添加时间返回‘yyyy-mm-dd’
		Long addTime = item.getAddTime()*1000;
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime, "yyyy-MM-dd"));
		
		return vo;
	}
	
}
