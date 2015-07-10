package com.simi.service.impl.dict;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.dict.ServiceTypeService;
import com.simi.po.dao.dict.DictServiceTypesMapper;
import com.simi.po.model.dict.DictServiceTypes;
import com.meijia.utils.TimeStampUtil;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

	@Autowired
	private DictServiceTypesMapper serviceTypeMapper;

	@Override
	public DictServiceTypes initServiceType() {
		DictServiceTypes record = new DictServiceTypes();

		record.setId(0L);
		record.setDescUrl("");
		record.setDisPrice(new BigDecimal(0));
		record.setKeyword("");
		record.setName("");
		record.setPrice(new BigDecimal(0));
		record.setAddTime(TimeStampUtil.getNow()/1000);
		record.setUpdateTime(0L);
		record.setEnable((short)0);

		return record;
	}

	/*
	 * 获取表dict_service_types的所有数据
	 * @param
	 * @return  List<DictServiceTypes>
	 */
	@Override
	public List<DictServiceTypes> getServiceTypes() {
		return serviceTypeMapper.selectAll();
	}

	/*
	 * 获取表dict_service_types的单条
	 * @param  id 主键id
	 * @return  DictServiceTypes
	 */
	@Override
	public DictServiceTypes getServiceTypesByPk(Long id) {
		return serviceTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insert(DictServiceTypes record) {
		return serviceTypeMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(DictServiceTypes record) {
		return serviceTypeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public PageInfo searchVoListPage(int pageNo, int pageSize) {
		 PageHelper.startPage(pageNo, pageSize);
         List<DictServiceTypes> list = serviceTypeMapper.selectByListPage();
        PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public int insertSelective(DictServiceTypes record) {
		return serviceTypeMapper.insert(record);
	}

	@Override
	public DictServiceTypes selectByPrimaryKey(Long id) {
		return serviceTypeMapper.selectByPrimaryKey(id);
	}

	//判断是否重复
	@Override
	public DictServiceTypes selectByName(String name) {
		return serviceTypeMapper.selectByName(name);
	}

	//判断是否重复
	@Override
	public DictServiceTypes selectByNameAndOtherId(String name, Long id) {

		HashMap map = new HashMap();
		map.put("id", id);
		map.put("name", name);
		return serviceTypeMapper.selectByNameAndOtherId(map);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return serviceTypeMapper.deleteByPrimaryKey(id);
	}

}
