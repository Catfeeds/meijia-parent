

var userId = getUrlParam("user_id");

$("#hrefAdd").attr("href","store-price-form.html?user_id="+userId);
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
			
			//给新增按钮的href赋值
         //   $("#hrefAdd").attr("href","store-price-form.html?user_id="+partnerServicePriceDetailVo[0].user_id);
            
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
				console.log(partnerServicePriceDetailVo[i].user_id+"~~~~~~~~~~~~~~~~~~~");
				
				partnerServiceTypeHtml += htmlPart;
				//console.log(htmlPart);
			
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
