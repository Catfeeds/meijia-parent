$('#drag').drag();

$("#login-btn").on('click', function(e) {
	
	var form = $('#login-form');
	
	var formValidity = $('#login-form').validator().data('amui.validator').validateForm().valid

	if (formValidity) {
		// done, submit form
		console.log("ok");
		
		
		var dragText = $(".drag_text").html();
		if (dragText != '验证通过') {
			console.log("drag fail");
			return false;
		}

		form.submit();
	} else {
		// fail
		console.log("fail");
		
	}
	;
});
