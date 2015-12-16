

var partnerUserId = getUrlParam("partner_user_id");
$.ajax({
	type : "GET",
	url : appRootUrl + "order/get_partner_list.json?partner_user_id="+partnerUserId,
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
	
		if (data.status == "999") return false;
		var orderListVo = data.data;
		console.log(orderListVo);
		
		var html = $('#order-am-list-part').html();
		var resultHtml = '';
		
		
		var partnerServiceTypeHtml = "";
		for(var i=0 ; i < orderListVo.length; i++){
			
			var htmlPart = html;
			var partnerUserHeadImg = '<img alt="" src="'+orderListVo[i].partner_user_head_img+'.png"></p>';
			console.log(partnerUserHeadImg);
			htmlPart = htmlPart.replace(new RegExp('{partnerUserHeadImg}',"gm"), partnerUserHeadImg);
			htmlPart = htmlPart.replace(new RegExp('{partnerUserName}',"gm"),orderListVo[i].partner_user_name);
			htmlPart = htmlPart.replace(new RegExp('{name}',"gm"),orderListVo[i].name);
			htmlPart = htmlPart.replace(new RegExp('{serviceTypeName}',"gm"),orderListVo[i].service_type_name);
			htmlPart = htmlPart.replace(new RegExp('{servicePriceName}',"gm"),orderListVo[i].service_price_name);
			htmlPart = htmlPart.replace(new RegExp('{addrName}',"gm"),orderListVo[i].addr_name);
			htmlPart = htmlPart.replace(new RegExp('{orderStatusName}',"gm"),orderListVo[i].order_status_name);
			htmlPart = htmlPart.replace(new RegExp('{addTimeStr}',"gm"),orderListVo[i].add_time_str);
			htmlPart = htmlPart.replace(new RegExp('{orderMoney}',"gm"),orderListVo[i].order_money);
			/*$("#partnerUserName").html(orderListVo[i].partner_user_name);
			$("#partnerUserHeadImg").attr("src",orderListVo[i].partner_user_head_img);
			$("#name").html(orderListVo[i].name);
			$("#serviceTypeName").html(orderListVo[i].service_type_name);
			$("#servicePriceName").html(orderListVo[i].service_price_name);
			$("#addrName").html(orderListVo[i].addr_name);
			$("#orderStatusName").html(orderListVo[i].order_status_name);
			$("#addTimeStr").html(orderListVo[i].add_time_str);
			$("#orderMoney").html(orderListVo[i].order_money);*/

			partnerServiceTypeHtml += htmlPart;
			console.log(partnerServiceTypeHtml);
		}	
		$("#card-am-list").append(partnerServiceTypeHtml);
	}
});
