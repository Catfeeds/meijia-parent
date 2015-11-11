var postdata = {};
postdata.service_price_id = 1;
$.ajax({
		type : "GET",
		url : siteAPIPath  +"partner/get_partner_service_price_detail.json?",
		dataType : "json",
		cache : true,
		data : postdata,
		success : function(data) {
		//	var result = JSON.parse(data.response);
		//	var partnerServicePriceDetail = result.data;
			
			//var result = data.response;
			var partnerServicePriceDetail = data.data;
			console.log(partnerServicePriceDetail);

			$("#contentStandard").html(partnerServicePriceDetail.content_standard);
			$("#contentDesc").html(partnerServicePriceDetail.content_desc);
			$("#contentFlow").html(partnerServicePriceDetail.content_flow);
			$("#price").html(partnerServicePriceDetail.price);
			$("#disPrice").html(partnerServicePriceDetail.dis_price);
			//$("#contentFlow").text(partnerServicePriceDetail.content_flow);
			//$("#contentFlow").val(partnerServicePriceDetail.content_flow);
		}
	});	