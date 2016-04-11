var partnerUserId = getUrlParam("partner_user_id");
var orderId = getUrlParam("order_id")

$("#partner_user_id").val(partnerUserId);
$("#order_id").val(orderId);
// 获取服务大类
$.ajax({
	type : "GET",
	url : appRootUrl + "order/get_partner_detail.json?partner_user_id=" + partnerUserId + "&order_id="+orderId,
	dataType : "json",
	cache : true,
	async : false,
	success : function(data) {

		if (data.status == "999") return false;
		var order = data.data;
		$("#userHeadImg").attr("src", order.head_img);
		$("#partnerUserName").html(order.partner_user_name);
		$("#orderStatusName").html(order.order_status_name);

		$("#orderMoney").html(order.order_money);
		$("#serviceTypeName").html(order.service_type_name);
		$("#orderStatusNameLast").html(order.order_status_name);
		$("#addTimeStr").html(order.add_time_str);
		$("#addrName").html(order.addr_name);

		$("#name").html(order.name);
		$("#nameLast").html(order.name);
		$("#mobile").html(order.mobile);
		
		$("#service_price_name").html(order.service_price_name);
		
		$("#remarks").html(order.remarks);
		
		$("#orderMoneyLast").html(order.order_money);
		$("#userCouponValue").html(order.user_coupon_value);
		$("#orderPay").html(order.order_pay);
		
	}
});

//读取订单进程
$.ajax({
	type : "GET",
	url : appRootUrl + "order/get_log.json?user_id=" + partnerUserId + "&order_id="+orderId,
	dataType : "json",
	cache : true,
	async : false,
	success : function(data) {

		if (data.status == "999") return false;
		var orderlogs = data.data;
		
		var liHtml = "";
		for(var i=0 ; i < orderlogs.length; i++){
			var d = orderlogs[i];
			var c = d.add_time_str + ":" + d.remarks;
			liHtml+="<li>"+c+"</li>";
		}
		$("#order_process_list").html(liHtml);
		genLineHeight();
		
	}
});


$("#toSell_btn").on('click', function(e) {
	var mobile=$("#mobile").html();
	location.href='tel:'+mobile;
});


//提交按钮事件
$("#process_btn").on('click', function(e) {
	if ($('#order-process-form').data('amui.validator').isFormValid()) {
		console.log("ok");
		var params = {};
		params.partner_user_id = $("#partner_user_id").val();
		params.order_id = $("#order_id").val();
		params.remarks = $("#process").val();
		console.log(params);
		
		$.ajax({
			type : "POST",
			url : appRootUrl + "order/parnter_order_process.json",
			dataType : "json",
			data : params,
			cache : true,
			async : false,
			success : function(data) {

				if (data.status == "999") {
					alert(data.msg);
					return false;
				}
				
				var d = data.data;
				var c = d.add_time_str + ":" + d.remarks;
				var lihtml = "<li>"+c+"</li>";
				$("#order_process_list").prepend(lihtml);
				genLineHeight();
				$("#process").val("");
			}
		});
	}
});


