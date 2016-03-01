
var userId = getUrlParam("user_id");

if (userId == undefined && userId == "") {
	return false;
}

//获取用户基础信息
$.ajax({
	type : "GET",
	url : appRootUrl + "user/get_user_base.json?user_id="+userId,
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
		if (data.status == "999") return false;		
		
		var vo = data.data;
		$("#mobile").val(vo.mobile);
	}
});

//获取送水的商品数据
$.ajax({
	type : "GET",
	url : appRootUrl + "user/get_addrs.json?user_id=18",
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {

		if (data.status == "999") return false;		
		var servicePriceList = data.data;
		
		var waterSelectOption = "";
		for(var i=0 ; i < servicePriceList.length; i++){
			var servicePriceId = servicePriceList[i].service_price_id;
			var disPrice = servicePriceList[i].disPrice;
			var serviceName = servicePriceList[i].name + "(" + disPrice + ")";

			waterSelectOption +="<option value =\""+servicePriceId+"\" disprice=\""+disPrice+"\">"+serviceName+"</option>";
			
		}	
		$("#addrId").append(userAddrHtml);

	}
});


//获取用户地址
$.ajax({
	type : "GET",
	url : appRootUrl + "user/get_addrs.json?user_id="+userId,
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {

		if (data.status == "999") return false;		
		var userAddr = data.data;
		
		var userAddrHtml = "";
		for(var i=0 ; i < userAddr.length; i++){
			var addrId = userAddr[i].id;
			var addrName = userAddr[i].name + userAddr[i].addr;
			var isDefault = userAddr[i].is_default;
			if (isDefault == "1") {
				userAddrHtml +="<option value =\""+addrId+"\" selected>"+addrName+"</option>";
			} else {
				userAddrHtml +="<option value =\""+addrId+"\" >"+addrName+"</option>";
			}
		}	
		$("#addrId").append(userAddrHtml);

	}
});

$("#add_btn").on('click', function(e) {
	console.log("reg-submit click");
	var $form = $('#order-green-form');

});
