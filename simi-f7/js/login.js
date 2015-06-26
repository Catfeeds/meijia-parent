myApp.onPageInit('login', function (page) {
	$$('#get_code').on('click',function(e) {
		
		var mobile = $$("#mobile").val();
		console.log(mobile);
        if(mobile == undefined || mobile == '') {
        	myApp.alert("请填写手机号。");
            return false;
        }
        
        if(mobile.length != 11) {
        	myApp.alert("请填写正确的手机号码");
        	return false;
        }

        var count = 60;
        var countdown = setInterval(CountDown, 1000);
        function CountDown(){
        	$$("#get_code").attr("disabled", true);
            $$("#get_code").css("background","#999");
            $$("#get_code").text(count + "秒后重新获取");
            if (count == 0) {
                $$("#get_code").removeAttr("disabled");
                $$("#get_code").css("background","#f37b1d");
                $$("#get_code").text("获取验证码");
                clearInterval(countdown);
            }
            count--;
        }

        var postdata = {};
        postdata.mobile = mobile;
        postdata.sms_type = 1;

        
        $$.ajax({
        	 url:siteAPIPath+"user/get_sms_token.json",
//    		headers: {"X-Parse-Application-Id":applicationId,"X-Parse-REST-API-Key":restApiKey},
    	    type: "GET",
    	    dataType:"json",
    	    cache:false,
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

    	   	console.log('Response body: '+data);				
    			
    	   	myApp.alert("验证码已发送到您的手机，请注意查收。");
    	};
        
        return false;
	});
});
    
