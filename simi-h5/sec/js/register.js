myApp.onPageInit('register', function (page) {
	$$('#get_code').on('click',function(e) {
		
		var mobile = $$("#mobile").val();	
		 //var verifyCode = $$("#verify_code").val();
		 
        if(mobile == undefined || mobile == '') {
        	myApp.alert("请填写手机号。");
            return false;
        }
        var moblieStr = mobile.trim();
        if(moblieStr.length != 11) {
        	myApp.alert("请填写正确的手机号码");
        	return false;
        }

        var count = 60;
        var countdown = setInterval(CountDown, 1000);
        function CountDown(){
        	$$("#get_code").attr("disabled", true);
            $$("#get_code").css("background","#999");
            $$("#get_code").text(count + "秒");
            if (count == 0) {
                $$("#get_code").removeAttr("disabled");
                $$("#get_code").css("background","#f37b1d");
                $$("#get_code").text("获取验证码");
                clearInterval(countdown);
            }
            count--;
        }

        var postdata = {};
        postdata.mobile = moblieStr;
        postdata.sms_type = 0;

        $$.ajax({
        	
        	url:siteAPIPath+"user/get_register_sms_token.json",
        	contentType:"application/x-www-form-urlencoded; charset=utf-8",
        	type: "GET",
    	    dataType:"json",
    	    cache:true,
    	    data: postdata,

    	    statusCode: {
    	    	201: smsTokenSuccess,
    	    	400: ajaxError,
    	    	500: ajaxError
    	    }
    	});
        
    	var smsTokenSuccess = function(data, textStatus, jqXHR) {
    		
    	   	myApp.hideIndicator();    			
    	   	myApp.alert("验证码已发送到您的手机，请注意查收。");
    	};
        
        return false;
	});
	//下一步
	
	 $$('#submit_btn').on('click', function(e){
		 
		 var name = $$("#name").val();
		 var mobile = $$("#mobile").val();
		 var verifyCode = $$("#verify_code").val();
		 
		 if(mobile == '' || verifyCode == '') {
	          myApp.alert("请填写手机号或验证码。");
	          return false;
	        }
	        if(mobile.length != 11 ) {
	          myApp.alert("请填写正确的手机号码");
	          return false;
	        }
		 
	        var postdata = {};
	        postdata.mobile = mobile;
	        postdata.name = name;
	        postdata.sms_token = verifyCode;        
	        postdata.user_type = 1;

	        $$.ajax({
	            type : "POST",
	            url  : siteAPIPath+"user/register.json",
	            dataType: "json",
	            cache : true,
	            data : postdata,
	            
	            statusCode: {
	            	200: registerSuccess,
	    	    	400: ajaxError,
	    	    	500: ajaxError
	    	    }
	        });
	    });	
	 function registerSuccess(data, textStatus, jqXHR) {
		/* $$("#mine-addr-save").removeAttr('disabled');
		myApp.hideIndicator();*/
		var results = JSON.parse(data.response);
		var user = results.data;
		mobile = user.mobile;
		name = user.name;
		
		if (results.status == "999") {
			myApp.alert(results.msg);
			return;
		}
		if (results.status == "0") {
		//	myApp.alert("信息修改成功");
			//?mobile=15712917308&name=yy
			mainView.router.loadPage("register2.html?mobile="+mobile +"&name="+name);
		}
	} 
	$$(".yanzheng1").click(function(){
        $$(".login-tishi1").css("display","block");
    })
});
    
