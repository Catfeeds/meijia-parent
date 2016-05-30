package com.simi.service.impl.op;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.op.OpAdService;
import com.simi.service.op.OpChannelService;
import com.simi.service.total.TotalHitService;
import com.simi.vo.op.OpAdVo;
import com.simi.vo.po.AdSearchVo;
import com.simi.vo.total.TotalHitSearchVo;
import com.simi.common.Constants;
import com.simi.po.dao.op.OpAdMapper;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.op.OpChannel;
import com.simi.po.model.total.TotalHit;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class OpAdServiceImpl implements OpAdService {

	@Autowired
	private OpAdMapper opAdMapper;
	
	@Autowired
	private OpChannelService opChannelService;		
	
	
	@Override
	public PageInfo searchVoListPage(AdSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<OpAd> list = opAdMapper.selectByListPage(searchVo);
		
		OpChannel cardChannel = opChannelService.initOpChannel();
		cardChannel.setName("日程广告位卡片");
		List<OpChannel> opChannels = opChannelService.selectByAll();
		opChannels.add(0, cardChannel);

		for (int i =0; i< list.size(); i++) {
			OpAd item = list.get(i);
			OpAdVo vo = new OpAdVo();
			
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			String adType = item.getAdType();
			
			String channelNames = "";
			
			String[] adTypeAry = StringUtil.convertStrToArray(adType);

			for (int j = 0; j < adTypeAry.length; j++) {
				if (StringUtil.isEmpty(adTypeAry[j].toString())) continue;
				
				for (OpChannel op : opChannels) {
					if (op.getChannelId().toString().equals(adTypeAry[j])) {
						channelNames+= op.getName() + ",";
					}
				}
			}
			
			if (!StringUtil.isEmpty(channelNames)) {
				channelNames = channelNames.substring(0, channelNames.length() - 1);
			}
			
			vo.setChannelNames(channelNames);
			
			String appType = item.getAppType();
			appType = MeijiaUtil.getAppTypeName(appType);
			item.setAppType(appType);
			
			list.set(i, vo);
		}
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}

	@Override
	public OpAd initAd() {

	    OpAd record = new OpAd();
		record.setId(0L);
		record.setNo((short)0);
		record.setImgUrl("");
		record.setGotoUrl("");
		record.setTitle("");
		record.setAppType("");
		record.setAdType("");
		record.setServiceTypeIds("");
		record.setAddTime(TimeStampUtil.getNow()/1000);
		record.setUpdateTime(0L);
		record.setEnable((short)1);
		
		return record;
	}
	@Override
	public OpAd selectByPrimaryKey(Long id) {
		return opAdMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<OpAd> selectByAdType(String adType) {
		return opAdMapper.selectByAdType(adType);
	}	
	
	@Override
	public List<OpAd> selectBySearchVo(AdSearchVo searchVo) {
		return opAdMapper.selectBySearchVo(searchVo);
	}	

	@Override
	public int updateByPrimaryKeySelective(OpAd record) {
		return opAdMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(OpAd opAd) {
		return opAdMapper.insertSelective(opAd);
	}
}
