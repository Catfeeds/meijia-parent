package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.Tags;
import com.sun.javadoc.Tag;


public interface TagsService {

	List<Tags> selectAll();

	List<Tags> selectByTagIds(List<Long> tagIds);

	List<Tags> selectByTagType(Short tagType);

	

}