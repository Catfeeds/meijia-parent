$(function() {
	
	var userId = $.urlParam('user_id');
	getUserInfo(userId);
	
	$('.form_datetime-1').datetimepicker({
		format : 'mm-dd hh:ii',
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

}

////订单支付类型触发事件
//$(":radio").click(function(){
//	console.log($('#order_money'));
//	var selected = $(this).val();
//	if (selected == "0") {
//		//不需要验证支付金额.
//		$('#order_money').prop('required', false);
//	}
//	
//	if (selected == "1") {
//		//需要验证支付金额.
//		$('#order_money').prop('required', true);
//	}
//	console.log($('#order_money'));
//});

//表单提交验证
$('#order-submit').on('click',function() {

	var $form = $('#order-form');
	var isValid = $('.am-form').data('amui.validator').isFormValid();
	if (!isValid) {
		alert("is false");
		return ;
	}
	
	//处理时间变成时间戳的问题
	var serviceDateSelect = $('#service_date_select').val();

	if (serviceDateSelect != undefined) {
		var date = new Date();
		serviceDateSelect = date.getFullYear() + "-" + serviceDateSelect;
		var date = new Date(serviceDateSelect.split(' ').join('T'))
		
		var serviceDate = date.getTime() /1000;
		$('#service_date').val(serviceDate);
		$('#start_time').val(serviceDate);
	}


	
	var ajaxUrl = siteAPIPath + "order/post_add.json";
	$.ajax({
        cache: true,
        type: "POST",
        url:ajaxUrl,
        data:$form.serialize(),// 你的formid
        async: false,
        error: function(request) {
            alert("订单提交失败，请查看你的输入是否正确!");
        },
        success: function(data) {
        	if (data.status == "999") {
        		alert(data.msg);
        		return;
        	}
        	
        	if (data.status == "0") {
        		alert("订单已经推送给用户,下一步会跳转到订单详情页面");
        	}
        }
    });	
	
});

