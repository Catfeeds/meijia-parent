
var userId = getUrlParam("user_id");
$("#hrefAdd").attr("href","store-price-form.html?user_id="+userId+"&service_price_id="+0);

$.ajax({
	type : "GET",
	url : appRootUrl + "partner/get_partnerStatus_by_user_id.json?user_id="+userId,
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
		var partners = data.data;

		var status = partners.status;
		if(status != 4){

			$('#hrefAdd').css('display','none'); 
			
		}
	}
});

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
			
			if (data.status == "999") {
			alert(data.msg);	
				return;
			}
			//return false;
			/*if (result.status == "999") {
				myApp.alert(result.msg);
				return;
			}*/
			var partnerServicePriceDetailVo = data.data;
			console.log(partnerServicePriceDetailVo);
			var html = $('#store-price-list-part').html();
		
			var partnerServiceTypeHtml = "";
			for(var i=0 ; i < partnerServicePriceDetailVo.length; i++){
				
				var htmlPart = html;
				htmlPart = htmlPart.replace('{name}',partnerServicePriceDetailVo[i].name);
				htmlPart = htmlPart.replace('{No}',partnerServicePriceDetailVo[i].no);
				htmlPart = htmlPart.replace('{disPrice}',partnerServicePriceDetailVo[i].dis_price);
				
				htmlPart = htmlPart.replace('{parentId}',partnerServicePriceDetailVo[i].parent_id);
				htmlPart = htmlPart.replace('{partnerId}',partnerServicePriceDetailVo[i].partner_id);
				htmlPart = htmlPart.replace('{serviceTypeId}',partnerServicePriceDetailVo[i].service_price_id);
				htmlPart = htmlPart.replace('{userId}',partnerServicePriceDetailVo[i].user_id);
				
				var isEnableName = "";
				if (partnerServicePriceDetailVo[i].is_enable_name == "0") isEnableName = "上架";
				if (partnerServicePriceDetailVo[i].is_enable_name == "1") isEnableName = "上架";
				
				htmlPart = htmlPart.replace('{isEnableName}',isEnableName);
				console.log(partnerServicePriceDetailVo[i].user_id+"~~~~~~~~~~~~~~~~~~~");
				
				htmlPart = "<a href=\"store-price-form.html?user_id="+partnerServicePriceDetailVo[i].user_id+"&service_price_id="+partnerServicePriceDetailVo[i].service_price_id+"\"> " + htmlPart + "</a>";
				
				
				partnerServiceTypeHtml += htmlPart;
			
			}	
			$("#scroller").append(partnerServiceTypeHtml);
		
		}
	});
}

//默认加载第一页
orderGetList();

function clickJieDanList (partnerUserId) {
	alert(partnerUserId.partnerUserId);
	console.log(partnerUserId.partnerUserId);
};
