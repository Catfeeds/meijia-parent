
var partnerUserId = getUrlParam("partner_user_id");
var orderId = getUrlParam("order_id");
$.ajax({
	type : "GET",
	url : appRootUrl + "/order/get_partner_detail.json?partner_user_id="+partnerUserId+"&order_id="+orderId,
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {

		if (data.status == "0") {
			var orderDetailVo = data.data;
			console.log(orderDetailVo);
			$("#partnerUserName").html(orderDetailVo.partner_user_name);
			$("#partnerUserHeadImg").attr("src",orderDetailVo.partner_user_head_img);
			$("#name").html(orderDetailVo.name);
			$("#serviceTypeName").html(orderDetailVo.service_type_name);
			$("#servicePriceName").html(orderDetailVo.service_price_name);
			$("#addrName").html(orderDetailVo.addr_name);
			$("#orderStatusName").html(orderDetailVo.order_status_name);
			$("#orderStatusNameLast").html(orderDetailVo.order_status_name);
			$("#orderStatusNameEnd").html(orderDetailVo.order_status_name);
			$("#addTimeStr").html(orderDetailVo.add_time_str);
			$("#orderMoney").html(orderDetailVo.order_money);
			$("#orderMoneyLast").html(orderDetailVo.order_money);
			$("#orderPay").html(orderDetailVo.order_pay);
			$("#userCouponValue").html(orderDetailVo.user_coupon_value);
			$("#remarks").html(orderDetailVo.remarks);
			$("#mobile").html(orderDetailVo.mobile);
			
				
			return false;
		}
	},
		
});
$("#toSell_btn").on('click', function(e) {
	var mobile=$("#mobile").html();
	console.log(111111111);
	console.log(mobile);
	location.href='tel:'+mobile;
});

