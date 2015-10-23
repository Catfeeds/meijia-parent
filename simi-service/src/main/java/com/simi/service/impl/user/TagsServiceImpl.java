package com.simi.service.impl.user;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.po.dao.user.TagsMapper;
import com.simi.po.model.user.Tags;
import com.simi.service.user.TagsService;
@Service
public class TagsServiceImpl implements TagsService {

	
	@Autowired
	private TagsMapper TagsMapper;
	
	
	@Override
	public List<Tags> selectAll() {
		
		return TagsMapper.selectAll();
	}
	
	@Override
	public List<Tags> selectByTagIds(List<Long> tagIds) {
		return TagsMapper.selectByTagIds(tagIds);
	}

	
}