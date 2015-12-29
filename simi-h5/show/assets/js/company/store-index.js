if ($.AMUI && $.AMUI.validator) {
	$.AMUI.validator.patterns.mobile = /^\s*1\d{10}\s*$/;
}

$.ajax({
	type : "GET",
	url : appRootUrl + "dict/get_service_type_list.json?parent_id=0",
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
	
		if (data.status == "999") return false;
		var partnerServiceType = data.data;
		console.log(partnerServiceType);
		
		var partnerServiceTypeHtml = "";
		for(var i=0 ; i < partnerServiceType.length; i++){
			var partnerServiceTypeId = partnerServiceType[i].id;
			console.log(partnerServiceTypeId+"$$$$$$$$$$$$$$$$$$$$$$$$444");
			var partnerServiceTypeName = partnerServiceType[i].name;
			//console.log(partnerServiceTypeName);
			partnerServiceTypeHtml +="<option value =\""+partnerServiceTypeId+"\" >"+partnerServiceTypeName+"</option>"
		}	
		$("#partnerServiceTypeId").val(partnerServiceTypeId);
		$("#partnerServiceTypeName").append(partnerServiceTypeHtml);

	}
});
//判断用户是否存在，存在则带出信息
var userId = getUrlParam("user_id");
$.ajax({
	type : "GET",
	url : appRootUrl + "partner/get_exist_by_user_id.json?user_id="+userId,
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
	
		if (data.status == "999") return false;
		console.log("+++++++++++++++++++");
		
		users = data.data;
		console.log(users)
		$("#mobile").val(users.mobile);
		$("#user_id").val(users.id);
		
	//	$("#partnerServiceTypeName").append(partnerServiceTypeHtml);

	}
});
$('#store-form').validator({
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
			params.mobile = $('#mobile').val();
			params.sms_token = $('#verify_code').val();
			params.sms_type = 3;
			return $.ajax({
				type : 'GET',
				url : appRootUrl + '/sec/check_sms_token.json',
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
		
		//身份证ID校验
		/*if ($(validity.field).is('.js-idcard-validate')) {
			var  idCard = $('#id_card').val();  
			validity.valid = isIdCardNo(idCard);

			return validity;    
		}*/  

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
$("#information_btn").on('click', function(e) {
	console.log("reg-submit click");
	var $form = $('#store-form');
	var validator = $form.data('amui.validator');
	var formValidity = validator.isFormValid()
	$.when(formValidity).then(function() {
		// done, submit form
		companyRegSubmit()
	}, function() {
		// fail
	});
});
//提交
function companyRegSubmit() {
	var userId = getUrlParam("user_id");
	var params = {};
	params.mobile = $('#mobile').val();
	params.register_type = $('input:radio[name=registerType]:checked').val();
	params.company_name = $('#companyName').val();
	params.service_type_id = $('#partnerServiceTypeId').val();
	params.user_id = $('#userId').val();
	// 提交数据，完成注册流程
	$.ajax({
		type : "POST",
		url : appRootUrl + "/partner/post_partner_register.json", // 发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {

			if (data.status == "999") {
				alert(data.msg);
				return false;
			}

			if (data.status == 0) {
				location.href = "store-reg-ok.html";
			}
		},
		error : function() {
			return false;
		}
	});
}

// 获取验证码功能
$("#get_code").click(
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
					$("#get_code").html('<span id=\"cd\">60</span>').attr(
							'disabled', 'disabled');
					$('#cd').countDown(function() {
						$("#get_code").html('获取验证码');
						$("#get_code").removeAttr('disabled');
					})
				}
			})

		});
