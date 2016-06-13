package com.simi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.simi.po.model.common.Imgs;
import com.simi.vo.ImgSearchVo;


public interface ImgService {

	Imgs selectByPrimaryKey(Long id);

	int deleteByPrimaryKey(Long id);

	Long insert(Imgs record);

	Long updateByPrimaryKey(Imgs record);

	int updateByPrimaryKeySelective(Imgs record);

	List<Imgs> selectBySearchVo(ImgSearchVo searchVo);

	Imgs initImg();

	void insertImgs(MultipartFile[] imgs, Long userId, Long linkId, String linkType);
	
}
