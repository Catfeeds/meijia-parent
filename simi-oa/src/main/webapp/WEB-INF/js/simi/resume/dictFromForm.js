$('#contentForm').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: false, // do not focus the last invalid input
	rules: {
		
		name: {
			required: true
		},
		
		webUrl: {
			required: true
		},
		
		
		



	},

	messages: {
		name: {
			required: "必填项"
		},
		
		webUrl: {
			required: "必填项"
		},
		
		

	},

	invalidHandler: function (event, validator) { //display error alert on form submit
		$('.alert-error', $('#contentForm')).show();
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

$('.contentForm input').keypress(function (e) {
	if (e.which == 13) {
		$("#btn-save").click();
		return false;
	}
});

$("#btn-save").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#contentForm').validate().form()) {
			$('#contentForm').submit();
		}
	}
});

