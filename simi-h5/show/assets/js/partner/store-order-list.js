

var partnerUserId = getUrlParam("partner_user_id");
var orderScroll = new IScroll('#wrapper', {
		mouseWheel: true,
		infiniteElements: '#scroller ',
		//infiniteLimit: 2000,
		dataset: requestData,
		dataFiller: updateContent,
		cacheSize: 1000
});


function requestData (start, count) {
	$.ajax({
		type : "GET",
		url : appRootUrl + "order/get_partner_list.json?partner_user_id="+partnerUserId,
		dataType : "json",
		cache : true,
		async : false,	
		success : function(data) {
			
			if (data.status == "999") return false;
			var orderListVo = data.data;
			
			var html = $('#order-list-part').html();
			var resultHtml = '';

			var partnerServiceTypeHtml = "";
			for(var i=0 ; i < orderListVo.length; i++){
				
				var htmlPart = html;
				var partnerUserHeadImg = '<img alt="" src="'+orderListVo[i].partner_user_head_img+'"></p>';
				console.log(partnerUserHeadImg);
				htmlPart = htmlPart.replace('{partnerUserHeadImg}', partnerUserHeadImg);
				htmlPart = htmlPart.replace('{partnerUserName}',orderListVo[i].partner_user_name);
				htmlPart = htmlPart.replace('{name}',orderListVo[i].name);
				htmlPart = htmlPart.replace('{serviceTypeName}',orderListVo[i].service_type_name);
				htmlPart = htmlPart.replace('{servicePriceName}',orderListVo[i].service_price_name);
				htmlPart = htmlPart.replace('{addrName}',orderListVo[i].addr_name);
				htmlPart = htmlPart.replace('{orderStatusName}',orderListVo[i].order_status_name);
				htmlPart = htmlPart.replace('{addTimeStr}',orderListVo[i].add_time_str);
				htmlPart = htmlPart.replace('{orderMoney}',orderListVo[i].order_money);

				partnerServiceTypeHtml += htmlPart;
				console.log(partnerServiceTypeHtml);
			}	
			$("#scroller").append(partnerServiceTypeHtml);
			orderScroll.refresh();
		}
	});
}

function updateContent (el, data) {
	el.innerHTML = data;
}

document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);






