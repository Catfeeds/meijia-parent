$(function() {
	var userId = $.urlParam('user_id');
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
console.log(data);
	formSetInfoSuccess(data.data);
}

function formSetInfoSuccess(data) {
	$("#head_img").attr("src",data.head_img);
	$("#name").text(data.name);
	var sex = data.sex;
	if(sex == 0){
		$("#sex").text("先生");
	}else{
		$("#sex").text("女士");
	}
	$("#mobile").text(data.mobile);
	$("#province_name").text(data.province_name);
	$("#rest_money").text(data.rest_money);
	$("#score").text(data.score);
	var userType,userFrom;
	var user_type = data.user_type;
	var user_from = data.user_from;
	if(user_type==0){
		userType = "普通用户";
	}else if(user_type==1){
		userType = "代理商";
	}else{
		userType ="";
	}
	if(user_from==0){
		userFrom = "APP";
	}else if(user_from == 1){
		userFrom = "微网站";
	}else if(user_from = 2){
		userFrom = "管理后台";
	}else{
		userFrom = "";
	}
	$("#user_type").text(userType);
	$("#user_from").text(userFrom);
}


function goToOrderForm() {
	var userId = $.urlParam('user_id');
	location.href = "wx-order-form.html?user_id=" + userId;
}

function goToOrderList() {
	var userId = $.urlParam('user_id');
	location.href = "wx-order-list.html?user_id=" + userId;
}
//wx-order-form.html
$("#coustomer_info_edit").bind("click",function(){
	var userId = $.urlParam('user_id');
	location.href="wx-customer-info-edit.html?user_id="+ userId;
});
