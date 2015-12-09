$('#channel-form').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: false, // do not focus the last invalid input
	rules: {
		
		name: {
			required: true
		}
	},

	messages: {
	
		name: {
			required: "请输入频道名称。"
		}
	},

	invalidHandler: function (event, validator) { //display error alert on form submit
		$('.alert-error', $('#channel-form')).show();
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

$('.channel-form input').keypress(function (e) {
	if (e.which == 13) {
		$("#addstaff_btn").click();
		return false;
	}
});

