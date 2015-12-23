package com.simi.service.impl.feed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.feed.FeedZanService;
import com.simi.service.user.UsersService;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.vo.feed.FeedZanViewVo;
import com.simi.po.model.feed.FeedZan;
import com.simi.po.model.user.Users;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.feed.FeedZanMapper;

@Service
public class FeedZanServiceImpl implements FeedZanService {
	@Autowired
	FeedZanMapper feedZanMapper;

	@Autowired
	UsersService usersService;

	@Override
	public FeedZan initFeedZan() {
		FeedZan record = new FeedZan();
		record.setId(0L);
		record.setFid(0L);
		record.setUserId(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public FeedZan selectByPrimaryKey(Long id) {
		return feedZanMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public FeedZan selectBySearchVo(FeedSearchVo searchVo) {
		return feedZanMapper.selectBySearchVo(searchVo);
	}	

	@Override
	public int totalByFid(Long fid) {
		return feedZanMapper.totalByFid(fid);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> totalByFids(List<Long> fids) {
		return feedZanMapper.totalByFids(fids);
	}

	@Override
	public List<FeedZanViewVo> getByTop10(Long fid) {

		List<FeedZanViewVo> result = new ArrayList<FeedZanViewVo>();

		List<FeedZan> feedzans = feedZanMapper.getByTop10(fid);

		if (feedzans.isEmpty()) return result;

		List<Long> userIds = new ArrayList<Long>();

		FeedZan item = null;
		for (int i = 0; i < feedzans.size(); i++) {
			item = feedzans.get(i);
			userIds.add(item.getUserId());
		}

		List<Users> userList = new ArrayList<Users>();
		if (userIds.size() > 0) {
			userList = usersService.selectByUserIds(userIds);
		}

		for (int i = 0; i < feedzans.size(); i++) {

			FeedZanViewVo vo = new FeedZanViewVo();
			item = feedzans.get(i);
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			for (Users u : userList) {
				if (vo.getUserId().equals(u.getId())) {
					vo.setName(u.getName());
					vo.setHeadImg(u.getHeadImg());
					break;
				}
			}
			result.add(vo);
		}

		return result;
	}

	@Override
	public int insertSelective(FeedZan record) {
		return feedZanMapper.insertSelective(record);
	}
	
	@Override
	public int insert(FeedZan record) {
		return feedZanMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(FeedZan record) {
		return feedZanMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FeedZan record) {
		return feedZanMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return feedZanMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteByFid(Long fid) {
		return feedZanMapper.deleteByFid(fid);
	}

}