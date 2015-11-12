
var serviceTypeId = urlParam("service_type_id");
var postdata = {};
postdata.service_price_id = serviceTypeId;
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
			var vo = data.data;
			console.log(vo);

			$("#contentStandard").html(vo.content_standard);
			$("#contentDesc").html(vo.content_desc);
			$("#contentFlow").html(vo.content_flow);
			$("#price").html(vo.price);
			$("#disPrice").html(vo.dis_price);
			$("#serviceTitle").html(vo.service_title);
			$("#name").html(vo.name);
			
			//$("#contentFlow").text(partnerServicePriceDetail.content_flow);
			//$("#contentFlow").val(partnerServicePriceDetail.content_flow);
		}
	});	