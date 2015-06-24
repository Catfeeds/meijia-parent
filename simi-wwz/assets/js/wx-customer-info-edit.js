$(function() {
	var userId = $.urlParam('user_id');
	getUserInfo(userId);
}());
// 获取用户信息
function getUserInfo(userId) {
	$.ajax({
		type : "GET",
		url : siteAPIPath + "user/get_userinfo.json?user_id="+userId,
		dataType : "json",
		cache : false,
		success : userInfoSuccess,
	});
}
function userInfoSuccess(data, status) {
	if (data.status != "0") {
		if (data.status == "999")
			alert(data.msg);
		else
			 alert(data);
			 alert("目前无法获取用户信息，请稍后再试。");
		return;
	}
	var user = data.data;
	$("#name").val(user.name);
	$("#mobile").val(user.mobile);
	$("#head_img").attr("src",user.head_img);
	console.log(user.sex);
	if(user.sex !=' ' && user.sex !=null){
		$("#sex option[value=" + user.sex + "]").attr("selected", true);
	}
}
$("#customer-info-submit").bind("click", function() {
	var $form = $('#customer_form');
	var isValid = $('.am-form').data('amui.validator').isFormValid();
	if (!isValid) {
		alert("is false");
		return ;
	}
	var userId = $.urlParam('user_id');
	var name = $("#name").val();
	var mobile = $("#mobile").val();
	var sex = $("#sex").val();
	$.ajax({
		type : "POST",
		url : siteAPIPath + "user/post_userinfo.json",
		dataType : "json",
		cache : false,
		data : {
			"user_id" : userId,
			"name" : name,
			"mobile":mobile, 
			"sex" : sex,
			"head_img" : "",
		},
		success : saveSecSuccess,
		error : onError
	});
})
//保存用户信息
function saveSecSuccess(data, status) {
	if (data.status != "0") {
		if (data.status == "999")
			alert(data.msg);
		else
			alert("亲，信息修改出现一些小问题，请稍后再试。");
		return;
	}
	var userId = $.urlParam('user_id');
	window.location.href = "wx-customer-info.html?user_id="+userId;
}
function onError(data, status) {
	console.log(data.msg);
	alert("修改个人信息失败");
}