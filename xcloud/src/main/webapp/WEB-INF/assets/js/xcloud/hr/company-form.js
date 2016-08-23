$("#btn-save").on('click', function(e) {
	
	var form = $('#company-form');
	
	var formValidity = $('#company-form').validator().data('amui.validator').validateForm().valid

	if (formValidity) {
		// done, submit form
		console.log("ok");
		form.submit();
	} else {
		// fail
		console.log("fail");
	}
	;
});
