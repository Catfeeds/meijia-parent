
//提交验证
$("#btn-express-submit").on('click', function(e) {

	/*var fv = formValidation();
	if (fv == false) return false;
	*/
    var form = $('#express-form');
	
	var formValidity = $('#express-form').validator().data('amui.validator').validateForm().valid
	
	if (formValidity) {
		// done, submit form
		//console.log("ok");
		//form.submit();
		// 组建提交卡片接口数据
		var params = {}
		params.user_id = $("#userId").val();
		params.express_no = $("#expressNo").val();
		params.express_type = $('input:radio[name=expressType]:checked').val();
		params.pay_type = $('input:radio[name=payType]:checked').val();
		
		params.express_id = $("#expressId").val();
		params.from_addr = $("#fromAddr").val();
		params.from_name = $("#fromName").val();
		params.from_tel = $("#fromTel").val();
		params.to_addr = $("#toAddr").val();
		params.to_name = $("#toName").val();
		params.to_tel = $("#toTel").val();
		params.remarks = $("#remarks").val();
		
		$.ajax({
			type : "POST",
			url : appRootUrl + "/record/post_add_express.json", // 发送给服务器的url
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.status == "999") {
					alert(data.msg);
					return false;
				}

				if (data.status == 0) {
					location.href = "/xcloud/xz/express/list";
				}
			}
		})
	} else  {
		// fail
		console.log("fail");
	};
});
