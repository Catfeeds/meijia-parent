

var formVal = $('#group-form').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: false, // do not focus the last invalid input
	rules: {
		name : {
			required: true
		},
	
	},

	messages: {
		
		name : {
			required: "输入名称"
		},

	},

	invalidHandler: function (event, validator) { //display error alert on form submit
		$('.alert-error', $('#group-form')).show();
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

$('.group-form input').keypress(function (e) {
	if (e.which == 13) {
		$("#save_btn").click();
		return false;
	}
});



$("#save_btn").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#group-form').validate().form()) {
			$('#group-form').submit();
		}
	}
});

