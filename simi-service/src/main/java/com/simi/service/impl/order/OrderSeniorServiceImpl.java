package com.simi.service.impl.order;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderSeniorService;
import com.simi.po.dao.order.OrderSeniorMapper;
import com.simi.po.model.order.OrderSenior;
import com.meijia.utils.DateUtil;
@Service
public class OrderSeniorServiceImpl implements OrderSeniorService {
	@Autowired
	private OrderSeniorMapper orderSeniorMapper;

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
	 * 根据历史管家卡订单获得当前的开始日期.
	 */
	@Override
	public Date getSeniorStartDate(String mobile) {

		//找出最新一个的历史订单.已支付状态.
		List<OrderSenior> list = orderSeniorMapper.selectByMobileAndPay(mobile);
		Date today = DateUtil.getNowOfDate();
		String todayStr = DateUtil.getToday();

		//如果没有历史订单，则用当前日期作为开始日期
		if (list.isEmpty()) {
			return today;
		}

		OrderSenior item = list.get(0);

		//兼容性检验
		if (item == null || item.getEndDate() == null) {
			return today;
		}

		Date endDate = item.getEndDate();
		String endDateStr = DateUtil.format(endDate, DateUtil.getDefaultPattern());

		//如果当前时间大于endDate ,则用当前日期作为开始日期
		if (DateUtil.compare(endDateStr, todayStr)) {
			return today;
		}

		String startDateStr =  DateUtil.addDay(endDate, 1, Calendar.DATE, DateUtil.getDefaultPattern());
		return DateUtil.parse(startDateStr);
	}

	/*
	 * 根据历史管家卡订单获得有效期
	 */
	@Override
	public HashMap<String, Date> getSeniorRangeDate(String mobile) {

		//找出最新一个的历史订单.已支付状态.
		List<OrderSenior> list = orderSeniorMapper.selectByMobileAndPay(mobile);
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