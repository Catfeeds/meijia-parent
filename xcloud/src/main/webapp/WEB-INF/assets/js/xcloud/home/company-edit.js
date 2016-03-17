$("#btn-edit-submit").on('click', function(e) {
	var form = $('#company-edit-form');
	
	var formValidity = $('#company-edit-form').validator().data('amui.validator').validateForm().valid
	
	if (formValidity) {
		// done, submit form
		console.log("ok");
		form.submit();
	} else  {
		// fail
		console.log("fail");
	};
});