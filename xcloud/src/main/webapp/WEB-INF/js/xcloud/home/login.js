

//表单验证
var formVal = $('#login-form').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: false, // do not focus the last invalid input
	focusCleanup : true,
	invalidHandler : function(event, validator) { // display error alert on
		$('.alert-error', $('#login-form')).show();
	},
	
	rules : {
		mobile : {
			required : true,
			isMobile : true
		},
		
		sms_token : {
			required : true,
			isRightfulString : true,
		}
		
	},
	
	messages : {
		mobile : {
			required : "请输入手机号码。"
		},

		
		sms_token : {
			required : "请输入验证码。",
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
		//error.insertAfter(element.parents(".col-lg-12"));
		element.placeholder = error;
	}

});

//获取验证码功能

$("#btn_sms_token").click(function() {

	var mobile = $('#mobile').val();
    var flag = true;
    var errors = {};
    
    console.log(settings);
    if(mobile == "" ){
		errors.mobile = "请输入手机号码。";
		flag = false;
	}	
        
    if(!flag) {
    	formVal.showErrors(errors);
		return false;
    }
		
	var params = {};
	params.mobile = mobile;
	params.sms_type = 1;
	//发送验证码
	$.ajax({
		type : "GET",
		url: appRootUrl + "/user/get_sms_token.json", //发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {
			$("#btn_sms_token").html('<span id=\"cd\">60秒</span>').attr('disabled','disabled');
	    	$('#cd').countDown(function(){
	    		$("#btn_sms_token").html('获取验证码').removeAttr('disabled');
			})
		}
	})   
});