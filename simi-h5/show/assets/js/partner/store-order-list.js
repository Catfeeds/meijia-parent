

var partnerUserId = getUrlParam("user_id");
var serviceTypeId = getUrlParam("service_type_id");
//alert(window.location.href);
var $partnerListPage = 1;

function orderGetList (page) {
	var ajaxUrl = appRootUrl + "order/get_partner_list.json?partner_user_id="+partnerUserId;
	ajaxUrl = ajaxUrl + "&page="+page;
	
	$.ajax({
		type : "GET",
		url : ajaxUrl,
		dataType : "json",
		cache : true,
		async : false,	
		success : function(data) {
			
			if (data.status == "999")// return false;
			{
				myApp.alert(result.msg);
				return;
			}
			var orderListVo = data.data;
			var html = $('#order-list-part').html();
			//var resultHtml = '';
            
			var partnerServiceTypeHtml = "";
			for(var i=0 ; i < orderListVo.length; i++){
				
				var htmlPart = html;
				var partnerUserHeadImg = '<img alt="" src="'+orderListVo[i].partner_user_head_img+'">';
				htmlPart = htmlPart.replace('{orderId}',orderListVo[i].order_id);
				htmlPart = htmlPart.replace('{userId}',orderListVo[i].user_id);
				htmlPart = htmlPart.replace('{partnerUserId}',orderListVo[i].partner_user_id);
				htmlPart = htmlPart.replace('{partnerUserHeadImg}', partnerUserHeadImg);
				htmlPart = htmlPart.replace('{partnerUserName}',orderListVo[i].partner_user_name);
				htmlPart = htmlPart.replace('{name}',orderListVo[i].name);
				
				htmlPart = htmlPart.replace('{serviceTypeId}',orderListVo[i].service_type_id);
				htmlPart = htmlPart.replace('{serviceTypeName}',orderListVo[i].service_type_name + " " + orderListVo[i].service_price_name);
				htmlPart = htmlPart.replace('{servicePriceName}',orderListVo[i].service_price_name);
				htmlPart = htmlPart.replace('{addrName}',orderListVo[i].addr_name);
				htmlPart = htmlPart.replace('{orderStatusName}',orderListVo[i].order_status_name);
				htmlPart = htmlPart.replace('{addTimeStr}',orderListVo[i].add_time_str);
				htmlPart = htmlPart.replace('{orderMoney}',orderListVo[i].order_money);
				
				htmlPart = htmlPart.replace('{orderExtra}',orderListVo[i].order_extra);
				
				htmlPart = "<a href=\"store-order-detail.html?partner_user_id="+orderListVo[i].partner_user_id+"&order_id="+orderListVo[i].order_id+"\"> " + htmlPart + "</a>";
				
				partnerServiceTypeHtml += htmlPart;
			}	
			$("#scroller").append(partnerServiceTypeHtml);
			
			//如果第一页并且返回数据等于10条，则可以显示加载更多按钮
			if (page == 1 && orderListVo.length >= 10) {
				$("#btn-get-more").css('display','block'); 
			}
			
			//如果为第二页以上，并且返回数据小于10条，则不显示加载更多按钮
			if (page > 1 && orderListVo.length < 10) {
				$("#btn-get-more").css('display','none'); 
			}
		}
	});
}

//默认加载第一页
orderGetList(1);


$("#btn-get-more").on('click', function(e) {
	$partnerListPage = $partnerListPage + 1;
	orderGetList($partnerListPage);
});

//function clickJieDanList (userId) {
//	alert(userId.userId);
//	console.log(userId.userId);
//};

function orderAddForm(userId, serviceTypeId) {
	//alert("user_id = " + userId + "-- order_id=" + orderId + "---service_type_id="+serviceTypeId);
	var url = "store-order-form.html?user_id=" + userId;
		url+= "&partner_user_id=" + partnerUserId;
		url+= "&service_type_id=" + serviceTypeId;
	window.location.href = url;
}


