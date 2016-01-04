if ($.AMUI && $.AMUI.validator) {
	$.AMUI.validator.patterns.join_mobile = /^\s*1\d{10}\s*$/;
}

var reqInvitationCode = getUrlParam("invitation_code");
var mobile = getUrlParam("mobile");
console.log("reqInvitationCode = " + reqInvitationCode);
if (reqInvitationCode != undefined) {
	$('#invitation_code').val(reqInvitationCode);
}

if (mobile != undefined) {
	$('#join_mobile').val(mobile);
}

$('#company-join-form').validator({
	validate : function(validity) {
		// Ajax 验证
		if ($(validity.field).is('.js-ajax-validate')) {
			// 异步操作必须返回 Deferred 对象
			var mobile = $('#join_mobile').val();
			if (mobile == undefined || mobile == "")  {
				validity.valid = false;
				return validity;
			}
			var params = {};
			params.mobile = $('#join_mobile').val();
			params.sms_token = $('#join_sms_token').val();
			params.sms_type = 3;
			return $.ajax({
				type : 'GET',
				url : appRootUrl + '/user/val_sms_token.json',
				cache: false,
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

	}
});

$("#join-submit").on('click', function(e) {
	console.log("join-submit click");	
	var $form = $('#company-join-form');
	var validator = $form.data('amui.validator');
	var formValidity = validator.isFormValid()
	$.when(formValidity).then(function() {
		// done, submit form
		companyJoinSubmit()
	}, function() {
		// fail
	});
	

	
});

function companyJoinSubmit() {
	var userName =  $('#join_mobile').val();
	
	var params = {};
	params.user_name = userName;
	params.sms_token = $('#join_sms_token').val();
	params.invitation_code = $('#invitation_code').val();
	//提交数据，完成注册流程
	$.ajax({
		type : "POST",
		url: appRootUrl + "/company/join.json", //发送给服务器的url
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
		error:function(){
			return false;
		}
	});	
}

//获取验证码功能
$("#join_btn_sms_token").click(function() {
	console.log("join_btn_sms_token click");
	var mobile = $('#join_mobile').val();
	if (mobile == undefined || mobile == "")  {
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
	//发送验证码
	$.ajax({
		type : "GET",
		url: appRootUrl + "/user/get_sms_token.json", //发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {
			$("#join_btn_sms_token").html('<span id=\"cd1\">60</span>').attr('disabled','disabled');
	    	$('#cd1').countDown(function(){
	    		$("#join_btn_sms_token").html('获取验证码');
	    		$("#join_btn_sms_token").removeAttr('disabled');
			})
		}
	})
	
	
});

