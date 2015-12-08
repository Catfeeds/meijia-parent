package com.simi.service.impl.op;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.dict.AdService;
import com.simi.service.op.OpAdService;
import com.simi.vo.op.OpAdVo;
import com.simi.po.dao.dict.DictAdMapper;
import com.simi.po.dao.op.OpAdMapper;
import com.simi.po.model.dict.DictAd;
import com.simi.po.model.op.OpAd;
import com.meijia.utils.TimeStampUtil;

@Service
public class OpAdServiceImpl implements OpAdService {

	@Autowired
	private OpAdMapper opAdMapper;

	@Override
	public PageInfo searchVoListPage(int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<OpAd> list = opAdMapper.selectByListPage();
		List<OpAd> listNew = new ArrayList<OpAd>();
		for (Iterator iterator = list.iterator();iterator.hasNext();) {
			
			OpAd opAd = (OpAd) iterator.next();
			OpAdVo opAdView = new OpAdVo();
			String imgUrl =	opAd.getImgUrl();
			
			if (!imgUrl.equals("")) {
				String extensionName = imgUrl.substring(imgUrl.lastIndexOf("."));
	             String beforName = imgUrl.substring(0,(imgUrl.lastIndexOf(".")));
	             String newImgUrl = beforName+"_small"+extensionName;
	             opAd.setImgUrl(newImgUrl);
			}
			listNew.add(opAd);
		}
		for (int i = 0; i < list.size(); i++) {
			if (listNew.get(i) != null) {
				list.set(i, listNew.get(i));
			}
		}
		PageInfo result = new PageInfo(list);
		
		return result;
	}

	@Override
	public OpAd initAd() {

		    OpAd record = new OpAd();
			record.setId(0L);
			record.setNo((short)0);
			record.setImgUrl("");
			record.setGotoUrl("");
			record.setTitle("");
			record.setAdType("");
			record.setServiceTypeIds("");
			record.setAddTime(TimeStampUtil.getNow()/1000);
			record.setUpdateTime(0L);
			record.setEnable((short)0);

			return record;
		}
	@Override
	public OpAd selectByPrimaryKey(Long id) {
		return opAdMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OpAd record) {
		return opAdMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(OpAd opAd) {
		// TODO Auto-generated method stub
		return opAdMapper.insertSelective(opAd);
	}
}
