$('#apply-form').validate({
	
});

$("#applyForm_btn").click(function() {
	if (confirm("确认要审批吗?")) {
		$('#apply-form').submit();
		if ($('#apply-form').validate().form()) {
			$('#apply-form').submit();
		}
	}
});