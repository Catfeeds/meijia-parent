
$("#mobile").on('blur', function(e) {
	
	var mobile = $("#mobile").val();
	
	if (!verifyMobile(mobile)) {
		return false;
	}
	
	var params = {};
	params.mobile = mobile;
	// 发送验证码
	$.ajax({
		type : "GET",
		url : "/xcloud/staff/get-by-mobile.json", // 发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {
			var vo = data.data;
			if (vo) {
				
				$("#name").val(vo.name);
				if (vo.id > 0) {
					alert("该用户已经为贵司员工,不需要重复添加.");
					$('#btn-staff-submit').attr("disabled","disabled"); // 禁用
					return false;
				}
			}
			$('#btn-staff-submit').removeAttr("disabled");
		}
	})
	
});



$("#btn-staff-submit").on('click', function(e) {
	var form = $('#staff-form');
	
	var formValidity = $('#staff-form').validator().data('amui.validator').validateForm().valid
	
	if (formValidity) {
		// done, submit form
		console.log("ok");
		form.submit();
	} else  {
		// fail
		console.log("fail");
	};
});