if ($.AMUI && $.AMUI.validator) {
	$.AMUI.validator.patterns.mobile = /^\s*1\d{10}\s*$/;
}



$("#login-btn").on('click', function(e) {
	console.log("login-btn click");
	var $form = $('#login-form');
	
	$form.validator();
	var validator = $form.data('amui.validator');
	if (!validator.isFormValid()) {
		return false;
	}
	
	$('#login-form').submit();
	
});
//
//function loginSubmit() {
//	var userName = $('#mobile').val();
//	var password = $('#password').val();
//
//	var params = {};
//	params.user_name = userName;
//	params.password = $('#password').val();
//	params.sms_token = $('#sms_token').val();
//	params.company_name = companyName;
//	params.short_name = $('#short_name').val();
//	// 提交数据，完成注册流程
//	$.ajax({
//		type : "POST",
//		url : appRootUrl + "/company/reg.json", // 发送给服务器的url
//		data : params,
//		dataType : "json",
//		async : false,
//		success : function(data) {
//
//			if (data.status == "999") {
//				alert(data.msg);
//				return false;
//			}
//
//			if (data.status == 0) {
//				location.href = "company-reg-ok.html";
//			}
//		},
//		error : function() {
//			return false;
//		}
//	});
//}