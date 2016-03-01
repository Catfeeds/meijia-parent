var formVal = $('#order-green-view-form').validate({
	errorElement : 'span', //default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	ignore: [],
	rules : {
		
		partnerOrderMoney: {
			required: true,
			number:true,
		},
		orderPay: {
			required: "请输入价格",
			number: "请输入正确的价格数字",
			
		},
		
		orderMoney: {
			required: "请输入价格",
			number: "请输入正确的价格数字",
		},
	
	
	},

	messages : {
		partnerOrderMoney: {
			required: "请输入价格",
			number: "请输入正确的价格数字",
			
		},
		orderPay: {
			required: "请输入价格",
			number: "请输入正确的价格数字",
			
		},
		orderMoney: {
			required: "请输入价格",
			number: "请输入正确的价格数字",
			
		},
	},

	invalidHandler : function(event, validator) { //display error alert on form submit
		$('.alert-error', $('#order-green-view-form')).show();
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

$('.order-green-view-form input').keypress(function(e) {
	if (e.which == 13) {
		$("#btn_submit").click();
		return false;
	}
});

$("#btn_submit").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#order-green-view-form').validate().form()) {
			
			$('#order-green-view-form').submit();
		}
	}
});

