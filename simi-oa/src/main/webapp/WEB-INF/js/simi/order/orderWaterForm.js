var formVal = $('#order-water-view-form').validate({
	errorElement : 'span', //default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	ignore: [],
	rules : {
		
		orderMoney: {
			required: true,
			isPrice:true,
		},
		
		orderPay: {
			required: true,
			isPrice:true,
		},
		
		addrId: {
			required: true,
		},
		
		linkMan: {
			required: true,
		},
		
		linkTel: {
			required: true,
		},
			
	},

	messages : {
		
		orderMoney: {
			required: "请输入总金额",
			isPrice: "金额只能包含数字,并且精确到小数点两位",
		},
		
		orderPay: {
			required: "请输入支付金额",
			isPrice: "金额只能包含数字,并且精确到小数点两位",
		},
		
		addrId: {
			required: "请选择服务地址.",
		},
		
		linkMan: {
			required: "请输入联系人.",
		},
		
		linkTel: {
			required: "请输入联系电话",
		},
	},

	invalidHandler : function(event, validator) { //display error alert on form submit
		$('.alert-error', $('#order-water-view-form')).show();
	},

	highlight : function(element) { // hightlight error inputs
		$(element).closest('.form-group').addClass('has-error'); // set error class to the control group
	},

	success : function(label) {
		label.closest('.form-group').removeClass('has-error');
		label.remove();
	},

	errorPlacement : function(error, element) {
		error.insertAfter(element.parents(".col-md-5"));
	}

});

var formValp = $('#order-water-partner-form').validate({
	errorElement : 'span', //default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	ignore: [],
	rules : {

		partnerId : {
			required: true,
		},
		
		partnerOrderMoney: {
			required: true,
			number:true,
		},
	},

	messages : {
		
		partnerId : {
			required: "请选择服务商",
		},
		partnerOrderMoney: {
			required: "请输入价格",
			number: "请输入正确的价格数字",
		},
	},

	invalidHandler : function(event, validator) { //display error alert on form submit
		$('.alert-error', $('#order-water-view-form')).show();
	},

	highlight : function(element) { // hightlight error inputs
		$(element).closest('.form-group').addClass('has-error'); // set error class to the control group
	},

	success : function(label) {
		label.closest('.form-group').removeClass('has-error');
		label.remove();
	},

	errorPlacement : function(error, element) {
		error.insertAfter(element.parents(".col-md-5"));
	}

});

$("#btn_submit").click(function() {
	if (confirm("确认要保存订单信息吗?")) {
		if ($('#order-water-view-form').validate().form()) {
			
			$('#order-water-view-form').submit();
		}
	}
});

$("#btn_submit_partner").click(function() {
	if (confirm("确认要保存订单信息吗?")) {
		if ($('#order-water-partner-form').validate().form()) {
			
			$('#order-water-partner-form').submit();
		}
	}
});
