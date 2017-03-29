package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderScoreService;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderScoreVo;
import com.simi.po.dao.order.OrderScoreMapper;
import com.simi.po.model.order.OrderScore;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderScoreServiceImpl implements OrderScoreService {

	@Autowired
	private OrderScoreMapper orderScoreMapper;

	@Autowired
	UsersService usersService;	
		
	@Autowired
	UserDetailScoreService userDetailScoreService;
		
	@Override
	public int deleteByPrimaryKey(Long id) {
		return orderScoreMapper.deleteByPrimaryKey(id);
	}

	@Override
	public OrderScore initOrderScore() {
		OrderScore record = new OrderScore();
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setUserId(0L);
		record.setMobile("");
		record.setOrderStatus((short) 0);		
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		record.setCredits(0);
		record.setAppkey("");
		record.setTimestamp("");
		record.setDescription("");
		record.setOrdernum("");
		record.setType("");
		record.setFaceprice("");
		record.setActualprice("");
		record.setIp("");
		record.setWaitaudit("");
		record.setParams("");
		record.setSign("");

		
		return record;
	}

	@Override
	public Long insert(OrderScore record) {
		return orderScoreMapper.insert(record);
	}

	@Override
	public Long insertSelective(OrderScore record) {
		return orderScoreMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKey(OrderScore record) {
		return orderScoreMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderScore record) {
		return orderScoreMapper.updateByPrimaryKeySelective(record);
	}
		
	@Override
	public List<OrderScore> selectByUserId(Long userId) {
		return orderScoreMapper.selectByUserId(userId);
	}


	@Override
	public OrderScore selectByOrderNo(String orderNo) {
		return orderScoreMapper.selectByOrderNo(orderNo);
	}
	
	@Override
	public OrderScore selectByPrimaryKey(Long orderId) {
		return orderScoreMapper.selectByPrimaryKey(orderId);
	}
	
	@Override
	public OrderScore selectByOrderId(Long orderId) {
		return orderScoreMapper.selectByOrderId(orderId);
	}
	
	@Override
	public OrderScore selectByOrderNum(String orderNum) {
		return orderScoreMapper.selectByOrderNum(orderNum);
	}
	
	@Override
	public PageInfo selectByListPage(OrderSearchVo searchVo, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<OrderScore> list = orderScoreMapper.selectByListPage(searchVo);
		PageInfo result = new PageInfo(list);
		return result;
	}
	
	@Override
	public List<OrderScore> selectBySearchVo(OrderSearchVo searchVo) {
		return orderScoreMapper.selectBySearchVo(searchVo);
	}
	
	@Override
	public OrderScoreVo getVos(OrderScore item) {
		OrderScoreVo vo = new OrderScoreVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
		
		String addTimeStr = TimeStampUtil.timeStampToDateStr(vo.getAddTime() * 1000, "yyyy-MM-dd HH:mm");
		vo.setAddTimeStr(addTimeStr);
		
		String orderStatusStr = "";
		if (vo.getOrderStatus().equals((short)0)) orderStatusStr = "兑换中";
		if (vo.getOrderStatus().equals((short)1)) orderStatusStr = "兑换成功";
		vo.setOrderStatusStr(orderStatusStr);
		
		//分转换为元
		String facePrice = vo.getFaceprice();
		BigDecimal facePriceMath = new BigDecimal(facePrice);
		facePriceMath = MathBigDecimalUtil.div(facePriceMath, new BigDecimal(100));
		facePrice = MathBigDecimalUtil.round2(facePriceMath);
		vo.setFaceprice(facePrice);
		
		String actualprice = vo.getActualprice();
		BigDecimal actualpriceMath = new BigDecimal(actualprice);
		actualpriceMath = MathBigDecimalUtil.div(actualpriceMath, new BigDecimal(100));
		actualprice = MathBigDecimalUtil.round2(actualpriceMath);
		vo.setActualprice(actualprice);
		
		return vo;
		
	}
	
}