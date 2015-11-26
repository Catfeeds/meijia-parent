
//表单验证
var formVal = $('#register-form').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: true, // do not focus the last invalid input
	invalidHandler : function(event, validator) { // display error alert on
		$('.alert-error', $('#register-form')).show();
	},
	
	rules : {
		userName : {
			required : true,
			isMobile : true
		},
		
		imgToken: {
			required : true,
			isRightfulString : true,
		},
		
		sendToken : {
			required : true,
			isRightfulString : true,
		},
		
		password : {
			required : true,
			minlength: 6,		
		},
		
		confirmPassword : {
			required : true,
			minlength: 6,
			equalTo: "#password"
		},
		
		companyName : {
			required : true,
		},
		
		shortName : {
			required : true,
		}
		
		
	},
	
	messages : {
		userName : {
			required : "请输入手机号"
		},
		imgToken : {
			required : "请输入图片验证",
		},
		
		sendToken : {
			required : "请输入验证码",
		},

		password : {
			required : "请输入密码",
			minlength: "长度在6-12之间",
		},
		
		confirmPassword : {
			required : "请再次输入密码",
			minlength: "长度在6-12之间",
			equalTo  : "两次输入密码不一致"
		},
		
		companyName : {
			required : "请输入公司名称",
		},
		
		shortName : {
			required : "请输入公司简称",
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
		error.insertAfter(element.parents(".col-md-12"));
	}

});

//获取验证码功能
$("#btn_sms_token").click(function() {
	console.log("btn_sms_token click");
	var userName = $('#userName').val();
    var imgToken = $('#imgToken').val();
    var flag = true;
    var errors = {};

    if(userName == "" ){
		errors.userName = "请输入手机号";
		formVal.showErrors(errors);
		return false;
	}	
    

	if (!verifyMobile(userName)) {
		errors.userName = "手机格式不对";
		formVal.showErrors(errors);
		return false;
	}
	
    if(imgToken == "" ){
		errors.imgToken = "请输入图片验证";
		formVal.showErrors(errors);
		return false;
	}
    
   var isExist = checkCompanyUserNameExist(userName, 0);
    
    if (isExist) {
    	errors.userName = "该用户名已绑定过公司";
    	formVal.showErrors(errors);
    	return false;
    }    
    
    var isSuccess = checkCaptcha(imgToken);
    if (!isSuccess) {
    	errors.imgToken = "验证码不正确";
    	formVal.showErrors(errors);
    	return false;
    }    
    
	var params = {};
	params.mobile = userName;
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
    
 

});



