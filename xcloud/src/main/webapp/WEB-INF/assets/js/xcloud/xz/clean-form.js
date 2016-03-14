
//提交验证
$("#btn-clean-submit").on('click', function(e) {

	/*var fv = formValidation();
	if (fv == false) return false;
	*/
    var form = $('#clean-form');
	
	var formValidity = $('#clean-form').validator().data('amui.validator').validateForm().valid
	
	if (formValidity) {
		// done, submit form
		//console.log("ok");
		//form.submit();
		// 组建提交卡片接口数据
		var params = {}
		params.user_id = $("#userId").val();
		params.addr_id = $("#addrId").val();
		//params.service_price_id = $("#servicePriceId").val();
	//	params.service_num = $("#serviceNum").val();
		params.link_tel = $("#linkTel").val();
		params.link_man = $("#linkMan").val();
		params.remarks = $("#remarks").val();
		params.clean_type = $("#cleanType").val();
		$.ajax({
			type : "POST",
			url : appRootUrl + "/order/post_add_clean.json", // 发送给服务器的url
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.status == "999") {
					alert(data.msg);
					return false;
				}

				if (data.status == 0) {
					location.href = "/xcloud/xz/clean/list";
				}
			}
		})
	} else  {
		// fail
		console.log("fail");
	};
});
