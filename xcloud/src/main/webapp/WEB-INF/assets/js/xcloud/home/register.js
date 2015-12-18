if ($.AMUI && $.AMUI.validator) {
	$.AMUI.validator.patterns.mobile = /^\s*1\d{10}\s*$/;
}

$('#company-reg-form').validator({
	validate : function(validity) {
		// Ajax 验证
		if ($(validity.field).is('.js-ajax-validate')) {
			// 异步操作必须返回 Deferred 对象
			var mobile = $('#userName').val();
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
				if (data.status == "999")
					validity.valid = false;
				if (data.status == "0")
					validity.valid = true;
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

$("#reg-submit").on('click', function(e) {
	console.log("reg-submit click");
	var $form = $('#company-reg-form');
	var validator = $form.data('amui.validator');
	var formValidity = validator.isFormValid()
	$.when(formValidity).then(function() {
		// done, submit form
		companyRegSubmit()
	}, function() {
		// fail
	});
});

function companyRegSubmit() {
	var userName = $('#mobile').val();
	var companyName = $('#company_name').val();

	var params = {};
	params.user_name = userName;
	params.password = $('#password').val();
	params.sms_token = $('#sms_token').val();
	params.company_name = companyName;
	params.short_name = $('#short_name').val();
	// 提交数据，完成注册流程
	$.ajax({
		type : "POST",
		url : appRootUrl + "/company/reg.json", // 发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {

			if (data.status == "999") {
				alert(data.msg);
				return false;
			}

			if (data.status == 0) {
				location.href = "company-reg-ok.html";
			}
		},
		error : function() {
			return false;
		}
	});
}

// 获取验证码功能
$("#btn_sms_token").click(
		function() {
			console.log("btn-get-sms click");
			var mobile = $('#mobile').val();
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
					$("#btn_sms_token").html('<span id=\"cd\">60</span>').attr(
							'disabled', 'disabled');
					$('#cd').countDown(function() {
						$("#btn_sms_token").html('获取验证码');
						$("#btn_sms_token").removeAttr('disabled');
					})
				}
			})

		});
