var userId;
myApp.onPageBeforeInit('user-info-edit', function (page) {
		
	 userId = page.query.user_id;

	if (userId == undefined || userId == '' || userId == 0) {
		return;
	}
	getUserInfo(userId);
	
	$$("#user_info_submit").on("click", function() {
		var formData = myApp.formToJSON('#user-form');
		$$.ajax({
			type : "POST",
			url : siteAPIPath + "user/post_userinfo.json",
			dataType : "json",
			cache : false,
			data : formData,
			statusCode : {
				200 : saveUserSuccess,
				400 : ajaxError,
				500 : ajaxError
			}
		});
	})
	
});

var onUserInfoSuccess =function(data, textStatus, jqXHR) {
 	myApp.hideIndicator();
   	var result = JSON.parse(data.response);
	if (result.status == "999") {
		myApp.alert(result.msg);
		return;
	}
	formSetInfoSuccess(result);
}
var userInfoSuccess =function(data, textStatus, jqXHR) {
	myApp.hideIndicator();
   	var result = JSON.parse(data.response);
	if (result.status == "999") {
		myApp.alert(result.msg);
		return;
	}
	var user = result.data;
	$$("#name").val(user.name);
	$$("#mobile").val(user.mobile);
	$$("#user_id").val(user.id);
	$$("#head_img").attr("src",user.head_img);
	console.log(user.sex);
	if(user.sex !=' ' && user.sex !=null){
		$$("#sex option[value=" + user.sex + "]").attr("selected", true);
	}
}
// 获取用户信息
function getUserInfo(userId) {
	var postdata = {};
    postdata.user_id = userId;   
	$$.ajax({
		type : "GET",
		url : siteAPIPath + "user/get_userinfo.json",
		dataType : "json",
		cache : true,
		data :postdata,
		statusCode: {
         	200: userInfoSuccess,
 	    	400: ajaxError,
 	    	500: ajaxError
 	    },
	});
}
$$("#mine_info_submit").on("click", function() {
	var formData = myApp.formToJSON('#mine-form');
	$$.ajax({
		type : "POST",
		url : siteAPIPath + "sec/post_secinfo.json",
		dataType : "json",
		cache : false,
		data : formData,
		statusCode : {
			200 : saveSecSuccess,
			400 : ajaxError,
			500 : ajaxError
		}
	});
});
//保存用户信息
function saveUserSuccess(data, textStatus, jqXHR) {
	myApp.hideIndicator();
	console.log("submit success");
	var result = JSON.parse(data.response);

	if (result.status == "999") {
		myApp.alert(result.msg);
		return;
	}
	if (result.status == "0") {
		myApp.alert("客户信息修改完成");
		mainView.router.loadPage("user/user-info.html?user_id="+userId);
	}
} 