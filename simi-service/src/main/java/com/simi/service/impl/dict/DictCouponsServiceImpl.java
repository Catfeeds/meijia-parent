package com.simi.service.impl.dict;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.po.dao.dict.DictCouponsMapper;
import com.simi.po.model.dict.DictCoupons;
import com.simi.service.dict.DictCouponsService;
import com.simi.vo.dict.CouponSearchVo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class DictCouponsServiceImpl implements DictCouponsService {

	@Autowired
	private DictCouponsMapper dictCouponsMapper;

	/*
	 * 获取表dict_coupons的所有数据
	 * @param
	 * @return  List<DictServiceTypes>
	 */
	@Override
	public List<DictCoupons> selectAll() {
		return dictCouponsMapper.selectAll();
	}

	@Override
	public List<DictCoupons> selectByIds(List<Long> ids) {
		return dictCouponsMapper.selectByIds(ids);
	}

	@Override
	public DictCoupons selectByPrimaryKey(Long id) {
		return dictCouponsMapper.selectByPrimaryKey(id);
	}

	@Override
	public DictCoupons selectByCardPasswd(String cardPasswd) {
		return dictCouponsMapper.selectByCardPasswd(cardPasswd);
	}

	@Override
	public int insert(DictCoupons record) {
		return dictCouponsMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(DictCoupons record) {
		return dictCouponsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
    public int updateByPrimaryKey(DictCoupons record) {
		return dictCouponsMapper.updateByPrimaryKey(record);
	}

	@Override
	public DictCoupons initRechargeCoupon() {

		DictCoupons po = new DictCoupons();
		po.setId(0L);
		po.setCardNo("");
		po.setCardPasswd("");
		po.setCouponType((short)1);
		po.setValue(new BigDecimal(0));
		po.setMaxValue(new BigDecimal(0));
		po.setRangType((short) 0);
		po.setRangFrom((short) 999);
		po.setServiceTypeId(0L);
		po.setServicePriceId(0L);
		po.setIntroduction("");
		po.setDescription("");
		po.setRangMonth((short)1);
		po.setFromDate(new Date());
		po.setToDate(new Date());
		po.setAddTime(TimeStampUtil.getNow() / 1000);
		po.setUpdateTime(TimeStampUtil.getNow() / 1000);
		return po;
	}
	@Override
	public DictCoupons initConvertCoupon() {
		
		DictCoupons po = new DictCoupons();
		po.setId(0L);
		po.setCardNo("");
		po.setCardPasswd("");
		po.setCouponType((short)0);
		po.setValue(new BigDecimal(0));
		po.setMaxValue(new BigDecimal(0));
		po.setRangType((short) 0);
		po.setRangFrom((short) 999);
		po.setServiceTypeId(0L);
		po.setServicePriceId(0L);
		po.setIntroduction("");
		po.setDescription("");
		po.setRangMonth((short)0);
		po.setFromDate(new Date());
		Long seconds = TimeStampUtil.getMillisOfDate(new Date())/1000;
		seconds = seconds +2592000;
		Date date =TimeStampUtil.timeStampToDateFull(seconds*1000, "yyyy-MM-dd");
		po.setToDate(date);
		po.setAddTime(TimeStampUtil.getNow() / 1000);
		po.setUpdateTime(TimeStampUtil.getNow() / 1000);
		return po;
	}

	@Override
	public PageInfo searchVoListPage(CouponSearchVo searchVo,short couponType,
			int pageNo, int pageSize) {
		HashMap<String,Object> conditions = new HashMap<String,Object>();
		String cardNo = searchVo.getCardNo();
		 String cardPasswd = searchVo.getCardPasswd();
		 String startDate = searchVo.getStartDate();
		String endDate = searchVo.getEndDate();

		if(cardPasswd !=null && !cardPasswd.isEmpty() ){
			conditions.put("cardPasswd", cardPasswd.trim());
		}
		if(cardNo !=null && !cardNo.isEmpty()){
			conditions.put("cardNo",cardNo.trim());
		}
		if(!StringUtil.isEmpty(startDate)){
			conditions.put("startDate",TimeStampUtil.getMillisOfDay(startDate.trim())/1000);
		}
		if(!StringUtil.isEmpty(endDate)){
			conditions.put("endDate",TimeStampUtil.getMillisOfDay(endDate.trim())/1000);
		}
		conditions.put("couponType", couponType);

		 PageHelper.startPage(pageNo, pageSize);
         List<DictCoupons> list = dictCouponsMapper.selectByListPage(conditions);
        PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public List<DictCoupons> selectAllByCardNo() {
		return dictCouponsMapper.selectAllByCardNo();
	}

	@Override
	public int insertSelective(DictCoupons record) {
		return dictCouponsMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return dictCouponsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Map<String, String> getSelectRangMonthSource() {
		Map<String, String> rangeMonth = new HashMap<String, String>();
		rangeMonth.put("1","一个月");
		rangeMonth.put("3","三个月");
		rangeMonth.put("6","半年");
		return rangeMonth;
	}

	@Override
	public Map<String, String> getSelectRangTypeSource() {
		Map<String, String> rangeType = new HashMap<String, String>();
		rangeType.put("0","通用");
		rangeType.put("1","唯一");
		return rangeType;
	}
	@Override
	public List<DictCoupons> getCouponsByCouponType(Short couponType) {
		
		return dictCouponsMapper.selectByCouponType(couponType);
	}

	@Override
	public Map<Long, String> getSelectRechargeCouponSource() {
		Map<Long, String> dictCouponsMap = new HashMap<Long, String>();
		List<DictCoupons> list = dictCouponsMapper.selectByCouponType((short)1);;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DictCoupons dictCoupons = (DictCoupons) iterator.next();
			dictCouponsMap.put(dictCoupons.getId(),dictCoupons.getIntroduction());
		}
		return dictCouponsMap;
	}
	/*
	 * 根据addTime查询导出的优惠券信息
	 */
	@Override
	public List<DictCoupons> selectBySearchVo(CouponSearchVo couponSearchVo) {
		Map<String,Object> conditions = new HashMap<String, Object>();
		String startDate = couponSearchVo.getStartDate();
		String endDate = couponSearchVo.getEndDate();
		Short couponType = couponSearchVo.getCoupontType();
		if(!StringUtil.isEmpty(startDate)){
			conditions.put("startDate",TimeStampUtil.getMillisOfDay(startDate.trim())/1000);
		}
		if(!StringUtil.isEmpty(endDate)){
			conditions.put("endDate", TimeStampUtil.getMillisOfDay(endDate.trim())/1000);
		}
		conditions.put("couponType",couponSearchVo.getCoupontType());
		return dictCouponsMapper.selectByListPage(conditions);
	}
	
	
	
	
	
}
