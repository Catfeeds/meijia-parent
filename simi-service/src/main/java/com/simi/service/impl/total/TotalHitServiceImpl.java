package com.simi.service.impl.total;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.total.TotalHitMapper;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.total.TotalHit;
import com.simi.service.total.TotalHitService;
import com.simi.vo.total.TotalHitSearchVo;

/**
 *
 * @author :hulj
 * @Date : 2016年5月18日下午6:11:44
 * @Description: 
 *
 */
@Service
public class TotalHitServiceImpl implements TotalHitService {
	
	@Autowired
	private TotalHitMapper hitMapper;
	
	@Override
	public TotalHit initTotalHit() {
		
		TotalHit hit = new TotalHit();
		
		hit.setId(0L);
		hit.setLinkId(0L);
		hit.setLinkType("");
		hit.setTotal(0L);
		hit.setAddTime(TimeStampUtil.getNowSecond());
		
		return hit;
	}

	@Override
	public TotalHit selectByPrimaryKey(Long id) {
		return hitMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TotalHit hit) {
		return hitMapper.updateByPrimaryKeySelective(hit);
	}

	@Override
	public int insertSelective(TotalHit hit) {
		return hitMapper.insertSelective(hit);
	}

	@Override
	public PageInfo searchVoListPage(TotalHitSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<TotalHit> list = hitMapper.selectByListPage(searchVo);
		
		for (TotalHit totalHit : list) {
			
			
			
		}
		
		
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}

	@Override
	public List<TotalHit> selectBySearchVo(TotalHitSearchVo searchVo) {
		return hitMapper.selectBySearchVo(searchVo);
	}

}
