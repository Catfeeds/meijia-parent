package com.simi.service.impl.promotion;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.promotion.ChannelService;
import com.simi.vo.chan.ChannelSearchVo;
import com.simi.po.dao.promotion.ChannelMapper;
import com.simi.po.model.promotion.Channel;
import com.meijia.utils.TimeStampUtil;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelMapper channelMapper;

	@Override
	public Channel initChannel() {
		Channel record = new Channel();
		record.setId(0L);
		record.setChannelType((short) 0);
		record.setName("");
		record.setToken("");
		record.setDownloadUrl("");
		record.setIsQrcode((short) 0);
		record.setShortUrl("");
		record.setQrcodeUrl("");
		record.setTotalDownloads(0);
		record.setIsOnline((short) 1);
		record.setAddTime(TimeStampUtil.getNow()/1000);
		record.setUpdateTime(0L);
		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return channelMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Channel record) {
		return channelMapper.insert(record);
	}

	@Override
	public int insertSelective(Channel record) {
		return channelMapper.insertSelective(record);
	}

	@Override
	public Channel selectByPrimaryKey(Long id) {
		return channelMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Channel record) {
		return channelMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Channel selectByToken(String token) {

		return channelMapper.selectByToken(token);
	}

	@Override
	public int updateByPrimaryKey(Channel record) {
		return channelMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageInfo searchVoListPage(ChannelSearchVo searchVo,int pageNo,
			int pageSize) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
        String name=searchVo.getName();
        String token=searchVo.getToken();
        Short channelType = searchVo.getChannelType();

        if(name !=null && ! name.isEmpty()){
        	conditions.put("name", name.trim());
        }
        if(token !=null &&!token.isEmpty()){
        	conditions.put("token", token.trim());
        }
        if(channelType !=null){
        	conditions.put("channelType", channelType);
        }

   		PageHelper.startPage(pageNo, pageSize);
		List<Channel> list = channelMapper.selectByListPage(conditions);
		PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public int updateByTotalDownload(String token) {
		return channelMapper.updateByTotalDownload(token);
	}

}
