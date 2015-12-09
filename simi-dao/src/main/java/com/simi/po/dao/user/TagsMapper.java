package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.Tags;

public interface TagsMapper {
    int deleteByPrimaryKey(Long tagId);

    int insert(Tags record);

    int insertSelective(Tags record);

    Tags selectByPrimaryKey(Long tagId);

    int updateByPrimaryKeySelective(Tags record);

    int updateByPrimaryKey(Tags record);

	List<Tags> selectAll();

	List<Tags> selectByTagIds(List<Long> tagIds);

	List<Tags> selectByTagType(Short tagType);
}