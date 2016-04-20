
$('.user-form input').keypress(function (e) {
	if (e.which == 13) {
		$("#userForm_btn").click();
		return false;
	}
});

$("#userForm_btn").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#user-form').validate().form()) {
			$('#user-form').submit();
		}
	}
});

