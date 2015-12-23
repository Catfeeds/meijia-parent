

var userId = getUrlParam("user_id");
//var $partnerListPage = 1;
function orderGetList () {
	var ajaxUrl = appRootUrl + "partner/get_partner_service_price_list.json?user_id="+userId;
	//ajaxUrl = ajaxUrl + "&page="+page;
	
	$.ajax({
		type : "GET",
		url : ajaxUrl,
		dataType : "json",
		cache : true,
		async : false,	
		success : function(data) {
			
			if (data.status == "999") return false;
			var partnerServicePriceDetailVo = data.data;
			console.log(partnerServicePriceDetailVo);
			var html = $('#store-price-list-part').html();
			//var resultHtml = '';

			var partnerServiceTypeHtml = "";
			for(var i=0 ; i < partnerServicePriceDetailVo.length; i++){
				
				var htmlPart = html;
				//var partnerUserHeadImg = '<img alt="" src="'+orderListVo[i].partner_user_head_img+'">';
				htmlPart = htmlPart.replace('{name}',partnerServicePriceDetailVo[i].name);
				htmlPart = htmlPart.replace('{No}',partnerServicePriceDetailVo[i].no);
				htmlPart = htmlPart.replace('{disPrice}',partnerServicePriceDetailVo[i].dis_price);
				
				htmlPart = htmlPart.replace('{parentId}',partnerServicePriceDetailVo[i].parent_id);
				htmlPart = htmlPart.replace('{partnerId}',partnerServicePriceDetailVo[i].partner_id);
				htmlPart = htmlPart.replace('{serviceTypeId}',partnerServicePriceDetailVo[i].service_price_id);
				htmlPart = htmlPart.replace('{userId}',partnerServicePriceDetailVo[i].user_id);
				
				
				/*htmlPart = htmlPart.replace('{partnerUserHeadImg}', partnerUserHeadImg);
				htmlPart = htmlPart.replace('{partnerUserName}',orderListVo[i].partner_user_name);
				htmlPart = htmlPart.replace('{name}',orderListVo[i].name);
				htmlPart = htmlPart.replace('{serviceTypeName}',orderListVo[i].service_type_name);
				htmlPart = htmlPart.replace('{servicePriceName}',orderListVo[i].service_price_name);
				htmlPart = htmlPart.replace('{addrName}',orderListVo[i].addr_name);
				htmlPart = htmlPart.replace('{orderStatusName}',orderListVo[i].order_status_name);
				htmlPart = htmlPart.replace('{addTimeStr}',orderListVo[i].add_time_str);
				htmlPart = htmlPart.replace('{orderMoney}',orderListVo[i].order_money);*/
			//	partner_id  service_type_id  user_id  传递的三个参数
				//htmlPart = "<a href=\"store-order-detail.html?partner_user_id="+orderListVo[i].partner_user_id+"&order_id="+orderListVo[i].order_id+"\"> " + htmlPart + "</a>";
				
				partnerServiceTypeHtml += htmlPart;
				//console.log("0001000000000000000");
				//console.log(htmlPart);
				//console.log("111111111111111111");
			
			}	
			$("#scroller").append(partnerServiceTypeHtml);
			
			//如果第一页并且返回数据等于10条，则可以显示加载更多按钮
			/*if (page == 1 && orderListVo.length >= 10) {
				$("#btn-get-more").css('display','block'); 
			}*/
			
			//如果为第二页以上，并且返回数据小于10条，则不显示加载更多按钮
			/*if (page > 1 && orderListVo.length < 10) {
				$("#btn-get-more").css('display','none'); 
			}*/
		}
	});
}

//默认加载第一页
orderGetList();


/*$("#btn-get-more").on('click', function(e) {
	$partnerListPage = $partnerListPage + 1;
	orderGetList($partnerListPage);
});*/

function clickJieDanList (partnerUserId) {
	alert(partnerUserId.partnerUserId);
	console.log(partnerUserId.partnerUserId);
};



