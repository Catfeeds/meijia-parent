package com.simi.vo.order;

import java.math.BigDecimal;

import com.simi.po.model.order.Orders;

public class OrdersDetailVo {

    private Long order_Id;

    private String mobile;

    private Long city_id;

    private String order_no;

    private BigDecimal rest_money; 
    
    private BigDecimal order_money;
    
    private BigDecimal order_pay;
    
    private String send_datas;
    
    private Long service_type;
    
    private Long pay_type;
    
    private Long order_rate;

    private Long service_date;
    
    private Long addr_id;

    private String remarks;
    
    private String start_time;

    private String service_hour;

    private Short order_status;

    private Short is_cancel;

    private Long add_time;
    
    private String order_rate_content;
    
    private BigDecimal price_hour;

    private BigDecimal price_hour_discount;
    
    private String addr;   
    
    private String cell_name;

    private String cell_addr;
    
    private String agent_mobile;

    private Short clean_tools;

    private BigDecimal clean_tools_price;
    
    public OrdersDetailVo() {
	}
    
	public OrdersDetailVo(Orders orders) {
		this.order_Id = orders.getId();
		this.mobile = orders.getMobile();
		this.city_id = orders.getCityId();
		this.order_no = orders.getOrderNo();
		this.order_rate_content = orders.getOrderRateContent();
		this.service_type = Long.valueOf(orders.getServiceType());
		this.order_rate = Long.valueOf(orders.getOrderRate());
		this.service_date = orders.getServiceDate();
		this.addr_id = orders.getAddrId();
		this.remarks = orders.getRemarks();
		this.start_time = String.valueOf(orders.getStartTime());
		this.service_hour = String.valueOf(orders.getServiceHours());
		this.order_status = orders.getOrderStatus();
		this.add_time = orders.getAddTime();
		this.agent_mobile = orders.getAgentMobile();
	}

	public Long getOrder_Id() {
		return order_Id;
	}

	public void setOrder_Id(Long order_Id) {
		this.order_Id = order_Id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public BigDecimal getOrder_money() {
		return order_money;
	}

	public void setOrder_money(BigDecimal order_money) {
		this.order_money = order_money;
	}

	public Long getService_type() {
		return service_type;
	}

	public void setService_type(Long service_type) {
		this.service_type = service_type;
	}

	public Long getService_date() {
		return service_date;
	}

	public void setService_date(Long service_date) {
		this.service_date = service_date;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getService_hour() {
		return service_hour;
	}

	public void setService_hour(String service_hour) {
		this.service_hour = service_hour;
	}

	public Short getOrder_status() {
		return order_status;
	}

	public void setOrder_status(Short order_status) {
		this.order_status = order_status;
	}

	public Long getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Long add_time) {
		this.add_time = add_time;
	}

	public BigDecimal getOrder_pay() {
		return order_pay;
	}



	public void setOrder_pay(BigDecimal order_pay) {
		this.order_pay = order_pay;
	}


	public String getSend_datas() {
		return send_datas;
	}



	public void setSend_datas(String send_datas) {
		this.send_datas = send_datas;
	}



	public Long getPay_type() {
		return pay_type;
	}



	public void setPay_type(Long pay_type) {
		this.pay_type = pay_type;
	}



	public Long getOrder_rate() {
		return order_rate;
	}



	public void setOrder_rate(Long order_rate) {
		this.order_rate = order_rate;
	}



	public Long getAddr_id() {
		return addr_id;
	}



	public void setAddr_id(Long addr_id) {
		this.addr_id = addr_id;
	}



	public String getRemarks() {
		return remarks;
	}



	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOrder_rate_content() {
		return order_rate_content;
	}

	public void setOrder_rate_content(String order_rate_content) {
		this.order_rate_content = order_rate_content;
	}

	public Short getIs_cancel() {
		return is_cancel;
	}

	public void setIs_cancel(Short is_cancel) {
		this.is_cancel = is_cancel;
	}

	public BigDecimal getPrice_hour() {
		return price_hour;
	}

	public void setPrice_hour(BigDecimal price_hour) {
		this.price_hour = price_hour;
	}

	public BigDecimal getPrice_hour_discount() {
		return price_hour_discount;
	}

	public void setPrice_hour_discount(BigDecimal price_hour_discount) {
		this.price_hour_discount = price_hour_discount;
	}

	public BigDecimal getRest_money() {
		return rest_money;
	}

	public void setRest_money(BigDecimal rest_money) {
		this.rest_money = rest_money;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCell_name() {
		return cell_name;
	}

	public void setCell_name(String cell_name) {
		this.cell_name = cell_name;
	}

	public String getCell_addr() {
		return cell_addr;
	}

	public void setCell_addr(String cell_addr) {
		this.cell_addr = cell_addr;
	}

	public String getAgent_mobile() {
		return agent_mobile;
	}

	public void setAgent_mobile(String agent_mobile) {
		this.agent_mobile = agent_mobile;
	}
	public Short getClean_tools() {
		return clean_tools;
	}

	public void setClean_tools(Short cleanTools) {
		this.clean_tools = cleanTools;
	}

	public BigDecimal getClean_tools_price() {
		return clean_tools_price;
	}

	public void setClean_tools_price(BigDecimal cleanToolsPrice) {
		this.clean_tools_price = cleanToolsPrice;
	}
}