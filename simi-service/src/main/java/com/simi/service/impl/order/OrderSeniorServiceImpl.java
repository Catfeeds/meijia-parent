package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderSeniorService;
import com.simi.po.dao.order.OrderSeniorMapper;
import com.simi.po.model.dict.DictSeniorType;
import com.simi.po.model.order.OrderSenior;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
@Service
public class OrderSeniorServiceImpl implements OrderSeniorService {
	@Autowired
	private OrderSeniorMapper orderSeniorMapper;
	
	@Override
	public OrderSenior initOrderSenior() {
		OrderSenior orderSenior = new OrderSenior();
		orderSenior.setUserId(0L);
		orderSenior.setSecId(0L);
		orderSenior.setMobile("");
		orderSenior.setSeniorOrderNo("");
		orderSenior.setSeniorType(0L);
		orderSenior.setOrderMoney(new BigDecimal(0));
		orderSenior.setOrderPay(new BigDecimal(0));
		orderSenior.setValidDay((short) 0);
		orderSenior.setStartDate(DateUtil.getNowOfDate());
		orderSenior.setEndDate(DateUtil.getNowOfDate());
		orderSenior.setPayType((short) 0);
		orderSenior.setOrderStatus((short) 0);
		orderSenior.setAddTime(TimeStampUtil.getNowSecond());
		orderSenior.setUpdateTime(TimeStampUtil.getNowSecond());
		return orderSenior;
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return orderSeniorMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(OrderSenior record) {
		return orderSeniorMapper.insert(record);
	}

	@Override
	public int insertSelective(OrderSenior record) {
		return orderSeniorMapper.insertSelective(record);
	}

	@Override
	public OrderSenior selectByPrimaryKey(Long id) {
		return orderSeniorMapper.selectByPrimaryKey(id);
	}

	@Override
	public OrderSenior selectByOrderSeniorNo(String id) {
		return orderSeniorMapper.selectByOrderSeniorNo(id);
	}

	@Override
	public int updateByPrimaryKey(OrderSenior record) {
		return orderSeniorMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderSenior record) {
		return orderSeniorMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<OrderSenior> selectByMobileAndPay (String mobile) {
		return orderSeniorMapper.selectByMobileAndPay(mobile);
	}

	/*
	 * 根据私密卡类型获得结束日期
	 */
	@Override
	public Date getSeniorStartDate(DictSeniorType dictSeniorType) {
		
		Short validDay = dictSeniorType.getValidDay();
		Date startDate = DateUtil.getNowOfDate();
		String endDateStr = "";
		if (validDay < 30) 
			endDateStr = DateUtil.addDay(startDate, validDay, Calendar.DATE, DateUtil.getDefaultPattern());
		
		if (validDay.equals((short)30)) 
			endDateStr = DateUtil.addDay(startDate, 1, Calendar.MONTH, DateUtil.getDefaultPattern());

		if (validDay.equals((short)90)) 
			endDateStr = DateUtil.addDay(startDate, 3, Calendar.MONTH, DateUtil.getDefaultPattern());
		
		Date endDate = DateUtil.parse(endDateStr);
		return endDate;
	}

	/*
	 * 根据历史管家卡订单获得有效期
	 */
	@Override
	public HashMap<String, Date> getSeniorRangeDate(Long userId) {

		//找出最新一个的历史订单.已支付状态.
		List<OrderSenior> list = orderSeniorMapper.selectByUserIdAndPay(userId);
		HashMap<String, Date> result = new HashMap<String,Date>();
		//如果没有历史订单，则有效期为空
		if (list.isEmpty()) {
			return result;
		}

		OrderSenior item = null;
		Date startDate = null;
		Date endDate = null;
		Date endDateTmp = null;
		String startDateStrTmp = "";
		//倒序循环，如果判断为不连续的时间则停止.
		for(int i = 0; i < list.size(); i++) {
			item = list.get(i);
			if (endDate == null) endDate = item.getEndDate();

			endDateTmp = item.getEndDate();

			if (startDate == null) {
				startDate = item.getStartDate();
				continue;
			}

			startDateStrTmp =  DateUtil.addDay(endDateTmp, 1, Calendar.DATE, DateUtil.getDefaultPattern());

			if (DateUtil.compareDateStr(DateUtil.formatDate(startDate) , startDateStrTmp) == 0 ) {
				startDate = item.getStartDate();
			} else {
				break;
			}
		}

		result.put("startDate", startDate);
		result.put("endDate", endDate);
//		String result = "有效期:" + DateUtil.formatDate(startDate) + "至" + DateUtil.formatDate(endDate);
		return result;
	}


}