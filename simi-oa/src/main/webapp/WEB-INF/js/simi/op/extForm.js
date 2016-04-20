

var formVal = $('#ext-form').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: false, // do not focus the last invalid input
	rules: {
		name : {
			required: true
		},
		
		expIn: {
			required: true,
			isFloat:true
		},
		
	},

	messages: {
		
		name : {
			required: "请输入名称"
		},
		
		expIn: {
			required: "请输入。",
			isFloat : "只能包含数字,并且精确到小数点两位"
		},
		
	},

	invalidHandler: function (event, validator) { //display error alert on form submit
		$('.alert-error', $('#dict-form')).show();
	},

	highlight: function (element) { // hightlight error inputs
		$(element)
			.closest('.form-group').addClass('has-error'); // set error class to the control group
	},

	success: function (label) {
		label.closest('.form-group').removeClass('has-error');
		label.remove();
	},

	errorPlacement: function (error, element) {
		error.insertAfter(element.parents(".col-md-5"));
	}

});

$('.exp-form input').keypress(function (e) {
	if (e.which == 13) {
		$("#extForm_btn").click();
		return false;
	}
});

$("#extForm_btn").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#ext-form').validate().form()) {
			$('#ext-form').submit();
		}
	}
});

