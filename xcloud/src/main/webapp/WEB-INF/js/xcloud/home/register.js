//公司行业
var tradeJson = getDictTrade();
if (tradeJson != undefined) {
	
	var output = "";
	$.each(tradeJson, function(i,pitem){
		
		if (pitem.parent_id > 0) return;
//		console.log(pitem.trade_id + "----" + pitem.name + "----" + pitem.parent_id);
		var optGroup = "";
		optGroup = "<optgroup label=\""+pitem.name+"\">";
		var tradeId = pitem.trade_id;
		$.each(tradeJson, function(j,item) {
			if (item.parent_id == tradeId && item.parent_id > 0) {
				optGroup+= "<option value=\""+item.trade_id+"\">"+ item.name +"</option>";
			}
		});
		optGroup += "</optgroup>";
		output += optGroup;
		
	});	
	
	$("#company_trade").html(output);
//	console.log(output);
}

//表单验证
$('#register-form').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: true, // do not focus the last invalid input
	invalidHandler : function(event, validator) { // display error alert on
		$('.alert-error', $('#register-form')).show();
	},
	
	rules : {},
	
	messages : {
		mobile : {
			required : "请输入手机号码。"
		},
		img_token : {
			required : "请输入图形验证码。",
		},
		
		sms_token : {
			required : "请输入验证码。",
		},
		
		company_name : {
			required : "请输入公司名称",
		},
		
		longitude : {
			required : "请输入公司地理位置",
		},
		
		addr : {
			required : "请输入公司详细地址",
		},
		
		user_name : {
			required : "请输入管理员账户",
		},
		
		password : {
			required : "请输入管理员密码",
			minlength: "密码长度在6-12之间，只能包含字符、数字和下划线。",
		},
		
		confirm_password : {
			required : "请再次输入管理员密码",
			minlength: "密码长度在6-12之间，只能包含字符、数字和下划线。",
			equalTo  : "两次输入密码不一致。"
		},
	},

	highlight : function(element) { // hightlight error inputs
		$(element).closest('.form-group').addClass('has-error'); // set error
	},

	success : function(label) {
		label.closest('.form-group').removeClass('has-error');
		label.remove();
	},

	errorPlacement : function(error, element) {
		error.insertAfter(element.parents(".col-lg-7"));
	}

});

var formvalStep1Rules = {
	mobile : {
		required : true,
		isMobile : true
	},
	
	img_token: {
		required : true,
		isRightfulString : true,
	},
	
	sms_token : {
		required : true,
		isRightfulString : true,
	}
}

var formvalStep2Rules = {
	
	company_name : {
		required : true,
	},
	
	longitude : {
		required : true,
	},
		
	
	addr : {
		required : true,
	},
	
	user_name : {
		required : true,
	},
	
	password : {
		required : true,
		minlength: 6,		
	},
	
	confirm_password : {
		required : true,
		minlength: 6,
		equalTo: "#password"
	}
}

//获取验证码功能

$("#btn_sms_token").click(function() {
	console.log("btn_sms_token click");
	var mobile = $('#mobile').val();
    var imgToken = $('#img_token').val();
    var flag = true;
    var errors = {};
    
    var settings = $("#register-form").validate().settings;
    console.log(settings);
    $.extend(settings, { rules: formvalStep1Rules});
    var formVal = $("#register-form").validate();
    
    console.log(settings);
    if(mobile == "" ){
		errors.mobile = "请输入手机号码。";
		flag = false;
	}	
    
    if(imgToken == "" ){
		errors.img_token = "请输入图形验证码。";
		flag = false;
	}
    
    if(!flag) {
    	formVal.showErrors(errors);
		return false;
    }

	$.ajax({
		type : "GET",
		url :  "/xcloud/check_captcha.json", // 发送给服务器的url
		data : "token=" + imgToken,
		dataType : "json",
		async : false,
		success : function(data) {
			console.log(data);
			if (data.status != "0") {
				errors.img_token = "验证码不正确";
				formVal.showErrors(errors);
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
					$("#btn_sms_token").html('<span id=\"cd\">60</span>').attr('disabled','disabled');
			    	$('#cd').countDown(function(){
			    		$("#btn_sms_token").html('获取验证码').removeAttr('disabled');
	  				})
				}
			})
			
		}
	});    
});


function validateStep2() {
	console.log("validateStep2");

    var settings = $("#register-form").validate().settings;
    console.log(settings);
    $.extend(settings, { rules: formvalStep1Rules});
	
	if ( !$('#register-form').valid()) {
		return false;
	}
	
	var formVal = $("#register-form").validate();
	
	var errors = {};
	var params = {};
	params.mobile = $('#mobile').val();
	params.sms_token = $('#sms_token').val();;
	params.sms_type = 3;
	//验证手机验证码是否正确
	var nextFlag = false;
	$.ajax({
		type : "GET",
		url: appRootUrl + "/user/val_sms_token.json", //发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {
			console.log(data);
			if (data.status == 999) {
				console.log("data.status = " + data.status);
				errors.sms_token = "验证码不正确";
				
				nextFlag = false;
			} else {
				$("#user_name").val($('#mobile').val());
				nextFlag = true;
			}
			
		}
	})
	
	if (nextFlag == false) {
		formVal.showErrors(errors);
		return false;
	}
	
	$("#user_name").val($('#mobile').val());
	//如果地址位置为空，则清空隐藏域.
	if ($("#addr_name").val() == "") {
		$("#longitude").val("");
		$("#latitude").val("");
	}
	
	return true;
}

$("#company_name").blur(function() {
	var companyName = $("#company_name").val();
	if (companyName.trim() == "") return ;
	
	var params = {};
	params.company_name = companyName;
	params.company_id = 0;
	$.ajax({
		type : "GET",
		url: appRootUrl + "/company/check-duplicate.json", //发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {
			console.log(data);
			
		}
	})
	
});

function validateStep3() {
	console.log("validateStep3");
	 var settings = $("#register-form").validate().settings;
    console.log(settings);
    $.extend(settings, { rules: formvalStep2Rules});
    console.log("lng = " + $("#longitude").val());
	if ( !$('#register-form').valid()) {
		return false;
	}
	
	$("#company_name_label").html($("#company_name").val());
	$("#company_size_label").html($("#company_size option:selected").text());
	$("#company_trade_label").html($("#company_trade option:selected").text());
	$("#addr_label").html($("#addr_name").val() + " " + $("#addr").val());
	$("#user_name_label").html($("#user_name").val());
	return true;
}
      