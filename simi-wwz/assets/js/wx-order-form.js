$(function() {
	
	var userId = $.urlParam('user_id');
	getUserInfo(userId);
	
	$('.form_datetime-1').datetimepicker({
		format : 'yyyy-mm-dd hh:ii',
		todayBtn : true,
		todayHighlight : true,
		language : 'zh-CN',
		pickerPosition : 'top-left',
		minuteStep : 30,
		autoclose : true
	})
})


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

	formSetInfoSuccess(data.data);
}

function formSetInfoSuccess(data) {
	$('#user_id').val(data.id);
	
	
	$('#name').val(data.name);
	
	if (data.mobile) {
		$('#mobile').val(data.mobile);
	}
}

//表单提交
$('#order_sumbit').on('click',function() {
	alert("submit");
	$('#order_submit').submit();
})

