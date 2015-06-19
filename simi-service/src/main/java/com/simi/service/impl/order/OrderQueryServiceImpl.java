package com.simi.service.impl.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.dict.DictService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderViewVo;
import com.simi.po.dao.order.OrderPricesMapper;
import com.simi.po.dao.order.OrdersMapper;
import com.simi.po.dao.user.UserAddrsMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

	@Autowired
	private OrdersMapper ordersMapper;

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
	UsersService usersService;

	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
	OrderLogService orderLogService;

	@Autowired
	DictService dictService;
	/**
	 * 根据订单主键进行查询
	 * @param id  订单id
	 * @return Orders
	 */
	@Override
	public Orders selectByPrimaryKey(Long id) {
		return ordersMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据订单编号进行查询
	 * @param orderNo  订单编号
	 * @return Orders
	 */
	@Override
	public Orders selectByOrderNo(String orderNo){
		return ordersMapper.selectByOrderNo(orderNo);
	}

	/**
	 * 根据单个订单状态进行查询
	 * @param orderStatus  订单状态
	 * @return List<Orders>
	 */
	@Override
	public List<Orders> selectByStatus(Short orderStatus) {
		return ordersMapper.selectByStatus(orderStatus);
	}
	
	/**
	 * 根据多个订单状态进行查询
	 * @param orderStatus  多个订单状态，list<Short>
	 *  @return List<Orders>
	 */	
	@Override
	public List<Orders> selectByStatuses(List<Short> orderStatus) {
		return ordersMapper.selectByStatuses(orderStatus);
	}	
	
	
	/**
	 * 根据多个查询条件进行查询，并分页展现
	 * @param orderSearchVo  查询条件，OrderSearchVo
	 * @param pageNo  		 页码
	 * @param pageSize		 每页个数
	 * @return PageInfo  List<OrderViewVo>
	 */	
	@Override
	public PageInfo selectByListPage(OrderSearchVo orderSearchVo, int pageNo, int pageSize) {

		 PageHelper.startPage(pageNo, pageSize);
         List<Orders> list = ordersMapper.selectByListPage(orderSearchVo);
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
	
	/**
	 * 根据用户ID进行订单查询，并分页展现
	 * @param orderSearchVo  查询条件，OrderSearchVo
	 * @param pageNo  		 页码
	 * @param pageSize		 每页个数
	 * @return PageInfo  List<OrderViewVo>
	 */
	@Override
	public List<OrderViewVo> selectByUserId(Long userId, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		OrderSearchVo orderSearchVo = new OrderSearchVo();
		orderSearchVo.setUserId(userId);
        List<Orders> list = ordersMapper.selectByListPage(orderSearchVo);
        List<OrderViewVo> result = this.getOrderViewList(list);
        return result;
	}
	
	/**
	 * 根据用户ID进行订单查询，并分页展现
	 * @param orderSearchVo  查询条件，OrderSearchVo
	 * @param pageNo  		 页码
	 * @param pageSize		 每页个数
	 * @return PageInfo  List<OrderViewVo>
	 */
	@Override
	public List<OrderViewVo> selectBySecId(Long secId, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		OrderSearchVo orderSearchVo = new OrderSearchVo();
		orderSearchVo.setSecId(secId);
        List<Orders> list = ordersMapper.selectByListPage(orderSearchVo);
        List<OrderViewVo> result = this.getOrderViewList(list);
        return result;
	}	

	/*
	 *  进行orderViewVo  结合了 orders , order_prices,  两张表的元素
	 */
	@Override
	public List<OrderViewVo> getOrderViewList(List<Orders> list) {

	    // 加载更多订单的信息
		List<Long> userIds = new ArrayList<Long>();
		List<Long> addrIds = new ArrayList<Long>();
        List<Long> orderIds = new ArrayList<Long>();
        List<String> orderNos = new ArrayList<String>();
        Orders item = null;
        for (int i = 0 ; i < list.size(); i ++) {
        	item = list.get(i);
        	if (item.getAddrId() > 0L) {
        		addrIds.add(item.getAddrId());
        	}
        	userIds.add(item.getUserId());
        	orderIds.add(item.getId());
        	orderNos.add(item.getOrderNo());
        }
        
        List<OrderPrices> orderPricesList = orderPricesService.selectByOrderIds(orderIds);
        
        List<Users> userList = usersService.selectByUserIds(userIds);
        
        List<UserAddrs> addrList = new ArrayList<UserAddrs>();
        if (addrIds.size() > 0) {
        	addrList = userAddrsService.selectByIds(addrIds);
        }
        //进行orderViewVo  结合了 orders , order_prices,  order_dispatchs 三张表的元素
        List<OrderViewVo> result = new ArrayList<OrderViewVo>();
        Long orderId = 0L;
        Long addrId = 0L;
        Long userId = 0L;

        for (int i = 0 ; i < list.size(); i ++) {
        	item = list.get(i);
        	orderId = item.getId();
        	addrId = item.getAddrId();
        	userId = item.getUserId();

        	OrderViewVo vo = new OrderViewVo();
        	BeanUtils.copyProperties(item, vo);

        	

        	//查找订单金额信息.
        	OrderPrices  orderPrice = null;
        	for (int k = 0; k < orderPricesList.size(); k++) {
        		orderPrice = orderPricesList.get(k);
        		if (orderPrice.getOrderId().equals(orderId)) {
        			vo.setOrderMoney(orderPrice.getOrderMoney());
        			vo.setOrderPay(orderPrice.getOrderPay());
        			vo.setCardPasswd(orderPrice.getCardPasswd());
        			vo.setPayType(orderPrice.getPayType());
        			break;
        		}
        	}

        	//城市名称
    		vo.setCityName("");
    		if (vo.getCityId() > 0L) {
    			String cityName = dictService.getCityName(vo.getCityId());
    			vo.setCityName(cityName);
    		}
    		
    		//服务类型名称
    		vo.setServiceTypeName("");
    		if (vo.getServiceType() > 0L) {
    			String serviceTypeName = dictService.getServiceTypeName(vo.getServiceType());
    			vo.setServiceTypeName(serviceTypeName);
    		}
    		
    		//用户姓名
    		String name = "";
    		Users u = null;
    		for (int j = 0; j < userList.size(); j++) {
    			u = userList.get(j);
    			if (u.getId().equals(userId)) {
    				name = u.getName();
    				break;
    			}
    		}
    		vo.setName("");
    		
        	
        	//用户地址
        	String addrName = "";
        	UserAddrs addr = null;
        	for(int n = 0; n < addrList.size(); n++) {
        		addr = addrList.get(n);
        		if (addr.getId().equals(addrId)) {
        			addrName = addr.getName() + addr.getAddr();
        			break;
        		}
        	}

        	vo.setServiceAddr(addrName);

        	result.add(vo);
        }

        return result;
	}
	
	/*
	 *  进行orderViewVo  结合了 orders , order_prices,  两张表的元素
	 */
	@Override
	public OrderViewVo getOrderView(Orders order) {

	    // 加载更多订单的信息
		OrderViewVo vo = new OrderViewVo();
		BeanUtils.copyProperties(order, vo);
		
		//订单价格信息
		OrderPrices orderPrice = orderPricesService.selectByOrderId(vo.getId());
		
		vo.setPayType(orderPrice.getPayType());
		vo.setOrderMoney(orderPrice.getOrderMoney());
		vo.setOrderPay(orderPrice.getOrderPay());
		vo.setCardPasswd(orderPrice.getCardPasswd());
		
		//城市名称
		vo.setCityName("");
		if (vo.getCityId() > 0L) {
			String cityName = dictService.getCityName(vo.getCityId());
			vo.setCityName(cityName);
		}
		
		//服务类型名称
		vo.setServiceTypeName("");
		if (vo.getServiceType() > 0L) {
			String serviceTypeName = dictService.getServiceTypeName(vo.getServiceType());
			vo.setServiceTypeName(serviceTypeName);
		}
		
		//用户称呼
		vo.setName("");
		if (vo.getUserId() > 0L) {
			Users user = usersService.getUserById(vo.getUserId());
			vo.setName(user.getName());
		}
		
        //用户地址
		vo.setServiceAddr("");
		if (vo.getAddrId() > 0L) {
			UserAddrs userAddr = userAddrsService.selectByPrimaryKey(vo.getAddrId());
			vo.setServiceAddr(userAddr.getName() + userAddr.getAddr());
		}
		
        return vo;
	}	

}