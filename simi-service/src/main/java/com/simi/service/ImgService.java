package com.simi.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

	Map<String, String> multiFileUpLoad(HttpServletRequest request);
	
}
