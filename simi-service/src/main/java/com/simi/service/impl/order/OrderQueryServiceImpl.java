package com.simi.service.impl.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.common.Constants;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderQueryService;
import com.simi.vo.OrdersList;
import com.simi.vo.order.OrderSearchVo;
import com.simi.vo.order.OrderViewVo;
import com.simi.vo.order.OrdersVo;
import com.simi.po.dao.order.OrderPricesMapper;
import com.simi.po.dao.order.OrdersMapper;
import com.simi.po.dao.user.UserAddrsMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserAddrs;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

	@Autowired
	private OrdersMapper ordersMapper;

	@Autowired
	private OrderPricesMapper orderPricesMapper;

	@Autowired
	UsersMapper usersMapper;

	@Autowired
	private UserAddrsMapper userAddrsMapper;

	@Autowired
	OrderLogService orderLogService;

	@Override
	public Orders selectByPrimaryKey(Long id) {
		return ordersMapper.selectByPrimaryKey(id);
	}

	@Override
	public Orders selectByOrderNo(String orderNo){
		return ordersMapper.selectByOrderNo(orderNo);
	}

	@Override
	public List<OrdersList> selectByMobile(String mobile, int page) {
		int start = 0;
		int end = Constants.PAGE_MAX_NUMBER;

		if(page>=1) {
			start = (page - 1) * Constants.PAGE_MAX_NUMBER;
//			end = page * Constants.PAGE_MAX_NUMBER;
		}
		return ordersMapper.selectByMobile(mobile, start, end);
	}

	@Override
	public List<OrdersList> selectByAgentMobile(String mobile, int page) {
		int start = 0;
		int end = Constants.PAGE_MAX_NUMBER;
		if(page>=1) {
			start = (page - 1) * Constants.PAGE_MAX_NUMBER;
//			end = page * Constants.PAGE_MAX_NUMBER;
		}
		return ordersMapper.selectByAgentMobile(mobile, start, end);
	}

	@Override
	public List<Orders> queryOrdersByState(Short order_state) {
		return ordersMapper.queryOrdersByState(order_state);
	}

	@Override
	public List<Orders> queryOrdersByStateAndScore() {
		return ordersMapper.queryOrdersByStateAndScore() ;
	}



	@Override
	public PageInfo searchVoListPage(OrderSearchVo searchModel, int pageNo, int pageSize) {

		HashMap<String,Object> conditions = new HashMap<String,Object>();
		String mobile = searchModel.getMobile();
		String orderNo = searchModel.getOrderNo();
		String cellId = searchModel.getCellId();
		Short orderFrom = searchModel.getOrderFrom();
		Short orderStatus = searchModel.getOrderStatus();
		Short serviceType = searchModel.getServiceType();
		String addTime = searchModel.getAddTime();
		String serviceDate = searchModel.getServiceDate();
		String serviceTime = searchModel.getServiceTime();

		if(serviceTime!=null && !serviceTime.isEmpty()
				&& serviceDate!=null && !serviceDate.isEmpty())
				{
			StringBuffer sb = new StringBuffer(serviceDate+" "+serviceTime);
			Long   startTimes = TimeStampUtil.getMillisOfDayFull(sb.toString())/1000;

			conditions.put("startTime", startTimes);
		}

		if(addTime!=null && !addTime.isEmpty()){
			Long addTimes = TimeStampUtil.getMillisOfDay(addTime)/1000;
			conditions.put("addTime", addTimes);
			conditions.put("addTimeOneDay", (addTimes+86400));
		}
		if(serviceDate!=null && !serviceDate.isEmpty()){
			Long serviceDateLong =TimeStampUtil.getMillisOfDay(serviceDate)/1000 ;
			conditions.put("serviceDate", serviceDateLong);
		}

		if(mobile!=null && !mobile.isEmpty()){
			conditions.put("mobile", mobile.trim());
		}
		if(orderNo!=null && !orderNo.isEmpty()){
			conditions.put("orderNo", orderNo.trim());
		}
		if(cellId!=null && !cellId.isEmpty() ){
			conditions.put("cellId",cellId.trim());
		}
		if(orderFrom!=null && orderFrom!=3 ){
			conditions.put("orderFrom",orderFrom);
		}
		if(orderStatus !=null && orderStatus!=8){
			conditions.put("orderStatus", orderStatus);
		}
		if(serviceType !=null && serviceType!=0){
			conditions.put("serviceType",serviceType);
		}

		 PageHelper.startPage(pageNo, pageSize);
         List<Orders> list = ordersMapper.selectByListPage(conditions);
         if(list!=null && list.size()!=0){
             List<OrderViewVo> orderViewList = this.getOrderViewList(list);

             for(int i = 0; i < list.size(); i++) {
            	 if (orderViewList.get(i) != null) {
            		 list.set(i, orderViewList.get(i));
            	 }
             }
         }


         PageInfo result = new PageInfo(list);
        return result;
    }
	@Override
	public PageInfo listPage(String mobile, String orderNo, int pageNo, int pageSize) {

		HashMap<String,String> conditions = new HashMap<String,String>();

		if(mobile!=null && !mobile.isEmpty()){
			conditions.put("mobile", mobile);
		}
		if(orderNo!=null && !orderNo.isEmpty()){
			conditions.put("orderNo", orderNo.trim());
		}

		 PageHelper.startPage(pageNo, pageSize);
         List<Orders> list = ordersMapper.selectByListPage(conditions);
         if(list!=null && list.size()>0){
        	 List<OrderViewVo> orderViewList = this.getOrderViewList(list);
        	 for(int i = 0; i < list.size(); i++) {
        	 if (orderViewList.get(i) != null) {
        		 list.set(i, orderViewList.get(i));
        	 }
         	}
         }

         PageInfo result = new PageInfo(list);
        return result;
    }

	@Override
	public PageInfo listPageOrdersVo(String mobile, String orderNo, int pageNo, int pageSize) {

		HashMap<String,String> conditions = new HashMap<String,String>();

		if(mobile!=null && !mobile.isEmpty()){
			conditions.put("mobile", mobile);
		}
		if(orderNo!=null && !orderNo.isEmpty()){
			conditions.put("orderNo", orderNo.trim());
		}

		 PageHelper.startPage(pageNo, pageSize);
         List<Orders> list = ordersMapper.selectByListPage(conditions);
         PageInfo result = new PageInfo(list);
         List<OrdersVo> listVos = new ArrayList<OrdersVo>();
         for (Iterator iterator = result.getList().iterator(); iterator.hasNext();) {
			Orders orders = (Orders) iterator.next();
			OrdersVo ordersVo = new OrdersVo();
			BeanUtils.copyProperties(orders, ordersVo);
			if(usersMapper.selectByMobile(orders.getMobile())!=null){
				ordersVo.setUserName(usersMapper.selectByMobile(orders.getMobile()).getName());
			}
			if(ordersMapper.selectByOrderNo(orders.getOrderNo())!=null &&
					userAddrsMapper.selectByPrimaryKey(ordersMapper.selectByOrderNo(orders.getOrderNo()).getAddrId())!=null){

				ordersVo.setUserAddrs(userAddrsMapper.selectByPrimaryKey(ordersMapper.selectByOrderNo(orders.getOrderNo()).getAddrId()).getAddr());
			}
	        listVos.add(ordersVo);
		}

        return new PageInfo(listVos);
    }

	/*
	 *  进行orderViewVo  结合了 orders , order_prices,  order_dispatchs 三张表的元素
	 */
	@Override
	public List<OrderViewVo> getOrderViewList(List<Orders> list) {

	       // 加载更多订单的信息
		List<Long> cellIds = new ArrayList<Long>();
		List<Long> addrIds = new ArrayList<Long>();
        List<Long> orderIds = new ArrayList<Long>();
        List<String> orderNos = new ArrayList<String>();
        Orders item = null;
        for (int i = 0 ; i < list.size(); i ++) {
        	item = list.get(i);
        	cellIds.add(item.getCellId());
        	addrIds.add(item.getAddrId());
        	orderIds.add(item.getId());
        	orderNos.add(item.getOrderNo());
        }
        List<OrderPrices> orderPricesList = orderPricesMapper.selectByOrderIds(orderIds);

        List<UserAddrs> addrList = userAddrsMapper.selectByIds(addrIds);
        //进行orderViewVo  结合了 orders , order_prices,  order_dispatchs 三张表的元素
        List<OrderViewVo> result = new ArrayList<OrderViewVo>();
        Long orderId = 0L;
        Long cellId = 0L;
        Long addrId = 0L;

        for (int i = 0 ; i < list.size(); i ++) {
        	item = list.get(i);
        	orderId = item.getId();
        	cellId = item.getCellId();
        	addrId = item.getAddrId();

        	OrderViewVo vo = new OrderViewVo();
        	BeanUtils.copyProperties(item, vo);

        	//用户地址

        	//查找订单金额信息.
        	OrderPrices  orderPrice = null;
        	for (int k = 0; k < orderPricesList.size(); k++) {
        		orderPrice = orderPricesList.get(k);
        		if (orderPrice.getOrderId().equals(orderId)) {
        			vo.setCleanTools(orderPrice.getCleanTools());
        			vo.setCleanToolsPrice(orderPrice.getCleanToolsPrice());
        			vo.setPriceHour(orderPrice.getPriceHour());
        			vo.setPriceHourDiscount(orderPrice.getPriceHourDiscount());
        			vo.setOrderMoney(orderPrice.getOrderMoney());
        			vo.setOrderPay(orderPrice.getOrderPay());
        			vo.setCardPasswd(orderPrice.getCardPasswd());
        			vo.setPayType(orderPrice.getPayType());
        			break;
        		}
        	}

        	String addrName = "";
        	UserAddrs addr = null;
        	for(int n = 0; n < addrList.size(); n++) {
        		addr = addrList.get(n);
        		if (addr.getId().equals(addrId)) {
        			addrName = addr.getName() + addr.getAddr();
        			break;
        		}
        	}

        	vo.setUserAddrs(addrName);

        	result.add(vo);
        }

        return result;
	}

	@Override
	public List<Orders> queryOrdersByStateAndStartTime(HashMap conditions){

		return ordersMapper.queryOrdersByStateAndStartTime(conditions);
	}

	@Override
	public List<Orders> queryOrdersCompletedAndUnEvaluated(HashMap conditions){
		return ordersMapper.queryOrdersCompletedAndUnEvaluated(conditions);
	}

	/*
	 * 根据订单号找出相同城市 + 相同日期 + 相同开始时间的订单信息, 并且为已派工的
	 */
	@Override
	public List<Orders> selectBySameDateTime(String orderNo) {

		List<Orders> result = new ArrayList<Orders>();
		Orders order = ordersMapper.selectByOrderNo(orderNo);
		if (order == null) {
			return result;
		}

		Long cityId = order.getCityId();
		Long serviceDate = order.getServiceDate();
		Long startTime = order.getStartTime();
		startTime = TimeStampUtil.timeStampToDateHour(startTime * 1000) /1000;

		Short serviceHour = order.getServiceHours();
		Long endTime = startTime + serviceHour * 3600;

		HashMap<String,Object> conditions = new HashMap<String,Object>();

		conditions.put("cityId", cityId);
		conditions.put("serviceDate", serviceDate);
		conditions.put("startTime", startTime);
		conditions.put("endTime", endTime);
		conditions.put("orderStatus", 3);

		result = ordersMapper.selectBySameDateTime(conditions);

		return result;

	}

	@Override
	public List<Orders> queryOrdersByStates(List<Short> orderStates) {
		return ordersMapper.queryOrdersByStates(orderStates);
	}
}