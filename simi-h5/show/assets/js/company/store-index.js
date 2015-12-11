if ($.AMUI && $.AMUI.validator) {
	$.AMUI.validator.patterns.mobile = /^\s*1\d{10}\s*$/;
}
/*$.ajax({
	type : "GET",
	url : appRootUrl + "dict/get_service_type_list.json?partner_id=0",
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
		if (data.status == "999") return false;
		var serviceTypeId = data.data;
		console.log(serviceTypeId);
		
		$("#serviceTypeId").val(serviceTypeId);
	}
});*/

/*function tagClick(tagId) {
	// 如果未选中，则换class为选中
	console.log("tagClick");
	var tagIds = $("#tag_ids").val();
	var obj = $('#'+tagId);
	console.log(obj);
	// am-btn-warning = 选中
	if (obj.is(".am-btn-default")) {
		obj.addClass("am-btn-warning");
		obj.removeClass("am-btn-default");

		if (tagIds.indexOf(tagId + ",") < 0) {
			tagIds += tagId + ",";
		}
	// am-btn-default = 未选中
	} else {
		obj.removeClass("am-btn-warning");
		obj.addClass("am-btn-default");
		if (tagIds.indexOf(tagId + ",") >= 0) {
			tagIds = tagIds.replace(tagId + ",", "");
		}
	}

	$("#tag_ids").val(tagIds);

	console.log($("#tag_ids").val());
}*/

/*var mobile=document.getElementById("mobile");
mobile.onblur=function(){
	//document.getElementById("mobile").value=mobile.value;
};*/
/*$("#mobile").on('blur', function(e) {
	
	//alert("失去焦点失去焦点！！");
	var params = {};
	params.mobile = $('#mobile').val();
	$.ajax({
		type : "POST",
		url : appRootUrl + "/sec/register.json", // 发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {

			if (data.status == "999") {
				alert(data.msg);
				console.log(data);
				console.log(data.data);
				var user = data.data;
					$("#mobile").val(user.mobile);
					$("#name").val(user.name);
					$("#real_name").val(user.real_name);
					$("#id_card").val(user.id_card);
					$("#sex").val(user.sex);
					$("#major").val(user.major);
					$("#degreeId").val(user.degree_id);
				return false;
			}
		},
		error : function() {
			return false;
		}
	});
	
});*/


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

	var params = {};
	params.mobile = $('#mobile').val();
	params.register_type = $('input:radio[name=registerType]:checked').val();
	params.company_name = $('#companyName').val();
	params.service_type_id = $('#serviceTypeId').val();
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
