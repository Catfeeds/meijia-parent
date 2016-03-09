package com.simi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.ImgService;
import com.simi.vo.ImgSearchVo;
import com.simi.po.dao.common.ImgsMapper;
import com.simi.po.model.common.Imgs;



@Service
public class ImgServiceImpl implements ImgService {

	@Autowired
	private ImgsMapper imgsMapper;

	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return imgsMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insert(Imgs record) {
		return imgsMapper.insert(record);
	}
	
	@Override
	public Long updateByPrimaryKey(Imgs record) {
		return imgsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Imgs record) {
		return imgsMapper.updateByPrimaryKeySelective(record);
	}	
	
	@Override
	public Imgs selectByPrimaryKey(Long id) {
		return imgsMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<Imgs> selectBySearchVo(ImgSearchVo searchVo) {
		return imgsMapper.selectBySearchVo(searchVo);
	}
	
	@Override
	public Imgs initImg() {
		Imgs record = new Imgs();
		record.setImgId(0L);
		record.setUserId(0L);
		record.setLinkId(0L);
		record.setLinkType("");
		record.setImgUrl("");
		record.setAddTime(0L);
		
		return record;
	}

}
