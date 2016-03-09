

var userId = getUrlParam("user_id");
//alert(window.location.href);
//商品管理
$("#storePrice").attr("href","store-price-list.html?user_id="+userId);
//订单处理
$("#orderControll").attr("href","store-order-list.html?user_id="+userId);
//人员管理
$("#userControll").attr("href","invite-index.html");

//判断用户是否是服务商
$.ajax({
	type : "GET",
	url : appRootUrl + "partner/get_userType_by_user_id.json?user_id="+userId,
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
	   
		//if (data.status == "999") return false;
		if (data.status == "999") {
			//alert(data.msg);	
			
			if (confirm(data.msg)) {
				location.href = "store-index.html?user_id="+userId;
				return;
			}
			//alert(data.msg);
			}
		if (data.status == "100") {
			
			if (confirm(data.msg)) {

				return false;
			}
			}
		var partnerUsers = data.data;
		console.log(partnerUsers);
		var partnerId = partnerUsers.partner_id;
		console.log(partnerId+"+++++++++++++++++++partnerId");
		var serviceTypeId = partnerUsers.service_type_id;
		console.log(serviceTypeId+"))))))))))))))))))serviceTypeId");
		$("#partner_id").val(partnerId);
		$("#user_id").val(userId);
		$("#service_type_id").val(serviceTypeId);

	}
});