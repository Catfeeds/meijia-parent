$(function() {
	var secId = localStorage['sec_id'];
	var mobile = localStorage['sec_mobile'];

	var userId = $.urlParam('user_id');
	console.log(userId);

	getUserInfo(userId);
}());

//获取用户信息接口
//对应文档
//http://182.92.160.194:8080/trac/wiki/%E8%B4%A6%E5%8F%B7%E4%BF%A1%E6%81%AF%E6%8E%A5%E5%8F%A3
function getUserInfo(userId) {
	$.ajax({
		type : "GET",
		url : siteAPIPath + "user/get_userinfo.json",
		dataType : "json",
		cache : false,
		data : "user_id=" + userId,
		success : onUserInfoSuccess,
		error : onError
	});
}

function onUserInfoSuccess(data, status) {
	
	if (data.status == "999") {
		alert(data.msg);
		return;
	}

	//
	
}

function formSetInfoSuccess(data) {
	//todo  form设定值.
}

