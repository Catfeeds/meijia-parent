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
     //   postdata.sms_token = verifyCode;  
        postdata.sms_type = 0;

        $$.ajax({
        	
        	//console.log(siteAPIPath);
        	url:siteAPIPath+"user/get_sms_token.json",
//    		headers: {"X-Parse-Application-Id":applicationId,"X-Parse-REST-API-Key":restApiKey},
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
    		
    		// We have received response and can hide activity indicator
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
	        postdata.sms_token = verifyCode;        
	      //  postdata.login_from = 1;
	        postdata.user_type = 2;

	        $$.ajax({
	            type : "POST",
	            // type : "GET",
	            url  : siteAPIPath+"user/register.json",
	            dataType: "json",
//	            contentType:"application/x-www-form-urlencoded; charset=utf-8",
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
		
		if (results.status == "999") {
			myApp.alert(results.msg);
			return;
		}
		if (results.status == "0") {
		//	myApp.alert("信息修改成功");
			mainView.router.loadPage("register2.html");
		}
	} 

	 //登录
   /* $$('#login_btn').on('click', function(e){
        //var formData = $('#loginform').serialize();
    	var mobile = $$("#user_mobile").val();
    	var verifyCode = $$("#verify_code").val();
        
        if(mobile == '' || verifyCode == '') {
          myApp.alert("请填写手机号或验证码。");
          return false;
        }
        if(mobile.length != 11 ) {
          myApp.alert("请填写正确的手机号码");
          return false;
        }

        var loginSuccess = function(data, textStatus, jqXHR) {
    		// We have received response and can hide activity indicator
    	   	myApp.hideIndicator();
    	   	
    	   	var result = JSON.parse(data.response);
    	   	if (result.status == "999") {
    	   		myApp.alert(result.msg);
    	   		return;
    	   	}
    	   	
    	   	if (result.status == "0") {
    	   	  //登录成功后记录用户有关信息
    	   	  localStorage['user_mobile'] = result.data.mobile;
    	   	  localStorage['user_id']= result.data.id;
    	   	  localStorage['im_username'] = result.data.im_user_name;
    	   	  localStorage['im_password'] = result.data.im_password;
    	   	 
    	   	  
    	   	  var logInAmId = result.data.am_id;
    	   	  
	    	  var logInAmMobile = result.data.am_mobile;
    	   	  
	    	  // 这里 不管是 null 还是 ‘null’ 都能进入循环！不影响后边判断
	    	  if(logInAmId != "" || logInAmId != null){
	    		  localStorage['am_id'] = logInAmId;
	    	  }
	    	  if(logInAmMobile !="" || logInAmMobile != null){
	    		  localStorage['am_mobile'] = logInAmMobile;
	    	  }
	    	  
	    	  //如果有默认地址则设置为默认地址
	    	  var userAddr = result.data.default_user_addr;
	    	  console.log(userAddr);
	    	  if (userAddr != undefined) {
	    		  if (userAddr.is_default == 1) {
	    			  
		    		  var addrId = userAddr.id;
		    		  var addrName = userAddr.name + " " + userAddr.addr;
	    			  localStorage['default_addr_id'] = addrId;
	    			  localStorage['default_addr_name'] = addrName;	
	    		  }
	    	  }
    	   	  
    	   	}
    	   	var flag =result.data.has_user_addr;
    	   	if(flag ==true){
    	   	//返回用户浏览的上一页
	   			var target = mainView.history[mainView.history.length-2];
	   			
	   			mainView.router.loadPage(target);
    	   //mainView.router.loadPage("index.html");
    	   	}else{
    	   		mainView.router.loadPage("user/mine-add-addr.html");
    	   	}
    	   
    	};                
        
        
        var postdata = {};
        postdata.mobile = mobile;
        postdata.sms_token = verifyCode;        
        postdata.login_from = 1;
        postdata.user_type = 2;

        $$.ajax({
            type : "POST",
            // type : "GET",
            url  : siteAPIPath+"user/login.json",
            dataType: "json",
//            contentType:"application/x-www-form-urlencoded; charset=utf-8",
            cache : true,
            data : postdata,
            
            statusCode: {
            	200: loginSuccess,
    	    	201: loginSuccess,
    	    	400: ajaxError,
    	    	500: ajaxError
    	    }
        });


        
        return false;
    });	
	*/
	$$(".yanzheng1").click(function(){
        $$(".login-tishi1").css("display","block");
    })
});
    
