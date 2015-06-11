package com.simi.service.impl.sec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.sec.SecMapper;
import com.simi.po.model.sec.Sec;
import com.simi.service.sec.SecService;
import com.simi.vo.sec.SecVo;



@Service
public class SecServiceImpl implements SecService{

	@Autowired
	private SecMapper secMapper;

	@Override
	public int insertSelective(Sec record) {
		
	   return secMapper.insert(record);
		
	}
	@Override
	public PageInfo searchVoListPage(int pageNo, int pageSize) {


		 PageHelper.startPage(pageNo, pageSize);
         List<Sec> list = secMapper.selectByListPage();
         
         List<Sec> listNew = new ArrayList<Sec>();
	        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Sec sec = (Sec) iterator.next();
				SecVo dicAdNew = new SecVo();
				 String imgUrl = sec.getHeadImg();
	             String extensionName = imgUrl.substring(imgUrl.lastIndexOf("."));
	             String beforName = imgUrl.substring(0,(imgUrl.lastIndexOf(".")));
	             String newImgUrl = beforName+"_small"+extensionName;
	             sec.setHeadImg(newImgUrl);
	             listNew.add(sec);
			}
	        for(int i = 0; i < list.size(); i++) {
	       	 if (listNew.get(i) != null) {
	       		 list.set(i, listNew.get(i));
	       	 }
	        }
         
         PageInfo result = new PageInfo(list);
		 return result;
		 
	}
	@Override
	public int deleteByPrimaryKey(Long id) {
		return secMapper.deleteByPrimaryKey(id);
	}
	@Override
	public Sec initSec() {
		Sec sec=new Sec();
		sec.setId(0L);
		sec.setName("");
		sec.setMobile("");
		sec.setNickName("");
		sec.setBirthDay(null);
		sec.setHeadImg("");
		sec.setStatus(null);
		sec.setAddTime(TimeStampUtil.getNow()/1000);
	    sec.setUpdateTime(0L);
		
		return sec;
	}
	//判断秘书名称是否重复
	@Override
	public Sec selectByNameAndOtherId(String name, Long id) {
		HashMap map = new HashMap();
		map.put("name", name);
		map.put("id", id);
		return secMapper.selectByNameAndOtherId(map);
	}
	
}
