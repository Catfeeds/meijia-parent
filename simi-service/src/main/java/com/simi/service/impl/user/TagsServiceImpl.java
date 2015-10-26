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
	private TagsMapper tagsMapper;
	
	
	@Override
	public List<Tags> selectAll() {
		
		return tagsMapper.selectAll();
	}
	
	@Override
	public List<Tags> selectByTagIds(List<Long> tagIds) {
		return tagsMapper.selectByTagIds(tagIds);
	}

	@Override
	public List<Tags> selectByTagType(Short tagType) {

      return tagsMapper.selectByTagType(tagType);
	}


	
}