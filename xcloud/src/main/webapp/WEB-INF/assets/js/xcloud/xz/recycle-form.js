
//提交验证
$("#btn-recycle-submit").on('click', function(e) {

	/*var fv = formValidation();
	if (fv == false) return false;
	*/
    var form = $('#recycle-form');
	
	var formValidity = $('#recycle-form').validator().data('amui.validator').validateForm().valid
	
	if (formValidity) {
		// done, submit form
		//console.log("ok");
		//form.submit();
		// 组建提交卡片接口数据
		var params = {}
		params.user_id = $("#userId").val();
		params.addr_id = $("#addrId").val();
		params.link_tel = $("#linkTel").val();
		params.link_man = $("#linkMan").val();
		params.remarks = $("#remarks").val();
		params.recycle_type = $("#recycleType").val();
		$.ajax({
			type : "POST",
			url : appRootUrl + "/order/post_add_recycle.json", // 发送给服务器的url
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.status == "999") {
					alert(data.msg);
					return false;
				}

				if (data.status == 0) {
					location.href = "/xcloud/xz/recycle/list";
				}
			}
		})
	} else  {
		// fail
		console.log("fail");
	};
});
