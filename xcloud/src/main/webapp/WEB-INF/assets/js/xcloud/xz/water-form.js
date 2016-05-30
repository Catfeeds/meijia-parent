

//提交验证
$("#btn-water-submit").on('click', function(e) {

	/*var fv = formValidation();
	if (fv == false) return false;
	*/
    var form = $('#water-form');
	
	var formValidity = $('#water-form').validator().data('amui.validator').validateForm().valid
	
	if (formValidity) {
		// done, submit form
		//console.log("ok");
		//form.submit();
		// 组建提交卡片接口数据
		var params = {}
		params.user_id = $("#userId").val();
		params.addr_id = $("#addrId").val();
		params.service_price_id = $("#servicePriceId").val();
		params.service_num = $("#serviceNum").val();
		params.link_tel = $("#linkTel").val();
		params.link_man = $("#linkMan").val();
		params.remarks = $("#remarks").val();
		$.ajax({
			type : "POST",
			url : appRootUrl + "/order/post_add_water.json", // 发送给服务器的url
		//	url : "/simi/app/order/post_add_water.json", // 发送给服务器的url
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.status == "999") {
					alert(data.msg);
					return false;
				}

				if (data.status == 0) {
					location.href = "/xcloud/xz/water/list";
				}
			}
		})
	} else  {
		// fail
		console.log("fail");
	};
});

$("#servicePriceId").change(function(){ 
	var imgUrl = $("#servicePriceId").find("option:selected").attr('imgUrl');
	var price = $("#servicePriceId").find("option:selected").attr('price');
	var disprice = $("#servicePriceId").find("option:selected").attr('disprice');
	$("#imgUrl").attr("src", imgUrl);
	$("#view-price").html(price);
	$("#view-disprice").html(disprice);
	
	//设定应付金额
	var serviceNum = $("#serviceNum").val();
	if (serviceNum == undefined) return false;
	if (serviceNum == "") return false;
	
	var orderPay = disprice * serviceNum;
	$("#order-pay").html(orderPay);
});

$("#serviceNum").keyup(function(){ 
	
	var disprice = $("#servicePriceId").find("option:selected").attr('disprice');

	//设定应付金额
	var serviceNum = $("#serviceNum").val();
	if (serviceNum == undefined) return false;
	if (serviceNum == "") return false;
	
	var orderPay = disprice * serviceNum;
	$("#order-pay").html(orderPay);
});
