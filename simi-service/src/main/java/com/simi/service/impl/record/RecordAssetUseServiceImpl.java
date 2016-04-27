package com.simi.service.impl.record;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.record.RecordAssetUseMapper;
import com.simi.po.model.record.RecordAssetUse;
import com.simi.po.model.record.RecordAssets;
import com.simi.po.model.user.Users;
import com.simi.service.record.RecordAssetUseService;
import com.simi.service.user.UsersService;
import com.simi.vo.AssetSearchVo;
import com.simi.vo.record.RecordAssetUseVo;


@Service
public class RecordAssetUseServiceImpl implements RecordAssetUseService {

	@Autowired
	RecordAssetUseMapper recordAssetUseMapper;
	
	@Autowired
	private UsersService userService;
	
	@Override
	public RecordAssetUse initRecordAssetUse() {

		RecordAssetUse record = new RecordAssetUse();
		record.setId(0L);
		record.setCompanyId(0L);
		record.setAssetJson("");
		record.setUserId(0L);
		record.setToUserId(0L);
		record.setName("");
		record.setMobile("");
		record.setPurpose("");
		record.setStatus((short) 0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(AssetSearchVo searchVo, int pageNo, int pageSize){

		PageHelper.startPage(pageNo, pageSize);
		List<RecordAssetUse> list = recordAssetUseMapper.selectByListPage(searchVo);
		
		PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public List<RecordAssetUse> selectBySearchVo(AssetSearchVo searchVo) {
		return recordAssetUseMapper.selectBySearchVo(searchVo);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return recordAssetUseMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insertSelective(RecordAssetUse RecordAssetUse) {
		return recordAssetUseMapper.insertSelective(RecordAssetUse);
	}

	@Override
	public RecordAssetUse selectByPrimarykey(Long id) {
		return recordAssetUseMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RecordAssetUse RecordAssetUse) {
		return recordAssetUseMapper.updateByPrimaryKeySelective(RecordAssetUse);
	}
	
	@Override
	public RecordAssetUseVo getUserAssetVo(RecordAssetUse assetUse) throws UnsupportedEncodingException {
		
		RecordAssetUseVo useVo = new RecordAssetUseVo();
//		
		BeanUtilsExp.copyPropertiesIgnoreNull(assetUse, useVo);
		
		String assetJson = assetUse.getAssetJson();
		
//		RecordAssets assets = GsonUtil.GsonToObject(assetJson, RecordAssets.class);
		
//		List<RecordAssets> list = GsonUtil.GsonToList(assetJson, RecordAssets.class);
		
		List<HashMap> list = GsonUtil.GsonToList(assetJson, HashMap.class);
		
		//领用 资产名称和 数量。。 可能一次领用 不同种类的 多件物品
		String assetNameAndNumStr = "";
		
		for (HashMap item : list) {
			if (StringUtil.isEmpty(item.get("asset_id").toString()))
				continue;
			if (StringUtil.isEmpty(item.get("total").toString()))
				continue;
			Long assetId = Long.valueOf(item.get("asset_id").toString());
			Integer total = Integer.valueOf(item.get("total").toString());
			
			//mysql 5.7  json格式 中文转码
			String newName = new String(item.get("name").toString().getBytes("ISO-8859-1"), "utf-8"); 
			
			assetNameAndNumStr += newName +" "+total + ";" ;
			
			// 回显字段。。TODO 多选时，不适用！！
			useVo.setAssetNum(total.longValue());
			useVo.setUseAssetId(assetId);
			
		}
		
//		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//			
//			RecordAssets recordAssets = (RecordAssets) iterator.next();
//			
//			String name = recordAssets.getName();
//			
//			/*
//			 *  !! 入库 没问题，从库中取出 json数据 中 包含中文时，会有乱码。
//			 * 
//			 * 	mysql5.7    说是  json 中文存储是  iso-8859-1
//			 * 	
//			 * 
//			 * 	 TODO 通用 拦截器 暂未实现
//			 * 
//			 */
//			String newName = new String(name.getBytes("ISO-8859-1"), "utf-8"); 
//			
//			Integer total = recordAssets.getTotal();
//			
//			assetNameAndNumStr += newName +" "+total + ";" ;
//			
//			
//			//TODO 就选了 一个。。需要处理
//			
//		}
		
		useVo.setAssetNameAndNumStr(assetNameAndNumStr);
		
		//领用时间
		useVo.setAddTimeStr(TimeStampUtil.fromTodayStr(assetUse.getAddTime() * 1000));
		
		/*
		 * 经手人
		 */
		Long userId = assetUse.getUserId();
		
		Users users = userService.selectByPrimaryKey(userId);
		
		useVo.setFromName(users.getName());
		useVo.setFromMobile(users.getMobile());
		useVo.setFromHeadImg(users.getHeadImg());
		
		//领用人头像
		Long toUserId = assetUse.getToUserId();
		Users toUser = userService.selectByPrimaryKey(toUserId);
		useVo.setToHeadImg(toUser.getHeadImg());
		
		
		return useVo;
	}
	
}
