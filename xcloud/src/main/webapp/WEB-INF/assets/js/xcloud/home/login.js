$('#drag').drag();

function loginSwitch(loginType) {
	if (loginType == "tab-login-pass") {
		$("#tab-login-pass").click();
	}
	
	if (loginType == "tab-login-sms") {
		$("#tab-login-sms").click();
	}
}

// 密码登陆
$("#login-btn").on('click', function(e) {
	
	var form = $('#login-form');
	
	var formValidity = $('#login-form').validator().data('amui.validator').validateForm().valid

	if (formValidity) {
		// done, submit form
		console.log("ok");
		
		var dragText = $(".drag_text").html();
		if (dragText != '验证通过') {
			console.log("drag fail");
			return false;
		}
		
		form.submit();
	} else {
		// fail
		console.log("fail");
		
	}
	;
});

// 验证码登陆
$("#btn_sms_token").click(function() {
	console.log("btn-get-sms click");
	var mobile = $('#mobile').val();
	console.log(mobile);
	if (mobile == undefined || mobile == "") {
		alert("请输入手机号!");
		return false;
	}
	
	if (!verifyMobile(mobile)) {
		alert("请输入正确的手机号!");
		return false;
	}
	
	var params = {};
	params.mobile = mobile;
	params.sms_type = 3;
	// 发送验证码
	$.ajax({
		type : "GET",
		url : appRootUrl + "/user/get_sms_token.json", // 发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {
			$("#btn_sms_token").html('<span id=\"cd\">60</span>').attr('disabled', 'disabled');
			$('#cd').countDown(function() {
				$("#btn_sms_token").html('获取验证码');
				$("#btn_sms_token").removeAttr('disabled');
			})
		}
	})

});

$('#login-form-sms').validator({
	validate : function(validity) {
		// Ajax 验证
		if ($(validity.field).is('.js-ajax-validate')) {
			// 异步操作必须返回 Deferred 对象
			var mobile = $('#mobile').val();
			if (mobile == undefined || mobile == "") {
				validity.valid = false;
				return validity;
			}
			var params = {};
			params.mobile = mobile;
			params.sms_token = $('#sms_token').val();
			params.sms_type = 3;
			return $.ajax({
				type : 'GET',
				url : appRootUrl + '/user/val_sms_token.json',
				cache : false,
				async : false,
				data : params,
				dataType : 'json'
			}).then(function(data) {
				if (data.status == "999") validity.valid = false;
				if (data.status == "0") validity.valid = true;
				return validity;
			}, function() {
				return validity;
			});
		}
		
	},
	
	// 域验证通过时添加的操作，通过该接口可定义各种验证提示
	markValid : function(validity) {
		// this is Validator instance
		var options = this.options;
		var $field = $(validity.field);
		var $parent = $field.closest('.am-form-group');
		$field.addClass(options.validClass).removeClass(options.inValidClass);
		
		$parent.addClass('am-form-success').removeClass('am-form-error');
		
		options.onValid.call(this, validity);
	},

});

$("#login-btn-sms").on('click', function(e) {
	
	var form = $('#login-form-sms');
	
	var formValidity = $('#login-form-sms').validator().data('amui.validator').validateForm().valid

	if (formValidity) {
		// done, submit form
		console.log("ok");
		
		form.submit();
	} else {
		// fail
		console.log("fail-sms");
		
	}

});