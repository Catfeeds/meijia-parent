myApp.onPageInit('mine', function(page) {

	var secMobile = localStorage['sec_mobile'];
	var secId = localStorage['sec_id'];

	var secInfoSuccess = function(data, textStatus, jqXHR) {
	  	myApp.hideIndicator();
		console.log(JSON.parse(data.response));
		var result = JSON.parse(data.response);
		if (result.status == "999") {
			myApp.alert(data.msg);
			return;
		}
		var sec = result.data;

		if (result.status == "0") {
			if (sec.nick_name != ' ' && sec.nick_name != null) {
				$$("#user_phone").text(sec.nick_name);
			} else {
				$$("#user_phone").text(sec.mobile);
			}
			var yuan = sec.rest_money + '元';
			$$("#user_money").text("0元");
		}
	};
	// 设置ajax请求的参数
	var postdata = {};
	postdata.sec_id = secId;
	postdata.mobile = secMobile;
	$$.ajax({
		url : siteAPIPath + "sec/get_secinfo.json",
		// headers: {"X-Parse-Application-Id":applicationId,"X-Parse-REST-API-Key":restApiKey},
		//contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "POST",
		dataType : "json",
		cache : true,
		data : postdata,
		statusCode : {
			200 : secInfoSuccess,
			400 : ajaxError,
			500 : ajaxError
		}
	});

});