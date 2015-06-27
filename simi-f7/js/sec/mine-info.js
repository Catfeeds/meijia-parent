myApp.onPageInit('mine-info', function(page) {

	var secMobile = localStorage['sec_mobile'];
	var secId = localStorage['sec_id'];

	
	var secInfoSuccess = function(data, textStatus, jqXHR) {
	  	myApp.hideIndicator();
		var result = JSON.parse(data.response);
		if (result.status == "999") {
			myApp.alert(data.msg);
			return;
		}
		var sec = result.data;

		  $$("#name").text(sec.name);
		  $$("#nickName").text(sec.nick_name);
		  $$("#sex").text(sec.sex);
		  $$("#mobile").text(sec.mobile);
		  $$("#birthDay").text(sec.birth_day);
		  $$("#cityName").text(sec.city_name);
	};
	var postdata = {};
	postdata.sec_id = secId;
	postdata.mobile = secMobile;
    $$.ajax({
        type:"POST",
        url:siteAPIPath+"sec/get_secinfo.json",
        dataType:"json",
        cache:false,
        data:postdata,
        statusCode : {
			200 : secInfoSuccess,
			400 : ajaxError,
			500 : ajaxError
		}
    });
});
//获取用户消息列表


