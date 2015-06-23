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
	var secId = localStorage['sec_id'];
	$('#sec_id').val(secId);
	$('#user_id').val(data.id);
	
	$('#name').val(data.name);
	
	if (data.mobile) {
		$('#mobile').val(data.mobile);
	}
	
	console.log(data);
	console.log($('#user_id').val());
	
}

//表单提交
$('#order-submit').on('click',function() {
	alert("submit");
	var $form = $('#order-form');
	var isValid = $('.am-form').data('amui.validator').isFormValid();
	if (!isValid) {
		alert("is false");
		return ;
	}
	
	var user_id = $("user_id").val();
	var name = $("name").val();
	
	var ajaxUrl = siteAPIPath + "order/post_add.json";
	$.ajax({
        cache: true,
        type: "POST",
        url:ajaxUrl,
        data:$form.serialize(),// 你的formid
        async: false,
        error: function(request) {
            alert(" error");
        },
        success: function(data) {
           console.log(data);
        }
    });	
	
});

