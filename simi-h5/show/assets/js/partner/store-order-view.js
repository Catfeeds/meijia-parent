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
		
		$("#name").html(order.name);
		$("#mobile").html(order.mobile);
		$("#service_type_name").html(order.service_type_name);
		$("#service_price_name").html(order.service_price_name);
		$("#order_money").html(order.order_money);
		$("#remarks").html(order.remarks);
		
	}
});