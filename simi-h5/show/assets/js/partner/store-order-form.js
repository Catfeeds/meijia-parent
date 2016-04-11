var userId = getUrlParam("user_id");
var partnerUserId = getUrlParam("partner_user_id");

var serviceTypeId = getUrlParam("service_type_id");
$("#partner_user_id").val(partnerUserId);
$("#user_id").val(userId);
//获取客户列表
$.ajax({
	type : "GET",
	url : appRootUrl + "partner/get_users.json?partner_user_id=" + partnerUserId,
	dataType : "json",
	cache : true,
	async : false,
	success : function(data) {

		if (data.status == "999") return false;
		var userList = data.data;
		
		var selectUserHtml = "";
		var curUser;
		for (var i=0 ; i < userList.length; i++){
			var u = userList[i];
			var tuserId = u.user_id;
			var name = u.name;
			var mobile = u.mobile;
			if (userId == tuserId) curUser = u;
			selectUserHtml +="<option value =\""+tuserId+"\" mobile=\""+mobile+"\" >"+name+"</option>"
		}
		
		$("#select-user").append(selectUserHtml);
		if (userId != undefined && userId != "") {
			 $("#select-user option[value='"+userId+"']").attr("selected", true);
		}
		
		if (curUser != undefined) {
			$("#mobile").val(curUser.mobile);
			$("#user_id").val(curUser.user_id);	
		}
	}
});

// 获取服务大类
$.ajax({
	type : "GET",
	url : appRootUrl + "partner/get_partner_service_type_list.json?partner_user_id=" + partnerUserId,
	dataType : "json",
	cache : true,
	async : false,
	success : function(data) {

		if (data.status == "999") return false;
		var serviceTypes = data.data;
		
		var selectServiceTypeHtml = "";
		for (var i=0 ; i < serviceTypes.length; i++){
			var serviceType = serviceTypes[i];
			var tServiceTypeId = serviceType.id;
			var name = serviceType.name;
			selectServiceTypeHtml +="<option value =\""+tServiceTypeId+"\" >"+name+"</option>"
		}
		
		$("#select-service-type").append(selectServiceTypeHtml);
		if (serviceTypeId != undefined && serviceTypeId != "") {
			 $("#select-service-type option[value='"+serviceTypeId+"']").attr("selected", true);
		}
	}
});

// 获取服务品类

$.ajax({
	type : "GET",
	url : appRootUrl + "partner/get_partner_service_price_list.json?user_id=" + partnerUserId,
	dataType : "json",
	cache : true,
	async : false,
	success : function(data) {

		if (data.status == "999") return false;
		var servicePrices = data.data;
		
		var selectServicePriceHtml = "";
		for (var i=0 ; i < servicePrices.length; i++){
			var servicePrice = servicePrices[i];
			var servicePriceId = servicePrice.service_price_id;
			var name = servicePrice.name;
			var disPrice = servicePrice.dis_price;
			selectServicePriceHtml +="<option value =\""+servicePriceId+"\" dis_price=\""+disPrice+"\">"+name+"</option>"
		}
		$("#select-service-price").append(selectServicePriceHtml);
	}
});

//客户列表选择事件
$("#select-user").change(function(){ 
	var sel = $(this).children('option:selected');
	var v = sel.val();
	if (v == "") return false;
	
	var m = sel.attr("mobile");
	
	$("#mobile").val(m);
	$("#user_id").val(v)
	
	console.log($("#user_id").val());
	console.log($("#mobile").val());
	
});

//服务品类列表选择事件
$("#select-service-price").change(function(){ 
	var sel = $(this).children('option:selected');
	var v = sel.val();
	if (v == "") return false;
	
	var selectText = sel.text();
	console.log("selectTExt = " + selectText);
	var disPrice = sel.attr("dis_price");
	
	$("#service_price_name").val(selectText);	
	$("#order_money").val(disPrice);	
});


$('#store-order-form').validator({
	onInValid: function(validity) {
		return false;
	},
});

//提交按钮事件
$("#add_btn").on('click', function(e) {
	console.log("store-order-form-submit click");

	
	if ($('#store-order-form').data('amui.validator').isFormValid()) {
		storeOrderSubmit();
	}

});

//提交
function storeOrderSubmit() {
	
	
	var params = {};

	params.user_id = $('#user_id').val();
	params.mobile = $('#mobile').val();
	params.partner_user_id = $('#partner_user_id').val();	
	params.service_type_id = $('#select-service-type').val();
	params.service_price_name = $("#service_price_name").val();
	params.service_price_id = $("#select-service-price").val();
	params.order_money = $("#order_money").val();
	params.remarks = $("#remarks").val();
	
	console.log(params);
	
	$.ajax({
		type : "POST",
		url : appRootUrl + "order/parnter_order.json",
		dataType : "json",
		data : params,
		cache : true,
		async : false,
		success : function(data) {

			if (data.status == "999") {
				alert(data.msg);
				return false;
			}
			
			var url = "store-order-list.html?user_id="+ partnerUserId + "&service_type_id="+serviceTypeId;
			window.location.href = url;
			
		}
	});
	
}


